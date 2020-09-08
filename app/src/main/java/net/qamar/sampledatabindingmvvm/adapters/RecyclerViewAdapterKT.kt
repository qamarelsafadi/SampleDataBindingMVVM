package net.qamar.sampledatabindingmvvm.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.databinding.TaskItemBinding
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.view.DetailsActivity
import net.qamar.sampledatabindingmvvm.viewmodel.TaskItemViewModel


class RecyclerViewAdapterKT(private val viewModel: TaskItemViewModel?) :
    PagedListAdapter<RetroPhoto, RecyclerViewAdapterKT.ViewHolder>(DIFF_CALLBACK) {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding: TaskItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.task_item,
            parent,
            false
        )

        return ViewHolder(binding, viewModel!!)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindItem(getItem(position)!!)

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class ViewHolder(binding: TaskItemBinding, viewModel: TaskItemViewModel) :
        RecyclerView.ViewHolder(binding.root) {

        private var binding: TaskItemBinding? = null
        var viewModel: TaskItemViewModel? = null


        init {
            this.binding = binding
            this.viewModel = viewModel

            this.binding!!.ConsContainer.setOnClickListener {

                Log.e("qmr", "${binding.item!!.id}")
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
        fun bindItem(item: RetroPhoto?) {
            binding!!.item = item
            viewModel!!.getItem(binding!!.item!!)
            binding!!.viewModel = viewModel
            binding!!.executePendingBindings()

        }


    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<RetroPhoto>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(
                oldPhoto: RetroPhoto,
                newPhoto: RetroPhoto
            ) = oldPhoto.id == newPhoto.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldPhoto: RetroPhoto, newPhoto: RetroPhoto) =
                oldPhoto == newPhoto
        }
    }
}

