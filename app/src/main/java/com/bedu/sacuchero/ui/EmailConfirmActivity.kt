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
import com.bedu.sacuchero.databinding.ActivityChangePasswordBinding
import com.bedu.sacuchero.databinding.ActivityEmailConfirmBinding
import kotlinx.coroutines.launch

class EmailConfirmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailConfirmBinding
    private lateinit var usersViewModel: UsersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        setupListeners()
    }

    private fun setupListeners() {

        binding.editTextEmail.addTextChangedListener(textWatcher)

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

        val email = binding.editTextEmail.text.toString().trim()

        // Habilitar el botón si todos los campos tienen texto
        binding.button.isEnabled = email.isNotEmpty()
    }

    private fun register() {

        val email = binding.editTextEmail.text.toString()

        lifecycleScope.launch {
            if(usersViewModel.checkEmailExists(email)) {
                val intent = Intent(this@EmailConfirmActivity, ChangePasswordActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
            }
        }

    }
}
