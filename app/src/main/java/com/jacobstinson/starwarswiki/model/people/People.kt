package com.jacobstinson.starwarswiki.model.people

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class People(@PrimaryKey var id: Int)