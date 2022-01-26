package com.example.gameguide.favourite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameguide.R
import com.example.gameguide.dataClasses.FavouriteGame
import com.example.gameguide.databinding.FragmentFavouritePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FavouritePage : Fragment() {

    private var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var binding: FragmentFavouritePageBinding
    private lateinit var FavouriteList: ArrayList<FavouriteGame>
    private lateinit var FavouriteAdapter: FavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouritePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.ab_favourite)

        binding.rvFgame.layoutManager = LinearLayoutManager(context)
        binding.rvFgame.setHasFixedSize(true)
        FavouriteList = arrayListOf<FavouriteGame>()
        FavouriteAdapter = FavouriteAdapter(FavouriteList)
        binding.rvFgame.adapter = FavouriteAdapter

        getFavGames()
    }


    private fun getFavGames() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        fireStore.collection("Users").document(uid.toString()).collection("favorite")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            FavouriteList.add(dc.document.toObject(FavouriteGame::class.java))
                        }
                    }
                    binding.rvFgame.adapter?.notifyDataSetChanged()
                }
            })
    }
}