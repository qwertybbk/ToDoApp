package com.example.todoapp


import android.app.TimePickerDialog
import android.graphics.Color.MAGENTA
import android.graphics.Color.RED
import android.graphics.Paint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.hardware.camera2.params.RggbChannelVector
import android.hardware.camera2.params.RggbChannelVector.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.*
import androidx.core.graphics.toColorInt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_plan_p.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PlanP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_p)

        listview11.choiceMode =ListView.CHOICE_MODE_SINGLE
        val items= ArrayList<String>()
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            items
        )
        listview11.adapter = arrayAdapter
        listview11.setOnItemClickListener{parent, view, position, id ->
            textview11.text = listview11.getItemAtPosition(position)as CharSequence}

        button11.setOnClickListener(){
            items.add(""+editText11.text) //추가
            arrayAdapter.notifyDataSetChanged() //배열어댑터에게 데이터정보가 변경되었음을 알림
        }


        button33.setOnClickListener(){
            val check = listview11.checkedItemPosition
            if(check >-1){
                items.removeAt(check) //삭제
                listview11.clearChoices() // 지워진 정보에 대해서 수정이 일어나지 않도록 방지하기위해
                arrayAdapter.notifyDataSetChanged()
            }
        }

        button44.setOnClickListener(){
            items.clear() //초기화
            arrayAdapter.notifyDataSetChanged()
        }


        val mPickTimeBtn = findViewById<Button>(R.id.StartTimeBtn)
        val ePickTimeBtn = findViewById<Button>(R.id.EndTimeBtn)
        val starttextView = findViewById<TextView>(R.id.timeStart)
        val endtextView = findViewById<TextView>(R.id.timeEnd)

        //시작시간 버튼
        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                starttextView.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        //끝나는 시간 버튼
        ePickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                endtextView.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

    }

}