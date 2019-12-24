@file:Suppress("DEPRECATION")

package com.example.konekdatabase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fakultas_list.view.*

@Suppress("UNREACHABLE_CODE")
class RVAdapterFakultas(private val arrayList: ArrayList<Fakultas>):RecyclerView.Adapter<RVAdapterFakultas.Holder>(){
    private val context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.fakultas_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.idfak.text=arrayList[position].id_fakultas.toString()
                holder.view.kodefak.text= arrayList[position].kode_fakultas
        holder.view.namafak.text="Nama Fakultas: "+ arrayList[position].nama_fakultas

        holder.view.cardview_list.setOnClickListener{

            val i=Intent(context ,ManageFakultasActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("editmode","1")
            i.putExtra("etidfak",arrayList[position].id_fakultas)
            i.putExtra("etkodefak",arrayList[position].kode_fakultas)
            i.putExtra("etnamafak",arrayList[position].nama_fakultas)
            context?.startActivity(i)

        }
    }

    class Holder(val view: View): RecyclerView.ViewHolder(view)
}
