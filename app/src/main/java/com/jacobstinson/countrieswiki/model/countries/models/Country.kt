package com.jacobstinson.countrieswiki.model.countries.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Country(@PrimaryKey val code: String, val name: String, val phone: String, val capital: String,
              val currency: String, val continentCode: String, val continentName: String, var lastRefresh: Date)