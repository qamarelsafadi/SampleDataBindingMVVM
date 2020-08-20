package com.example.kotlinrv.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import  java.util.ArrayList

class RecyclerViewAdapterKT(albums: ArrayList<RetroPhoto>?, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapterKT.ViewHolder>() {


    var albums: ArrayList<RetroPhoto>? = albums;
    private var context: Context? = context;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterKT.ViewHolder {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        val vh = ViewHolder(v)
        return vh;
    }

    override fun getItemCount(): Int {

        return albums?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterKT.ViewHolder, position: Int) {

        holder.txtID?.setText("${albums?.get(position)?.id}")
        holder.txtTitle?.setText(albums?.get(position)?.title)
        holder.txtDescription?.setText(albums?.get(position)?.url)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var txtID: TextView? = null
        var txtTitle: TextView? = null
        var txtDescription: TextView? = null


        init {
            txtID = view.findViewById(R.id.txtTaskID)
            txtTitle = view.findViewById(R.id.txtTitle)
            txtDescription = view.findViewById(R.id.txtDescription)
        }

        override fun onClick(p0: View?) {
        }


    }
}

