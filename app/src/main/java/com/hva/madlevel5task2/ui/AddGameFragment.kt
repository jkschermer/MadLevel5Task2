package com.hva.madlevel5task2.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hva.madlevel5task2.R
import com.hva.madlevel5task2.model.Game
import com.hva.madlevel5task2.model.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_add.*
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {
    private val viewModel: GameViewModel by viewModels()
    private val simpleFormat = SimpleDateFormat("dd-MM-yyyy")

    @SuppressLint("ResourceType")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.add_game_title)
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fabBtnGameAdd = view.findViewById(R.id.fabGameAdd) as FloatingActionButton

        fabBtnGameAdd.setColorFilter(Color.WHITE)

        fabGameAdd.setOnClickListener{
            onAddGame()
        }

        (activity as MainActivity).changeNavBar((activity as MainActivity).supportActionBar!!,
        true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.deleteAll)

        if (item != null) {
            item.isVisible = false
        }
    }

    private fun onAddGame() {
        var errorMessage = false
        val gameTitle = etTitleGame.text.toString()
        val gamePlatform = etPlatform.text.toString()
        val gameReleaseDate = etReleaseDate.text.toString()

        val date = parseStringToDate(gameReleaseDate)

        // if the field is not empty insert the data
        if(gameTitle.isNotBlank() && gamePlatform.isNotBlank() && gameReleaseDate.isNotBlank()) {
            viewModel.insertGame(Game(gameTitle, gamePlatform, date))
            findNavController().popBackStack()
        }

        // some error handling


        if(gameTitle.isEmpty()) {
            errorMessage = true
            Toast.makeText(activity, R.string.empty_title, Toast.LENGTH_SHORT).show()
        }

        if(gamePlatform.isEmpty()) {
            errorMessage = true
            Toast.makeText(activity, R.string.empty_platform, Toast.LENGTH_SHORT).show()
        }

        if (gameReleaseDate.isEmpty()) {
            errorMessage = true
            Toast.makeText(activity, R.string.empty_release_date, Toast.LENGTH_SHORT).show()
        }

        if(errorMessage) {
            Toast.makeText(activity, R.string.not_valid_input, Toast.LENGTH_SHORT).show()
        }
    }

    private fun parseStringToDate(date: String?): Date {
        var result: Date? = null
        try {
            simpleFormat.isLenient = false
            result = simpleFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()

        }

        return result!!
    }
}