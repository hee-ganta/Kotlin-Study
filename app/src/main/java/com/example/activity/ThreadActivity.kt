package com.example.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_thread.*

class ThreadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        var percent1 : Int = 0
        var percent2 : Int = 0
        var percent3 : Int = 0

        //thread생성 방법1 <runnable 따로 만들어서 thread생성자에 건내주기>
        //runOnUiThread -> mianthread에 변동이 있을 때마다 적용일 시켜 주워야 한다.(한번 호출 될 때마다 한번씩 갱신됨)
        val runnable: Runnable = object: Runnable{
            override fun run() {
                progressbar1.setProgress(0)
                percent1 = 0
                while(true){
                    try{
                        Thread.sleep(200)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                    percent1++
                    if(percent1 > 100) break;
                    runOnUiThread{
                        progressbar1.setProgress(percent1)
                    }
                }
            }
        }
        val thread: Thread = Thread(runnable)

        thread1_btn.setOnClickListener{
            thread.start()
        }

        //thread생성 방법2<thread객체를 생성하면서 Runnable 정의해주기>
        thread2_btn.setOnClickListener {
            Thread(object : Runnable {
                override fun run() {
                    progressbar2.setProgress(0)
                    percent2 = 0
                    while(true){
                        try{
                            Thread.sleep(200)
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                        percent2++
                        if(percent2 > 100) break;
                        runOnUiThread{
                            progressbar2.setProgress(percent2)
                        }
                    }
                }
            }).start()
        }

        //thread생성 방법3<Runnable을 람다 방식으로 정의해주기>
        thread3_btn.setOnClickListener {
            Thread(Runnable {
                    progressbar3.setProgress(0)
                    percent3 = 0
                    while(true) {
                        try {
                            Thread.sleep(200)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        percent3++
                        if (percent3 > 100) break;
                        runOnUiThread {
                            progressbar3.setProgress(percent3)
                        }
                    }
            }).start()
        }

        next_btn4.setOnClickListener {
            val intent = Intent(this@ThreadActivity,LibraryActivity::class.java)
            startActivity(intent)
        }
    }
}
