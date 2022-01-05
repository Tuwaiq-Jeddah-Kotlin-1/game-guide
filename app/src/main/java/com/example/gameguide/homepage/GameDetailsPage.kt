package com.example.gameguide.homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.gameguide.R
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

        //drawRed = binding.root.resources.getDrawable(R.drawable.ic_baseline_favorite_24,binding.root.resources.newTheme())
        //drawRed.setTint(binding.root.resources.getColor(R.color.Red,binding.root.resources.newTheme()))
        //drawRed.setTintMode(PorterDuff.Mode.SRC_IN)

        with(binding){
            tvGdTitle.text = args.currentGame.title
            tvGdRate.text = args.currentGame.rating
            tvGdRcount.text = args.currentGame.ratingsCount.toString()
            tvGdMeta.text = args.currentGame.metacritic.toString()
            tvGdDate.text = args.currentGame.released
            tvGdPt.text = args.currentGame.playtime
            ivGdPoster.load(args.currentGame.Background)

        }
        val col = args.currentGame.domin

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
            favourite(true)

        }
        favourite(false)



    }


    private fun favourite(onClick:Boolean){

        val uId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("Users").document("$uId").collection("favorite")
        try {
            //coroutine

            val fav = FavouriteGame(args.currentGame.title,args.currentGame.rating,args.currentGame.metacritic,args.currentGame.released,args.currentGame.Background,args.currentGame.playtime,args.currentGame.ratingsCount)
            val docRef = db.collection("Users").document("$uId").collection("favorite").document(args.currentGame.title)
            ref.document(args.currentGame.title).get().addOnCompleteListener {
                if (it.result!!.exists()){
                    if (onClick){
                        ref.document(args.currentGame.title).delete()
                        binding.fabGdFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }else{
                        //binding.fabGdFav.setImageDrawable(drawRed)
                        binding.fabGdFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                        Toast.makeText(context,"favorite",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    if (onClick) {
                        db.collection("Users").document("$uId").collection("favorite")
                            .document(args.currentGame.title).set(fav)
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