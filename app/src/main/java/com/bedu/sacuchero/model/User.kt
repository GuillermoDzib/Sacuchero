package com.bedu.sacuchero.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(@PrimaryKey @ColumnInfo(name = "name") var name: String, @ColumnInfo(name = "email") var email: String, @ColumnInfo(name = "pass") var pass: String, @ColumnInfo(name = "dir") var dir: String, @ColumnInfo(name = "kart") var kart: String)