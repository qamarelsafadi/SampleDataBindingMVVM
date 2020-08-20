package com.example.kotlinrv.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.databinding.TaskItemBinding
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import java.util.*


class RecyclerViewAdapterKT(albums: ArrayList<RetroPhoto>?, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapterKT.ViewHolder>() {


    var albums: ArrayList<RetroPhoto>? = albums;
    private var context: Context? = context;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterKT.ViewHolder {


      val binding: TaskItemBinding = DataBindingUtil.inflate( LayoutInflater.from(parent.context),
          R.layout.task_item, parent, false);
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return albums?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterKT.ViewHolder, position: Int) {

        val item = albums!![position]
        holder.bindItem(item)

    }


    class ViewHolder( binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        var binding: TaskItemBinding? = null

        init {
            this.binding = binding
        }

        override fun onClick(p0: View?) {
        }

        fun bindItem(item: RetroPhoto?) {
            binding!!.item = item
            binding!!.executePendingBindings()
        }


    }
}

