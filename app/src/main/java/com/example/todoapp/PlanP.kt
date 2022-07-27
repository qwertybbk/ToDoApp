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
import android.widget.ListView
import android.widget.*
import kotlinx.android.synthetic.main.activity_plan_p.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PlanP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_p)
        listview1.choiceMode = ListView.CHOICE_MODE_SINGLE
        val items = ArrayList<String>()
        val arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_single_choice,
                items
        )
        listview1.adapter = arrayAdapter
        listview1.setOnItemClickListener { parent, view, position, id ->
            val check = listview1.checkedItemPosition
            items[check] = "" + "done"
            // textview1.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            Toast.makeText(this, "you can do it", Toast.LENGTH_SHORT).show()
            arrayAdapter.notifyDataSetChanged()
        }

        //setTextColor(BLUE)

        button1.setOnClickListener() {
            items.add("" + editText1.text) //추가
            arrayAdapter.notifyDataSetChanged() //배열어댑터에게 데이터정보가 변경되었음을 알림
        }

        /*button2.setOnClickListener(){

            //setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG)
            arrayAdapter.notifyDataSetChanged()
        }*/

        button3.setOnClickListener {

            val check = listview1.checkedItemPosition
            if (check > -1) {
                items.removeAt(check) //삭제
                listview1.clearChoices() // 지워진 정보에 대해서 수정이 일어나지 않도록 방지하기위해
                arrayAdapter.notifyDataSetChanged()
            }
        }   //괄호가 밑에 있어서 안되나 해서 추가해봤습니다!
        button4.setOnClickListener(){
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