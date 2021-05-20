package com.flexcode.aviatorshub


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flexcode.aviatorshub.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getting extra intent from registered user details
        /*val userId = intent.getStringExtra("userId")
        val emailId = intent.getStringExtra("emailId")



        tvUser_id.text = "UserId :: $userId"
        tvEmail_id.text = "EmailId :: $emailId"*/
        

        binding.btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }



    }
}