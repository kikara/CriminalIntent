package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import java.util.*

/*
 *  Torbogoshev Artur
 *  last update: 340
 *
 */

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), CrimeListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Сначала запрашивается у FragmentManager фрагмент
       val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
//        Если его нет, то создаем новый экземпляр и новую транзакцию
        if(currentFragment == null) {
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager.commit {
                add(R.id.fragment_container, fragment)
                setReorderingAllowed(true)
            }
        }


    }

    override fun onCrimeSelected(crimeId: UUID) {
        val fragment = CrimeFragment.newInstance(crimeId)
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }
}