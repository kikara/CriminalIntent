package com.bignerdranch.android.criminalintent

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

/*
 *  Torbogoshev Artur
 *  last update: 228
 *
 */


class MainActivity : AppCompatActivity() {
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
}