package com.example.veterinarymedicinelibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            firebaseAuth= FirebaseAuth.getInstance()
           checkUser()
        },2000)

    }

    private fun checkUser() {
        val firebaseUser =firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }else{

            val ref= FirebaseDatabase.getInstance().getReference("Users")
            ref.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {


                        val userType=snapshot.child("userType").value
                        if (userType == "user"){
                            startActivity(Intent(this@SplashActivity,Dashboarduser::class.java))
                            finish()
                        }
                        if (userType == "admin"){
                            startActivity(Intent(this@SplashActivity,Dashboardadmin::class.java))
                            finish()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {

                    }


                })
        }
    }
}