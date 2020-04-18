package com.jacobstinson.countrieswiki.model.countries.models

import java.lang.IllegalArgumentException

enum class CountrySortByFields {
    NAME,
    CAPITAL,
    CODE,
    CURRENCY,
    PHONE;

    companion object {

        fun getIndex(field: String): Int {
            return when(field.toUpperCase()) {
                NAME.name -> 0
                CAPITAL.name -> 1
                CODE.name -> 2
                CURRENCY.name -> 3
                PHONE.name -> 4
                else -> throw IllegalArgumentException("argument field is not a valid value.")
            }
        }
    }
}