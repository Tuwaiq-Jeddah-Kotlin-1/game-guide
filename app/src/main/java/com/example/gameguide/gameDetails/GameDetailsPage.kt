package com.example.gameguide.gameDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.gameguide.R
import com.example.gameguide.data.GDdata.GameDetailsdata
import com.example.gameguide.dataClasses.FavouriteGame
import com.example.gameguide.databinding.FragmentGameDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_game_details.*


class GameDetailsPage : Fragment() {

private lateinit var binding: FragmentGameDetailsBinding
private val args: GameDetailsPageArgs by navArgs()
private val isFavorite: Boolean = true
    lateinit var drawRed: Drawable
    private val vm by lazy {
        ViewModelProvider(this).get(DetilsVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.currentGame.id
        loadGameDetails(id,view)



    }
    private fun loadGameDetails(id:Int,view: View) {
        vm.gamesDetails(id).observe(viewLifecycleOwner, { GD ->
            //drawRed = binding.root.resources.getDrawable(R.drawable.ic_baseline_favorite_24,binding.root.resources.newTheme())
            //drawRed.setTint(binding.root.resources.getColor(R.color.Red,binding.root.resources.newTheme()))
            //drawRed.setTintMode(PorterDuff.Mode.SRC_IN)

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
                    GD.playtime >= 1 -> {

                        (GD.playtime.toString() + "" + getString(R.string.GD_Hour)+"").also { tvGdPt.text = it }
                    }
                    GD.playtime == 2 -> {
                        (GD.playtime.toString() + "" + getString(R.string.GD_Hours2)+"").also { tvGdPt.text = it }
                    }
                    GD.playtime in 3..9 -> {
                        (GD.playtime.toString() + "" + getString(R.string.GD_Hours39)+"").also { tvGdPt.text = it }
                    }
                    GD.playtime >= 10 -> {
                        (GD.playtime.toString() + "" + getString(R.string.GD_Hours)+"").also { tvGdPt.text = it }
                    }
                    else -> tvGdPt.text = "N/A"
                }


                tvGdTitle.text = GD.name
                tvGdRate.text = GD.rating.toString()
                tvRateTop.text = "/${GD.rating_top}"

                var n = ""
                var max = 0
                for (i in GD.ratings.indices){
                    if (GD.ratings[i].title == "exceptional"){
                        if(GD.ratings[i].count >= max)
                            max = GD.ratings[i].count
                        /*binding.imageView13.setImageResource(R.drawable.windows)
                        binding.imageView13.visibility = View.VISIBLE*/
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
                        }
                        "recommended" -> {
                            tvRatings.text = getString(R.string.GD_recommended)
                            ivRating.setImageResource(R.drawable.like)

                        }
                        "meh" -> {
                            tvRatings.text = getString(R.string.GD_meh)
                            ivRating.setImageResource(R.drawable.meh)

                        }
                        "skip" -> {
                            tvRatings.text = getString(R.string.GD_skip)
                            ivRating.setImageResource(R.drawable.forbidden)

                        }else ->{
                        ivRating.visibility = View.GONE

                    }

                    }
                }


                tvGdRcount.text = GD.ratings_count.toString()

                //here for loop for order

                var n1 = ""
                var n2 = ""
                var n3 = ""
                var n4 = ""
                var max1 = 0
                var max2 = 0
                var max3 = 0
                var max4 = 0
                val emptyIntgArray = arrayOf<Int>()

                val list: MutableList<Int> = ArrayList()
                val sb = StringBuilder()
                val appendSeparator = false
                for (i in GD.ratings.indices) {
                    list += GD.ratings[i].count
                    list.sort()

                    for (item in list.indices) {

                        if (item == GD.ratings[i].count){
                            n1 += "${GD.ratings[i].title}, ${GD.ratings[i].count}.\n"
                        }
                    }

                    /*if (GD.ratings[i].title == "recommended") {
                        when {
                            GD.ratings[i].count >= max1 -> max1 = GD.ratings[i].count
                            GD.ratings[i].count >= max2 -> max2 = GD.ratings[i].count
                            GD.ratings[i].count >= max3 -> max3 = GD.ratings[i].count
                            GD.ratings[i].count >= max4 -> max4 = GD.ratings[i].count
                        }
                    }
                    if (GD.ratings[i].title == "meh") {
                        when {
                            GD.ratings[i].count >= max1 -> max1 = GD.ratings[i].count
                            GD.ratings[i].count >= max2 -> max2 = GD.ratings[i].count
                            GD.ratings[i].count >= max3 -> max3 = GD.ratings[i].count
                            GD.ratings[i].count >= max4 -> max4 = GD.ratings[i].count
                        }
                    }
                    if (GD.ratings[i].title == "skip") {
                        when {
                            GD.ratings[i].count >= max1 -> max1 = GD.ratings[i].count
                            GD.ratings[i].count >= max2 -> max2 = GD.ratings[i].count
                            GD.ratings[i].count >= max3 -> max3 = GD.ratings[i].count
                            GD.ratings[i].count >= max4 -> max4 = GD.ratings[i].count
                        }
                    }

                    if (max1 == GD.ratings[i].count) {
                        n1 += GD.ratings[i].title
                    }
                    if (max2 == GD.ratings[i].count) {
                        n2 += GD.ratings[i].title
                    }
                    if (max3 == GD.ratings[i].count) {
                        n3 += GD.ratings[i].title
                    }
                    if (max4 == GD.ratings[i].count) {
                        n4 += GD.ratings[i].title
                    }



                    when (n) {
                        "exceptional" -> {
                            tvRatings.text = "exceptional"
                            ivRating.setImageResource(R.drawable.ic_baseline_military_tech_24)
                        }
                        "recommended" -> {
                            tvRatings.text = "recommended"
                            ivRating.setImageResource(R.drawable.ic_baseline_thumb_up_24)

                        }
                        "meh" -> {
                            tvRatings.text = "meh"
                            ivRating.setImageResource(R.drawable.ic_baseline_thumb_down_24)

                        }
                        "skip" -> {
                            tvRatings.text = "skip"
                            ivRating.setImageResource(R.drawable.ic_baseline_not_interested_24)

                        }
                        else -> {
                            ivRating.visibility = View.GONE

                        }
                    }*/
                }
                /*for (i in GD.ratings.indices) {
                    if (GD.ratings[i].title == "exceptional") {
                        when {
                            GD.ratings[i].count >= max1 -> max1 = GD.ratings[i].count
                            GD.ratings[i].count >= max2 -> max2 = GD.ratings[i].count
                            GD.ratings[i].count >= max3 -> max3 = GD.ratings[i].count
                            GD.ratings[i].count >= max4 -> max4 = GD.ratings[i].count
                        }

                    }
                    if (GD.ratings[i].title == "recommended") {
                        when {
                            GD.ratings[i].count >= max1 -> max1 = GD.ratings[i].count
                            GD.ratings[i].count >= max2 -> max2 = GD.ratings[i].count
                            GD.ratings[i].count >= max3 -> max3 = GD.ratings[i].count
                            GD.ratings[i].count >= max4 -> max4 = GD.ratings[i].count
                        }
                    }
                    if (GD.ratings[i].title == "meh") {
                        when {
                            GD.ratings[i].count >= max1 -> max1 = GD.ratings[i].count
                            GD.ratings[i].count >= max2 -> max2 = GD.ratings[i].count
                            GD.ratings[i].count >= max3 -> max3 = GD.ratings[i].count
                            GD.ratings[i].count >= max4 -> max4 = GD.ratings[i].count
                        }
                    }
                    if (GD.ratings[i].title == "skip") {
                        when {
                            GD.ratings[i].count >= max1 -> max1 = GD.ratings[i].count
                            GD.ratings[i].count >= max2 -> max2 = GD.ratings[i].count
                            GD.ratings[i].count >= max3 -> max3 = GD.ratings[i].count
                            GD.ratings[i].count >= max4 -> max4 = GD.ratings[i].count
                        }
                    }

                    if (max1 == GD.ratings[i].count) {
                        n1 += GD.ratings[i].title
                    }
                    if (max2 == GD.ratings[i].count) {
                        n2 += GD.ratings[i].title
                    }
                    if (max3 == GD.ratings[i].count) {
                        n3 += GD.ratings[i].title
                    }
                    if (max4 == GD.ratings[i].count) {
                        n4 += GD.ratings[i].title
                    }



                    when (n) {
                        "exceptional" -> {
                            tvRatings.text = "exceptional"
                            ivRating.setImageResource(R.drawable.ic_baseline_military_tech_24)
                        }
                        "recommended" -> {
                            tvRatings.text = "recommended"
                            ivRating.setImageResource(R.drawable.ic_baseline_thumb_up_24)

                        }
                        "meh" -> {
                            tvRatings.text = "meh"
                            ivRating.setImageResource(R.drawable.ic_baseline_thumb_down_24)

                        }
                        "skip" -> {
                            tvRatings.text = "skip"
                            ivRating.setImageResource(R.drawable.ic_baseline_not_interested_24)

                        }
                        else -> {
                            ivRating.visibility = View.GONE

                        }
                    }
                }*/


                tvAbout.text = GD.description_raw


                var f=""
                if (GD.parent_platforms.indices.count() >=1) {
                    f = GD.parent_platforms[0].platform.name
                }
                var s =""
                val fs = f+s
                for (i in GD.parent_platforms.indices){
                    if(i <= GD.parent_platforms.lastIndex-1) {
                        s += ", ${GD.parent_platforms[i + 1].platform.name}"
                    }
                }
                tvPlatform.text = f+s

                if (GD.metacritic>=75){
                    tvGdMeta.text = GD.metacritic.toString()
                    tvGdMeta.setBackgroundResource(R.drawable.higher_75)
                    tvGdMeta.setTextColor(Color.parseColor("#4CAF50"))

                }else if(GD.metacritic in 1..74){
                    tvGdMeta.text = GD.metacritic.toString()
                    tvGdMeta.setBackgroundResource(R.drawable.lower_75)
                    tvGdMeta.setTextColor(Color.parseColor("#FF9800"))

                }else{
                    tvGdMeta.text = "N/A"

                }

                var f2=""
                if (GD.genres.indices.count() >=1) {
                    f2 = GD.genres[0].name
                }
                var s2 =""
                val fs2 = f2+s2
                for (i in GD.genres.indices){
                    if(i <= GD.genres.lastIndex-1) {
                        s2 += ", ${GD.genres[i+1].name}"
                    }
                }

                tvGeners.text = f2+s2

                tvDate.text = GD.released


                var f3=""
                if (GD.developers.indices.count() >=1) {
                    f3 = GD.developers[0].name
                }
                var s3 =""
                val fs3 = f3+s3
                for (i in GD.developers.indices){
                    if(i <= GD.developers.lastIndex-1) {
                        s3 += ", ${GD.developers[i + 1].name}"
                    }
                }
                tvDevelopers.text = f3+s3


                var f4=""
                if (GD.publishers.indices.count() >=1) {
                    f4 = GD.publishers[0].name
                }
                var s4 =""
                val fs4 = f4+s4
                for (i in GD.publishers.indices){
                    if(i <= GD.publishers.lastIndex-1) {
                        s4 += ", ${GD.publishers[i + 1].name}"
                    }
                }
                tvPublishers.text = f4+s4

                if(GD.esrb_rating?.name?.isNotEmpty() == true){
                    tvAgeRating.text = GD.esrb_rating.name
                }else{
                    tvDisAge.visibility = View.GONE
                    tvAgeRating.visibility = View.GONE
                }



                var f5=""
                if (GD.tags.indices.count() >=1) {
                    f5 = GD.tags[0].name
                }
                var s5 =""
                val fs5 = f5+s5
                for (i in GD.tags.indices){
                    if(i <= GD.tags.lastIndex-1) {
                        s5 += ", ${GD.tags[i + 1].name}"
                    }
                }
                tvTags.text = f5+s5

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
                        //binding.fabGdFav.setImageDrawable(drawRed)
                        binding.fabGdFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                        Toast.makeText(context,"favorite",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    if (onClick) {
                        docRef.set(fav)
                        //binding.fabGdFav.setImageDrawable(drawRed)
                        binding.fabGdFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                    }else {
                        binding.fabGdFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        Toast.makeText(context,"not favorite",Toast.LENGTH_SHORT).show()

                    }
                }
            }
            //if (item)
            /*db.collection("Users").document("$uId").collection("favorite").document(args.currentGame.title).set(fav)
            Toast.makeText(context, "here", Toast.LENGTH_LONG).show()*/


        } catch (e: Exception) {

                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                Log.e("FUNCTION createUserFirestore", "${e.message}")

        }

    /*val uId = FirebaseAuth.getInstance().currentUser?.uid
        try {
            //coroutine
            val db = FirebaseFirestore.getInstance()
            val fav = FavouriteGame(args.currentGame.title,args.currentGame.rating,args.currentGame.metacritic,args.currentGame.released,args.currentGame.Background,args.currentGame.playtime,args.currentGame.ratingsCount)
            db.collection("Users").document("$uId").collection("favorite").document(args.currentGame.title).set(fav)
            Toast.makeText(context, "here", Toast.LENGTH_LONG).show()


        } catch (e: Exception) {

                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                Log.e("FUNCTION createUserFirestore", "${e.message}")

        }*/
    }

}