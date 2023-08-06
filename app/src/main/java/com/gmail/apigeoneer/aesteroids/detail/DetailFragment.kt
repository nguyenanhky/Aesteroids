package com.gmail.apigeoneer.aesteroids.detail

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gmail.apigeoneer.aesteroids.R
import com.gmail.apigeoneer.aesteroids.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    // data binding
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireNotNull(activity).application

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        binding.asteroid = asteroid

        val viewModelFactory = DetailViewModel.DetailViewModelFactory(asteroid, application)

        binding.detailViewModel = ViewModelProvider(this,
            viewModelFactory).get(DetailViewModel::class.java)

        // display a help info dialog on clicking the help (??) button
        binding.helpIv.setOnClickListener {
            displayHelpExplanationDialog()
        }

        // navigate up
        binding.backIv.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun displayHelpExplanationDialog() {
        AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.help_explanation))
            .setPositiveButton("OK", null)
            .create()
            .show()
    }
}