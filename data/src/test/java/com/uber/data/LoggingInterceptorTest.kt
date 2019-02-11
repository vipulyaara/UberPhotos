package com.uber.data

import com.uber.data.api.Request
import com.uber.data.api.Response
import com.uber.data.api.UberNetworking
import com.uber.data.mapper.ResponseMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.lang.reflect.AccessibleObject.setAccessible



/**
 * @author Vipul Kumar; dated 07/02/19.
 */
@RunWith(JUnit4::class)
class LoggingInterceptorTest {

    private val url = "http://url"
    private var request: Request = Request(url)
    private lateinit var networking: UberNetworking

    @Before
    fun setup() {
        networking = Mockito.mock(UberNetworking::class.java)
        Whitebox.setInternalState(underTest, "person", mockedPerson);
    }

    @Test
    fun testRequestInterceptor() {
        networking.execute(request, object : ResponseMapper<String> {
            override fun map(from: String) = from
        })

        networking.interceptors.forEach {
            Mockito.verify(it.interceptRequest(request))
        }
    }
}

fun Any.setInternalState(field: String, value: Any) {
    val c = this.javaClass
    try {
        val f = getFieldFromHierarchy(c, field)  // Checks superclasses.
        f.setAccessible(true)
        f.set(this, value)
    } catch (e: Exception) {
        throw RuntimeException(
            "Unable to set internal state on a private field. [...]", e
        )
    }
}
