package com.flexcode.aviatorshub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.flexcode.aviatorshub.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class ForgotPassword : AppCompatActivity() {

    private lateinit var binding:ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //INTENT FOR BACK BUTTON TO LOGIN
        binding.ivBack.setOnClickListener {
            val back = Intent(this@ForgotPassword,MainActivity::class.java)
            startActivity(back)
            onBackPressed()
        }

        //forgot password firebase
        binding.btnSubmit.setOnClickListener {
            val email: String = binding.etEmail.text.toString().trim()
            if (email.isEmpty()){
                Toast.makeText(this@ForgotPassword,
                "Please Enter Email!",
                Toast.LENGTH_SHORT).show()
            }else if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
                return@setOnClickListener
            }else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task->
                        if (task.isSuccessful){
                            Toast.makeText(this@ForgotPassword, "Email sent Successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }else {
                            Toast.makeText(this@ForgotPassword, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
}