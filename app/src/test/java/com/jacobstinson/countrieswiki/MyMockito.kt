package com.jacobstinson.countrieswiki

import org.mockito.Mockito

object MyMockito {
    fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    fun <T> any(type: Class<T>): T {
        return Mockito.any<T>(type)
    }

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}