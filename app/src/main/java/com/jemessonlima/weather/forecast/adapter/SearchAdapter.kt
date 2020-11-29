package com.jemessonlima.weather.forecast.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jemessonlima.weather.forecast.R
import com.jemessonlima.weather.forecast.model.Element
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class SearchAdapter(
    private val elements: MutableList<Element>
): RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return elements.size
    }

    fun addItems(newElements: MutableList<Element>) {
        elements.clear()
        newElements.forEach { elements.add(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position < elements.size) {
            val element = elements[position]
            holder.bindView(element)
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvCityName: TextView = itemView.tv_city_name
        private val tvCityCode: TextView = itemView.tv_city_number
        private val ivWeather: ImageView = itemView.image_view_weather

        fun bindView(element: Element) = with(itemView) {
            tvCityName.text = element. name
            tvCityCode.text = element.id.toString()

            Glide.with(context)
                    .load("http://openweathermap.org/img/wn/${element.weather[0].icon}@4x.png")
                    .placeholder(R.drawable.ic_weather_placeholder)
                    .error(R.drawable.ic_weather_placeholder)
                    .centerCrop()
                    .into(ivWeather)
        }
    }

    class WeatherDecoration(private val size: Int) : RecyclerView.ItemDecoration() {

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
