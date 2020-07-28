package com.example.activity

import io.realm.RealmObject
import java.io.Serializable

open class Person() : RealmObject(){
    var name : String? = null
    var number : String? = null
}

class PersonForList(val name : String?, val number : String?){

}

class NetworkPersonForList(
    val id :Int? = null,
    var name : String? = null,
    var age: Int? = null,
    var intro : String? = null
):Serializable