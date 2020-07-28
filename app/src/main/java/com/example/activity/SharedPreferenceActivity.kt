package com.example.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_shared_preference.*

class SharedPreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)

        //SharedPreference : 데이터들을 공유
        //Mode
        //MODE_PRIVATE : 생성한 application에서만 사용 가능(우리가 만든 앱에서만)<거의 이거 사용함>
        //MODE_WORLD_READABLE:다른 APPLICATION 사용가능 -> 읽을수만 있다
        //MODE_WORLD_WRITEABLE:다른 APPLICATION 사용가능 -> 읽기/ 쓰기 가능
        //MODE_MULTI_PROCESS : 이미 호출되어 사용중인지 체크
        //MODE_APPEND : 기존 preference에 신규로 추가
        //val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)

        pre_save_btn.setOnClickListener{
            val sharedPreference1 = getSharedPreferences("stoarge1", Context.MODE_PRIVATE)//생성한 어플리케이션 내에서 사용
            val editor1: SharedPreferences.Editor = sharedPreference1.edit()
            editor1.putString("dataOne","data1")
            editor1.putString("dataTwo","data2")

            val sharedPreference2 = getSharedPreferences("stoarge2", Context.MODE_PRIVATE)//생성한 어플리케이션 내에서 사용
            val editor2: SharedPreferences.Editor = sharedPreference2.edit()
            editor2.putString("dataOne","data3")
            editor2.putString("dataTwo","data4")

            editor1.commit()
            editor2.commit()
        }

        //각각의 저장소마다 name이 붙어서 저장소가 생성이 되고
        //각각 이름이 다른 저장소에 같은 키값을 가진 데이터들이 존대 할 수 있다.
        // stoarge1->Sharedpreference
        // (Key,Value) -> ("dataOne","data1")
        // stoarge2->Sharedpreference
        // (Key,Value) -> ("dataTwo","data2")

        pre_load_btn.setOnClickListener{
            //sharedPreference에 값을 불러오는 방법
            val sharedPreference1 = getSharedPreferences("stoarge1",Context.MODE_PRIVATE)
            val sharedPreference2 = getSharedPreferences("stoarge2",Context.MODE_PRIVATE)
            val value1 = sharedPreference1.getString("dataOne","데이터 없음")
            val value2 = sharedPreference1.getString("dataTwo","데이터 없음")

            val value3 = sharedPreference2.getString("dataOne","데이터 없음")
            val value4 = sharedPreference2.getString("dataTwo","데이터 없음")

            Log.d("key-value",value1)
            Log.d("key-value",value2)
            Log.d("key-value",value3)
            Log.d("key-value",value4)
        }

        pre_delete_btn.setOnClickListener{
            val sharedPreference1 = getSharedPreferences("stoarge1", Context.MODE_PRIVATE)//생성한 어플리케이션 내에서 사용
            val editor1: SharedPreferences.Editor = sharedPreference1.edit()

            val sharedPreference2 = getSharedPreferences("stoarge2", Context.MODE_PRIVATE)//생성한 어플리케이션 내에서 사용
            val editor2: SharedPreferences.Editor = sharedPreference2.edit()

            editor1.remove("dataOne")
            editor1.commit()

            editor2.remove("dataOne")
            editor2.commit()
        }

        pre_delete_all_btn.setOnClickListener{
            val sharedPreference1 = getSharedPreferences("stoarge1", Context.MODE_PRIVATE)//생성한 어플리케이션 내에서 사용
            val editor1: SharedPreferences.Editor = sharedPreference1.edit()

            val sharedPreference2 = getSharedPreferences("stoarge2", Context.MODE_PRIVATE)//생성한 어플리케이션 내에서 사용
            val editor2: SharedPreferences.Editor = sharedPreference2.edit()

            editor1.clear()
            editor1.commit()

            editor2.clear()
            editor2.commit()
        }

        next_async.setOnClickListener {
            val intent = Intent(this@SharedPreferenceActivity,AsyncActivity::class.java)
            startActivity(intent)
        }

    }
}
