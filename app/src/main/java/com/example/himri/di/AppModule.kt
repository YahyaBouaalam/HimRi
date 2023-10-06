package com.example.himri.di

import android.app.Application
import com.example.himri.room.dao.HistoryDao
import com.example.himri.room.dao.UserDao
import com.example.himri.room.database.LocalDatabase
import com.example.himri.room.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRepository(userDAO: UserDao, historyDao: HistoryDao): Repository {
        return Repository(userDAO = userDAO, historyDAO = historyDao)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): LocalDatabase {
        return LocalDatabase.getInstance(app)
    }

    @Singleton
    @Provides
    fun provideUserDao(database: LocalDatabase): UserDao {
        return database.userDAO()
    }

    @Singleton
    @Provides
    fun provideHistoryDao(database: LocalDatabase): HistoryDao {
        return database.historyDAO()
    }
}