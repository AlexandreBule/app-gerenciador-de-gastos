package com.example.gastos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Gastos")
data class Gasto(@PrimaryKey(autoGenerate = true) val gastoId: Int = 0, val description: String, val price: Double, var tagGasto: String, val location: String, val day: String, val month: String, val year: String, val week: String, val image: String) {
    override fun toString(): String {
        return description
    }
}