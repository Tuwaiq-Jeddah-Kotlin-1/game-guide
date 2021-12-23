package com.example.gameguide

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.gameguide.databinding.FragmentGameDetailsBinding
import kotlinx.android.synthetic.main.fragment_game_details.*

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

        binding.fabGdShare.setOnClickListener {
            val title: String = tvGdTitle.text.toString()
            val rating: String = tvGdRate.text.toString()
            val message : String =  "game name:$title\n game rating:$rating\n\n download game guide to see latest game updates"

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent,"share to : "))
        }



    }
}