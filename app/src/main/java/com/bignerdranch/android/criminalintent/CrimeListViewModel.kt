package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel

/*
    ViewModel объект позволяет сохранять данные UI при изменении конфигурации приложения
 */
class CrimeListViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()
//    к данным обращаемся через Repository
    val crimeListLiveData = crimeRepository.getCrimes()

}