package net.qamar.sampledatabindingmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import net.qamar.sampledatabindingmvvm.R
import net.qamar.sampledatabindingmvvm.databinding.ActivityDetailsBinding
import net.qamar.sampledatabindingmvvm.databinding.ActivityMainBinding
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import net.qamar.sampledatabindingmvvm.viewmodel.SampleViewModel

class DetailsActivity : AppCompatActivity() {
    lateinit var data : RetroPhoto
    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailsBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_details
        )

        if(intent!=null)
            data = intent.getSerializableExtra("data") as RetroPhoto

        viewModel.dataDetails(data)
        binding.viewmodel = viewModel





    }
}