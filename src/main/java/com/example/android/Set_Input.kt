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
            val tid = tid_txt.text.toString()
            val text = text_txt.text.toString()
            if(tid == "" || text == "") {
                Toast.makeText(this,"Have to fill in all text",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(tid.toIntOrNull() !is Int) {
                Toast.makeText(this,"tid have to be Integer type",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            requestText(
                JSONObject()
                    .put("tid", Integer.parseInt(tid))
                    .put("txt",text),
                Request.Method.PUT,
                "PUT"
            )
        }
        update_btn.setOnClickListener {
            val tid = tid_txt.text.toString()
            val text = text_txt.text.toString()
            if(tid == "" || text == "") {
                Toast.makeText(this,"Have to fill in all text",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(tid.toIntOrNull() !is Int) {
                Toast.makeText(this,"tid have to be Integer type",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            requestText(
                JSONObject()
                    .put("tid", Integer.parseInt(tid))
                    .put("txt",text),
                Request.Method.PATCH,
                "PATCH"
            )
        }
        delete_btn.setOnClickListener {
            val tid = tid_txt.text.toString()
            val text = text_txt.text.toString()
            if(tid == "" || txt == "") {
                Toast.makeText(this,"Have to fill in all text",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(tid.toIntOrNull() !is Int) {
                Toast.makeText(this,"tid have to be Integer type",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            requestText(
                JSONObject()
                    .put("tid", Integer.parseInt(tid))
                    .put("txt",text),
                Request.Method.DELETE,
                "DELETE"
            )
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
            JsonObjectRequest(method, "${App.mainIp}set/text/${url}/${json["tid"]}",
                json,
                Response.Listener{ response ->
                    if(response.has("error")) {
                        var s:String ?= null
                        if(response.has("txt"))
                            s = response["txt"].toString()
                        App.errormessage(
                            this,
                            Integer.parseInt(response["error"].toString()), url,
                            Integer.parseInt(response["tid"].toString()), s
                        )
                    }
                    else
                        Toast.makeText(this, "completely $url ${response["tid"]} : ${response["txt"]}", Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener {
                        error ->  Toast.makeText(this, error.toString(),Toast.LENGTH_LONG).show()
                })
        )
    }

}