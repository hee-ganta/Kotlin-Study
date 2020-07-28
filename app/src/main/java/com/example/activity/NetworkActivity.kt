package com.example.activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_network.*
import kotlinx.android.synthetic.main.activity_recycler_view.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkActivity : AppCompatActivity() {

    var task: NetworkList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        task = NetworkList(
            json_recycler_view,
            LayoutInflater.from(this@NetworkActivity),
            this@NetworkActivity
        )

        task?.execute()

        next_permission.setOnClickListener{
            val intent = Intent(this@NetworkActivity,Permission::class.java)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        task?.cancel(true)
    }
}

class JsonRecyclerViewAdapter(
    val itemList: Array<NetworkPersonForList>?,
    val inflater: LayoutInflater
): RecyclerView.Adapter<JsonRecyclerViewAdapter.viewHolder>(){
    inner class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var id : TextView
        var name : TextView
        var age : TextView
        var intro : TextView

        //holder에 넣어줄 항목들 지정해줌
        init{
            id = itemView.findViewById(R.id.person_id)
            name = itemView.findViewById(R.id.person_name)
            age = itemView.findViewById(R.id.person_age)
            intro = itemView.findViewById(R.id.person_intro)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JsonRecyclerViewAdapter.viewHolder {
        val view = inflater.inflate(R.layout.network_person_item,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(holder: JsonRecyclerViewAdapter.viewHolder, position: Int) {
        holder.name.setText(itemList?.get(position)?.id.toString()?:"")
        holder.name.setText(itemList?.get(position)?.name?:"")
        holder.age.setText(itemList?.get(position)?.age.toString()?:"")
        holder.intro.setText(itemList?.get(position)?.intro?:"")
    }
}


//AsyncTask 제네릭 부분의 맨 마지막 부분 백그라운드에서 작업을 해 준다음 결과물을 반환
class NetworkList(
    val recyclerView: RecyclerView,
    val inflater: LayoutInflater,
    val context : Context
): AsyncTask<Any?, Any?, Array<NetworkPersonForList>>(){

    override fun doInBackground(vararg params: Any?): Array<NetworkPersonForList> {
        val urlString :  String = "http://mellowcode.org/json/students/"
        val url : URL = URL(urlString)
        val connection : HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        //postman에서 확인 가능
        connection.setRequestProperty("Contect-Type", "application/json")

        var buffer = ""
        if(connection.responseCode == HttpURLConnection.HTTP_OK){
            var reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )
            buffer = reader.readLine()
        }

        //그냥 데이터로 받은 데이터들을 클래스 포멧에 바꿔서 저장
        //Gson객체를 활용
        val data = Gson().fromJson(buffer,Array<NetworkPersonForList>::class.java)
        return data
    }

    //작업이 완료 되었을 때
    //받은 리스트를 활용하여서 recyclerView 붙혀줌
    override fun onPostExecute(result: Array<NetworkPersonForList>?) {
        val adapter = JsonRecyclerViewAdapter(result,inflater)
        recyclerView.adapter = adapter
        //이 부분이 안들어가야 하는 이유는 잘 모르겠음
        //recyclerView.layoutManager = LinearLayoutManager(context)
        super.onPostExecute(result)
    }
}
