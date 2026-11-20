package com.surya.socialsphere.ui.activities.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.surya.socialsphere.R
import com.surya.socialsphere.ui.activities.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private var isLoginMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtAuthTitle = findViewById<TextView>(R.id.txtAuthTitle)
        val txtAuthSubtitle = findViewById<TextView>(R.id.txtAuthSubtitle)
        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val txtForgotPassword = findViewById<TextView>(R.id.txtForgotPassword)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val txtTogglePrompt = findViewById<TextView>(R.id.txtTogglePrompt)
        val txtToggleMode = findViewById<TextView>(R.id.txtToggleMode)

        // Toggle between Login and Sign Up modes smoothly
        txtToggleMode.setOnClickListener {
            isLoginMode = !isLoginMode
            if (isLoginMode) {
                txtAuthTitle.text = "WELCOME BACK"
                txtAuthSubtitle.text = "Sign in to continue to VYRO"
                etFullName.visibility = View.GONE
                txtForgotPassword.visibility = View.VISIBLE
                btnSubmit.text = "LOGIN"
                txtTogglePrompt.text = "Don't have an account? "
                txtToggleMode.text = "Sign Up"
            } else {
                txtAuthTitle.text = "CREATE ACCOUNT"
                txtAuthSubtitle.text = "Join the VYRO community"
                etFullName.visibility = View.VISIBLE
                txtForgotPassword.visibility = View.GONE
                btnSubmit.text = "SIGN UP"
                txtTogglePrompt.text = "Already have an account? "
                txtToggleMode.text = "Login"
            }
        }

        // Validate inputs and navigate to MainActivity
        btnSubmit.setOnClickListener {
            val name = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (!isLoginMode && name.isEmpty()) {
                etFullName.error = "Please enter your name"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                etEmail.error = "Please enter your email"
                return@setOnClickListener
            }
            if (password.length < 6) {
                etPassword.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            // Proceed to Main Dashboard
            Toast.makeText(this, if (isLoginMode) "Logged in!" else "Account created!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}