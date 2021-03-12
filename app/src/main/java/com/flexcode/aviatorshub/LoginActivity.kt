package com.flexcode.aviatorshub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_registration.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        tvRegister.setOnClickListener(View.OnClickListener {
            val registerPage = Intent(this@LoginActivity,RegistrationActivity::class.java)
            startActivity(registerPage)
        })

        //validating user login details
        btnLogin.setOnClickListener(View.OnClickListener {
            //reading the user details and converting to string
            val email = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please Enter your Email!",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please Enter your Password!",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }

            progressBar.visibility = View.VISIBLE

            //Firebase login using user email and password
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                    //if task is successful
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            "You are logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        //Intent for main activity after successful log in
                        val logIn =
                            Intent(this@LoginActivity, MainActivity::class.java)
                        logIn.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        logIn.putExtra("userId",FirebaseAuth.getInstance().currentUser!!.uid)
                        logIn.putExtra("emailId", email)
                        startActivity(logIn)
                        finish()
                    } else {
                        //unsuccessful login
                        Toast.makeText(
                            this@LoginActivity,
                            "Error!" + task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                        progressBar.visibility = View.GONE
                    }
                })
        })
    }
}