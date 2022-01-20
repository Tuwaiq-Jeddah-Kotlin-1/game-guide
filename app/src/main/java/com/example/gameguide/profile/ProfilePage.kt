package com.example.gameguide.profile

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.fragment.findNavController
import com.example.gameguide.R
import com.example.gameguide.databinding.FragmentProfileBinding
import com.example.gameguide.settingUtil.SettingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfilePage : Fragment() {
    private lateinit var sharedPreference:SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var binding: FragmentProfileBinding
    private lateinit var setting: SettingUtil


    private var state = 0
    //private var savedText = ""




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.ab_profile)
        //loadData()
        getUserInfo()
        setting = SettingUtil(requireContext())
        sharedPreference = this.requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)

        val name = sharedPreference.getString("NAME","")
        val phone = sharedPreference.getString("PHONE","")
        val email = sharedPreference.getString("EMAIL","")
        binding.tvProfileUserName.text= name.toString()
        binding.tvProfilePhone.text= phone.toString()
        binding.tvProfileEmail.text= email.toString()


        binding.btnProfileEdit.setOnClickListener{
            dialogEditProfile()
        }

        binding.btnProfLanguage.setOnClickListener {
            sharedPreference = this.requireActivity().getSharedPreferences("Settings",Context.MODE_PRIVATE)
            val checked = sharedPreference.getString("LOCALE_TO_SET", "")
            if (checked == "en"){
                state = 0
            }else if (checked == "ar"){
                state = 1
            }
            val languages = arrayOf("English", "عربى")
            val langSelectorBuilder = AlertDialog.Builder(requireContext(), R.style.AppCompatAlertDialogStyle)
            langSelectorBuilder.setTitle(getString(R.string.pro_chooseLang))
            langSelectorBuilder.setSingleChoiceItems(languages, state) { dialog, selection ->
                when(selection) {
                    0 -> {
                        setLocate("en")
                    }
                    1 -> {
                        setLocate("ar")
                    }
                }
            }.setPositiveButton(getString(R.string.prof_dialog_confirm)) { dialog, selection ->
                recreate(context as Activity)
                dialog.dismiss()
            }.setNeutralButton(getString(R.string.prof_dialog_cancel)) { dialog, selection ->

            }
            langSelectorBuilder.create().show()
        }



        //binding.swchProfMode.text = savedText
        sharedPreference = this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        //val darkMode = sharedPreference.getBoolean("DARK_MODE",false)

        binding.swchProfMode.isChecked = sharedPreference.getBoolean("DARK_MODE",false)
        //savedText = sharedPreference.getString("DARK_MODE_NAME",null)!!


        binding.swchProfMode.setOnCheckedChangeListener { _, isChecked ->
            editor= sharedPreference.edit()
            if (!isChecked){
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }else{
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
            editor.putBoolean("DARK_MODE",isChecked)
            editor.apply()
        }


        /*binding.btnProfMode.setOnClickListener {

            sharedPreference = this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
            editor= sharedPreference.edit()

            val darkMode = sharedPreference.getBoolean("DARK_MODE",false)

            if(darkMode) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                editor.putBoolean("DARK_MODE",false)
                editor.apply()
                binding.btnProfMode.text = getString(R.string.proff_enable_dark_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                editor.putBoolean("DARK_MODE",true)
                editor.apply()
                binding.btnProfMode.text = getString(R.string.proff_disable_dark_mode)
            }
        }*/

        binding.tvProLogOut.setOnClickListener {
            val langSelectorBuilder = AlertDialog.Builder(requireContext(), R.style.AppCompatAlertDialogStyle)
            langSelectorBuilder.setTitle(getString(R.string.pro_warningLog))
                .setPositiveButton(getString(R.string.prof_dialog_confirm)) { dialog, _ ->
                    sharedPreference = this.requireActivity().getSharedPreferences("prefence", Context.MODE_PRIVATE)

                    val emailOut = sharedPreference.getString("EMAIL","")
                    binding.tvProfileEmail.text = emailOut

                    editor = sharedPreference.edit()
                    editor.clear()
                    editor.apply()
                    findNavController().navigate(R.id.action_profileFragment_to_signIn)
                    dialog.dismiss()
                }.setNeutralButton(getString(R.string.prof_dialog_cancel)) { dialog, _ ->

                }
            langSelectorBuilder.create().show()

        }
    }



    /*private fun loadData() {
        sharedPreference = this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        editor= sharedPreference.edit()

        val darkMode = sharedPreference.getBoolean("DARK_MODE",swchProfMode.isChecked)
        if(darkMode){
            binding.swchProfMode.text= getString(R.string.proff_disable_dark_mode)
            editor.putString("DARK_MODE_NAME",getString(R.string.proff_disable_dark_mode))

        }else{
            binding.swchProfMode.text= getString(R.string.proff_enable_dark_mode)
            editor.putString("DARK_MODE_NAME",getString(R.string.proff_disable_dark_mode))
        }
    }*/

    private fun getUserInfo() = CoroutineScope(Dispatchers.IO).launch {
        sharedPreference = this@ProfilePage.requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        editor= sharedPreference.edit()

        val uId = FirebaseAuth.getInstance().currentUser?.uid
        try {
            //coroutine
            val db = FirebaseFirestore.getInstance()
            db.collection("Users").document("$uId").get().addOnCompleteListener {

                if (it.result?.exists()!!) {
                    //+++++++++++++++++++++++++++++++++++++++++
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

    private fun dialogEditProfile() {
        val view: View = layoutInflater.inflate(R.layout.bosh_change_profile, null)

        val builder = BottomSheetDialog(requireContext())//requireView()?.context
        builder.setTitle("edit profile")

        val usernameEt = view.findViewById<EditText>(R.id.etChangeProfUsername)
        val userPhoneEt = view.findViewById<EditText>(R.id.etChangeProfPhone)
        val continueBtn = view.findViewById<Button>(R.id.btnChangeProfConfirm)

        usernameEt.setText(binding.tvProfileUserName.text.toString())
        userPhoneEt.setText(binding.tvProfilePhone.text.toString())

        continueBtn.setOnClickListener {
            if (usernameEt.text.isNotEmpty()&&userPhoneEt.text.isNotEmpty()){
                updateData(usernameEt.text.toString(),userPhoneEt.text.toString())
                builder.dismiss()
            }else
                Toast.makeText(context,"can't be null",Toast.LENGTH_SHORT).show()
        }
        builder.setContentView(view)

        builder.show()
    }

    private fun updateData (usernameEt: String, userPhotoEt: String){
        sharedPreference = this.requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        val uId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef = Firebase.firestore.collection("Users").document(uId.toString()).update("userName",usernameEt,
            "userPhone",userPhotoEt)

        editor.putString("NAME",usernameEt)
        editor.putString("PHONE",userPhotoEt)
        editor.apply()

        binding.tvProfileUserName.text = usernameEt
        binding.tvProfilePhone.text = userPhotoEt

        userRef
    }

    private fun setLocate(s: String) {
        setting.setLocate(s)
        sharedPreference = this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        editor.putString("LOCALE_TO_SET", s)
        editor.apply()
    }
}



