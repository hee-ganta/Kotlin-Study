package com.example.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : Fragment() {
    lateinit var dataSet : RealmResults<Person>;
    var personList = ArrayList<PersonForList>()
    private var container : LinearLayout? = null;
    private var inflater : LayoutInflater? = null;
    var adapter : RecyclerViewAdapter? = null;


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

        adapter = RecyclerViewAdapter(personList, inflater)

        //뷰를 건들일 때는 뷰를 붙힌 다음에 처리를 해 주워야 함!
//        recycler_view.adapter = adapter
//        recycler_view.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_recycler_view, container, false)
    }

    //해당 fragment의 뷰 부분을 처리하고 싶을 땐 이 부분에서 처리
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn = view.findViewById<Button>(R.id.next_preference3)
        btn.setOnClickListener {
            val intent = Intent(view.context,SharedPreferenceActivity::class.java)
            startActivity(intent)
        }

        //layoutmanagement에는 여러 종류가 있다
        //Linear, Gride, StaggeredGrid
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(view.context)
    }
}

//recyclerView adapter만들기
class RecyclerViewAdapter(
    val itemList: ArrayList<PersonForList>,
    val inflater: LayoutInflater?
): RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>(){

    inner class viewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        lateinit var personName: TextView
        lateinit var  personNumber : TextView

        init{
            personName = itemView.findViewById(R.id.person_name)
            personNumber = itemView.findViewById(R.id.person_number)
            //아이템 뷰마다 클릭해서 처리하는 로직들 넣어 줄 수 있음
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = inflater?.inflate(R.layout.person_item,parent,false)
        return viewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.personName.setText(itemList.get(position).name)
        holder.personNumber.setText(itemList.get(position).number)
    }
}