package com.example.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson7databasepassport.R
import com.example.androidlesson7databasepassport.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding)
        {
            allCitizens.setOnClickListener {
                findNavController().navigate(R.id.citizenDetailsFragment)
            }
            issuanceOfPassport.setOnClickListener {
                findNavController().navigate(R.id.addCitizenDetailsFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

    }
}