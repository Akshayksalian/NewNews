package com.example.newnews

data class News (                       // data class Constructor
    val title: String,                  // title we need from api in a string format
    val author: String,                 // same for all
    val url: String,
    val urlToImage: String
)
