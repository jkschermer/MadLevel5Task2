package com.hva.madlevel5task2.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hva.madlevel5task2.model.Game

@Dao
interface GameDao {

    @Insert
    fun insertGame(game: Game)

    @Query("select * from gameTable")
    fun getGames():LiveData<List<Game?>>

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM gameTable")
    suspend fun deleteAllGames()
}