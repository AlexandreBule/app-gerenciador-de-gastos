package com.example.gastos

import androidx.room.*

@Dao
interface GastoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirGasto(vararg gasto:Gasto)
    @Update
    fun atualizarGasto(vararg gasto:Gasto)
    @Delete
    fun removerGasto(vararg gasto:Gasto)
    @Query("SELECT * FROM Gastos")
    fun todosGastos() : List<Gasto>
    @Query("SELECT * FROM Gastos WHERE description LIKE :q")
    fun buscaGastoPelaDescricao(q : String) : Gasto
    @Query("SELECT * FROM Gastos WHERE month LIKE :q")
    fun buscaGastoPeloMes(q : String) : Array<Gasto>
    @Query("SELECT * FROM Gastos WHERE year LIKE :q")
    fun buscaGastoPeloAno(q : String) : Gasto
}