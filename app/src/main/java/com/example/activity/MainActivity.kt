package com.example.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.ViewCompat.setBackground
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("life_cycle" , "onCreate")

        //val mainView : View = findViewById(R.id.main1)
        //mainView.setBackgroundResource(R.drawable.ic_launcher_foreground)

        //val v : View = main1
        // val image : Drawable? = getDrawable(R.drawable.background1)

        next_btn1.setOnClickListener {
            val intent = Intent(this@MainActivity, IntentActivity::class.java)
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
