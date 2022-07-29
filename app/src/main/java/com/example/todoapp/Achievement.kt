package com.example.todoapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Achievement : AppCompatActivity() {

    lateinit var resultTextView: TextView
    lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)


        var achPercent= intent.getIntExtra("achPercent",-1)


        resultTextView=findViewById<TextView>(R.id.ach_str) //결과 표시 텍스트의 id
        imageView=findViewById<ImageView>(R.id.ach_image) //결과 표시 이미지의 id

        //글자로 출력
        when{
            achPercent >= 80 -> resultTextView.text = "Perfect"
            achPercent >= 30 -> resultTextView.text = "Good"
            achPercent > 0 -> resultTextView.text = "Bad"
        }

        //이미지 출력
        when{
            achPercent >= 80 ->
                imageView.setImageResource(R.drawable.j_perfect)
            achPercent >= 30 ->
                imageView.setImageResource(R.drawable.j_good)
            achPercent > 0 ->
                imageView.setImageResource(R.drawable.j_bad)

        }

    }
}