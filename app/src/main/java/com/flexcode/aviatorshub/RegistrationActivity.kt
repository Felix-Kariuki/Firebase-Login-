package com.flexcode.aviatorshub

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.flexcode.aviatorshub.databinding.ActivityRegistrationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.tvLogin.setOnClickListener(View.OnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
            onBackPressed()
        })



        //event listener for the register button
        val btnRegister = binding.btnRegister
        val etPassword = binding.etPassword
        val etConfirmPassword = binding.etConfirmPassword
        val etPhone = binding.etPhone
        val etFullName = binding.etFullName
        val etEmail = binding.etEmail

        btnRegister.setOnClickListener(View.OnClickListener {
            //reading the user details and converting to string
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val fullName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()



            if (TextUtils.isEmpty(fullName)) {
                etFullName.error = "Name Cannot be blank!"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                etEmail.error = "Email Cannot be blank!"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(phone)) {
                etPhone.error = "Phone Cannot be empty"
                return@OnClickListener
            }
            if (phone.length <10){
                etPhone.error = "Please check your Phone Number"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.error="Password cannot be empty!"
                return@OnClickListener
            }
            if (password.length < 6) {
                etPassword.error="Password Cannot be less than six characters"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.error = "Please Confirm Your Password"
            if (confirmPassword != password) {
                etConfirmPassword.error = "Password Don't match"
            }
            return@OnClickListener
            }

            //SHOWING THE PROGRESS BAR WHEN EVALUATING INPUT
            binding.progressBar.visibility = View.VISIBLE

            //Creating firebase auth instance and register email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                //If registration was successful
                                if (task.isSuccessful) {

                                    //Firebase registers user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                            this@RegistrationActivity, "Registration successful", Toast.LENGTH_SHORT).show()

                                    /*Intent to Send the user to main activity after successful registration
                                    * in other words login the user that has been registered */
                                    val intent = Intent(this@RegistrationActivity,MainActivity::class.java)
                                    //To remove the extra layers when we move to ...... -> end of code more description
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK//various activities to also close app when user clicks back
                                    intent.putExtra("userId",firebaseUser.uid)
                                    intent.putExtra("emailId", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    //when the registration is unsuccessful
                                    Toast.makeText(
                                            this@RegistrationActivity, "Error!" + task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                    binding.progressBar.visibility = View.GONE
                                }
                            }
                    )

        })

    }
}