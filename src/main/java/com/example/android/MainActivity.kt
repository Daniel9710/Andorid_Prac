package com.example.android

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(App.prefs.setColor) {
            MySingleton.getInstance(this).addToRequestQueue(
                JsonObjectRequest(
                    Request.Method.GET, "${App.mainIp}set/color/GET/1",
                    JSONObject(),
                    Response.Listener { response ->
                        if(response.has("error")) {
                            var s:String ?= null
                            if(response.has("color"))
                                s = response["color"].toString()
                            App.errormessage(
                                this,
                                Integer.parseInt(response["error"].toString()), "GET",
                                Integer.parseInt(response["cid"].toString()), s
                            )
                        }
                        else
                            Toast.makeText(this, "completely GET ${response["cid"]} : ${response["color"]}", Toast.LENGTH_LONG).show()
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, " That didn't work!", Toast.LENGTH_SHORT).show()
                    }
                )
            )
        }

        input_btn.setOnClickListener {
            val nextIntent = Intent(this, Set_Input::class.java)
            startActivityForResult(nextIntent, 1)
        }
        color_btn.setOnClickListener {
            val nextIntent = Intent(this, Set_Color::class.java)
            startActivityForResult(nextIntent, 2)
        }
        setting_btn.setOnClickListener {
            val nextIntent = Intent(this, Setting::class.java)
            startActivityForResult(nextIntent, 3)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 1 && App.prefs.setText)
                textView.setText(data?.getStringExtra("Text"))
            else if(requestCode == 2 && App.prefs.setColor)
                textView.setTextColor(Color.parseColor(data?.getStringExtra("color")) )
            else if(requestCode == 3 ) {
                if(data?.getBooleanExtra("set_text",true)!! == false)
                    textView.setText("입력하세요")
                if(data.getBooleanExtra("Color_Changed", true))
                    textView.setTextColor(Color.parseColor(data.getStringExtra("color")))
            }



        }
        else {
            Toast.makeText(this, "Err result : ${requestCode}", Toast.LENGTH_SHORT).show()
        }
    }
}
