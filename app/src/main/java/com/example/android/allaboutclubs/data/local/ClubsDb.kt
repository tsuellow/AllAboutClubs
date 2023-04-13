package com.example.android.allaboutclubs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.allaboutclubs.domain.model.Club

@Database(
    entities = [Club::class],
    version = 1
)
abstract class ClubsDb:RoomDatabase() {
    abstract val clubDao:ClubDao

    companion object{
        const val DB_NAME="clubs_db"
    }
}