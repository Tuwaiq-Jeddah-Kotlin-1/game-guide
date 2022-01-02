package com.example.gameguide.favourite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gameguide.R
import com.example.gameguide.dataClasses.FavouriteGame
import com.example.gameguide.databinding.FragmentFavouritePageBinding
import com.example.gameguide.databinding.FragmentHomepageBinding
import com.example.gameguide.homepage.GameAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

class FavouritePage : Fragment() {

    private var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var binding: FragmentFavouritePageBinding
    private lateinit var articleList :ArrayList<FavouriteGame>
    private lateinit var articleAdapter :FavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritePageBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        articleList = arrayListOf<FavouriteGame>()
        articleAdapter = FavouriteAdapter(articleList)
        binding.recyclerView.adapter = articleAdapter

        getAllArticles()
    }


    private fun getAllArticles(){
        var uid = FirebaseAuth.getInstance().currentUser?.uid
        fireStore.collection("Users").document(uid.toString()).collection("favorite").addSnapshotListener(object :EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("Firestore",error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        articleList.add(dc.document.toObject(FavouriteGame::class.java))
                    }
                }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        })
    }
}