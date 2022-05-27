package com.example.edvora

import android.graphics.Typeface
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.json.JSONObject
import org.json.JSONTokener


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val stateAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.State,android.R.layout.simple_spinner_item)
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateDropDown.adapter = stateAdapter

        val cityAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.City,android.R.layout.simple_spinner_item)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cityDropDown.adapter = cityAdapter

        setUpTabs()
        fetchUser()

        filter.setOnClickListener {
            if (filterCard.visibility == View.GONE){
                filterCard.visibility = View.VISIBLE
            }
            else{
                filterCard.visibility = View.GONE
            }
        }


        tabs.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Nearest(), "Nearest")
        adapter.addFragment(Upcoming(), "Upcoming")
        adapter.addFragment(Past(), "Past")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)


    }

    fun fetchUser() {
        val userUrl = "https://assessment.api.vweb.app/user"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(Request.Method.GET, userUrl,
            {
                val jsonArray = JSONTokener(it).nextValue() as JSONObject

                userName.text = jsonArray.getString("name")
                Glide.with(this).load(jsonArray.getString("url")).into(userImg)
            },
            {
                Toast.makeText(this, "er", Toast.LENGTH_SHORT).show()
            })


        queue.add(stringRequest)
    }


}