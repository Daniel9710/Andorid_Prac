package com.example.android

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.setting.*
import org.json.JSONObject

class Setting: AppCompatActivity() {
    var set_text: Boolean = App.prefs.setText
    var set_color: Boolean = App.prefs.setColor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

        switch_text.setChecked(App.prefs.setText)
        switch_color.setChecked(App.prefs.setColor)

        switch_color.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                set_color = isChecked
            }
        })
        switch_text.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                set_text = isChecked
            }
        })
        button.setOnClickListener{
            if(set_color == false && App.prefs.setColor == true) {
                intent.putExtra("color", "#000000")
                intent.putExtra("Color_Changed", true)
            }
            else if(set_color == true && App.prefs.setColor == false) {
                MySingleton.getInstance(this).addToRequestQueue(
                    JsonObjectRequest(
                        Request.Method.GET, "${App.mainIp}set/color/GET/1", JSONObject(),
                        Response.Listener { response ->
                            intent.putExtra("color", response["color"].toString())
                            intent.putExtra("Color_Changed", true)
                        },
                        Response.ErrorListener {
                            error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                        }
                    )
                )
            }
            else
                intent.putExtra("Color_Changed", false)

            if(set_text == false && App.prefs.setText)
                intent.putExtra("set_text", false)

            App.prefs.setColor = set_color
            App.prefs.setText = set_text

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}