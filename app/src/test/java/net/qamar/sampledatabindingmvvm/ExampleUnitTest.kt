package net.qamar.sampledatabindingmvvm

import androidx.core.content.res.TypedArrayUtils.getText
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import net.qamar.sampledatabindingmvvm.model.RetroPhoto
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun doAction_doesSomething(){
        val mock = mock<RetroPhoto> {
            on { title } doReturn "test"
        }
        val classUnderTest = RetroPhoto(1,1,"test","teest","tesst")

        classUnderTest.isEmpty()

        verify(mock).isEmpty()
    }
}
