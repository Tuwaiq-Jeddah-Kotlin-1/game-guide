package com.example.gameguide.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gameguide.GameData
import com.example.gameguide.R
import com.example.gameguide.data.Results
import com.example.gameguide.databinding.GameRvItemBinding

class GameAdapter(val gameData: List<Results>) : RecyclerView.Adapter<CustomHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val bind = DataBindingUtil.inflate<GameRvItemBinding>(LayoutInflater.from(parent.context), R.layout.game_rv_item,parent,false)
        return CustomHolder(bind)

    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val game= gameData[position]
        holder.bind(game)

    }

    override fun getItemCount(): Int {
        return gameData.size
    }


}

class CustomHolder(private val binding: GameRvItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(game: Results) {
         binding.tvTitle.setText(game.name)
        val title = binding.tvTitle.text.toString()

         binding.ivPoster.load(game.background_image)

         binding.tvRate.setText(game.rating.toString())
        val rate = binding.tvRate.text.toString()

         binding.tvDate.setText(game.released)
        val date = binding.tvDate.text.toString()

        val metacritic = game.metacritic
        val playtime = game.playtime
        val ratingsCount = game.ratings_count

        binding.root.setOnClickListener{
            val gamee = GameData(title,rate,metacritic,
                date, game.background_image, playtime.toString(),ratingsCount )
            /*findNavController().navigate(actionHomepageToGameDetails(game))*/
            val action = HomepageDirections.actionHomepageToGameDetails(gamee)
            binding.root.findNavController().navigate(action)
        }
    }

}

