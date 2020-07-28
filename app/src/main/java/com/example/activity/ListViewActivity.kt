package com.example.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_list_view.*

class ListViewActivity : Fragment() {

    lateinit var dataSet : RealmResults<Person>;
    var personList = ArrayList<PersonForList>()
    private var container : LinearLayout? = null;
    private var inflater : LayoutInflater? = null;

    //해당 fragment의 context 처리 부분은 여기에서 처리
    override fun onAttach(context: Context) {
        super.onAttach(context)

        Realm.init(context)
        val config : RealmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
        val realm = Realm.getDefaultInstance()

        //데이터베이스에서 데이터를 꺼내옴
        realm.executeTransaction{
            dataSet = it.where(Person::class.java).findAll()
            //데이터베이스에 있는 정보를 리스트로 전환
            for(i in 1 until dataSet.size){
                personList.add(PersonForList(dataSet[i].name,dataSet[i].number));
            }
        }
        //뷰에다가 새로운 뷰를 추가해주는 작업-> inflater 이용
        inflater = LayoutInflater.from(context)
    }

    //이 함수의 inflater의 조상은 fragment를 호출한 엑티비티
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_list_view, container, false)
    }

    //해당 fragment의 뷰 부분을 처리하고 싶을 땐 이 부분에서 처리
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn = view.findViewById<Button>(R.id.next_preference2)
        btn.setOnClickListener {
            val intent = Intent(view.context,SharedPreferenceActivity::class.java)
            startActivity(intent)
        }

        val adapter = ListViewAdapter(personList,inflater)
        listview_container.adapter = adapter

    }
}

class ListViewAdapter(
    val PersonList : ArrayList<PersonForList>,
    val layoutInflater : LayoutInflater?
) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //안드로이드 권장사함
        val view: View?
        val holder : ViewHolder

        if(convertView == null){
            view = layoutInflater?.inflate(R.layout.person_item,null)
            holder = ViewHolder()//텍스트 뷰들을 가지고 있음

            holder.personName= view?.findViewById(R.id.person_name)
            holder.personNumber = view?.findViewById(R.id.person_number)

            view?.tag = holder
        }else{//이미 한번 만들어진 텍스트뷰는 재사용함(findViewById 과정을 생략하여 성능을 높힘)
            holder = convertView.tag as ViewHolder
            view = convertView
        }

        holder.personName?.setText(PersonList.get(position).name)
        holder.personNumber?.setText(PersonList.get(position).number)

        return view!!
    }

    override fun getItem(position: Int): Any {
        //그리고자 하는 아이템 리스트의 하나(포지션에 해당하는)
        return PersonList.get(position)
    }

    override fun getItemId(position: Int): Long {
        //해당 포지션에 위치에 있는 아이템 뷰의 아이디 설정
        return position.toLong()
    }

    override fun getCount(): Int {
        //그리고자하는 아이템 리스트의 전체 갯수
        return PersonList.size
    }
}

class ViewHolder{
    var personName: TextView? = null
    var personNumber : TextView? = null
}
