package com.example.newnews

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {                   // because news item clicked is an interface so it should be implemented here

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager=LinearLayoutManager(this)          // say recycler view that this linear layout manager
        fetchData()                                                          // calling function
        mAdapter=NewsListAdapter(this)                                // instance of News list adapter class
        recyclerView.adapter= mAdapter                                       // linking recycler view with adapter
    }

    private fun fetchData() {
        val url= "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"    // api url
        val jsonObjectRequest = JsonObjectRequest(                          // Requesting a JsonObjectRequest response from the provided URL.
            Request.Method.GET,
            url,                                                           // get method to get url
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")          //articles is type of array so we are using get Json array
                val newsArray = ArrayList<News>()                             //News is the class which we created
                for (i in 0 until newsJsonArray.length()) {                     // iterating into the news json array
                val newsJsonObject = newsJsonArray.getJSONObject(i)            // iterating into the array
                val news = News(                                               // creating object of news class
                    newsJsonObject.getString("title"),                   // inside article array we need only these things ( this are mentioned in the news class)
                    newsJsonObject.getString("author"),
                    newsJsonObject.getString("url"),
                    newsJsonObject.getString("urlToImage")
                )
                    newsArray.add(news)                                         // now we are adding the news into the news array
            }
                mAdapter.updateNews(newsArray)                                 //update news is added to news array
            },
            {
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {                                   // implementing the interface of new item clicked

        val builder = CustomTabsIntent.Builder()                               // to build the custom chrome tab
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}