package com.flexcode.aviatorshub

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.flexcode.aviatorshub.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject
import java.util.*


class LoginActivity : AppCompatActivity() {

    //private var CallbackManager callbackManager
    private lateinit var callbackManager: CallbackManager
    //lateinit var graphRequest: GraphRequest

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //call back for facebook login
        callbackManager = CallbackManager.Factory.create()


        //facebook login
        binding.loginButton.setPermissions(listOf("user_gender","public_profile",
            "email"))
        // If you are using in a fragment, call loginButton.setFragment(this);
        // Callback registration
        // If you are using in a fragment, call loginButton.setFragment(this);
        // Callback registration


        // Callback registration

        // Callback registration
        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
                Log.d("demo","login successful")
                val logIn = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(logIn)

                val graphRequest = GraphRequest.newMeRequest(loginResult?.accessToken){
                    `object` ,response -> getFacebookData(`object`)
                }
            }

            override fun onCancel() {
                // App code
                Log.d("demo","login unsuccessful")

            }

            override fun onError(exception: FacebookException) {
                // App code
                Log.d("demo","login error")

            }
        })


        //register callback
        /*login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult>?) {
            override fun OnSuccess(loginResult: LoginResult?) {

            }
             override fun onCancel() {

             }
            override fun onError(exception: FacebookException){

            }
        }*/


        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        //REGISTER PAGE INTENT
        binding.tvRegister.setOnClickListener {
            val registerPage = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(registerPage)
        }

        //INTENT FOR FORGOT PASSWORD
        binding.tvResetPassword.setOnClickListener {
            val intent =   Intent(this@LoginActivity,ForgotPassword::class.java)
            startActivity(intent)
        }

        //Login the user if already has an account
        if (FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }

        //validating user login details
        binding.btnLogin.setOnClickListener(View.OnClickListener {
            //reading the user details and converting to string
            val email = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@LoginActivity, "Please Enter your Email!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                return@OnClickListener
            } else {
                binding.etUsername.error = "Invalid Email"
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity,"Please Enter your Password!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            progressBar.visibility = View.VISIBLE

            //Firebase login using user email and password
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    //if task is successful
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            "You are logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        //Intent for main activity after successful log in
                        val logIn = Intent(this@LoginActivity, VerificationActivity::class.java)
                        // To remove the extra layers when we move to ...... -> end of code more description
                        /*intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK//various activities to also close app when user clicks back
                        //sending user data through put extra
                        logIn.putExtra("userId",FirebaseAuth.getInstance().currentUser!!.uid)
                        logIn.putExtra("emailId", email)*/
                        startActivity(logIn)
                        finish()
                    } else {
                        //unsuccessful login
                        Toast.makeText(
                            this@LoginActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                        progressBar.visibility = View.GONE
                    }
                }
        })
    }

    private fun getFacebookData(jsonObject: JSONObject?) {
        val profilePic = "https://graph.facebook.com/${jsonObject?.getString("id")}"


    }

    //passing the login results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

    /*override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)




    }*/
}