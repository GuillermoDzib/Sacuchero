package com.bedu.sacuchero.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bedu.sacuchero.data.room.UsersViewModel
import com.bedu.sacuchero.databinding.ActivityRegisterBinding
import com.bedu.sacuchero.model.User
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        setupListeners()
    }

    private fun setupListeners() {
        binding.editTextName.addTextChangedListener(textWatcher)
        binding.editTextEmail.addTextChangedListener(textWatcher)
        binding.editTextPassword.addTextChangedListener(textWatcher)
        binding.editTextPassword2.addTextChangedListener(textWatcher)
        binding.editTextAddress.addTextChangedListener(textWatcher)

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
        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val password2 = binding.editTextPassword2.text.toString().trim()
        val address = binding.editTextAddress.text.toString().trim()

        // Habilitar el botón si todos los campos tienen texto
        binding.button.isEnabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty() && address.isNotEmpty()
    }

    private fun register() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val password2 = binding.editTextPassword2.text.toString()
        val address = binding.editTextAddress.text.toString()

        if (password == password2) {
            lifecycleScope.launch {
                if (!usersViewModel.checkEmailExists(email)) {
                    val user = User(name, email, password, address, "")
                    usersViewModel.insertUser(user)
                    Toast.makeText(
                        this@RegisterActivity,
                        "Usuario registrado exitosamente",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "El email ya está registrado, iniciar sesión o registre un email diferente",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            Toast.makeText(this@RegisterActivity, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
    }
}
