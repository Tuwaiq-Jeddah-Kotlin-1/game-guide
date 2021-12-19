package com.example.gameguide.homepage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gameguide.R
import com.example.gameguide.databinding.FragmentForgetPasswordBinding
import com.example.gameguide.databinding.FragmentHomepageBinding


class Homepage : Fragment() {
    private lateinit var binding:FragmentHomepageBinding
    private val vm by lazy {
        ViewModelProvider(this).get(HomeVM::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomepageBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvGame.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )//GridLayoutManager(this, 2)

        loadMovieImages("3")
    }

    private fun loadMovieImages(pageNumber:String) {
        vm.fetchIntrestingList(pageNumber).observe(requireActivity(), {
            binding.rvGame.adapter = GameAdapter(it.results)
            Log.d("Movie Main Response", it.results.toString())
        })
    }

}



