package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val TAG = "CrimeListFragment"

class CrimeListFragment: Fragment() {

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private lateinit var crimeRecyclerView: RecyclerView
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }
    private var crimeAdapter: CrimeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

//    Настройка адаптера RecyclerView
    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        crimeAdapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = crimeAdapter
    }

//    RecyclerView отображает View завернутые в ViewHolder(он хранит ссылку на представление элемента)
//    ViewHolder имеет свои itemView
    private inner class CrimeHolder(view: View)
        :RecyclerView.ViewHolder(view) {
//    Ссылки на текстовые представления
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title) as TextView
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date) as TextView
    }

//    Адаптер является связующим звеном между RecyclerView и набором данных
    private inner class CrimeAdapter(var crimes: List<Crime>)
        : RecyclerView.Adapter<CrimeHolder>() {
//        Оборачивает view в holder и возвращает результат, здесь наполняет в layout
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }
//        Заполнение холдера из данной позиции
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.apply {
                titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()
            }
        }
//        RecyclerView запрашивает количество элементов в списке
        override fun getItemCount(): Int = crimes.size

    }

}