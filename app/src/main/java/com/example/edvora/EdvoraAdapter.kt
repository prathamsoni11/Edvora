package com.example.edvora

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.view.*

class EdvoraAdapter(val item: ArrayList<Edvora>) :
    RecyclerView.Adapter<EdvoraAdapter.EdvoraViewHolder>() {
    class EdvoraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val map = itemView.map.findViewById<ImageView>(R.id.map)
        val city = itemView.city.findViewById<TextView>(R.id.city)
        val state = itemView.state.findViewById<TextView>(R.id.state)
        val rideIt = itemView.rideIt.findViewById<TextView>(R.id.rideIt)
        val origin = itemView.origin.findViewById<TextView>(R.id.origin)
        val date = itemView.date.findViewById<TextView>(R.id.date)
        val distance = itemView.distance.findViewById<TextView>(R.id.distance)
        val station = itemView.station.findViewById<TextView>(R.id.station)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EdvoraViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return EdvoraViewHolder(view)
    }

    override fun onBindViewHolder(holder: EdvoraViewHolder, position: Int) {
        holder.city.text = item[position].city
        holder.station.text = item[position].station_path.replace(",", ", ")
        holder.rideIt.text = item[position].ride_id.toString()
        holder.origin.text = item[position].origin_station.toString()
        holder.date.text = item[position].date
        holder.distance.text = (item[position].distance - item[position].origin_station).toString()
        holder.state.text = item[position].state
        Glide.with(holder.itemView.context).load(item[position].map_url).into(holder.map)
    }

    override fun getItemCount(): Int {
        return item.size
    }
}