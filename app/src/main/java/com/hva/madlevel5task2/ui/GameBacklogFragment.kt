package com.hva.madlevel5task2.ui

import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hva.madlevel5task2.R
import com.hva.madlevel5task2.model.Game
import com.hva.madlevel5task2.model.GameViewModel
import kotlinx.android.synthetic.main.fragment_backlog_game.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {
    private var games: ArrayList<Game> = arrayListOf()
    private lateinit var gameAdapter: GameListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.game_backlog_title)
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_backlog_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).changeNavBar((activity as MainActivity).supportActionBar!!,
            false)

        val fabBtnGameBacklog = view.findViewById(R.id.fabGameBacklog) as FloatingActionButton

        fabBtnGameBacklog.setColorFilter(Color.WHITE)

        fabGameBacklog.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        initRv()
        observeAddGameResults()
    }

    // override on options of main activity and when trash can is clicked will delete the list
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.deleteAll ->{
                viewModel.deleteAllGames()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRv() {
        gameAdapter = GameListAdapter(games)
        viewManager = LinearLayoutManager(activity)

        createItemTouchHelper().attachToRecyclerView(rvGameList)

        rvGameList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = gameAdapter
        }

    }

    private fun observeAddGameResults() {
        viewModel.games.observe(viewLifecycleOwner, Observer {
            gameList ->
            this@GameBacklogFragment.games.clear()
            this@GameBacklogFragment.games.addAll(gameList as Collection<Game>)
            games.sortWith(compareBy<Game> {it.releaseDate})
            gameAdapter.notifyDataSetChanged()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]

                viewModel.deleteGame(gameToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }
}