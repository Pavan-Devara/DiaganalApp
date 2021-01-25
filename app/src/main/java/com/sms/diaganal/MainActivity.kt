package com.sms.diaganal

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


//intialize itemList with names of posters, itemImages which contains the images, gson
val itemList = listOf<String>().toMutableList()
val itemImages = listOf<Int>().toMutableList()
val itemImagesNames = listOf<String>().toMutableList()
var searchList = listOf<String>().toMutableList()
var searchImages = listOf<Int>().toMutableList()
var titleHead: String = ""
val gson = Gson()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initialize the grid view
        val gridview = findViewById<GridView>(R.id.gridview)

        //call listingImages function to add all the names and posters present in given json
        listingImages("CONTENTLISTINGPAGE-PAGE1.json")
        listingImages("CONTENTLISTINGPAGE-PAGE2.json")
        listingImages("CONTENTLISTINGPAGE-PAGE3.json")

        heading_groups.setText(titleHead.toString())
        Log.i("itemList", itemList.toString())
        //adapter to display the grid view with parameters -> context, custom layout to be inflated, list of names, list of images

        search.setOnClickListener {
            heading_groups.visibility = View.INVISIBLE
            back_groups.visibility = View.GONE
            nav_bar.visibility = View.VISIBLE
            search.visibility = View.GONE
            search_cancel.visibility = View.VISIBLE
        }
        search_cancel.setOnClickListener {
            heading_groups.visibility = View.VISIBLE
            back_groups.visibility = View.VISIBLE
            nav_bar.visibility = View.GONE
            search_cancel.visibility = View.GONE
            search.visibility = View.VISIBLE

            val adapter = GridViewAdapter(applicationContext, R.layout.list_view, itemList, itemImages)
            adapter.notifyDataSetChanged()
            gridview.adapter = adapter
        }

        //adding a TextChangedListener
        //to call a method whenever there is some change on the EditText
        nav_bar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                val adapter = GridViewAdapter(applicationContext, R.layout.list_view, itemList, itemImages)
                adapter.notifyDataSetChanged()
                gridview.adapter = adapter
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                if (editable.toString().length >= 3) {
                    filter(editable.toString())
                }
            }
        })

        val adapter = GridViewAdapter(this, R.layout.list_view, itemList, itemImages)
        adapter.notifyDataSetChanged()
        gridview.adapter = adapter
    }

    private fun filter(text: String) {
        searchList = listOf<String>().toMutableList()
        searchImages = listOf<Int>().toMutableList()
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<String> = ArrayList()

        //looping through existing elements
        for (i in 0 until itemList.size){
            if (itemList[i].toLowerCase().contains(text.toLowerCase())) {
                searchList.add(itemList[i])
                when (itemImagesNames[i]) {
                    "poster1.jpg" -> searchImages.add(R.drawable.poster1)
                    "poster2.jpg" -> searchImages.add(R.drawable.poster2)
                    "poster3.jpg" -> searchImages.add(R.drawable.poster3)
                    "poster4.jpg" -> searchImages.add(R.drawable.poster4)
                    "poster5.jpg" -> searchImages.add(R.drawable.poster5)
                    "poster6.jpg" -> searchImages.add(R.drawable.poster6)
                    "poster7.jpg" -> searchImages.add(R.drawable.poster7)
                    "poster8.jpg" -> searchImages.add(R.drawable.poster8)
                    "poster9.jpg" -> searchImages.add(R.drawable.poster9)
                    else -> searchImages.add(R.drawable.missing_poster_resize)
                }
            }
        }

        //calling a method of the adapter class and passing the filtered list
        val adapter = GridViewAdapter(this, R.layout.list_view, searchList, searchImages)
        adapter.notifyDataSetChanged()
        gridview.adapter = adapter
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            //reading the json file in assets folder
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


    fun listingImages(fileName: String) {
        //getJsonDataFromAsset function is called to extract the data from json in assets folder
        val jsonFileString = getJsonDataFromAsset(this, fileName)

        if (jsonFileString != null) {
            //logging the returned data
            Log.i("data", jsonFileString)
        }

        //parsing the json string to required kotlin classes
        val listPosters = object : TypeToken<PosterImagesData>() {}.type
        val posters: PosterImagesData = gson.fromJson(jsonFileString, listPosters)
        titleHead = posters.page.title

        //for loop to add the names and images to lists initialized and sent to adapter to display
        for (names in posters.page.content_items.content){
            itemList.add(names.name)
            itemImagesNames.add(names.poster_image)
            when (names.poster_image){
                "poster1.jpg" -> itemImages.add(R.drawable.poster1)
                "poster2.jpg" -> itemImages.add(R.drawable.poster2)
                "poster3.jpg" -> itemImages.add(R.drawable.poster3)
                "poster4.jpg" -> itemImages.add(R.drawable.poster4)
                "poster5.jpg" -> itemImages.add(R.drawable.poster5)
                "poster6.jpg" -> itemImages.add(R.drawable.poster6)
                "poster7.jpg" -> itemImages.add(R.drawable.poster7)
                "poster8.jpg" -> itemImages.add(R.drawable.poster8)
                "poster9.jpg" -> itemImages.add(R.drawable.poster9)
                else -> itemImages.add(R.drawable.missing_poster_resize)
            }
        }
    }
}