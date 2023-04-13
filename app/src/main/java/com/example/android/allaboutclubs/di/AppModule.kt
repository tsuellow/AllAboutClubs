package com.example.android.allaboutclubs.di

import android.app.Application
import androidx.room.Room
import com.example.android.allaboutclubs.common.Constants
import com.example.android.allaboutclubs.common.PreferenceManager
import com.example.android.allaboutclubs.data.local.ClubsDb
import com.example.android.allaboutclubs.data.remote.ClubsApi
import com.example.android.allaboutclubs.data.remote.repository.ClubsRepositoryImpl
import com.example.android.allaboutclubs.domain.repository.ClubsRepository
import com.example.android.allaboutclubs.domain.use_case.GetClubByIdUseCase
import com.example.android.allaboutclubs.domain.use_case.GetClubsListUseCase
import com.example.android.allaboutclubs.domain.use_case.RefreshDataUseCase
import com.example.android.allaboutclubs.domain.use_case.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesClubsDb(app:Application):ClubsDb{
        return Room.databaseBuilder(
            app,
            ClubsDb::class.java,
            ClubsDb.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesClubsApi():ClubsApi{
        return Retrofit.Builder()
            .baseUrl(Constants.PLACEHOLDER_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ClubsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesClubsRepository(
        api:ClubsApi,
        db:ClubsDb
    ):ClubsRepository{
        return ClubsRepositoryImpl(
            clubDao = db.clubDao,
            clubsApi = api
        )
    }

    @Provides
    @Singleton
    fun providesUseCases(repository: ClubsRepository): UseCases {
        return UseCases(
            getClubByIdUseCase = GetClubByIdUseCase(repository),
            getClubsListUseCase = GetClubsListUseCase(repository),
            refreshDataUseCase = RefreshDataUseCase(repository)
        )
    }



}