package com.example.gameguide

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.gameguide.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfilePage : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getUserInfo()

        /*binding.btnProfileEdit.setOnClickListener{
            *//*dialogEditProfile()*//*
        }
        binding.btnProfileLogOut.setOnClickListener {

        }*/
    }

    /*private fun dialogEditProfile() {
        val view: View = layoutInflater.inflate(R.layout.bosh_change_profile, null)

        val builder = BottomSheetDialog(requireView()?.context)
        builder.setTitle("Forgot Password")

        val usernameEt = view.findViewById<EditText>(R.id.etChangeProfUsername)
        val userPhotoEt = view.findViewById<EditText>(R.id.etChangeProfPhone)
        val continueBtn = view.findViewById<Button>(R.id.btnChangeProfConfirm)


        continueBtn.setOnClickListener {
            val upDateUser = hashMapOf(
                "fullName" to "${edName}",
                "phoneNumber" to "${edPhoneNumber}"
            )
            val userRef = Firebase.firestore.collection("Users")
            //-----------UID------------------------
            val uId = FirebaseAuth.getInstance().currentUser?.uid
            userRef.document("$uId").set(upDateUser, SetOptions.merge()).addOnCompleteListener {
                it
                when {
                    it.isSuccessful -> {
                        readUserData()
                        Toast.makeText(context, "UpDate ", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        //dialog error
                    }
                }
            }
        }
        builder.setContentView(view)

        builder.show()
    }*/


    private fun getUserInfo() = CoroutineScope(Dispatchers.IO).launch {

        val uId = FirebaseAuth.getInstance().currentUser?.uid
        try {
            //coroutine
            val db = FirebaseFirestore.getInstance()
            db.collection("Users").document("$uId")
                .get().addOnCompleteListener {

                    if (it.result?.exists()!!) {
                        //+++++++++++++++++++++++++++++++++++++++++
                        val name = it.result!!.getString("userName")
                        val userPhone = it.result!!.getString("userPhone")
                        val userEmail = it.result!!.getString("userEmail")

                        binding.tvProfileUserName.text= name.toString()
                        binding.tvProfilePhone.text= userPhone.toString()
                        binding.tvProfileEmail.text= userEmail.toString()

                    } else {
                        Log.e("error", "error in displaying")
                    }
                }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                // Toast.makeText(coroutineContext,0,0, e.message, Toast.LENGTH_LONG).show()
                Log.e("FUNCTION createUserFirestore", "${e.message}")
            }
        }
    }
}