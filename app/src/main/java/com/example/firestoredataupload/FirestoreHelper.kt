package com.example.firestoredataupload

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirestoreHelper {

    // Firestore 인스턴스 생성
    private val db = FirebaseFirestore.getInstance()

    // 데이터를 Firestore에 저장하는 함수
    suspend fun addPlaces() {
        val places = listOf(
            mapOf(
                "name" to "강아지봄",
                "address" to "경기 용인시 수지구 심곡로 87 수지 제일 아이조움",
                "category" to "강아지 미용실",
                "latitude" to 37.3107,
                "longitude" to 127.0814,
                "images" to listOf(
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDAxMTBfMTc5%2FMDAxNzA0ODYyNDQ3MDA1.v4hmzrNavkks_lrGTG8PTC7I9E0UQ0EJTA68CKoZ45gg.4o2rlq3gEuwsqQWzsqL7nXnqtjOjARzDWSokO7whUOkg.JPEG.zonever7%2F20240104_170033.jpg",
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDAxMTBfMTIy%2FMDAxNzA0ODYyNzQ2MjY4.Vbw_z60vJju3MxvsXH0GPFohQyx4JMqbhW9TOh4kVtwg.dZ4UTUo3l8SbdWchgj7pstqZ2GSTPv0sA4Le64G_kGsg.JPEG.zonever7%2F20240104_170204.jpg"
                ),
                "price" to "목욕 2만원, 미용 3만원",
                "url" to "https://naver.me/FWJPzce2",
                "phone" to "0507-1416-4131"
            ),
            mapOf(
                "name" to "웰니스동물병원",
                "address" to "경기 용인시 수지구 성복2로 38 롯데몰 수지점 B1층 웰니스동물병원",
                "category" to "동물 병원",
                "latitude" to 37.3134,
                "longitude" to 127.0816,
                "images" to listOf(
                    "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20190906_152%2F1567739082250DiFyU_JPEG%2FAeav2lhKH38lA0mQfDuaVLdA.jpeg.jpg",
                    "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20190906_137%2F1567739052949mMaAu_JPEG%2FTS4Qx0mDh5FXZZlcAzXTggR3.jpeg.jpg"
                ),
                "price" to "5kg미만 호텔 30,000원, 기타 문의",
                "url" to "https://naver.me/56I9ipOj",
                "phone" to "0507-1373-4030"
            )
        )

        // Firestore에 중복 확인 후 저장
        for (place in places) {
            val name = place["name"] as String
            val address = place["address"] as String

            // Firestore에서 동일한 name과 address가 있는지 확인
            val querySnapshot = db.collection("places")
                .whereEqualTo("name", name)
                .whereEqualTo("address", address)
                .get()
                .await()

            if (querySnapshot.isEmpty) {
                // 데이터가 없으면 Firestore에 저장
                db.collection("places").add(place)
                    .addOnSuccessListener { documentReference ->
                        println("DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        println("Error adding document: $e")
                    }
            } else {
                println("Duplicate found for: $name, skipping insertion.")
            }
        }
    }
}
