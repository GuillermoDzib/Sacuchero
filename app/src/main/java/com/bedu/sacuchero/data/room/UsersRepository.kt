package com.bedu.sacuchero.data.room

import androidx.room.Insert
import androidx.room.Query
import com.bedu.sacuchero.model.Food
import com.bedu.sacuchero.model.User

class UsersRepository(private val dao: UsersDAO) {


    suspend fun getKartByEmail(email: String): String {
        return dao.getKartByEmail(email)
    }

    suspend fun updateKartByEmail(email: String, kart: String) {
        return dao.updateKartByEmail(email, kart)
    }

    suspend fun checkCredentials(email: String, pass: String): Boolean {
        return dao.checkCredentials(email, pass)
    }

    suspend fun getNameByEmail(email: String): String {
        return dao.getNameByEmail(email)
    }

    suspend fun getDirByEmail(email: String): String {
        return dao.getDirByEmail(email)
    }

    suspend fun insertUser(user: User){
        return dao.insertUser(user)
    }

    suspend fun checkEmailExists(email: String): Boolean {
        return dao.checkEmailExists((email))
    }

    suspend fun updatePasswordByEmail(email: String, newPass: String) {
        return dao.updatePasswordByEmail(email, newPass)
    }

}