package com.example.firestoredataupload

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 강아지 봄 데이터 추가
        FirestoreHelper.addPlace(
            name = "강아지봄",
            address = "경기 용인시 수지구 심곡로 87 수지 제일 아이조움",
            category = "강아지 미용실",
            latitude = 37.310764,
            longitude = 127.081431,
            price = "목욕 2만원, 미용 3만원",
            url = "https://naver.me/FWJPzce2",
            images = listOf(
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDAxMTBfMTc5%2FMDAxNzA0ODYyNDQ3MDA1.v4hmzrNavkks_lrGTG8PTC7I9E0UQ0EJTA68CKoZ45gg.4o2rlq3gEuwsqQWzsqL7nXnqtjOjARzDWSokO7whUOkg.JPEG.zonever7%2F20240104_170033.jpg",
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDAxMTBfMTIy%2FMDAxNzA0ODYyNzQ2MjY4.Vbw_z60vJju3MxvsXH0GPFohQyx4JMqbhW9TOh4kVtwg.dZ4UTUo3l8SbdWchgj7pstqZ2GSTPv0sA4Le64G_kGsg.JPEG.zonever7%2F20240104_170204.jpg"
            )
        )

        // 김밥천국 데이터 추가
        FirestoreHelper.addPlace(
            name = "김밥천국",
            address = "경기 용인시 수지구 포은대로231",
            category = "음식점",
            latitude = 37.3112,  // 예시 좌표
            longitude = 127.0814,  // 예시 좌표
            price = "김밥 3,000원, 라면 4,000원",
            url = "https://example.com/kimbap",
            images = listOf(
                "https://example.com/image1.jpg",
                "https://example.com/image2.jpg"
            )
        )
    }
}