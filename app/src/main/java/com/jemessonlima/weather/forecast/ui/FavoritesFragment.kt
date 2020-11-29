package com.jemessonlima.weather.forecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jemessonlima.weather.forecast.R
import com.jemessonlima.weather.forecast.adapter.FavoriteAdapter
import com.jemessonlima.weather.forecast.database.WeatherAppDatabase
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = context?.let { WeatherAppDatabase.getInstance(it) }
        val listFavorites = db?.cityDatabaseDao()?.getAllCitiesDatabase()

        recyclerViewFavorites.adapter = FavoriteAdapter(listFavorites)
        recyclerViewFavorites.layoutManager = LinearLayoutManager(view.context)
        recyclerViewFavorites.addItemDecoration(FavoriteAdapter.FavoriteItemDecoration(25))
    }
}
