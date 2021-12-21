package com.example.gameguide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.gameguide.databinding.FragmentGameDetailsBinding
import com.example.gameguide.databinding.FragmentHomepageBinding
import com.example.gameguide.databinding.FragmentSignInBinding

class GameDetailsPage : Fragment() {

private lateinit var binding: FragmentGameDetailsBinding
private val args: GameDetailsPageArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvGdTitle.text = args.currentGame.title
        binding.tvGdRate.text = args.currentGame.rating
        binding.tvGdRcount.text = args.currentGame.ratingsCount.toString()
        binding.tvGdMeta.text = args.currentGame.metacritic.toString()
        binding.tvGdDate.text = args.currentGame.released
        binding.tvGdPt.text = args.currentGame.playtime
        binding.ivGdPoster.load(args.currentGame.Background)



    }
}