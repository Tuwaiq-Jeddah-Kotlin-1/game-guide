package com.example.gameguide

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.gameguide.databinding.FragmentForgetPasswordBinding
import com.example.gameguide.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth


class ForgetPassword : Fragment() {


    private lateinit var binding: FragmentForgetPasswordBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvResetBackToSignIn.setOnClickListener{
            view.findNavController().navigate(ForgetPasswordDirections.actionForgetPasswordToSignIn())
        }
        binding.btnResetPassword.setOnClickListener{
            restPass(binding.etResetPasswordEmail.text.toString())
        }


    }
    private fun restPass(fEmail: String) {

        val email: String = fEmail.toString().trim { it <= ' ' }

        if (email.isEmpty()){
            Toast.makeText(context, "email field is empty", Toast.LENGTH_LONG).show()

    }else{
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)

                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e("OK", "registration is sucessfully done")
                        findNavController().navigate(R.id.action_forgetPassword_to_signIn)
                    } else {
                        Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }.addOnCompleteListener {

                }
        }

    }

}