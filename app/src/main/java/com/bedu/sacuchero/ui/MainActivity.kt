package com.bedu.sacuchero.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bedu.sacuchero.R
import com.bedu.sacuchero.data.room.UsersViewModel
import com.bedu.sacuchero.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var usersViewModel: UsersViewModel

    private lateinit var binding: ActivityMainBinding

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        binding.usernameEditText.text = null
        binding.passwordEditText.text = null


        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                if (usersViewModel.checkCredentials(binding.usernameEditText.text.toString(), binding.passwordEditText.text.toString())) {
                    val intent = Intent(this@MainActivity, LoggedActivity::class.java)
                    intent.putExtra("email", binding.usernameEditText.text.toString())
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Email o contraseña incorrectos, revise la información", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.forgotPasswordTextView.setOnClickListener {
            startActivity(Intent(this, EmailConfirmActivity::class.java))
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

