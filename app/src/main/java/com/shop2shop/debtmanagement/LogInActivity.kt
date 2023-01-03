package com.shop2shop.debtmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shop2shop.debtmanagement.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener{

            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.button.setOnClickListener{
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{

                Toast.makeText(this,"Check on the Empty Field", Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}