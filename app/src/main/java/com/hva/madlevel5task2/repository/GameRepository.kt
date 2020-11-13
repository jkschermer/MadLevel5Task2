package com.hva.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hva.madlevel5task2.dao.GameDao
import com.hva.madlevel5task2.database.GameRoomDatabase
import com.hva.madlevel5task2.model.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao?

    init {
        val database =
            GameRoomDatabase.getDatabase(context)
            gameDao = database!!.gameDao()
    }

    fun getAllGames() : LiveData<List<Game?>> {
        return gameDao?.getGames() ?: MutableLiveData(emptyList())
    }

    fun insertGame(game: Game) {
        gameDao?.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao?.deleteGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao?.deleteAllGames()
    }
}