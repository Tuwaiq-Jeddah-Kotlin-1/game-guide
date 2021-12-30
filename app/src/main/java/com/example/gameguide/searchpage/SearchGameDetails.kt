package com.example.gameguide.searchpage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.gameguide.databinding.FragmentSearchGameDetailsBinding
import kotlinx.android.synthetic.main.fragment_game_details.*


class SearchGameDetails : Fragment() {

    private lateinit var binding: FragmentSearchGameDetailsBinding
    private val args: SearchGameDetailsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
           binding = FragmentSearchGameDetailsBinding.inflate(inflater, container, false)
           return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSgdTitle.text = args.currentSearchGame.title
        binding.tvSgdRate.text = args.currentSearchGame.rating
        binding.tvSgdRcount.text = args.currentSearchGame.ratingsCount.toString()
        binding.tvSgdMeta.text = args.currentSearchGame.metacritic.toString()
        binding.tvSgdDate.text = args.currentSearchGame.released
        binding.tvSgdPt.text = args.currentSearchGame.playtime
        binding.ivSgdPoster.load(args.currentSearchGame.Background)

        binding.fabSgdShare.setOnClickListener {
            val title: String = binding.tvSgdTitle.text.toString()
            val rating: String = binding.tvSgdDate.text.toString()
            val message =  "game name:$title\n game rating:$rating\n\n download game guide to see latest game updates"

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent,"share to : "))
        }


    }
}