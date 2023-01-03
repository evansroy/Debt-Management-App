package com.shop2shop.debtmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shop2shop.debtmanagement.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()

        binding.textView.setOnClickListener{
            val intent = Intent(this,LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.button.setOnClickListener{
            val fullName = binding.fullNameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (fullName.isNotEmpty() && pass.isNotEmpty() && email.isNotEmpty() && confirmPass.isNotEmpty()){

                if (pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if (it.isSuccessful){

                            createNewUser(fullName,email)
                            val intent = Intent(this,LogInActivity::class.java)
                            startActivity(intent)
                            finish()

                        }else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT)
                        }
                    }

                }else{

                    Toast.makeText(this,"Password is not Matching", Toast.LENGTH_SHORT)
                }

            }
            else{
                Toast.makeText(this,"Empty Field Are not Allowed !!", Toast.LENGTH_SHORT)
            }
        }
    }

     private fun createNewUser(fullName: String, email: String) {
        val db = FirebaseFirestore.getInstance()
        val user:MutableMap<String,Any> = HashMap()
         user["fullNames"] = fullName
         user["email"] = email

         db.collection("users")
             .add(user)
             .addOnSuccessListener {
                 Toast.makeText(this,"User added Successfully",Toast.LENGTH_SHORT).show()
             }
             .addOnFailureListener{
                 Toast.makeText(this,"Failed!!",Toast.LENGTH_SHORT).show()
             }
    }
}