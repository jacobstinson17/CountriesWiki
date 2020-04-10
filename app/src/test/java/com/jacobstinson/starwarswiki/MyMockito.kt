package com.jacobstinson.starwarswiki

import org.mockito.Mockito

object MyMockito {
    fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}