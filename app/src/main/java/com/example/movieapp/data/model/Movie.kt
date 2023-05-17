package com.example.movieapp.data.model


import com.google.gson.annotations.SerializedName


data class Movie(
    @SerializedName("adult") val adult: Boolean = false,
    @SerializedName("backdrop_path") val backdrop_path: String = "",
    @SerializedName("genre_ids") val genre_ids: List<Int> = listOf(),
    @SerializedName("id") val id: Int = -1,
    @SerializedName("original_title") val original_title: String = "",
    @SerializedName("original_language") val original_language: String = "",
    @SerializedName("overview") val overview: String = "",
    @SerializedName("popularity") val popularity: Double = -1.0,
    @SerializedName("poster_path") val poster_path: String = "",
    @SerializedName("release_date") val release_date: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("video") val video: Boolean = false,
    @SerializedName("vote_average") val vote_average: Double = -1.0,
    @SerializedName("vote_count") val vote_count: Int = -1
)

data class MovieList(val results: List<Movie> = listOf())
