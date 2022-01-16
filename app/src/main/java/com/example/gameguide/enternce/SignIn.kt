package com.example.gameguide.enternce

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
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.gameguide.R
import com.example.gameguide.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignIn : Fragment() {

    private var mIsShowPass = false
    private var isRemember = true
    private lateinit var sharedPreference:SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var binding: FragmentSignInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.ab_sign)

        sharedPreference = this.requireActivity().getSharedPreferences("prefence", Context.MODE_PRIVATE)
        isRemember = sharedPreference.getBoolean("CHECKBOX", false)

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

        val logEmail: String = sEmail.trim { it <= ' ' }
        val logPassword: String = sPassword.trim { it <= ' ' }

        if (logEmail.isNotEmpty() && logPassword.isNotEmpty()){
            //sing in
            FirebaseAuth.getInstance().signInWithEmailAndPassword(logEmail, logPassword).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //firebase register user
                    val email = binding.etSignInEmail.text.toString()
                    val password = binding.etSignInPassword.text.toString()
                    val checked = binding.cbSignInRememberMe.isChecked

                    editor = sharedPreference.edit()
                    editor.putString("EMAIL", email)
                    editor.putString("PASSWORD", password)
                    editor.putBoolean("CHECKBOX", checked)
                    editor.apply()

                    getUserInfo()

                    Toast.makeText(context,"information saved!",Toast.LENGTH_LONG).show()

                    Log.e("OK", "registration is sucessfully done")
                    findNavController().navigate(R.id.action_signIn_to_homepage)
                } else {
                    Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }.addOnCompleteListener {

            }
            }else{
            Toast.makeText(context, "email field or password is empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun getUserInfo() = CoroutineScope(Dispatchers.IO).launch {

        sharedPreference = this@SignIn.requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        val uId = FirebaseAuth.getInstance().currentUser?.uid
        try {
            val db = FirebaseFirestore.getInstance()
            db.collection("Users").document("$uId").get().addOnCompleteListener {
                    if (it.result?.exists()!!) {

                        val name = it.result!!.getString("userName")
                        val userPhone = it.result!!.getString("userPhone")
                        val userEmail = it.result!!.getString("userEmail")

                        editor.putString("NAME",name)
                        editor.putString("PHONE",userPhone)
                        editor.putString("EMAIL",userEmail)
                        editor.apply()

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