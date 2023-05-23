package com.bedu.sacuchero.data.room

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bedu.sacuchero.model.Food
import com.bedu.sacuchero.model.User

@Dao
interface UsersDAO {
    @Query("SELECT kart FROM users_table WHERE email = :email")
    suspend fun getKartByEmail(email: String): String


    @Query("UPDATE users_table SET kart = :kart WHERE email = :email")
    suspend fun updateKartByEmail(email: String, kart: String)

    @Query("SELECT EXISTS(SELECT 1 FROM users_table WHERE email = :email AND pass = :pass)")
    suspend fun checkCredentials(email: String, pass: String): Boolean

    @Query("SELECT name FROM users_table WHERE email = :email")
    suspend fun getNameByEmail(email: String): String

    @Query("SELECT dir FROM users_table WHERE email = :email")
    suspend fun getDirByEmail(email: String): String

    @Insert
    suspend fun insertUser(user: User)



    @Query("SELECT EXISTS(SELECT 1 FROM users_table WHERE email = :email)")
    suspend fun checkEmailExists(email: String): Boolean

    @Query("UPDATE users_table SET pass = :newPass WHERE email = :email")
    suspend fun updatePasswordByEmail(email: String, newPass: String)
}