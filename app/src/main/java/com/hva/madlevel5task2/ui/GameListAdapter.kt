package com.hva.madlevel5task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hva.madlevel5task2.R
import com.hva.madlevel5task2.model.Game
import kotlinx.android.synthetic.main.item_game.view.*
import java.text.SimpleDateFormat

class GameListAdapter(private var games: List<Game>) :
    RecyclerView.Adapter<GameListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            fun databind(game: Game) {
                itemView.tvTitleGame.text = game.title.toString()

                val format = SimpleDateFormat("dd-MM-yyyy")
                val dateString = format.format(game.releaseDate!!)
                itemView.tvConsoleReleaseDate.text =
                    game.platform.toString() + " Release: " + dateString
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_game,
            parent, false
        ))
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }
}