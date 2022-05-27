package com.example.edvora

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class Upcoming : Fragment() {

    private lateinit var upcomingRecyclerView: RecyclerView
    private lateinit var upcomingArray: ArrayList<Edvora>
    private lateinit var adapter: EdvoraAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingArray = ArrayList<Edvora>()

        adapter = EdvoraAdapter(upcomingArray)
        upcomingRecyclerView = view.findViewById<RecyclerView>(R.id.upcomingRecyclerView)
        upcomingRecyclerView.layoutManager = LinearLayoutManager(context)

        fetchRides()
    }

    fun fetchRides() {
        val rideUrl = "https://assessment.api.vweb.app/rides"

        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET, rideUrl,
            {
                val jsonArray = JSONTokener(it).nextValue() as JSONArray


                for (i in 0 until jsonArray.length()) {
                    val newsJsonObject: JSONObject = jsonArray.getJSONObject(i)
                    var news = Edvora(
                        ride_id = newsJsonObject.getInt("id"),
                        date = newsJsonObject.getString("date"),
                        city = newsJsonObject.getString("city"),
                        state = newsJsonObject.getString("state"),
                        map_url = newsJsonObject.getString("map_url"),
                        origin_station = newsJsonObject.getInt("origin_station_code"),
                        distance = newsJsonObject.getInt("destination_station_code"),
                        station_path = newsJsonObject.getJSONArray("station_path").toString()
                    )
                    upcomingArray.add(news)
                }
                upcomingRecyclerView.adapter = adapter
            },
            {

            })


        queue.add(stringRequest)
    }

}