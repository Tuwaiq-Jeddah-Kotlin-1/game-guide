package com.example.gameguide.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gameguide.R
import com.example.gameguide.data.Results
import com.example.gameguide.databinding.FragmentHomepageBinding
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
        binding.tvTitle.text = game.name
        binding.ivPoster.load(game.background_image)
        binding.tvRate.text = game.rating.toString()
        binding.tvDate.text = game.released
        }
    }

