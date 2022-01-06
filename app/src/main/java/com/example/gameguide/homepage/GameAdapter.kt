package com.example.gameguide.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gameguide.GameData
import com.example.gameguide.R
import com.example.gameguide.data.Results
import com.example.gameguide.databinding.GameRvItemBinding

class GameAdapter(private val gameData: List<Results>) : RecyclerView.Adapter<CustomHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val bind = DataBindingUtil.inflate<GameRvItemBinding>(LayoutInflater.from(parent.context), R.layout.game_rv_item,parent,false)
        return CustomHolder(bind)

    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val game = gameData[position]
        holder.bind(game)

    }

    override fun getItemCount(): Int {
        return gameData.size
    }


}

class CustomHolder(private val binding: GameRvItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(game: Results) {
        binding.tvTitle.text = game.name
        val title = binding.tvTitle.text.toString()

         //binding.ivPoster.load(game.background_image)
         binding.ivPoster.load(game.short_screenshots[1].image)

        binding.tvRate.text = game.rating.toString()
        val rate = binding.tvRate.text.toString()



        for (i in game.parent_platforms.indices){
            if (game.parent_platforms[i].platform.name == "PC"){
                binding.imageView.setImageResource(R.drawable.windows)
                binding.imageView.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "PlayStation"){
                binding.imageView2.setImageResource(R.drawable.playstation)
                binding.imageView2.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "Xbox"){
                binding.imageView3.setImageResource(R.drawable.xbox)
                binding.imageView3.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "Nintendo"){
                binding.imageView4.setImageResource(R.drawable.nintendo)
                binding.imageView4.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "iOS"){
                binding.imageView5.setImageResource(R.drawable.apple)
                binding.imageView5.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "Android"){
                binding.imageView6.setImageResource(R.drawable.android)
                binding.imageView6.visibility = View.VISIBLE
            }
        }

        val pPlatform = game.parent_platforms!!


        /* binding.tvDate.text = game.released
         val date = binding.tvDate.text.toString()*/

        val date = game.released
        val metacritic = game.metacritic
        val playtime = game.playtime
        val ratingsCount = game.ratings_count
        val col = game.dominant_color


        binding.root.setOnClickListener{
            val gamee = GameData(title,rate,metacritic,
                date, game.background_image, playtime.toString(),ratingsCount, col, pPlatform)
            /*findNavController().navigate(actionHomepageToGameDetails(game))*/
            val action = HomepageDirections.actionHomepageToGameDetails(gamee)
            binding.root.findNavController().navigate(action)
        }
    }

}

