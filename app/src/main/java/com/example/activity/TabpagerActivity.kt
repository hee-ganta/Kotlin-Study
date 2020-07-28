package com.example.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tabpager.*

class TabpagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabpager)
        //tablayout은 gradle에 import시켜 주워야 함

        tab_layout.addTab(tab_layout.newTab().setText("AddView"))
        tab_layout.addTab(tab_layout.newTab().setText("ListView"))
        tab_layout.addTab(tab_layout.newTab().setText("RecyclerView"))

        val pagerAdapter = FragmentPageAdapter(supportFragmentManager,3)
//        val pagerAdapter =  ViewPageAdapter(LayoutInflater.from(this@TabpagerActivity))
        view_pager.adapter = pagerAdapter
        //페이지가 이동했을 때 탭을 이동시키는 코드(연동작업)
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {//탭이 다시 선택될 때
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {//탭이 선택되어지지 않을 때
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {//탭이 선택될 떄
                view_pager.currentItem = tab!!.position
            }
        })

    }
}


// fragment단위로 tabPager에 붙히기
//tabpgaeLayout은 Adapter를 이용하여 구현이 되어짐
//각각의 tabPage는 클래스 객체를 만들어서 접근을 하는 형태
class FragmentPageAdapter(
    fragmentManager : FragmentManager,
    val tabCount : Int
): FragmentStatePagerAdapter(fragmentManager){
    override fun getItem(position : Int): Fragment {
        when(position){
            0->{
                return AddViewActivity()
            }
            1->{
                return ListViewActivity()
            }
            2->{
                return RecyclerViewActivity()
            }
            else -> return AddViewActivity()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}

//View 단위로 붙히기
class ViewPageAdapter(
    val layoutInflater: LayoutInflater
):PagerAdapter(){
    override fun instantiateItem(container: ViewGroup, position: Int): Any{
        when(position){
            0-> {
                val view = layoutInflater.inflate(R.layout.activity_add_view, container, false)
                container.addView(view)
                return view
            }

            1-> {
                val view = layoutInflater.inflate(R.layout.activity_list_view, container, false)
                container.addView(view)
                return view
            }

            2-> {
                val view = layoutInflater.inflate(R.layout.activity_recycler_view, container, false)
                container.addView(view)
                return view
            }

            else-> {
                val view = layoutInflater.inflate(R.layout.activity_add_view, container, false)
                container.addView(view)
                return view
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // instantiateItem에서 view 리턴해준게 `object로 들어옴
        container.removeView(`object` as View)
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        // instantiateItem에서 view 리턴해준게 `object로 들어옴
        return view === `object` as View
        //object 대쉬같은 기호는 자바로 쓰여졌는데 코틀린 예약어이지만 자바에서는 예약어 아닐때 이렇게 구분해서 씀
    }

    override fun getCount(): Int {
        return 3
    }
}
