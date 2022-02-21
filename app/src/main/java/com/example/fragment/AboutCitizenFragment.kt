package com.example.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson7databasepassport.R
import com.example.androidlesson7databasepassport.databinding.FragmentAboutCitizenBinding
import com.example.room.database.PassportDB


class AboutCitizenFragment : Fragment(R.layout.fragment_about_citizen) {
    private val binding: FragmentAboutCitizenBinding by viewBinding()


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding)
        {
            val id = arguments?.getInt("id")
            val passportDB = PassportDB.getInstance(requireContext())
            val citizenDao = passportDB.citizenDao()
            val citizen = citizenDao.getCitizenById(id!!)
            setHasOptionsMenu(true)
            (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar!!.title =
                "${citizen.lastname} ${citizen.firstname}"
            image.setImageURI(Uri.parse(citizen.photoPath))
            firstname.text = "Firstname : ${citizen.firstname}"
            lastname.text = "Lastname : ${citizen.lastname}"
            patronymic.text = "Patronymic : ${citizen.patronymic}"
            dateOfBirth.text = "Date of birth : ${citizen.dateOfBirth}"
            nationality.text = "Nationality : ${citizen.nationality}"
            region.text = "Region : ${citizen.region}"
//            cityDistrict.text = "City/District : ${citizen.city}"
//            address.text = "Address : ${citizen.address}"
            dateOfIssue.text = "Date of issue : ${citizen.dateOfIssue}"
            dateOfExpiry.text = "Date of expiry : ${citizen.dateOfExpiry}"
            sex.text = "Gender : ${citizen.gender}"
            passportNumber.text = "Passport number : ${citizen.passportNumber}"
        }
    }

}