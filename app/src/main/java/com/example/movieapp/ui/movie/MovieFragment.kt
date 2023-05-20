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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.movieapp.core.Resource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.remote.MovieDataSource
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.domain.MovieRepositoryImpl
import com.example.movieapp.domain.RetrofitClient
import com.example.movieapp.domain.WebService
import com.example.movieapp.presentation.MovieViewModel
import com.example.movieapp.presentation.MovieViewModelFactory
import com.example.movieapp.ui.movie.adapters.MovieAdapter
import com.example.movieapp.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.movieapp.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.movieapp.ui.movie.adapters.concat.UpcomingConcatAdapter
import kotlin.math.log


class MovieFragment : Fragment(), MovieAdapter.OnMovieClickListener {

    private lateinit var mBinding : FragmentMovieBinding
    private lateinit var concatAdapter: ConcatAdapter

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

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Resource.Loading ->{
                    //Log.d("LiveData", "Loading...")
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success ->{
                    mBinding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0,UpcomingConcatAdapter(MovieAdapter(result.data.first.results, this@MovieFragment)))
                        addAdapter(1,TopRatedConcatAdapter(MovieAdapter(result.data.second.results, this@MovieFragment)))
                        addAdapter(2,PopularConcatAdapter(MovieAdapter(result.data.third.results, this@MovieFragment)))
                    }
                    mBinding.rvMovies.adapter=concatAdapter

                   // Log.d("LiveData", "Upcoming: ${result.data.first} ")
                   // Log.d("Livedata", "TopRated: ${result.data.second}")
                   // Log.d("Livedata", "Popular: ${result.data.third}")
                }
                is Resource.Failure->{
                    mBinding.progressBar.visibility = View.GONE
                    Log.d("Error","${result.exception}")
                }
            }
        })


    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)

        //Log.d("Movie", "onMovieClick: $movie ")
    }
}