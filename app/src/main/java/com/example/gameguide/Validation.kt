package com.example.gameguide

fun fieldValidation(userName: String,
                    email: String,
                    phone: String,
                    password: String,
                    rePassword: String):Boolean {
    if (userName.isEmpty() || email.isEmpty()
        || phone.isEmpty() || password.isEmpty()
        || rePassword.isEmpty()) {
        return true
    }
    return false
}

fun checkPasswordNotMatch(password : String, rePassword : String)= password != rePassword

fun invalidPhone(phone: String):Boolean{
    if (phone.length <= 9){
        return true
    }
    return false

}