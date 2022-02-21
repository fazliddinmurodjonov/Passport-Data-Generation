package com.example.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.adapter.SpinnerAdapter
import com.example.androidlesson7databasepassport.BuildConfig
import com.example.androidlesson7databasepassport.R
import com.example.androidlesson7databasepassport.databinding.CustomPermissionDialogBinding
import com.example.androidlesson7databasepassport.databinding.FragmentAddCitizenDetailsBinding
import com.example.room.dao.CitizenDao
import com.example.room.database.PassportDB
import com.example.room.entity.Citizen
import com.example.util.Empty
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddCitizenDetailsFragment : Fragment(R.layout.fragment_add_citizen_details) {
    private val binding: FragmentAddCitizenDetailsBinding by viewBinding()
    var imagePath: String? = null
    var photoURI: Uri? = null
    lateinit var regionList: ArrayList<String>
    lateinit var sexList: ArrayList<String>
    lateinit var passportDB: PassportDB
    lateinit var citizenDao: CitizenDao
    lateinit var spinnerRegion: SpinnerAdapter
    lateinit var spinnerSex: SpinnerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding)
        {
            actionBar()
            loadValues()
            saveButton.setOnClickListener {
                val firstname = firstname.text.toString()
                val lastname = lastname.text.toString()
                val patronymic = patronymic.text.toString()
                val dateOfBirth = dateOfBirth.text.toString()
                val nationality = nationality.text.toString()
                val regionPosition = region.selectedItemPosition
//                val city = cityDistrict.text.toString()
//                val address = address.text.toString()
                val dateOfIssue = dateOfIssue.text.toString()
                val dateOfExpiry = dateOfExpiry.text.toString()
                val sexPosition = sex.selectedItemPosition
                val region = regionList[regionPosition]
                val sex = sexList[sexPosition]
                val firstnameBol = Empty.empty(firstname)
                val lastnameBol = Empty.empty(lastname)
                val patronymicBol = Empty.empty(patronymic)
                val dateOfBirthBol = Empty.empty(dateOfBirth)
                val nationalityBol = Empty.empty(nationality)
//                val cityBol = Empty.empty(city)
//                val addressBol = Empty.empty(address)
                val dateOfIssueBol = Empty.empty(dateOfIssue)
                val dateOfExpiryBol = Empty.empty(dateOfExpiry)
                val firstSpace = Empty.space(firstname)
                val lastSpace = Empty.space(lastname)
                val patronymicSpace = Empty.space(patronymic)
                val dateOfBirthSpace = Empty.space(dateOfBirth)
                val nationalitySpace = Empty.space(nationality)
//                val citySpace = Empty.space(city)
//                val addressSpace = Empty.space(address)
                val dateOfIssueSpace = Empty.space(dateOfIssue)
                val dateOfExpirySpace = Empty.space(dateOfExpiry)
                val bol =
                    firstnameBol && lastnameBol && patronymicBol && dateOfBirthBol && nationalityBol && dateOfIssueBol && dateOfExpiryBol
                val space =
                    firstSpace && lastSpace && patronymicSpace && dateOfBirthSpace && nationalitySpace && dateOfIssueSpace && dateOfExpirySpace
                if (bol && space && sexPosition != 0 && regionPosition != 0 && imagePath != null) {
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setMessage("Are you sure your information is correct?")
                    dialog.setPositiveButton("Yes"
                    ) { dialog, which ->
                        val passportNumber = passportGeneration()

                        val citizen = Citizen(firstname,
                            lastname,
                            patronymic,
                            dateOfBirth,
                            nationality,
                            region,
                            dateOfIssue,
                            dateOfExpiry,
                            sex,
                            imagePath,
                            passportNumber)
                        citizenDao.addCitizen(citizen)
                        dialog.dismiss()
                        findNavController().popBackStack()
                    }
                    dialog.setNegativeButton("No"
                    ) { dialog, which -> dialog.dismiss() }
                    dialog.show()

                }

            }
            image.setOnClickListener {
                val dialog = Dialog(requireContext())
                val dialogView =
                    CustomPermissionDialogBinding.inflate(LayoutInflater.from(requireContext()),
                        null,
                        false)
                dialog.setContentView(dialogView.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialogView.camera.setOnClickListener {
                    val imageFile = createImageFile()
                    photoURI =
                        FileProvider.getUriForFile(requireContext(),
                            BuildConfig.APPLICATION_ID,
                            imageFile)
                    getTakeImageContent.launch(photoURI)
                    dialog.dismiss()
                }
                dialogView.gallery.setOnClickListener {
                    pickImageFromGalleryNew()
                    dialog.dismiss()

                }
                dialog.show()
            }


        }


    }

    private fun passportGeneration(): String {
        var passportNumber = ""
        var passLetter = ""
        var passNumber = ""
        for (i in 0 until 2) {
            val randomLetter = ('A' until 'Z').random()
            passLetter += randomLetter
        }
        for (i in 0 until 7) {
            val randomNumber = (0 until 9).random()
            passNumber += randomNumber
        }
        passportNumber += passLetter
        passportNumber += passNumber
        return passportNumber
    }

    private fun loadValues() {
        passportDB = PassportDB.getInstance(requireContext())
        citizenDao = passportDB.citizenDao()

        regionList = ArrayList()
        sexList = ArrayList()
        regionList.addAll(ArrayList(listOf(*resources.getStringArray(R.array.regions))))
        sexList.addAll(ArrayList(listOf(*resources.getStringArray(R.array.sex))))
        spinnerRegion = SpinnerAdapter(regionList)
        spinnerSex = SpinnerAdapter(sexList)
        binding.region.adapter = spinnerRegion
        binding.sex.adapter = spinnerSex
    }

    private fun actionBar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.arrow_back)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val externalFilesDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", externalFilesDir).apply {

        }
    }

    private val getTakeImageContent =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                binding.image.setImageURI(photoURI)
                val openInputStream = requireActivity().contentResolver?.openInputStream(photoURI!!)
                var numberText = "1234567890"
                val toCharArray = numberText.toCharArray()
                toCharArray.shuffle()
                var imageName: String = ""
                for (c in toCharArray) {
                    imageName += c
                }
                val file = File(requireActivity().filesDir, "$imageName.jpg")
                val fileOutputStream = FileOutputStream(file)
                openInputStream?.copyTo(fileOutputStream)
                openInputStream?.close()
                fileOutputStream.close()
                imagePath = file.absolutePath
            }
        }

    private fun pickImageFromGalleryNew() {
        getImageContent.launch("image/*")
    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri ?: return@registerForActivityResult
            binding.image.setImageURI(uri)
            val openInputStream = requireActivity().contentResolver?.openInputStream(uri)
            var numberText = "1234567890"
            val toCharArray = numberText.toCharArray()
            toCharArray.shuffle()

            var imageName: String = ""
            for (c in toCharArray) {
                imageName += c
            }
            val file = File(requireActivity().filesDir, "$imageName.jpg")
            val fileOutputStream = FileOutputStream(file)
            openInputStream?.copyTo(fileOutputStream)
            openInputStream?.close()
            fileOutputStream.close()
            val absolutePath = file.absolutePath
            imagePath = absolutePath
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }

}