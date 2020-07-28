package com.example.activity

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_async.*

class AsyncActivity : AppCompatActivity() {

    var task: BackgroundAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async)

        //쓰레드와의 차이점 -> main Thread와 통신을 중간마다 할 수 있음
        //activity가 종료가 되더라도 계속 수행이 되기 떄문에 따로 종료처리를 해 주워야 한다.
        // onpause()부분에 종료 처리를 해 줌
        //네트워크 작업을 할 떄 좋음
        //재사용이 불가능

        async_start.setOnClickListener {
            task = BackgroundAsyncTask(async_progressbar,ment)
            task?.execute()
        }
        async_stop.setOnClickListener {
            task?.cancel(true)
        }

        next_network.setOnClickListener {
            val intent = Intent(this@AsyncActivity,NetworkActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        //엑티비티가 화면 상에서 사라지면 비동기 과정을 취소함
        task?.cancel(true)
    }
}

//Async 테스크를 상속받는 자식 클래스를 하나 생성해준다
class BackgroundAsyncTask(
    val progressbar : ProgressBar,
    val progressText : TextView
): AsyncTask<Int, Int, Int>(){
    //params -> doInBackground 에서 사용할 타임
    //progress -> onProgressUpdate 에서 사용할 타입
    //result -> onPostExcute 에서 사용할 타입
    //vararg : int가 여러개 올 수 있다

    var percent : Int = 0

    //비동기과정 생성
    override fun onPreExecute() {
        percent = 0
        progressbar.setProgress(percent)
    }

    //비동기 과정 수행
    override fun doInBackground(vararg params: Int?): Int {
        while(isCancelled() == false){//완료가 되지 않았는데 취소됐다면 참(이미 있는 메소드)
            percent++
            if(percent > 100){
                break
            }else{
                publishProgress(percent) // onProgressUpdate로 감
            }
            try{
                Thread.sleep(100)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        return percent
    }

    //비동기 과정 수행중에 mianActivity(thread)와 통신을 하는 부분
    override fun onProgressUpdate(vararg values: Int?) {
        progressbar.setProgress(values[0] ?: 0)
        progressText.setText("퍼센트 : " + values[0])
        super.onProgressUpdate(*values)
    }

    //작업이 완료
    override fun onPostExecute(result: Int?) {
        progressText.setText("작업이 완료되었습니다.")
    }

    //작업이 취소 되었을 때
    override fun onCancelled() {
        progressbar.setProgress(0)
        progressText.setText("작업이 취소되었습니다.")
    }
}
