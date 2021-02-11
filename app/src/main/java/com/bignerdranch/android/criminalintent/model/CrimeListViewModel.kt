package com.bignerdranch.android.criminalintent.model

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.criminalintent.Crime
import com.bignerdranch.android.criminalintent.CrimeRepository

/*
    ViewModel объект позволяет сохранять данные UI при изменении конфигурации приложения
 */
class CrimeListViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()
//    к данным обращаемся через Repository
    val crimeListLiveData = crimeRepository.getCrimes()

    fun addCrime(crime: Crime) {
        crimeRepository.addCrime(crime)
    }

}