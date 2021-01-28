package com.bignerdranch.android.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bignerdranch.android.criminalintent.Crime
import java.util.*

/*
    Объект доступа к данным, через этот класс оборачиваются запросы SQLite
 */
@Dao
interface CrimeDao {

//    Возвращает список преступлений
    @Query("SELECT * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>

//    Возвращает один объект Crime по id
    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime?>



}