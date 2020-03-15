package com.example.android

import android.app.Application
import android.content.Context
import android.widget.Toast

class App: Application() {
    companion object {
        lateinit var prefs : MySharedPreferences
        const val mainIp:String = "http://192.168.201.118:8000/"
        fun errormessage(context: Context, errnum: Int, method:String, id:Int, detail:String?){
            when(errnum) {
                1 -> Toast.makeText(context, "(${method} error) already exists $id : $detail", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(context, "(${method} error) already equal txt $id : $detail", Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(context, "(${method} error) already equal color $id : $detail", Toast.LENGTH_SHORT).show()
                4 -> Toast.makeText(context, "(${method} error) no exists $id", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context, "undefined error number", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
    }

}