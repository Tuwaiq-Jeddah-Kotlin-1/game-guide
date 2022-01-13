package com.example.gameguide.homepage

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

        val id = game.id

        binding.tvTitle.text = game.name
        //val title = binding.tvTitle.text.toString()

         //binding.ivPoster.load(game.background_image)
         binding.ivPoster.load(game.background_image)

       // binding.tvRate.text = game.rating.toString()
       // val rate = binding.tvRate.text.toString()

        val listName:ArrayList<Int>?=null
        var n = ""
        var max = 0

        /*for (i in game.ratings.indices){
            listName?.add(game.ratings[i].count)
            max = listName?.maxOrNull()!!


            if(max == game.ratings[i].count){
                n += game.ratings[i].title
            }

            when (n) {
                "exceptional" -> {
                    binding.imageView13.setImageResource(R.drawable.ic_baseline_military_tech_24)
                }
                "recommended" -> {
                    binding.imageView13.setImageResource(R.drawable.ic_baseline_thumb_up_24)

                }
                "meh" -> {
                    binding.imageView13.setImageResource(R.drawable.ic_baseline_thumb_down_24)

                }
                "skip" -> {
                    binding.imageView13.setImageResource(R.drawable.ic_baseline_not_interested_24)

                }else ->{
                binding.imageView13.visibility = View.GONE

            }

            }

        }*/
        for (i in game.ratings.indices){
            if (game.ratings[i].title == "exceptional"){
                if(game.ratings[i].count >= max)
                    max = game.ratings[i].count
                /*binding.imageView13.setImageResource(R.drawable.windows)
                binding.imageView13.visibility = View.VISIBLE*/
            }
            if (game.ratings[i].title  == "recommended"){
                if(game.ratings[i].count >= max)
                    max = game.ratings[i].count
            }
            if (game.ratings[i].title  == "meh"){
                if(game.ratings[i].count >= max)
                    max = game.ratings[i].count
            }
            if (game.ratings[i].title  == "skip"){
                if(game.ratings[i].count >= max)
                    max = game.ratings[i].count
            }

            if(max == game.ratings[i].count){
                n += game.ratings[i].title
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

                }else ->{
                binding.imageView13.visibility = View.GONE

            }

            }
        }
        /*var max = listofVechileName?.maxOrNull()
        for (i in game.ratings.indices){
            if(max == game.ratings[i].count){
                n += game.ratings[i].title
            }
        }*/





        for (i in game.parent_platforms.indices){
            if (game.parent_platforms[i].platform.name == "PC"){
                binding.imageView.setImageResource(R.drawable.windowss)
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

        if (game.metacritic>=75){

            binding.tvItemMetacritic.text = game.metacritic.toString()
            binding.tvItemMetacritic.setBackgroundResource(R.drawable.higher_75)
            binding.tvItemMetacritic.setTextColor(Color.parseColor("#4CAF50"))

        }else{

            binding.tvItemMetacritic.text = game.metacritic.toString()
            binding.tvItemMetacritic.setBackgroundResource(R.drawable.lower_75)
            binding.tvItemMetacritic.setTextColor(Color.parseColor("#FF9800"))

        }


        binding.tvItemRating.text = game.added.toString()

        var f=""
        if (game.genres.indices.count() >=1) {
            f = game.genres[0].name
        }
        var s =""
        val fs = f+s
        for (i in game.genres.indices){
            if(i <= game.genres.lastIndex-1) {
                s += ", ${game.genres[i+1].name}"
            }
            /*do {
                s += "${game.genres[i].name} ,"
            }while (i != game.genres[i].name.lastIndex)
            s += "${game.genres[i].name}."*/




            /*if (i != game.genres[i].name.lastIndex){
                s += "${game.genres[i].name}, "
                binding.tvGenereItem.text = s

            }else{
                s += game.genres[i].name
                //s += "$i,"
                binding.tvGenereItem.text = s
            }*/
            /*if (i==game.genres[i].name.lastIndex){
                s += ".${game.genres[i].name}."
            }else{
                s += ",${game.genres[i].name} "
                //s += "$i,"
                binding.tvGenereItem.text = s
            }*/


        }

        binding.tvGenereItem.text = f+s

        binding.tvReleased.text=game.released

        val isvisible : Boolean = game.visibility

        binding.expandableView.visibility = if (isvisible) View.VISIBLE else View.GONE

        binding.ivItemExpand.setOnClickListener {
            game.visibility = !game.visibility
            binding.expandableView.visibility = if (game.visibility) View.VISIBLE else View.GONE
            if(game.visibility){
                binding.ivItemExpand.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }else{
                binding.ivItemExpand.setImageResource(R.drawable.ic_baseline_expand_more_24)
            }
         }



        //val pPlatform = game.parent_platforms!!


        /* binding.tvDate.text = game.released
         val date = binding.tvDate.text.toString()*/

        //val date = game.released
        //val metacritic = game.metacritic
        //val playtime = game.playtime
        //val ratingsCount = game.ratings_count
        //val col = game.dominant_color


        binding.root.setOnClickListener{
            val gamee = GameData(id)
            /*findNavController().navigate(actionHomepageToGameDetails(game))*/
            val action = HomepageDirections.actionHomepageToGameDetails(gamee)
            binding.root.findNavController().navigate(action)
        }
    }

}

