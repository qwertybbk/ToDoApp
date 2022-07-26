package com.example.todoapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

class percent : AppCompatActivity() {
    //생성한 위젯들 변수
    lateinit var edittextDone : EditText
    lateinit var edittextList : EditText
    lateinit var Successbtn : Button
    lateinit var resultValue : TextView
    // 입력받은 값 저장할 변수
    lateinit internal var num1: String
    lateinit internal var num2: String
    internal var result: Double? = null

    internal var numbuttons = ArrayList<Button>(10)
    internal var numbtns = arrayOf(
        R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5,
        R.id.num6, R.id.num7, R.id.num8, R.id.num9
    )
    internal var I: Int = 0 //이건 모르겠음.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_percent)
        //연결
        edittextDone = findViewById(R.id.edittextDone)
        edittextList = findViewById(R.id.edittextList)
        Successbtn = findViewById(R.id.Successbtn)
        resultValue = findViewById(R.id.value)

        // num1 : 달성한 일, num2 : 전체할일
        Successbtn.setOnTouchListener { view, motionEvent ->
            num1 = edittextDone.text.toString()    // string으로 받아옴
            num2 = edittextList.text.toString()

            var num3 : Double = num1.toDouble() // double로 바꿈
            var num4 : Double = num2.toDouble()

            result = num3 / num4 * 100  // double로 바꾼거 계산
            var Finalresult = result!!.roundToInt()  // double로 바꾼거를 int로 바꿈
            resultValue.text = "오늘 하루 달성률 " + Finalresult.toString() + "%"
            //resultValue.textColors(ContextCompat.getColor(!!, R.color.))
            false
        }


        //0~9까지 버튼 누르는 곳
        for (i in 0..9) {
            numbuttons.add(findViewById(numbtns[i]))
        }
        println(numbtns.size)

        for (i in 0..numbtns.size - 1) {
            numbuttons[i].setOnClickListener {
                if (edittextDone.isFocused == true) {
                    num1 = edittextDone.text.toString() + numbuttons[i].getText().toString()
                    edittextDone.setText(num1)
                } else if (edittextList.isFocused == true) {
                    num2 = edittextList.text.toString() + numbuttons[i].getText().toString()
                    edittextList.setText(num2)
                } else {
                    Toast.makeText(applicationContext, "먼저 텍스트를 선택", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 달성률 화면이랑 연결되는 부분
        // 제작할때 혹시 몰라서 넣은거라 성은님이 생각하신 연결방식으로 변경하셔도 상관없습니다!
        // 버튼말고 메뉴등으로 성은님 편하신 방법대로 넣으시면 될것 같습니다!!
/*      val go_JyPIntent = findViewById(R.id.CheckBtn) as Button
        go_JyPIntent.setOnClickListener{
            val intent = Intent(this, 달성률화면넣으시면 될것같습니다!::class.java)
            startActivity(intent)
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item?.itemId) {
                R.id.plan_j -> {
                    val intent = Intent(this, PlanJ::class.java)
                    startActivity(intent)
                    return true
                }
            }
            return super.onOptionsItemSelected(item)
        }

}