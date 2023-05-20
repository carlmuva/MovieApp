package com.example.movieapp.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.FragmentMovieDetailBinding


class MovieDetailFragment : Fragment() {

    private lateinit var mBinding : FragmentMovieDetailBinding
    private val args  by navArgs<MovieDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentMovieDetailBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentMovieDetailBinding.bind(view)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterImageUrl}").centerCrop().into(mBinding.imgMovie)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.backgroundImageUrl}").centerCrop().into(mBinding.imgBackground)
        mBinding.txtDescription.text=args.overview
        mBinding.txtMovieTitle.text= args.title
        mBinding.txtLanguage.text="Language ${args.lenguage}"
        mBinding.txtRating.text="${args.voteAverage} (${args.voteCount} Reviews)"
        mBinding.txtReleased.text= "Released ${args.releaseDate}"
    }


}