package com.jacobstinson.countrieswiki.model.util

import java.util.*

object RefreshData {
    private const val FRESH_TIMEOUT_IN_DAYS = 30

    fun getMinRefreshTime(): Date {
        val cal = Calendar.getInstance()
        cal.time = Date(System.currentTimeMillis())
        cal.add(Calendar.DAY_OF_YEAR, -FRESH_TIMEOUT_IN_DAYS)
        return cal.time
    }
}