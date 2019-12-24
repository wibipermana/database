@file:Suppress("DEPRECATION")

package com.example.konekdatabase

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    var arrayList = ArrayList<Fakultas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title="Data Fakultas"

        recycle_view.setHasFixedSize(true)
        recycle_view.layoutManager= LinearLayoutManager(this)

        mfb.setOnClickListener{
            startActivity(Intent(this,ManageFakultasActivity::class.java))
        }
        loadAllFakultas()
    }

    override fun onResume() {
        super.onResume()
        loadAllFakultas()
    }
    private fun loadAllFakultas(){
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat Data")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    val jsonArray: JSONArray? = response?.optJSONArray("Result")
                    arrayList.clear()
                    if (jsonArray?.length() == 0) {
                        loading.dismiss()
                        Toast.makeText(applicationContext,"Fakultas data is empty, Add the data first",Toast.LENGTH_LONG).show()
                    }

                    for(i in 0 until jsonArray?.length()!!){
                        val jsonObject: JSONObject = jsonArray.optJSONObject(i)
                        arrayList.add(
                            Fakultas(
                                jsonObject.getString("id_fakultas"),
                                jsonObject.getString("kode_fakultas"),
                                jsonObject.getString("nama_fakultas")
                            )
                        )

                        if (jsonArray.length() - 1 == i) {
                            loading.dismiss()
                            val adapter = RVAdapterFakultas(arrayList)
                            adapter.notifyDataSetChanged()
                            recycle_view.adapter = adapter
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connecting Failure",Toast.LENGTH_LONG).show()
                }
            })
        }

}
