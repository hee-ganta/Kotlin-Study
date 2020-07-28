package com.example.activity

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_permission.*
import java.util.jar.Manifest

class Permission : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        //위험 권한을 manifest에 추가를 해야 작동이 됨
        //권한 강제 삭제 : 설정 -> apps -> permisions들어가서 삭제

       permission_btn.setOnClickListener{
            val cameraPermissionCheck = ContextCompat.checkSelfPermission(
                this@Permission,
                android.Manifest.permission.CAMERA
            )

            if(cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                //권한이 없는 경우
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    1000
                )
            }else{
                //권한이 있는 경우
                Log.d("permission", "권한이 이미 있음")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //request번호와 맞으면 승낙
        if(requestCode== 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //승낙
                Log.d("permission", "승낙")
            }else
            {
                Log.d("permission", "거부")
            }
        }
    }
}
