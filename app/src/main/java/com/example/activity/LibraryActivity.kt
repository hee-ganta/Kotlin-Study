package com.example.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_library.*

class LibraryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        val image : Drawable? = getDrawable(R.drawable.people1)

        //인터넷 permission을 해 주워야 외부에서 가져 올 수 있음 -> mainifast 가서 permission 해 주워야 함
        //추가적인 기능은 추후 필요할때 찾아보기


        //1, drawable 객체로 불러오기
        Glide.with(this@LibraryActivity)
            .load(image)
            .circleCrop()
            .into(glide)

        //2, url주소로 불러오기
        Glide.with(this@LibraryActivity)
            .load("https://images.unsplash.com/photo-1587815340818-6f056e0d95c9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80")
            .circleCrop()
            .into(glide2)

        next_btn4.setOnClickListener {
            val intent = Intent(this@LibraryActivity,TabpagerActivity::class.java)
            startActivity(intent)
        }

    }
}
