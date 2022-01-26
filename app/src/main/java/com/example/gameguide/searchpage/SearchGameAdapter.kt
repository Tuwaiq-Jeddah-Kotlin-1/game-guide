package com.example.gameguide.searchpage

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
import com.example.gameguide.data.GDdata.Name
import com.example.gameguide.data.Results
import com.example.gameguide.databinding.GameRvItemBinding

class SearchGameAdapter(private val gameData: List<Results>) :
    RecyclerView.Adapter<CustomHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val bind = DataBindingUtil.inflate<GameRvItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.game_rv_item,
            parent,
            false
        )
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

        val id = game.id

        binding.tvTitle.text = game.name

        binding.ivPoster.load(game.background_image)

        var n = ""
        var max = 0
        for (i in game.ratings.indices) {
            if (game.ratings[i].count != 0) {

                if (game.ratings[i].title == "exceptional") {
                    if (game.ratings[i].count >= max) {
                        max = game.ratings[i].count
                        n = game.ratings[i].title
                    }
                }
                if (game.ratings[i].title == "recommended") {
                    if (game.ratings[i].count >= max) {
                        max = game.ratings[i].count
                        n = game.ratings[i].title
                    }
                }
                if (game.ratings[i].title == "meh") {
                    if (game.ratings[i].count >= max) {
                        max = game.ratings[i].count
                        n = game.ratings[i].title
                    }
                }
                if (game.ratings[i].title == "skip") {
                    if (game.ratings[i].count >= max) {
                        max = game.ratings[i].count
                        n = game.ratings[i].title
                    }

                }
            }
        }

        when (n) {
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
            }
            "" -> {
                binding.imageView13.setImageResource(R.drawable.sleep)
            }

        }


        for (i in game.parent_platforms.indices) {
            if (game.parent_platforms[i].platform.name == "PC") {
                binding.imageView.setImageResource(R.drawable.windowss)
                binding.imageView.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "PlayStation") {
                binding.imageView2.setImageResource(R.drawable.playstation)
                binding.imageView2.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "Xbox") {
                binding.imageView3.setImageResource(R.drawable.xbox)
                binding.imageView3.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "Nintendo") {
                binding.imageView4.setImageResource(R.drawable.nintendo)
                binding.imageView4.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "iOS") {
                binding.imageView5.setImageResource(R.drawable.apple)
                binding.imageView5.visibility = View.VISIBLE
            }
            if (game.parent_platforms[i].platform.name == "Android") {
                binding.imageView6.setImageResource(R.drawable.android)
                binding.imageView6.visibility = View.VISIBLE
            }
        }

        when {
            game.metacritic >= 75 -> {
                binding.tvItemMetacritic.text = game.metacritic.toString()
                binding.tvItemMetacritic.setBackgroundResource(R.drawable.higher_75)
                binding.tvItemMetacritic.setTextColor(Color.parseColor("#4CAF50"))
            }
            game.metacritic in 1..74 -> {
                binding.tvItemMetacritic.text = game.metacritic.toString()
                binding.tvItemMetacritic.setBackgroundResource(R.drawable.lower_75)
                binding.tvItemMetacritic.setTextColor(Color.parseColor("#FF9800"))
            }
            else -> {
                binding.tvItemMetacritic.text = "N/A"
            }
        }

        binding.tvItemRating.text = game.added.toString()


        binding.tvGenereItem.text = listItem(game.genres)

        binding.tvReleased.text = game.released


        val isVisible: Boolean = game.visibility

        binding.expandableView.visibility = if (isVisible) View.VISIBLE else View.GONE

        binding.ivItemExpand.setOnClickListener {
            game.visibility = !game.visibility
            binding.expandableView.visibility = if (game.visibility) View.VISIBLE else View.GONE
            if (game.visibility) {
                binding.ivItemExpand.setImageResource(R.drawable.ic_baseline_expand_less_24)
            } else {
                binding.ivItemExpand.setImageResource(R.drawable.ic_baseline_expand_more_24)
            }
        }

        binding.root.setOnClickListener {
            val gameD = GameData(id)
            val action = SearchPageDirections.actionSearchPageToGameDetails(gameD)
            binding.root.findNavController().navigate(action)
        }
    }
}

private fun <T : Name> listItem(coll: List<T>): String {
    var f = ""
    if (coll.indices.count() >= 1) {
        f = coll[0].name
    }
    var s = ""
    for (i in coll.indices) {
        if (i <= coll.lastIndex - 1) {
            s += ", ${coll[i + 1].name}"
        }
    }
    return f + s
}
