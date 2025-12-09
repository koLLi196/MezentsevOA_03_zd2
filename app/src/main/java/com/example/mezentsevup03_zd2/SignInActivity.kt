package com.example.mezentsevup03_zd2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class SignInActivity : Activity() {

    lateinit var email:EditText
    lateinit var password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
    }

    fun Login(view: View) {
        if(email.text.isNotEmpty() && password.text.isNotEmpty()){
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else{
            Snackbar.make(view, "Заполните поля!", Snackbar.LENGTH_SHORT).show()
        }
    }
}