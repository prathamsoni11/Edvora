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

class Nearest : Fragment() {

    private lateinit var nearestRecyclerView: RecyclerView
    private lateinit var nearestArray: ArrayList<Edvora>
    private lateinit var adapter: EdvoraAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        nearestRecyclerView = view.findViewById<RecyclerView>(R.id.nearestRecyclerView)
        nearestRecyclerView.layoutManager = LinearLayoutManager(context)

        fetchRides()
    }

    fun fetchRides() {
        val rideUrl = "https://assessment.api.vweb.app/rides"

        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET, rideUrl,
            {
                val jsonArray = JSONTokener(it).nextValue() as JSONArray

                val tempArray: ArrayList<Edvora> = ArrayList()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject: JSONObject = jsonArray.getJSONObject(i)

                    var news = Edvora(
                        ride_id = jsonObject.getInt("id"),
                        date = jsonObject.getString("date"),
                        city = jsonObject.getString("city"),
                        state = jsonObject.getString("state"),
                        map_url = jsonObject.getString("map_url"),
                        origin_station = jsonObject.getInt("origin_station_code"),
                        distance = jsonObject.getInt("destination_station_code"),
                        station_path = jsonObject.getJSONArray("station_path").toString(),
                        temp = jsonObject.getJSONArray("station_path")[0] as Int
                    )
                    tempArray.add(news)
                }

                nearestArray = ArrayList(tempArray.sortedBy { it.temp })
                adapter = EdvoraAdapter(nearestArray)
                nearestRecyclerView.adapter = adapter
            },
            {

            })


        queue.add(stringRequest)

    }
}