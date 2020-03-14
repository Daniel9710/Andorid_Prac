package com.example.android

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.color.*
import org.json.JSONObject

class Set_Color : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color)

        var color1 = "#000000"

        red_btn.setOnClickListener{
            color1 = "#FF0000"
            back.setBackgroundColor(Color.parseColor("#FF0000"))
        }

        green_btn.setOnClickListener{
            color1 = "#00FF00"
            back.setBackgroundColor(Color.parseColor("#00FF00"))
        }

        blue_btn.setOnClickListener{
            color1 = "#0000FF"
            back.setBackgroundColor(Color.parseColor("#0000FF"))
        }

        back_btn.setOnClickListener{
            if(App.prefs.setColor) {
                MySingleton.getInstance(this).addToRequestQueue(
                    JsonObjectRequest(
                        Request.Method.PATCH, "${App.mainIp}set/color/PATCH/1",
                        JSONObject().put("cid", 1).put("color", color1),
                        Response.Listener { response ->
                            if(response.has("error"))
                                Toast.makeText(this, response["error"].toString(), Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(this, response["data"].toString(), Toast.LENGTH_SHORT).show()
                        },
                        Response.ErrorListener {
                            Toast.makeText(this, " That didn't work!", Toast.LENGTH_SHORT).show()
                        }
                    )
                )
                intent.putExtra("color", color1)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}