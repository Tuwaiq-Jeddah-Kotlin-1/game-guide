package com.example.gameguide.gameDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.gameguide.R
import com.example.gameguide.data.GDdata.GameDetailsdata
import com.example.gameguide.data.GDdata.Name
import com.example.gameguide.dataClasses.FavouriteGame
import com.example.gameguide.databinding.FragmentGameDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_game_details.*


class GameDetailsPage : Fragment() {

private lateinit var binding: FragmentGameDetailsBinding
private val args: GameDetailsPageArgs by navArgs()
    private val vm by lazy {
        ViewModelProvider(this)[DetilsVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.ab_game_details)

        val id = args.currentGame.id
        loadGameDetails(id)
    }

    private fun loadGameDetails(id: Int) {
        vm.gamesDetails(id).observe(viewLifecycleOwner, { GD ->
            with(binding){
                ivGdPoster.load(GD.background_image)

                for (i in GD.parent_platforms.indices){
                    if (GD.parent_platforms[i].platform.name == "PC"){
                        ivPc.setImageResource(R.drawable.windowss)
                        ivPc.visibility = View.VISIBLE
                    }
                    if (GD.parent_platforms[i].platform.name == "PlayStation"){
                        ivPS.setImageResource(R.drawable.playstation)
                        ivPS.visibility = View.VISIBLE
                    }
                    if (GD.parent_platforms[i].platform.name == "Xbox"){
                        ivXb.setImageResource(R.drawable.xbox)
                        ivXb.visibility = View.VISIBLE
                    }
                    if (GD.parent_platforms[i].platform.name == "Nintendo"){
                        ivNs.setImageResource(R.drawable.nintendo)
                        ivNs.visibility = View.VISIBLE
                    }
                    if (GD.parent_platforms[i].platform.name == "iOS"){
                        ivIos.setImageResource(R.drawable.apple)
                        ivIos.visibility = View.VISIBLE
                    }
                    if (GD.parent_platforms[i].platform.name == "Android"){
                        ivAndroid.setImageResource(R.drawable.android)
                        ivAndroid.visibility = View.VISIBLE
                    }
                }

                tvGdDate.text = GD.released

                when {
                    GD.playtime == 1 -> {
                        (getString(R.string.GD_Hour)+"").also { tvGdPt.text = it }
                    }
                    GD.playtime == 2 -> {
                        //(GD.playtime.toString() + "" + getString(R.string.GD_Hours2)+"").also { tvGdPt.text = it }
                        "${GD.playtime.toString()} ${getString(R.string.GD_Hours2)}".also { tvGdPt.text = it }
                    }
                    GD.playtime in 3..10 -> {
                        //(GD.playtime.toString() + "" + getString(R.string.GD_Hours310)+"").also { tvGdPt.text = it }
                        "${GD.playtime.toString()} ${getString(R.string.GD_Hours310)}".also { tvGdPt.text = it }
                    }
                    GD.playtime >= 11 -> {
                        //(GD.playtime.toString() + "" + getString(R.string.GD_Hours)+"").also { tvGdPt.text = it }
                        "${GD.playtime.toString()} ${getString(R.string.GD_Hours)}".also { tvGdPt.text = it }
                    }
                    else -> tvGdPt.text = "N/A"
                }

                tvGdTitle.text = GD.name

                tvGdRate.text = GD.rating.toString()

                "/${GD.rating_top}".also { tvRateTop.text = it }

                var n = ""
                var max = 0
                for (i in GD.ratings.indices){
                    if (GD.ratings[i].title == "exceptional"){
                        if(GD.ratings[i].count >= max)
                            max = GD.ratings[i].count
                    }
                    if (GD.ratings[i].title  == "recommended"){
                        if(GD.ratings[i].count >= max)
                            max = GD.ratings[i].count
                    }
                    if (GD.ratings[i].title  == "meh"){
                        if(GD.ratings[i].count >= max)
                            max = GD.ratings[i].count
                    }
                    if (GD.ratings[i].title  == "skip"){
                        if(GD.ratings[i].count >= max)
                            max = GD.ratings[i].count
                    }

                    if(max == GD.ratings[i].count){
                        n += GD.ratings[i].title
                    }

                    when (n) {
                        "exceptional" -> {
                            tvRatings.text = getString(R.string.GD_exceptional)
                            ivRating.setImageResource(R.drawable.target)
                            tvNumOfRate.text = max.toString()
                        }
                        "recommended" -> {
                            tvRatings.text = getString(R.string.GD_recommended)
                            ivRating.setImageResource(R.drawable.like)
                            tvNumOfRate.text = max.toString()
                        }
                        "meh" -> {
                            tvRatings.text = getString(R.string.GD_meh)
                            ivRating.setImageResource(R.drawable.meh)
                            tvNumOfRate.text = max.toString()
                        }
                        "skip" -> {
                            tvRatings.text = getString(R.string.GD_skip)
                            ivRating.setImageResource(R.drawable.forbidden)
                            tvNumOfRate.text = max.toString()
                        }
                    }
                }

                tvGdRcount.text = GD.ratings_count.toString()

                tvAbout.text = GD.description_raw

                var f=""
                if (GD.parent_platforms.indices.count() >=1) {
                    f = GD.parent_platforms[0].platform.name
                }
                var s =""
                for (i in GD.parent_platforms.indices){
                    if(i <= GD.parent_platforms.lastIndex-1) {
                        s += ", ${GD.parent_platforms[i + 1].platform.name}"
                    }
                }

                (f+s).also { tvPlatform.text = it }


                when {
                    GD.metacritic>=75 -> {
                        tvGdMeta.text = GD.metacritic.toString()
                        tvGdMeta.setBackgroundResource(R.drawable.higher_75)
                        tvGdMeta.setTextColor(Color.parseColor("#4CAF50"))
                    }
                    GD.metacritic in 1..74 -> {
                        tvGdMeta.text = GD.metacritic.toString()
                        tvGdMeta.setBackgroundResource(R.drawable.lower_75)
                        tvGdMeta.setTextColor(Color.parseColor("#FF9800"))
                    }
                    else -> {
                        tvGdMeta.text = "N/A"
                    }
                }



                tvGeners.text = listItem(GD.genres)

                tvDate.text = GD.released

                 tvDevelopers.text = listItem(GD.developers)

                tvPublishers.text = listItem(GD.publishers)

                if(GD.esrb_rating?.name?.isNotEmpty() == true){
                    tvAgeRating.text = GD.esrb_rating.name
                }else{
                    tvAgeRating.text = getString(R.string.gd_not_rated)
                }

                tvTags.text= listItem(GD.tags)

                tvWebsite.text = GD.website
            }

            /*val col = GD.dominant_color
            binding.clDetails.setBackgroundColor(Color.parseColor("#$col"))
*/

            binding.fabGdShare.setOnClickListener {
                val title: String = tvGdTitle.text.toString()
                val rating: String = tvGdRate.text.toString()
                val message=  "game name: $title\n game rating: $rating\n\n download Game Guide to see latest game details"

                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent,"share to : "))
            }

            binding.fabGdFav.setOnClickListener {
                favourite(true,GD)
            }
            favourite(false,GD)
        })
    }

    private fun <T:Name>listItem(coll: List<T>):String{
        var f=""
        if (coll.indices.count() >=1) {
            f = coll[0].name
        }
        var s =""
        for (i in coll.indices){
            if(i <= coll.lastIndex-1) {
                s += ", ${coll[i + 1].name}"
            }
        }
        return f+s
    }

    private fun favourite(onClick: Boolean, id: GameDetailsdata){

        val uId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("Users").document("$uId").collection("favorite")
        try {
            //coroutine
            val fav = FavouriteGame(id.id,id.name,id.background_image,tvRatings.text.toString(),tvPlatform.text.toString(),id.metacritic,id.added,tvGeners.text.toString(),id.released,id.visibility)
            val docRef = db.collection("Users").document("$uId").collection("favorite").document(id.name)
            ref.document(id.name).get().addOnCompleteListener {
                if (it.result!!.exists()){
                    if (onClick){
                        ref.document(id.name).delete()
                        binding.fabGdFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }else{
                        binding.fabGdFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                    }
                }else{
                    if (onClick) {
                        docRef.set(fav)
                        binding.fabGdFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                    }else {
                        binding.fabGdFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                }
            }
        } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                Log.e("FUNCTION createUserFirestore", "${e.message}")
        }
    }
}