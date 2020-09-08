package net.qamar.sampledatabindingmvvm.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import net.qamar.sampledatabindingmvvm.adapters.AlbumAdapter
import net.qamar.sampledatabindingmvvm.adapters.RecyclerViewAdapterKT
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.databinding.ActivityMainBinding
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.util.ToolsUtil
import net.qamar.sampledatabindingmvvm.viewmodel.AlbumViewModel
import net.qamar.sampledatabindingmvvm.viewmodel.TaskItemViewModel
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val viewModel: AlbumViewModel by viewModels()
    private val taskItemViewModel: TaskItemViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setupObservers()

        viewModel.tosatMsg.observe(this, Observer { event ->
            showToast(event?.getContentIfNotHandledOrReturnNull().toString())
        })

        taskItemViewModel.toastMsg.observe(this, Observer { event ->
            showToast(event?.getContentIfNotHandledOrReturnNull().toString())

        })


    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupObservers() {
        progressBar.visibility = View.VISIBLE
        val adapterKT = RecyclerViewAdapterKT(taskItemViewModel)

        if (ToolsUtil.isNetworkConnected(this)) {
            viewModel.getAlbums().observe(this, Observer { data ->
                adapterKT.submitList(data)
                progressBar.visibility = View.GONE


            })
            recyclerView.adapter = adapterKT


        } else {

            var adapter : AlbumAdapter
            //Offline Mode
            val list = Paper.book().read("list",ArrayList<RetroPhoto>())
            if(list.size!=0) {
                progressBar.visibility = View.GONE

                 adapter = AlbumAdapter(list, taskItemViewModel)
                recyclerView.adapter = adapter


                viewModel.albumList.observe(this, Observer { data ->
                     adapter = AlbumAdapter(data, taskItemViewModel)
                    recyclerView.adapter = adapter

                })
                taskItemViewModel.albumList!!.observe(this, Observer { data ->
                     adapter = AlbumAdapter(data, taskItemViewModel)
                    recyclerView.adapter = adapter

                })

            }else{
                showToast("You need to connect a network on your first time.")
            }


        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        setupObservers()


    }

    private fun showToast(message: String) {
        if (message != "")
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


}
