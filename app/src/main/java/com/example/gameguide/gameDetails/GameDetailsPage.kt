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
                tvGdTitle.text = GD.name
                tvGdRate.text = GD.rating.toString()
                tvGdRcount.text = GD.ratings_count.toString()
                tvGdMeta.text = GD.metacritic.toString()
                tvGdDate.text = GD.released
                tvGdPt.text = GD.playtime.toString()
                ivGdPoster.load(GD.background_image)

            }

            for (i in GD.parent_platforms.indices){
                if (GD.parent_platforms[i].platform.name == "PC"){
                    binding.imageView7.setImageResource(R.drawable.windows)
                    binding.imageView7.visibility = View.VISIBLE
                }
                if (GD.parent_platforms[i].platform.name == "PlayStation"){
                    binding.imageView8.setImageResource(R.drawable.playstation)
                    binding.imageView8.visibility = View.VISIBLE
                }
                if (GD.parent_platforms[i].platform.name == "Xbox"){
                    binding.imageView9.setImageResource(R.drawable.xbox)
                    binding.imageView9.visibility = View.VISIBLE
                }
                if (GD.parent_platforms[i].platform.name == "Nintendo"){
                    binding.imageView10.setImageResource(R.drawable.nintendo)
                    binding.imageView10.visibility = View.VISIBLE
                }
                if (GD.parent_platforms[i].platform.name == "iOS"){
                    binding.imageView11.setImageResource(R.drawable.apple)
                    binding.imageView11.visibility = View.VISIBLE
                }
                if (GD.parent_platforms[i].platform.name == "Android"){
                    binding.imageView12.setImageResource(R.drawable.android)
                    binding.imageView12.visibility = View.VISIBLE
                }
            }

            val col = GD.dominant_color

            /*if(arjobjphob == "playstation")
                bbtb.rbrbw.setimage(R.Drawable.sony )
            elseif()*/

            binding.clDetails.setBackgroundColor(Color.parseColor("#$col"))

            binding.fabGdShare.setOnClickListener {
                val title: String = tvGdTitle.text.toString()
                val rating: String = tvGdRate.text.toString()
                val message : String =  "game name: $title\n game rating: $rating\n\n download Game Guide to see latest game updates"

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

            val fav = FavouriteGame(id.name,id.rating.toString(),id.metacritic,id.released,id.background_image,id.playtime.toString(),id.ratings_count)
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