package com.example.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_intent.*
import org.w3c.dom.Text

class IntentActivity : AppCompatActivity() {

    private val REQUEST_NUM: Int = 200;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        naver_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"))
            startActivity(intent)
        }

        daum_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.daum.net/"))
            startActivity(intent)
        }

        intent_btn1.setOnClickListener {
            val intent = Intent(this@IntentActivity,IntentActivity2::class.java)
            val text1 : EditText = findViewById<EditText>(R.id.user_name)
            val text2 : EditText = findViewById<EditText>(R.id.user_password)

            intent.apply{
                this.putExtra("name",text1.text.toString())
                this.putExtra("number",text2.text.toString())

            }
            //이렇게 보내면 어디서 요청을 보냈는지 구분을 할 수 없음
            //startActivity(intent)

            //이 엑티비티에서 요청을 보낸면 requestCode 는 200번으로 온다.
            startActivityForResult(intent,REQUEST_NUM)
        }

        next_btn2.setOnClickListener {
            val intent = Intent(this@IntentActivity,FragmentActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 200){
            if(resultCode == Activity.RESULT_OK){
                val result = data?.getStringExtra("result")
                Toast.makeText(this@IntentActivity, result,Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@IntentActivity,"Fail",Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
