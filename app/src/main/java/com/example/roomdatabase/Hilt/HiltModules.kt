package com.example.roomdatabase.Hilt

import android.content.Context
import com.example.roomdatabase.PersonRepo
import com.example.roomdatabase.PersonDao
import com.example.roomdatabase.PersonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object HiltModules {


    /** Hilt Module to tell the Dagger Hilt how to inject PersonDao and PersonRespository */


    @Provides
    @Singleton
    fun providePersonDao(@ApplicationContext context: Context): PersonDao {
        return PersonDatabase.getInstance(context).getPersonDao()
    }


    @Singleton
    @Provides
    fun personRepo(personDao: PersonDao): PersonRepo {
        return PersonRepo(personDao)
    }

}