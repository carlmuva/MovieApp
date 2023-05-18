package com.example.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.movieapp.core.Resource
import com.example.movieapp.data.remote.MovieDataSource
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.domain.MovieRepositoryImpl
import com.example.movieapp.domain.RetrofitClient
import com.example.movieapp.domain.WebService
import com.example.movieapp.presentation.MovieViewModel
import com.example.movieapp.presentation.MovieViewModelFactory
import kotlin.math.log


class MovieFragment : Fragment() {

    private lateinit var mBinding : FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {MovieViewModelFactory(MovieRepositoryImpl(
        MovieDataSource(RetrofitClient.webService)
    ))  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentMovieBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Resource.Loading ->{
                    Log.d("LiveData", "Loading...")
                }
                is Resource.Success ->{
                    Log.d("LiveData", "Upcoming: ${result.data.first} ")
                    Log.d("Livedata", "TopRated: ${result.data.second}")
                    Log.d("Livedata", "Popular: ${result.data.third}")
                }
                is Resource.Failure->{
                    Log.d("Error","${result.exception}")
                }
            }
        })


    }

}