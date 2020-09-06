package com.example.kotlinrv.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.databinding.TaskItemBinding
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.view.DetailsActivity
import java.util.*


class RecyclerViewAdapterKT() :
    PagedListAdapter<RetroPhoto, RecyclerViewAdapterKT.ViewHolder>(DIFF_CALLBACK) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterKT.ViewHolder {

      val binding: TaskItemBinding = DataBindingUtil.inflate( LayoutInflater.from(parent.context), R.layout.task_item, parent, false);
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: RecyclerViewAdapterKT.ViewHolder, position: Int) {

        val item = getItem(position)!!
        holder.bindItem(item)

    }


    class ViewHolder( binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root){

        var binding: TaskItemBinding? = null


        init {
            this.binding = binding

            this.binding!!.ConsContainer.setOnClickListener {
                val intent = Intent(this.binding!!.root.context, DetailsActivity::class.java)
                intent.putExtra("data",binding.item)
                this.binding!!.root.context. startActivity(intent)
            }

        }


        fun bindItem(item: RetroPhoto) {
            binding!!.item = item
            binding!!.executePendingBindings()
        }



    }
    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<RetroPhoto>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldConcert: RetroPhoto,
                                         newConcert: RetroPhoto) = oldConcert.id == newConcert.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldConcert: RetroPhoto, newConcert: RetroPhoto) = oldConcert == newConcert
        }
    }
}

