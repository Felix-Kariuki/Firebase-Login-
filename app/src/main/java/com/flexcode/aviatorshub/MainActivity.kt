package com.flexcode.aviatorshub


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getting extra intent from registered user details
        /*val userId = intent.getStringExtra("userId")
        val emailId = intent.getStringExtra("emailId")



        tvUser_id.text = "UserId :: $userId"
        tvEmail_id.text = "EmailId :: $emailId"*/
        

        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }



    }
}