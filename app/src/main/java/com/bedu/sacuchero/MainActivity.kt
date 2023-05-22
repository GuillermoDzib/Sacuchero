package com.bedu.sacuchero

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bedu.sacuchero.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    val user = "guille"
    val pass = "qwerty"

    private lateinit var binding: ActivityMainBinding

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            if(binding.usernameEditText.text.toString() == user && binding.passwordEditText.text.toString() == pass) {
                startActivity(Intent(this, LoggedActivity::class.java))
            }
        }



        binding.passwordVisibilityImageView.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Ocultar contraseña
            binding.passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.passwordVisibilityImageView.setImageResource(R.drawable.ic_eye)
        } else {
            // Mostrar contraseña
            binding.passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.passwordVisibilityImageView.setImageResource(R.drawable.ic_eye_off)
        }

        isPasswordVisible = !isPasswordVisible
        binding.passwordEditText.text?.let { binding.passwordEditText.setSelection(it.length) }
    }

}

