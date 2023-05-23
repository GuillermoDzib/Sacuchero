package com.bedu.sacuchero.data.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bedu.sacuchero.model.Food
import com.bedu.sacuchero.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UsersRepository

    init {
        val userDao = UsersDataBase.getDatabase(application).wordDao()
        repository = UsersRepository(userDao)
    }

    suspend fun getKartByEmail(email: String): String{
        return withContext(Dispatchers.IO) {
            repository.getKartByEmail(email)
        }
    }

    suspend fun updateKartByEmail(email: String, kart: String) {
        withContext(Dispatchers.IO) {
            repository.updateKartByEmail(email, kart)
        }
    }

    suspend fun checkCredentials(email: String, pass: String): Boolean {
        return withContext(Dispatchers.IO) {
            repository.checkCredentials(email, pass)
        }
    }

    suspend fun getNameByEmail(email: String): String {
        return withContext(Dispatchers.IO) {
            repository.getNameByEmail(email)
        }    }

    suspend fun getDirByEmail(email: String): String {
        return withContext(Dispatchers.IO) {
            repository.getDirByEmail(email)
        }
    }

    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    suspend fun checkEmailExists(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            repository.checkEmailExists(email)
        }
    }

    suspend fun updatePasswordByEmail(email: String, newPass: String) {
        withContext(Dispatchers.IO) {
            repository.updatePasswordByEmail(email, newPass)
        }
    }

}