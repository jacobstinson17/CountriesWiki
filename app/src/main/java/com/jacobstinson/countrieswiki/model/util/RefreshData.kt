package com.jacobstinson.countrieswiki.model.util

object RefreshData {

    private const val THIRTY_DAYS_IN_MS = 1000L * 60L * 60L * 24L * 30L

    fun getMinRefreshMs(): Long {
        return System.currentTimeMillis() - THIRTY_DAYS_IN_MS
    }
}