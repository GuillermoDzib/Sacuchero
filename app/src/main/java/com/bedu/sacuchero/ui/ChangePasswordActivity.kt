package com.bedu.sacuchero.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bedu.sacuchero.data.room.UsersViewModel
import com.bedu.sacuchero.databinding.ActivityChangePasswordBinding
import com.bedu.sacuchero.model.User
import kotlinx.coroutines.launch

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var usersViewModel: UsersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        setupListeners()
    }

    private fun setupListeners() {

        binding.editTextPassword.addTextChangedListener(textWatcher)
        binding.editTextPassword2.addTextChangedListener(textWatcher)

        binding.button.setOnClickListener {
            register()
        }

        updateButtonState()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            updateButtonState()
        }
    }

    private fun updateButtonState() {

        val password = binding.editTextPassword.text.toString().trim()
        val password2 = binding.editTextPassword2.text.toString().trim()

        // Habilitar el botón si todos los campos tienen texto
        binding.button.isEnabled = password.isNotEmpty() && password2.isNotEmpty()
    }

    private fun register() {

        val password = binding.editTextPassword.text.toString()
        val password2 = binding.editTextPassword2.text.toString()

        val email = intent.getStringExtra("email")

        // Utilizar el valor recibido
        if (email != null) {
            if (password == password2) {
                lifecycleScope.launch {
                    usersViewModel.updatePasswordByEmail(email, password)
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "Contraseña actualizada exitosamente",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@ChangePasswordActivity, MainActivity::class.java))
                }
            } else {
                Toast.makeText(this@ChangePasswordActivity, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }
}