package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class jAchievement : AppCompatActivity() {

    lateinit var jStrView: TextView
    lateinit var jTodayView: TextView
    lateinit var jImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_j_achievement)

        var jAchPercent = intent.getDoubleExtra("jPercentKey", -1.0)


        jStrView = findViewById<TextView>(R.id.jStr) //결과 표시 텍스트의 id
        jImageView = findViewById<ImageView>(R.id.jImage) //결과 표시 이미지의 id
        jTodayView = findViewById<TextView>(R.id.jToday) //"오늘의 하루는" 표시 텍스트의 id
        jTodayView.text = "오늘 J의 하루는 ..."

        //글자로 출력
        when {
            jAchPercent >= 80 -> jStrView.text = "Perfect"
            jAchPercent >= 30 -> jStrView.text = "Good"
            jAchPercent >= 0 -> jStrView.text = "Bad"
            jAchPercent == -1.0 -> jStrView.text = "달성률이 계산되지 않았습니다."

        }

        //이미지 출력
        when {
            jAchPercent >= 80 ->
                jImageView.setImageResource(R.drawable.j_perfect)
            jAchPercent >= 30 ->
                jImageView.setImageResource(R.drawable.j_good)
            jAchPercent > 0 ->
                jImageView.setImageResource(R.drawable.j_bad)
        }

    }
}