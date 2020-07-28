package com.example.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_intent2.*

class IntentActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent2)

        val userName = intent.getStringExtra("name")
        val userNumber = intent.getStringExtra("number")

        user_name2.setText(userName)
        user_number2.setText(userNumber)

        //로컬 데이터베이스에 입력 데이터를 저장하는 과정
        Realm.init(this@IntentActivity2)
        val config: RealmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()//migration(데이터베이스를 동기화 시키겠다)필요한 경우 지우겠다
            //데이터베이스 틀이 아예 바뀌면 데이터베이스 정보 다 지우겠다.
            .build()

        Realm.setDefaultConfiguration(config)
        val realm = Realm.getDefaultInstance()

//        //결과값을 보내주는 작업
        intent_btn2.setOnClickListener {

            //누름과 동시에 로컬 데이터베이스에 저장을 시켜줌
            realm.executeTransaction{
                with(it.createObject(Person::class.java)){
                    this.name = userName
                    this.number = userNumber
                }
            }


            val resultIntent = Intent()
            resultIntent.putExtra("result",userName)

            setResult(Activity.RESULT_OK,resultIntent)
            this.finish()
        }


    }
}
