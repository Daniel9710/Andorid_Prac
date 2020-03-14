package com.example.android

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.input.*
import org.json.JSONObject

class Set_Input : AppCompatActivity() {

    var txt:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input)

        insert_btn.setOnClickListener {
            val tid:Int = Integer.parseInt(tid_txt.text.toString())
            var jsonrequest = JSONObject().put("tid", tid).put("txt",text_txt.text.toString())
            requestText(jsonrequest, Request.Method.PUT, "PUT")
        }
        update_btn.setOnClickListener {
            val tid:Int = Integer.parseInt(tid_txt.text.toString())
            var jsonrequest = JSONObject().put("tid", tid).put("txt",text_txt.text.toString())
            requestText(jsonrequest, Request.Method.PATCH, "PATCH")
        }
        delete_btn.setOnClickListener {
            val tid:Int = Integer.parseInt(tid_txt.text.toString())
            var jsonrequest = JSONObject().put("tid", tid).put("txt",text_txt.text.toString())
            requestText(jsonrequest, Request.Method.DELETE, "DELETE")
        }

        print_btn.setOnClickListener {
            Toast.makeText(this, text_txt.text.toString(), Toast.LENGTH_LONG).show()
            txt = text_txt.text.toString()
        }

        back_btn.setOnClickListener {
            intent.putExtra("Text", txt)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    fun requestText(json: JSONObject, method: Int, url:String){
        MySingleton.getInstance(this).addToRequestQueue(
            JsonObjectRequest(method, "${App.mainIp}/set/text/${url}/${json["tid"]}",
                json,
                Response.Listener{ response ->
                    if(response.has("data"))
                        Toast.makeText(this, response["data"].toString(), Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(this, response["error"].toString(), Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener {
                        error ->  Toast.makeText(this, error.toString(),Toast.LENGTH_LONG).show()
                })
        )
    }

}