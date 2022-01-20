package com.example.gameguide.searchpage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gameguide.R
import com.example.gameguide.databinding.FragmentSearchPageBinding


class SearchPage : Fragment() {
    private lateinit var binding: FragmentSearchPageBinding
    private val vm by lazy {
        ViewModelProvider(this)[SearchVM::class.java]
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchPageBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.ab_search)

        setHasOptionsMenu(true)
        binding.rvSgame.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        loadgames()
    }

    private fun loadgames(query: String? = null/*,pageNumber:String*/) {
        vm.searchGames(query/*,"3"*/).observe(viewLifecycleOwner, {
            if (query.isNullOrEmpty()) {
                binding.rvSgame.adapter = SearchGameAdapter(it.results)
            } else {
                binding.rvSgame.swapAdapter(SearchGameAdapter(it.results), false)
            }
            Log.d("game Main Response", it.results.toString())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchIcon: MenuItem = menu.findItem(R.id.app_bar_search)
        val TAG = "searchView"
        val searchView = searchIcon.actionView as SearchView
        searchView.imeOptions= searchView.imeOptions or EditorInfo.IME_FLAG_NO_EXTRACT_UI

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG, "Query text submit: $query")
                    loadgames(query?.trim())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "QueryTextChange:  $$newText")
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu,inflater)
    }
}