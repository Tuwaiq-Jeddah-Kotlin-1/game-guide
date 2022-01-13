package com.example.gameguide.settingUtil

import com.example.gameguide.checkPasswordNotMatch
import com.example.gameguide.fieldValidation
import com.example.gameguide.invalidPhone
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ValidationKtTest {
    @Before
    fun setUp() {
    }

    private  var username1 ="badr"
    private  var email1 ="gggg@gmail.com"
    private  var phone1 ="0505509439"
    private  var password1 ="12345678"
    private  var rePassword1 ="12345678"

    private  var username2 =""
    private  var email2 ="gggg@gmail.com"
    private  var phone2 ="0505509439"
    private  var password2 ="12345678"
    private  var rePassword2 ="12345678"

    private  var matchPassword ="huhuf"
    private  var matchRePassword ="huhuf"

    private  var passwordFalse ="huhuf"
    private  var rePasswordFalse ="fhfhfhf"

    private var validPhone = "0547985969"
    private var invalidPhone = "0547985"

    @Test
    fun `all field filled`(){
        assertTrue(fieldValidation(username1, email1, phone1, password1, rePassword1))
    }
    @Test
    fun `some filed not filled`(){
        assertTrue(fieldValidation(username2, email2, phone2, password2, rePassword2))

    }

    @Test
    fun `Passwords do Not Match`(){
        assertFalse(checkPasswordNotMatch(passwordFalse,rePasswordFalse))
    }
    @Test
    fun `Passwords Match`(){
        assertTrue(checkPasswordNotMatch(matchPassword,matchRePassword))

    }
    @Test
    fun `valid phone number`(){
        assertFalse(invalidPhone(validPhone))
    }
    @Test
    fun `invalid phone number`(){
        assertFalse(invalidPhone(invalidPhone))
    }
}
