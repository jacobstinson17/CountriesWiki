package com.jacobstinson.starwarswiki

import com.jacobstinson.starwarswiki.model.util.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}