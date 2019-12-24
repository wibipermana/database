@file:Suppress("DEPRECATION")

package com.example.konekdatabase

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_manage_fakultas.*
import kotlinx.android.synthetic.main.fakultas_list.*
import org.json.JSONObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class ManageFakultasActivity : AppCompatActivity() {
    lateinit var i : Intent
    lateinit var add:Button
    lateinit var edit : Button
    lateinit var delete : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_fakultas)

        add=findViewById(R.id.btnCreate)
        edit=findViewById(R.id.btnUpdate)
        delete=findViewById(R.id.btnDelete)
        i=intent
        if (i.hasExtra("editmode")){
            if (i.getStringExtra("editable") == "1"){
                onEditMode()
            }
        }
        add.setOnClickListener{
            onCreate()
        }
        edit.setOnClickListener{
            onUpdate()
        }
        delete.setOnClickListener{
            onDelete()
        }
    }

    private fun onEditMode() {
        etidfak.isEnabled=false
        etkodefak.setText(i.getStringExtra("kodefak"))
        etnamafak.setText(i.getStringExtra("namafak"))
        btnCreate.visibility= View.GONE
        btnUpdate.visibility= View.VISIBLE
        btnDelete.visibility= View.VISIBLE
    }

    private fun onCreate() {
        val loading =ProgressDialog(this)
        loading.setMessage("Menambahkan Data...")
        loading.show()
        AndroidNetworking.post(ApiEndPoint.CREATE)
            .addBodyParameter("kode_fakultas",kodefak.text.toString())
            .addBodyParameter("nama_fakultas",namafak.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_LONG).show()
                    if (response?.getString("message")?.contains("Success")!!){
                        this@ManageFakultasActivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Gagal Menyambungkan",Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun onUpdate() {
        val loading = ProgressDialog(this)
        loading.setMessage("Ubah Data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.UPDATE)
            .addBodyParameter("id_fakultas",kodefak.text.toString())
            .addBodyParameter("kode_fakultas",kodefak.text.toString())
            .addBodyParameter("nama_fakultas",namafak.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_LONG).show()
                    if (response?.getString("message")?.contains("Success")!!){
                        this@ManageFakultasActivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Gagal Menyambungkan",Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun onDelete() {
        val loading = ProgressDialog(this)
        loading.setMessage("Hapus Data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.DELETE)
            .addBodyParameter("id_fakultas",kodefak.text.toString())
            .addBodyParameter("kode_fakultas",kodefak.text.toString())
            .addBodyParameter("nama_fakultas",namafak.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_LONG).show()
                    if (response?.getString("message")?.contains("Success")!!){
                        this@ManageFakultasActivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Gagal Menyambungkan",Toast.LENGTH_LONG).show()
                }
            })
    }
}
