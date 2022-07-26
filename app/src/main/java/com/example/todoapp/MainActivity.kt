package com.example.todoapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    lateinit var buttonLink: Button
    lateinit var imageJ: ImageView
    lateinit var imageP: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageJ = findViewById(R.id.imageJ)
        imageP = findViewById(R.id.imageP)
        buttonLink = findViewById(R.id.buttonLink)

        imageJ.setOnClickListener {
            val intent = Intent(this, PlanJ::class.java)
            startActivity(intent)
        }

        imageP.setOnClickListener {
            val intent = Intent(this, PlanP::class.java)
            startActivity(intent)
        }

        buttonLink.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ko/%EB%AC%B4%EB%A3%8C-%EC%84%B1%EA%B2%A9-%EC%9C%A0%ED%98%95-%EA%B2%80%EC%82%AC"))
            startActivity(intent)
        }
    }
}