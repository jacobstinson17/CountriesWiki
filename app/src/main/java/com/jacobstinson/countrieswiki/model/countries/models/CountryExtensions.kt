package com.jacobstinson.countrieswiki.model.countries.models

fun List<Country>.hasOutdatedData(minRefreshMs: Long): Boolean {
    return this.any { country -> country.lastRefreshMs < minRefreshMs }
}