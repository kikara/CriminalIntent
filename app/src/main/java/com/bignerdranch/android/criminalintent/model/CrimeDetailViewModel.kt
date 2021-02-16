package com.bignerdranch.android.criminalintent.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.criminalintent.Crime
import com.bignerdranch.android.criminalintent.CrimeRepository
import java.io.File
import java.io.FileReader
import java.util.*

class CrimeDetailViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()
//    id отображаемого в данный момент преступления
    private val crimeIdLiveData = MutableLiveData<UUID>()

//    Позволяет установить преобразование данных в реальном времени
    var crimeLiveData: LiveData<Crime?> =
            Transformations.switchMap(crimeIdLiveData) { crimeId ->
                crimeRepository.getCrime(crimeId)
            }

    fun loadCrime(crimeId: UUID) {
        crimeIdLiveData.value = crimeId
    }
    fun saveCrime(crime:Crime) {
        crimeRepository.updateCrime(crime)
    }

    fun getPhotoFile(crime: Crime): File {
        return crimeRepository.getPhotoFile(crime)
    }
}