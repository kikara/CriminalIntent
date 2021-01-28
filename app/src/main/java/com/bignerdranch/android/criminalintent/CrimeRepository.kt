package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.criminalintent.database.CrimeDataBase
import java.util.*

/*
    Предоставляет прописать API для работы с БД через DAO
 */
private const val DATABASE_NAME = "crime-database"
//Синглтон - в процессе приложения сущетсвует только один его экземпляр
class CrimeRepository private constructor(context: Context) {

    companion object {
        private var INSTANCE: CrimeRepository? = null
//        Создаем экземпляр
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }

//    Создаем конкретную реализацию абстрактного класса CrimeDatabase
    private val database: CrimeDataBase = Room.databaseBuilder(
            context.applicationContext,
            CrimeDataBase::class.java,
            DATABASE_NAME)
            .build()
//    Реализация функция DAO
    private val crimeDao = database.crimeDao()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)




}