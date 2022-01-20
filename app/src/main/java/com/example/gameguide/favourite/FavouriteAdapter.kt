package com.example.gameguide.favourite

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gameguide.GameData
import com.example.gameguide.R
import com.example.gameguide.dataClasses.FavouriteGame
import com.example.gameguide.databinding.GameRvItemBinding

class FavouriteAdapter (private val gameData: List<FavouriteGame>) : RecyclerView.Adapter<CustomHolder>() {

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

    fun bind(game: FavouriteGame) {

        val id = game.id

        binding.tvTitle.text = game.name

        binding.ivPoster.load(game.background)

        binding.tvItemRating.text = game.ratings

        if (game.parent_platforms.contains("PC")){
            binding.imageView.setImageResource(R.drawable.windowss)
            binding.imageView.visibility = View.VISIBLE
        }
        if(game.parent_platforms.contains("PlayStation")){
            binding.imageView2.setImageResource(R.drawable.playstation)
            binding.imageView2.visibility = View.VISIBLE
        }
        if(game.parent_platforms.contains("Xbox")){
            binding.imageView3.setImageResource(R.drawable.xbox)
            binding.imageView3.visibility = View.VISIBLE
        }
        if(game.parent_platforms.contains("Nintendo")){
            binding.imageView4.setImageResource(R.drawable.nintendo)
            binding.imageView4.visibility = View.VISIBLE
        }
        if(game.parent_platforms.contains("iOS")){
            binding.imageView5.setImageResource(R.drawable.apple)
            binding.imageView5.visibility = View.VISIBLE
        }
        if(game.parent_platforms.contains("Android")){
            binding.imageView6.setImageResource(R.drawable.android)
            binding.imageView6.visibility = View.VISIBLE
        }

        when {
            game.metacritic>=75 -> {
                binding.tvItemMetacritic.text = game.metacritic.toString()
                binding.tvItemMetacritic.setBackgroundResource(R.drawable.higher_75)
                binding.tvItemMetacritic.setTextColor(Color.parseColor("#4CAF50"))
            }game.metacritic in 1..74 -> {
                binding.tvItemMetacritic.text = game.metacritic.toString()
                binding.tvItemMetacritic.setBackgroundResource(R.drawable.lower_75)
                binding.tvItemMetacritic.setTextColor(Color.parseColor("#FF9800"))
            }else -> {
                binding.tvItemMetacritic.text = "N/A"
            }
        }

        binding.tvItemRating.text = game.added.toString()

        when (game.ratings) {
            "exceptional" -> {
                binding.imageView13.setImageResource(R.drawable.target)
            }
            "recommended" -> {
                binding.imageView13.setImageResource(R.drawable.like)
            }
            "meh" -> {
                binding.imageView13.setImageResource(R.drawable.meh)
            }
            "skip" -> {
                binding.imageView13.setImageResource(R.drawable.forbidden)
            }"not rated yet" ->{
                binding.imageView13.setImageResource(R.drawable.sleep)
            }else->{
                binding.imageView13.setImageResource(R.drawable.sleep)
            }
        }

        binding.tvGenereItem.text = game.genres

        binding.tvReleased.text=game.released

        val isVisible : Boolean = game.visibility

        binding.expandableView.visibility = if (isVisible) View.VISIBLE else View.GONE

        binding.ivItemExpand.setOnClickListener {
            game.visibility = !game.visibility
            binding.expandableView.visibility = if (game.visibility) View.VISIBLE else View.GONE
            if(game.visibility){
                binding.ivItemExpand.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }else{
                binding.ivItemExpand.setImageResource(R.drawable.ic_baseline_expand_more_24)
            }
        }

        binding.root.setOnClickListener{
            val gameD = GameData(id)
            val action = FavouritePageDirections.actionFavouritePageToGameDetails(gameD)
            binding.root.findNavController().navigate(action)
        }
    }

}