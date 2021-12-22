package com.example.gameguide

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.gameguide.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth


class SignIn : Fragment() {



    private var mIsShowPass = false

    private var isRemember = true
    lateinit var sharedPrefence:SharedPreferences
    private lateinit var binding: FragmentSignInBinding





    companion object {
        fun newInstance() = SignIn()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefence = this.requireActivity().getSharedPreferences("prefence", Context.MODE_PRIVATE)
        isRemember = sharedPrefence.getBoolean("CHECKBOX", false)

        binding.ivSignInVisible.setOnClickListener {

                mIsShowPass = !mIsShowPass
                showPassword(mIsShowPass)
        }
                showPassword(mIsShowPass)


        binding.tvSignInLogInNow.setOnClickListener {
            view.findNavController().navigate(SignInDirections.actionSignInToRegistration())
        }
        binding.tvSignInRestPassword.setOnClickListener {
            view.findNavController().navigate(SignInDirections.actionSignInToForgetPassword())
        }
        if (isRemember){
            findNavController().navigate(R.id.action_signIn_to_homepage)

        }
        binding.btnSignIn.setOnClickListener {

            login(binding.etSignInEmail.text.toString(), binding.etSignInPassword.text.toString())

        }



    }

    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            // To show the password
            binding.etSignInPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.ivSignInVisible.setImageResource(R.drawable.ic_baseline_visibility_off)
        } else {
            // To hide the pass
            binding.etSignInPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.ivSignInVisible.setImageResource(R.drawable.ic_baseline_visibility)
        }
            // This line of code to put the pointer at the end of the password string
        binding.etSignInPassword.setSelection(binding.etSignInPassword.text.toString().length)

    }


        private fun login(sEmail: String, sPassword: String) {

        val email: String = sEmail.toString().trim { it <= ' ' }
        val password: String = sPassword.toString().trim { it <= ' ' }

        if (email.isNotEmpty() && password.isNotEmpty()){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            //sing in
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //firebase register user
                    val email = binding.etSignInEmail.text.toString()
                    val password = binding.etSignInPassword.text.toString()
                    val checked = binding.cbSignInRememberMe.isChecked

                    val editor: SharedPreferences.Editor = sharedPrefence.edit()
                    editor.putString("EMAIL", email)
                    editor.putString("PASSWORD", password)
                    editor.putBoolean("CHECKBOX", checked)
                    editor.apply()

                    Toast.makeText(context,"information saved!",Toast.LENGTH_LONG).show()

                    Log.e("OK", "registration is sucessfully done")
                    findNavController().navigate(R.id.action_signIn_to_homepage)
                } else {
                    Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }.addOnCompleteListener {

            }
            }else{
            Toast.makeText(context, "email field or password is empty", Toast.LENGTH_LONG).show()
        }
    }
}