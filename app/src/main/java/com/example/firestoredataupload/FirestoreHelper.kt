package com.example.firestoredataupload

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.content.Context
import com.google.android.libraries.places.api.model.Place
import com.google.gson.Gson
import java.io.InputStreamReader

data class PlaceData(
    val name: String,
    val address: String,
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val images: List<String>,
    val price: String,
    val url: String,
    val phone: String,
    val time: String
)

object FirestoreHelper {

    private val db = FirebaseFirestore.getInstance()

    // JSON 파일에서 데이터를 읽어 Firestore에 추가하는 함수
    suspend fun addPlacesFromJson(context: Context) {
        val inputStream = context.assets.open("places.json")
        val reader = InputStreamReader(inputStream)
        val places = Gson().fromJson(reader, Array<PlaceData>::class.java).toList()

        for (place in places) {
            val existingPlace = checkForDuplicate(place)  // 중복 체크
            if (existingPlace == null) {
                addPlaceToFirestore(place)
            }
        }
    }

    // Firestore에서 중복된 장소가 있는지 확인하는 함수
    private suspend fun checkForDuplicate(place: PlaceData): PlaceData? {
        val querySnapshot = db.collection("places")
            .whereEqualTo("name", place.name)
            .whereEqualTo("address", place.address)
            .get()
            .await()

        return if (querySnapshot.documents.isNotEmpty()) {
            querySnapshot.documents[0].toObject(PlaceData::class.java)
        } else {
            null
        }
    }

    // Firestore에 장소 데이터를 저장하는 함수
    private suspend fun addPlaceToFirestore(place: PlaceData) {
        db.collection("places")
            .add(place)
            .await()
    }
}

