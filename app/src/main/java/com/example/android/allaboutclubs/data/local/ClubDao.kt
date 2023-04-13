package com.example.android.allaboutclubs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.allaboutclubs.domain.model.Club
import kotlinx.coroutines.flow.Flow

@Dao
interface ClubDao {

    @Query("SELECT * FROM club")
    fun getClubs(): Flow<List<Club>>

    @Query("SELECT * FROM club WHERE id=:clubId")
    suspend fun getClubById(clubId:String):Club

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClubs(clubs:List<Club>)

}