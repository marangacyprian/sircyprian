package com.example.veterinarymedicinelibrary

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.veterinarymedicinelibrary.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class login : AppCompatActivity() {

    private  lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.noaccountIv.setOnClickListener{
            startActivity(Intent(this,signup2::class.java))
        }
        binding.loginbtn.setOnClickListener {
            validateData()
        }

    }
    private var email=""
    private var password=""

    private fun validateData() {
        email=binding.emailEt.text.toString().trim()
        password=binding.passwordEt.text.toString().trim()


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "invalid email..", Toast.LENGTH_SHORT).show()
        }else if (password.isEmpty()){
            Toast.makeText(this, "enter password..", Toast.LENGTH_SHORT).show()
        }else{
            loginUser()
        }
    }

    private fun loginUser() {
        progressDialog.setMessage("logging in...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                checkUser()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        progressDialog.setMessage("checking user...")

        startActivity(Intent(this@login, Dashboarduser::class.java))

    }
        //val firebaseUser=firebaseAuth.currentUser!!
       // val ref=FirebaseDatabase.getInstance().getReference("Users")
        //ref.child(firebaseUser.uid)
          //  .addListenerForSingleValueEvent(object :ValueEventListener{
            //    override fun onDataChange(snapshot: DataSnapshot) {
              //      progressDialog.dismiss()

                //    val userType=snapshot.child("userType").value
                  //  if (userType == "user"){
                    //    startActivity(Intent(this@login,Dashboarduser::class.java))
                      //  finish()
                    //}

                    //if (userType == "admin"){
                      //  startActivity(Intent(this@login,Dashboardadmin::class.java))
                        //finish()
                   // }
                //}
                //override fun onCancelled(error: DatabaseError) {

                //}


            //})
    //}
}