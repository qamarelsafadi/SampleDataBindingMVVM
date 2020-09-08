package net.qamar.sampledatabindingmvvm

import android.content.Context
import com.nhaarman.mockitokotlin2.notNull
import io.paperdb.Paper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.qamar.sampledatabindingmvvm.viewmodel.AlbumViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ViewModelTest {


    private var context: Context = RuntimeEnvironment.application.applicationContext


    init {
// view model need paper init so i was need to init it here also.
        Paper.init(context)
    }

    @Test
    fun isNameValid() {
        val viewModel = AlbumViewModel()
        Assert.assertEquals("Qamar A. Safadi", viewModel.name.value)
    }

    @Test
    fun showToast() {
        val viewModel = AlbumViewModel()
        Assert.assertEquals("", viewModel.tosatMsg.value!!.peekContent())
    }

    @Test
    fun listNotNull() {
        val viewModel = AlbumViewModel()
        Assert.assertEquals(notNull(), viewModel.albumList.value)
    }


}