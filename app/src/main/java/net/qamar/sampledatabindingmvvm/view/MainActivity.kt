package net.qamar.sampledatabindingmvvm.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.kotlinrv.Adapters.RecyclerViewAdapterKT
import kotlinx.android.synthetic.main.activity_main.*
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.databinding.ActivityMainBinding
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.apiNetworking.Status.*
import net.qamar.sampledatabindingmvvm.viewmodel.AlbumViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: AlbumViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = setContentView(
            this,
            R.layout.activity_main
        )
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setupObservers()

        viewModel.tosatMsg.observe(this, Observer { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                showToast(it)
            }
        })


    }



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupObservers() {
        progressBar.visibility = View.VISIBLE
        val adapterKT = RecyclerViewAdapterKT()

        viewModel.getAlbums().observe(this, Observer { data ->

            adapterKT.submitList(data)
            progressBar.visibility = View.GONE


        })

        recyclerView.adapter = adapterKT

    }





    fun showToast(message:String){
        if(message!="")
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }


}
