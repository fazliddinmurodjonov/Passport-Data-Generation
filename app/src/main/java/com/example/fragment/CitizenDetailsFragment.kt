package com.example.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.adapter.CitizenRVAdapter
import com.example.androidlesson7databasepassport.R
import com.example.androidlesson7databasepassport.databinding.ActivityMainBinding
import com.example.androidlesson7databasepassport.databinding.FragmentCitizenDetailsBinding
import com.example.room.database.PassportDB
import com.example.room.entity.Citizen

class CitizenDetailsFragment : Fragment(R.layout.fragment_citizen_details) {
    private val binding: FragmentCitizenDetailsBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadActionBar()
        val passportDB = PassportDB.getInstance(requireContext())
        val citizenDao = passportDB.citizenDao()
        val allCitizenList = citizenDao.getAllCitizen()
        val citizenRVAdapter = CitizenRVAdapter(requireContext(), allCitizenList)
        binding.citizenRV.adapter = citizenRVAdapter
        citizenRVAdapter.setOnItemClickListener(object : CitizenRVAdapter.OnItemClickListener {
            override fun onClick(citizen: Citizen) {
                val bundleOf = bundleOf("id" to citizen.id)
                findNavController().navigate(R.id.aboutCitizenFragment, bundleOf)
            }

        })
    }

    private fun loadActionBar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
            }
            else -> {
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }

    override fun onResume() {
        super.onResume()
    }


}

