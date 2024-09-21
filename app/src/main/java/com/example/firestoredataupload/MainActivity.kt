package com.example.firestoredataupload

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 코루틴을 사용하여 FirestoreHelper.addPlacesFromJson() 호출
        CoroutineScope(Dispatchers.IO).launch {
            try {
                FirestoreHelper.addPlacesFromJson(this@MainActivity)
                withContext(Dispatchers.Main) {
                    Log.d("Firestore", "Data upload successful")
                }
            } catch (e: Exception) {
                Log.e("FirestoreError", "Error during data upload", e)
            }
        }
    }
}