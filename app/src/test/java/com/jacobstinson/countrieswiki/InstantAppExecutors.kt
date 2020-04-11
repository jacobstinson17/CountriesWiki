package com.jacobstinson.countrieswiki

import com.jacobstinson.countrieswiki.model.util.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}