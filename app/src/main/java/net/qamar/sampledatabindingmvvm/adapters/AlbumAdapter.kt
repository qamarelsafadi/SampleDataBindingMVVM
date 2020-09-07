package com.example.kotlinrv.Adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.databinding.TaskItemBinding
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.view.DetailsActivity
import net.qamar.sampledatabindingmvvm.viewmodel.TaskItemViewModel
import java.util.*


class AlbumAdapter(albums: ArrayList<RetroPhoto>,
    val context: Context,
    private val viewModel: TaskItemViewModel?
) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
private val  list = albums

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: TaskItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.task_item,
            parent,
            false
        );

        return ViewHolder(binding, viewModel!!,binding.root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItem(list[position])

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class ViewHolder(binding: TaskItemBinding, viewModel: TaskItemViewModel , root:View) :
        RecyclerView.ViewHolder(binding.root) {

        var binding: TaskItemBinding? = null

        var viewModel: TaskItemViewModel? = null
        var root: View? = null

        init {
            this.binding = binding
            this.viewModel = viewModel
            this.root = root

            this.binding!!.ConsContainer.setOnClickListener {
                val intent = Intent(this.binding!!.root.context, DetailsActivity::class.java)
                intent.putExtra("data", binding.item)
                this.binding!!.root.context.startActivity(intent)
            }
            this.binding!!.imgDelete.setOnClickListener {
                viewModel.deleteItem(it, binding.item!!.id,binding.item!!)
            }
            this.binding!!.imgEdit.setOnClickListener {
                viewModel.showDialog(it, binding.item!!)
            }

        }


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bindItem(item: RetroPhoto) {
            binding!!.item = item
            viewModel!!.getItem(binding!!.item!!)
            binding!!.viewModel = viewModel
            binding!!.executePendingBindings()
        }


    }
}

