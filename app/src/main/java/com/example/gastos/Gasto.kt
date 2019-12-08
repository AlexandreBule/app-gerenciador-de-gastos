package com.example.gastos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Gastos")
data class Gasto(@PrimaryKey val description: String, val price: Double, var tagGasto: String, val location: String, val day: String, val month: String, val year: String, val image: String) {
    override fun toString(): String {
        return description
    }
}