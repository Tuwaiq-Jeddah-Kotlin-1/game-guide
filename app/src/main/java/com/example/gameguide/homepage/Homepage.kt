package com.example.gameguide.homepage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gameguide.R
import com.example.gameguide.databinding.FragmentHomepageBinding


class Homepage : Fragment() {
    private lateinit var binding:FragmentHomepageBinding
    private val vm by lazy {
        ViewModelProvider(this)[HomeVM::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomepageBinding.inflate(inflater, container, false)

        return binding.root

    }
    companion object {
        var i = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.ab_home)

        binding.tvHomePnum.text = i.toString()

        loadMovieImages("$i")

        val minPage = 1
        val maxPage = 33076

        binding.ivHomeNext.setOnClickListener {
            if (i<maxPage-10) {
                i++
                loadMovieImages("$i")
                binding.tvHomePnum.text = i.toString()
            }else{
                binding.tvHomePnum.text = i.toString()
            }
        }

        binding.ivHome10Next.setOnClickListener {
            if (i<=maxPage) {
                i += 10
                loadMovieImages("$i")
                binding.tvHomePnum.text = i.toString()
            }else{
                binding.tvHomePnum.text = i.toString()
            }
        }

        binding.ivHomePrev.setOnClickListener {
           if(i<=minPage){
               i = 1
           }else{
               i--
               loadMovieImages("$i")
               binding.tvHomePnum.text = i.toString()
           }
        }

        binding.ivHome10Prev.setOnClickListener {
           if(i<minPage+10){
               i = 1
           }else{
               i -= 10
               loadMovieImages("$i")
               binding.tvHomePnum.text = i.toString()
           }
        }
    }

    private fun loadMovieImages(pageNumber:String) {
        vm.fetchIntrestingList(pageNumber).observe(viewLifecycleOwner, {
            binding.rvGame.adapter = GameAdapter(it.results)
            Log.d("Movie Main Response", it.results.toString())

            binding.rvGame.layoutManager = StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )
        })
    }
}



