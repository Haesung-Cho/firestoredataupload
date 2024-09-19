package com.example.firestoredataupload

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()

    fun addPlace(name: String, address: String, category: String, latitude: Double, longitude: Double, price: String, url: String, images: List<String>) {
        val place = hashMapOf(
            "name" to name,
            "address" to address,
            "category" to category,
            "latitude" to latitude,
            "longitude" to longitude,
            "price" to price,
            "url" to url,
            "images" to images
        )

        // Firestore에 장소 데이터 추가
        db.collection("places").add(place)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding document: $e")
            }
    }
}
