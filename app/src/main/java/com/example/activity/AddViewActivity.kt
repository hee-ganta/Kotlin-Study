package com.example.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class AddViewActivity : Fragment() {

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
        return inflater.inflate(R.layout.activity_add_view, container, false)
    }

    //해당 fragment의 뷰 부분을 처리하고 싶을 땐 이 부분에서 처리
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val container = view.findViewById<LinearLayout>(R.id.addview_container)
        val btn = view.findViewById<Button>(R.id.next_preference1)
        btn.setOnClickListener {
            val intent = Intent(view.context,SharedPreferenceActivity::class.java)
            startActivity(intent)
        }

        for (i in 0 until personList.size) {
            val itemView = inflater?.inflate(R.layout.person_item, null)//불힐 뷰를 생성하는 과정
            val personNameView = itemView?.findViewById<TextView>(R.id.person_name)
            val personNumberView = itemView?.findViewById<TextView>(R.id.person_number)

            personNameView?.setText(personList.get(i).name)
            personNumberView?.setText(personList.get(i).number)
            container?.addView(itemView)
        }

    }

}


