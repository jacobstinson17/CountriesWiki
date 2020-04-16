package com.jacobstinson.countrieswiki.model.countries.models

class CountryParameters(var orderByField: String = DEFAULT_ORDER_BY_FIELD, var isDescending: Boolean = DEFAULT_IS_DESCENDING) {

    companion object {
        const val DEFAULT_ORDER_BY_FIELD = "name"
        const val DEFAULT_IS_DESCENDING = false
    }
}