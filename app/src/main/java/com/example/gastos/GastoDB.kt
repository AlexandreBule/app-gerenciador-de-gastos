package com.example.gastos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities= arrayOf(Gasto::class), version=1, exportSchema = false)
abstract class GastoDB : RoomDatabase() {
    abstract fun GastoDAO() : GastoDAO
    companion object {
        private var INSTANCE : GastoDB? = null
        fun getDatabase(ctx : Context) : GastoDB {
            if (INSTANCE == null) {
                synchronized(GastoDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        GastoDB::class.java,
                        "gasto.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}