package com.jemessonlima.weather.forecast.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jemessonlima.weather.forecast.R
import com.jemessonlima.weather.forecast.adapter.SearchAdapter
import com.jemessonlima.weather.forecast.database.WeatherAppDatabase
import com.jemessonlima.weather.forecast.manager.OpenWeatherManager
import com.jemessonlima.weather.forecast.model.City
import com.jemessonlima.weather.forecast.model.CityDatabase
import com.jemessonlima.weather.forecast.model.Element
import com.jemessonlima.weather.forecast.model.Root
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = SearchAdapter(mutableListOf())

        btnSearch.setOnClickListener {
            val context = view.context

            when(isConnectivityAvailable(context)) {
                true ->  {
                    searchWeather(context)
                }
                false -> {
                    Toast.makeText(context, getText(R.string.disconnected), Toast.LENGTH_SHORT).show()
                }
            }
        }

        floatingActionButton.setOnClickListener {
            val city = edtCityName.text.toString()
            val service = OpenWeatherManager().getOpenWeatherService()

            val call = service.getCityWeather(city)
            call.enqueue(object : Callback<City> {
                override fun onResponse(call: Call<City>, response: Response<City>) {
                    response.body()?.run {
                        val context = view.context
                        val db = WeatherAppDatabase.getInstance(context)

                        db?.cityDatabaseDao()?.save(CityDatabase(this.id, this.name))
                        Toast.makeText(context, getText(R.string.city_favorite), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<City>, t: Throwable) {
                    Toast.makeText(view.context, getText(R.string.city_favorite_failure), Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun isConnectivityAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }

        return result
    }

    private fun searchWeather(context: Context) {
        val cityName = edtCityName.text.toString()
        val service = OpenWeatherManager().getOpenWeatherService()

        val call = service.findTemperatures(cityName)
        call.enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {
                when (response.isSuccessful) {
                    true -> {
                        response.body()?.run {
                            val elements = mutableListOf<Element>()

                            this.list.forEach {
                                elements.add(it)
                            }

                            (recyclerView.adapter as SearchAdapter).addItems(elements)
                            recyclerView.layoutManager = LinearLayoutManager(context)
                            recyclerView.addItemDecoration(SearchAdapter.WeatherDecoration(30))
                        }
                    }
                    false -> {
                        Log.e("JEM", "Response was not successfully.")
                    }
                }
            }

            override fun onFailure(call: Call<Root>, t: Throwable) {
                Log.e("JEM", "There is an error: ${t.message}.")
            }
        })
    }
}
