package com.jemessonlima.weather.forecast.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jemessonlima.weather.forecast.R
import com.jemessonlima.weather.forecast.model.CityDatabase
import kotlinx.android.synthetic.main.favorites_item.view.*

class FavoriteAdapter(
    private val list: List<CityDatabase>?
): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.favorites_item, parent, false)

        return FavoriteViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (position < list?.size!!) {
            val element = list[position]
            holder.bindView(element)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvCityName: TextView = itemView.tv_favorite_name
        private val tvCityNumber: TextView = itemView.tv_favorite_number

        fun bindView(cityDatabase: CityDatabase) {
            tvCityName.text = cityDatabase.cityName
            tvCityNumber.text = cityDatabase.id.toString()
        }
    }

    class FavoriteItemDecoration(private val size: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = size
                }
                left = size
                right = size
                bottom = size
            }
        }
    }

}
