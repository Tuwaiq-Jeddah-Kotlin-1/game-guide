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
import com.example.gameguide.checkPasswordNotMatch
import com.example.gameguide.dataClasses.User
import com.example.gameguide.databinding.FragmentRegistrationBinding
import com.example.gameguide.fieldValidation
import com.example.gameguide.invalidPhone
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Registration : Fragment() {
    private var mIsShowPass = false
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()



        binding.ivRegisterVisiPass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }
        showPassword(mIsShowPass)

        binding.ivRegisterVisiRePass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showRePassword(mIsShowPass)
        }
        showRePassword(mIsShowPass)

        binding.tvRegistrationBackSign.setOnClickListener{
            view.findNavController().navigate(RegistrationDirections.actionRegistrationToSignIn())
        }

        binding.btnRegistration.setOnClickListener{
            registerUser(binding.etRegistrationUserName.text.toString(),binding.etRegistrationEmail.text.toString(),
                binding.etRegistrationPhone.text.toString(),binding.etRegistrationPassword.text.toString(),
                binding.etRegistrationRePassword.text.toString())
        }
    }

    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            // To show the password
            binding.etRegistrationPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.ivRegisterVisiPass.setImageResource(R.drawable.ic_baseline_visibility_off)
        } else {
            // To hide the pass
            binding.etRegistrationPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.ivRegisterVisiPass.setImageResource(R.drawable.ic_baseline_visibility)
        }
        // This line of code to put the pointer at the end of the password string
        binding.etRegistrationPassword.setSelection(binding.etRegistrationPassword.text.toString().length)
    }

    private fun showRePassword(isShow: Boolean) {
        if (isShow) {
            // To show the password
            binding.etRegistrationRePassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.ivRegisterVisiRePass.setImageResource(R.drawable.ic_baseline_visibility_off)
        } else {
            // To hide the pass
            binding.etRegistrationRePassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.ivRegisterVisiRePass.setImageResource(R.drawable.ic_baseline_visibility)
        }
        // This line of code to put the pointer at the end of the password string
        binding.etRegistrationRePassword.setSelection(binding.etRegistrationRePassword.text.toString().length)
    }

    //class Firebase
    private fun registerUser(
        eUsername: String,
        eEmail: String,
        ePhone: String,
        ePassword: String,
        eRePassword: String)
    {
        sharedPreference = this.requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        val userName: String = eUsername.trim { it <= ' ' }
        val email: String = eEmail.trim { it <= ' ' }
        val phone: String = ePhone.trim { it <= ' ' }
        val password: String = ePassword.trim { it <= ' ' }
        val rePassword: String = eRePassword.trim { it <= ' ' }

        when {
            fieldValidation(userName,email,phone,password,rePassword) -> {
                Toast.makeText(context, "some of the fields are empty", Toast.LENGTH_LONG).show()
            }
            checkPasswordNotMatch(password, rePassword) -> {
                Toast.makeText(context, "the password and repassword do not match", Toast.LENGTH_LONG).show()
            }
            invalidPhone(phone) -> {
                Toast.makeText(context, "phone number less than 10 digits", Toast.LENGTH_LONG).show()
            }
            else -> {
                //register
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        userData(userName, email, phone)

                        editor.putString("NAME",userName)
                        editor.putString("PHONE",email)
                        editor.putString("EMAIL",phone)
                        editor.apply()

                        Toast.makeText(context, "registration successful", Toast.LENGTH_LONG).show()
                        Log.e("OK", "registration successful")
                    } else {
                        Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }.addOnCompleteListener {
                }
            }
        }
    }

    private fun userData(username:String, email:String, phone:String){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userData = User()
        userData.userName = username
        userData.userEmail = email
        userData.userPhone = phone
        userData.UID = userId.toString()
        userFireStore(userData)
    }

    private fun userFireStore(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val userRef = Firebase.firestore.collection("Users")
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            userRef.document("$userId").set(user).addOnCompleteListener { it
                when {
                    it.isSuccessful -> {
                        Toast.makeText(context , "is Successful fire store", Toast.LENGTH_LONG).show()
                        Log.e("OK", "registration successful fire store")
                        findNavController().navigate(R.id.action_registration_to_homepage)
                    }
                    else -> {
                        Toast.makeText(context, "is not Successful fire store ", Toast.LENGTH_LONG).show()
                    }
                }
            }
            withContext(Dispatchers.Main) {
                //Toast.makeText(coroutineContext.javaClass, "Welcome ${user.fullName.toString()}", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                // Toast.makeText(coroutineContext,0,0, e.message, Toast.LENGTH_LONG).show()
                Log.e("userFireStore", "${e.message}")
            }
        }
    }

}


