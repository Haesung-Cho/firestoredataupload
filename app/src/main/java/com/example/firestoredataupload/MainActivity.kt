package com.example.firestoredataupload

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.firestoredataupload.FirestoreHelper.addPlaces
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 코루틴을 사용하여 FirestoreHelper.addPlaces() 호출
        CoroutineScope(Dispatchers.IO).launch {
            FirestoreHelper.addPlaces()
        }
    }
}
