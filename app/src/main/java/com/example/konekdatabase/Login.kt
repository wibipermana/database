package com.example.konekdatabase

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Login : AppCompatActivity() {

    lateinit var login : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val login = findViewById<Button>(R.id.btn_login)
        login.setOnClickListener{
            onLogin()
        }
    }

    private fun onLogin() {
        val loading = ProgressDialog(this)
        loading.setMessage("Login...")
        loading.show()
        AndroidNetworking.post(ApiEndPoint.LOGIN)
            .addBodyParameter("user",user.text.toString())
            .addBodyParameter("password",pass.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"), Toast.LENGTH_LONG).show()
                    if (response?.getString("message")?.contains("Success")!!){
                        this@Login.finish()
                        startActivity(Intent(this@Login,MainActivity::class.java))
                    }
                }
               override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Gagal Menyambungkan", Toast.LENGTH_LONG).show()
                }
            })
    }
}