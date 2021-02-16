package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.criminalintent.database.CrimeDataBase
import com.bignerdranch.android.criminalintent.database.migration_1_2
import java.util.*
import java.util.concurrent.Executors

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

    private val executor = Executors.newSingleThreadExecutor()

//    Создаем конкретную реализацию абстрактного класса CrimeDatabase
    private val database: CrimeDataBase = Room.databaseBuilder(
            context.applicationContext,
            CrimeDataBase::class.java,
            DATABASE_NAME)
            .addMigrations(migration_1_2).build()
//    Реализация функция DAO
    private val crimeDao = database.crimeDao()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    fun updateCrime(crime: Crime) {
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    fun addCrime(crime:Crime) {
        executor.execute {
            crimeDao.addCrime(crime)
        }
    }


}