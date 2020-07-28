package com.example.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TokenWatcher
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity(),FragmentSegment.OnDatapassListener {

    override fun onDataPass(data: String?) {
        //데이터는 넘어오지만 toast메세지 같이 fragment activity에 직접 접근하는 것은 안됨
        Log.d("pass", "" + data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        Log.d("life_cycle","onCreate")


        val fragmentTest : FragmentSegment = FragmentSegment()

        //bundle객체는 fragment에 데이터를 보내주는 기능을 한다
        val bundle: Bundle = Bundle()
        bundle.putString("checkMessage","send_data")
        fragmentTest.arguments = bundle

        //fragment를 동적으로 생성을 하는 방법(xml로 fragment 생성하는 것은 정적인 방법)
        pop_btn.setOnClickListener {
            val fragmentManager: FragmentManager = supportFragmentManager//엑티비티가 가지고 있음

            //실질적으로 동작을 하는 부분
            //이미 fragment가 붙어 있으면 더이상 붙히지는 않음
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.segment,fragmentTest)
            fragmentTransaction.replace(R.id.segment,fragmentTest)
            //commit -> 시간 될 때 해(좀더 안정적)
            //commitnow -> 지금당장해
            fragmentTransaction.commit()
        }

        delete_btn.setOnClickListener {
            //프래그먼트 remove/detach 하는 방법 //둘은 조금 다름
            //이 부분까지는 생성부분과 동일
            //(manager 객체 만들고 -> 트렌젝션 생성)
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.detach(fragmentOne) // 다시 붙히지는 않음
            fragmentTransaction.remove(fragmentTest) //다시 붙히기 가능
            fragmentTransaction.commit()
        }

        next_btn3.setOnClickListener {
            val intent = Intent(this@FragmentActivity,ThreadActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("life_cycle","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("life_cycle","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("life_cycle","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("life_cycle","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("life_cycle","onDestroy")
    }
}
