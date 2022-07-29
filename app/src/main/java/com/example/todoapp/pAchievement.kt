package com.example.todoapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class pAchievement : AppCompatActivity() {

    lateinit var resultTextView: TextView
    lateinit var todayTextView: TextView
    lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)


        var achPercent= intent.getDoubleExtra("pPercentKey",-1.0)


        resultTextView=findViewById<TextView>(R.id.pStr) //결과 표시 텍스트의 id
        imageView=findViewById<ImageView>(R.id.pImage) //결과 표시 이미지의 id
        todayTextView=findViewById<TextView>(R.id.pToday) //"오늘의 하루는" 표시 텍스트의 id
        todayTextView.text = "오늘 P의 하루는 ..."

        //글자로 출력
        when{
            achPercent >= 80 -> resultTextView.text = "Perfect"
            achPercent >= 30 -> resultTextView.text = "Good"
            achPercent >= 0 -> resultTextView.text = "Bad"
            achPercent == -1.0 -> resultTextView.text = "달성률이 계산되지 않았습니다."

        }

        //이미지 출력
        when{
            achPercent >= 80 ->
                imageView.setImageResource(R.drawable.p_perfect)
            achPercent >= 30 ->
                imageView.setImageResource(R.drawable.p_good)
            achPercent > 0 ->
                imageView.setImageResource(R.drawable.p_bad)
        }

    }
}