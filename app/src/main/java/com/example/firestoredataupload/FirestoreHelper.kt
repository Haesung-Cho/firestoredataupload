package com.example.firestoredataupload

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader


data class PlaceData(
    val name: String,
    val address: String,
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val images: List<String>? = emptyList(),
    val price: String? = null,
    val url: String? = null,
    val phone: String? = null,
    val time: String? = null,
    val instagram: String? = null
)

object FirestoreHelper {

    private val db = FirebaseFirestore.getInstance()

    // JSON 파일에서 데이터를 읽어 Firestore에 추가 또는 업데이트하는 함수
    suspend fun addPlacesFromJson(context: Context) {
        val inputStream = context.assets.open("places.json")
        val reader = InputStreamReader(inputStream)
        val places = Gson().fromJson(reader, Array<PlaceData>::class.java).toList()

        for (place in places) {
            val existingPlace = checkForDuplicate(place)  // 중복 체크
            if (existingPlace == null) {
                addPlaceToFirestore(place)  // 중복 없으면 새로 추가
            } else {
                updatePlaceInFirestore(place, existingPlace.id)  // 중복 있으면 업데이트
            }
        }
    }

    // Firestore에서 중복된 장소가 있는지 확인하는 함수
    private suspend fun checkForDuplicate(place: PlaceData): PlaceDocument? {
        val querySnapshot = db.collection("places")
            .whereEqualTo("name", place.name)
            .whereEqualTo("address", place.address)
            .get()
            .await()

        return if (querySnapshot.documents.isNotEmpty()) {
            querySnapshot.documents[0].toObject(PlaceDocument::class.java)?.apply {
                id = querySnapshot.documents[0].id
            }
        } else {
            null
        }
    }

    // Firestore에 새로운 장소 데이터를 저장하는 함수
    private suspend fun addPlaceToFirestore(place: PlaceData) {
        db.collection("places")
            .add(place)
            .await()
    }

    // Firestore에 이미 존재하는 장소의 데이터를 업데이트하는 함수
    private suspend fun updatePlaceInFirestore(newPlaceData: PlaceData, documentId: String) {
        val placeUpdates = hashMapOf<String, Any>()
            // null 체크 후 업데이트할 값만 추가
            newPlaceData.price?.let { placeUpdates["price"] = it }
                    newPlaceData.time?.let { placeUpdates["time"] = it }
                    newPlaceData.url?.let { placeUpdates["url"] = it }
                    newPlaceData.phone?.let { placeUpdates["phone"] = it }
                    newPlaceData.instagram?.let { placeUpdates["instagram"] = it }

        // 이미지 배열 업데이트
        if (newPlaceData.images?.isNotEmpty() == true) {
            placeUpdates["images"] = newPlaceData.images
        }

        // 장소 정보를 Firestore에 업데이트
        db.collection("places")
            .document(documentId)
            .set(placeUpdates, com.google.firebase.firestore.SetOptions.merge())
            .await()
    }

    // PlaceDocument 데이터 클래스 (문서 ID 포함)
    data class PlaceDocument(
        var id: String = "",
        val name: String = "",
        val address: String = ""
    )
}