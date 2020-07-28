package com.example.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_segment.*

class FragmentSegment : Fragment(){

    //인터페이스 만들기
    //상속하는 클래스에서 해당 함수들을 구현하여 주면 됨
    interface OnDatapassListener{
        fun onDataPass(data: String?)
    }

    lateinit var dataPassListener : OnDatapassListener

    override fun onAttach(context: Context) {
        Log.d("life_cycle","F onAttach")
        super.onAttach(context)
        //context를 받아오고 그 중 인터페이스를 받아오는 기법
        dataPassListener = context as OnDatapassListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("life_cycle","F onCreate")
        super.onCreate(savedInstanceState)
    }

    //뷰를 생성
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("life_cycle","F onCreateView")
        return inflater.inflate(R.layout.fragment_segment,container,false)
    }

    //받은 뷰를  UI에 그리는 부분
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("life_cycle","F onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        //원래 activity에서 했던 작업들을 fragment에서는 여기서 해 준다.
        pass_btn.setOnClickListener {
            //상속한 클래스에서 정의된 메소드 로직이 수행이 됨
            dataPassListener.onDataPass(pass_data.text.toString())
        }
    }

    //엑티비티가 실행되는 부분
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("life_cycle","F onActivityCreated")

        //bundle 객체로서 이전 엑티비티에 대한 정보를 받는데 그 정보를 받는 로직을 수행
        val data = arguments?.getString("checkMessage")
        Log.d("data", data)

        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d("life_cycle","F onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("life_cycle","F onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("life_cycle","F onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("life_cycle","F onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("life_cycle","F onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("life_cycle","F onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("life_cycle","F onDetach")
        super.onDetach()
    }
}


