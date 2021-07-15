package com.example.newnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener:NewsItemClicked):RecyclerView.Adapter<NewsViewHolder>() {             // CLass Adapter with Constructor  (of News view Holder class ) // newsitemclicked is named as listner

    private val items:ArrayList<News> = ArrayList()                                                              // this will be the array list of news    // we cant directly pass the items to the adapter so function update news is created below

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)         // layout inflate is used to convert any xml to view  // title_news is the xml which will be converted
        val viewHolder=NewsViewHolder(view)                                                                     // instance of view holder
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])                                           // call back interface is created below
        }
        return viewHolder                                                                                       // returning view holder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {                                  // bind our item with our data
        val currentItem=items[position]                                                                     // iterating to items
        holder.titleView.text=currentItem.title                                                             // setting title view text to this particular place
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.image)
    }

    override fun getItemCount(): Int {                                                              // this will say how many items are there in this list
        return items.size                                                                           // size
    }

    fun updateNews(updatedNews: ArrayList<News>) {                                                  // update news of type array list of news
        items.clear()                                                                               // clearing the old if it is present
        items.addAll(updatedNews)                                                                   // adding all the news stuffs we wanted into the update news and it is called in the main activity

        notifyDataSetChanged()                                                                      // this will make all 3 function in the adapter to run again
    }
}
class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){                             // Class ViewHolder
    val titleView:TextView=itemView.findViewById(R.id.title)                                        // title view of type textview  (id of title from activity)
    val image:ImageView=itemView.findViewById(R.id.image)
    val author:TextView=itemView.findViewById(R.id.author)
}

interface NewsItemClicked{                                                                          // interface for onclick
    fun onItemClicked(item:News)                                                                    // function
}

