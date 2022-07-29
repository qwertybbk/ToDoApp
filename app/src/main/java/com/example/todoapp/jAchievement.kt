package com.example.todoapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class jAchievement : AppCompatActivity() {

    lateinit var resultTextView2: TextView
    lateinit var todayTextView2: TextView
    lateinit var imageView2: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)


        var achPercent= intent.getDoubleExtra("jPercentKey",-1.0)


        resultTextView2=findViewById<TextView>(R.id.jStr) //결과 표시 텍스트의 id
        imageView2=findViewById<ImageView>(R.id.jImage) //결과 표시 이미지의 id
        todayTextView2=findViewById<TextView>(R.id.jToday) //"오늘의 하루는" 표시 텍스트의 id
        todayTextView2.text = "오늘 J의 하루는 ..."

        //글자로 출력
        when{
            achPercent >= 80 -> resultTextView2.text = "Perfect"
            achPercent >= 30 -> resultTextView2.text = "Good"
            achPercent >= 0 -> resultTextView2.text = "Bad"
            achPercent == -1.0 -> resultTextView2.text = "달성률이 계산되지 않았습니다."

        }

        //이미지 출력
        when{
            achPercent >= 80 ->
                imageView2.setImageResource(R.drawable.j_perfect)
            achPercent >= 30 ->
                imageView2.setImageResource(R.drawable.j_good)
            achPercent > 0 ->
                imageView2.setImageResource(R.drawable.j_bad)
        }

    }
}