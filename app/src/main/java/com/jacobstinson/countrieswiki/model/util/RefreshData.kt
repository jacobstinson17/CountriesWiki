package com.jacobstinson.countrieswiki.model.util

import java.util.*

object RefreshData {
    private const val FRESH_TIMEOUT_IN_DAYS = 30

    fun getMaxRefreshTime(currentDate: Date): Date {
        val cal = Calendar.getInstance()
        cal.time = currentDate
        cal.add(Calendar.DAY_OF_YEAR, -FRESH_TIMEOUT_IN_DAYS)
        return cal.time
    }
}