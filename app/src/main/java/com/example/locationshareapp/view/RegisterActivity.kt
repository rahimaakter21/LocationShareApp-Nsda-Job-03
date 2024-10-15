package com.example.locationshareapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.locationshareapp.R
import com.example.locationshareapp.databinding.ActivityRegisterBinding
import com.example.locationshareapp.viewModel.AuthenticationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {

 private  lateinit var  binding: ActivityRegisterBinding
private  lateinit var   authenticationViewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)

        binding.registerBtn.setOnClickListener{

            val name = binding.displayNameEt.text.toString()
            val  email = binding.emailEt.text.toString()
            val  password = binding.passwordEt.text.toString()
            val confirmPassword= binding.conPasswordEt.text.toString()
            if(name.isEmpty() || email.isEmpty()||password.isEmpty()|| confirmPassword.isEmpty()){
                    Toast.makeText(this,"please fill al,l fields",Toast.LENGTH_SHORT).show()

            }
            else if(password!=confirmPassword){

                Toast.makeText(this,"password does not match",Toast.LENGTH_SHORT).show()
            }
            else if(password.length<6){

                Toast.makeText(this,"password must be at least 6 characters",Toast.LENGTH_SHORT).show()
            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher((email)).matches ()){

                Toast.makeText(this,"please enter valid email",Toast.LENGTH_SHORT).show()

            }
            else {
                authenticationViewModel.register(email, password, {

                    startActivity(Intent(this, MainActivity::class.java))

                    finish()

                }, {

                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
            }
        }


    }
    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser!=null){
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            finish()
        }
    }
}