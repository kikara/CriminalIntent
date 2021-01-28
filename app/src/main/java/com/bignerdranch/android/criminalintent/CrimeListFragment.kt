package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
//    LiveData требует времени, пока инициализируем пустой список
    private var crimeAdapter: CrimeAdapter? = CrimeAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
//        Инициализируем пустой список
        crimeRecyclerView.adapter = crimeAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Настраиваем адаптер на новый список преступлений когда данные будут готовы в LiveData
//        Регистрация наблуюдателя за экземпляром LiveData
//        Время жизни связан с фрагментом
        crimeListViewModel.crimeListLiveData.observe(viewLifecycleOwner, Observer {
//            Этот блок отвечает за новые данные в LiveData
            it?.let {
                Log.i(TAG, "Got crimes ${it.size}")
                updateUI(it)
            }
        })

    }

//    Настройка адаптера RecyclerView
    private fun updateUI(crimes: List<Crime>) {
        crimeAdapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = crimeAdapter
    }

//    RecyclerView отображает View завернутые в ViewHolder(он хранит ссылку на представление элемента)
//    ViewHolder имеет свои itemView
    private inner class CrimeHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime
//    Ссылки на представления
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title) as TextView
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date) as TextView
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved) as ImageView

        init {
            itemView.setOnClickListener(this)
        }
        fun bind(crime:Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            solvedImageView.visibility = if(crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
//        Обработка нажатий
        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT)
                    .show()
        }
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
            holder.bind(crime)
        }
//        RecyclerView запрашивает количество элементов в списке
        override fun getItemCount(): Int = crimes.size

    }

}