package com.jacobstinson.countrieswiki.model.countries.models

class CountryParameters(var orderByField: String = DEFAULT_ORDER_BY_FIELD, var isDescending: Boolean = DEFAULT_IS_DESCENDING,
                        var minLastRefreshMs: Long = DEFAULT_MIN_LAST_REFRESH_MS) {

    companion object {
        const val DEFAULT_ORDER_BY_FIELD = "name"
        const val DEFAULT_IS_DESCENDING = false
        const val DEFAULT_MIN_LAST_REFRESH_MS = 0L
    }
}