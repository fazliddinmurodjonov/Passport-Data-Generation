package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

class Citizen{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "first_name")
    var firstname: String? = null

    @ColumnInfo(name = "last_name")
    var lastname: String? = null

    @ColumnInfo(name = "patronymic")
    var patronymic: String? = null

    @ColumnInfo(name = "date_of_birth")
    var dateOfBirth: String? = null

    @ColumnInfo(name = "nationalitys")
    var nationality: String? = null

    @ColumnInfo(name = "region")
    var region: String? = null

//    @ColumnInfo(name = "city")
//    var city: String? = null
//
//    @ColumnInfo(name = "address")
//    var address: String? = null

    @ColumnInfo(name = "dateOfIssue")
    var dateOfIssue: String? = null

    @ColumnInfo(name = "dateOfExpiry")
    var dateOfExpiry: String? = null

    @ColumnInfo(name = "sex")
    var gender: String? = null

    @ColumnInfo(name = "photo")
    var photoPath: String? = null

    @ColumnInfo(name = "passport_number")
    var passportNumber: String? = null

    constructor(
        firstname: String?,
        lastname: String?,
        patronymic: String?,
        dateOfBirth: String?,
        nationality: String?,
        region: String?,
        dateOfIssue: String?,
        dateOfExpiry: String?,
        gender: String?,
        photoPath: String?,
        passportNumber: String?,
    ) {
        this.firstname = firstname
        this.lastname = lastname
        this.patronymic = patronymic
        this.dateOfBirth = dateOfBirth
        this.nationality = nationality
        this.region = region
        this.dateOfIssue = dateOfIssue
        this.dateOfExpiry = dateOfExpiry
        this.gender = gender
        this.photoPath = photoPath
        this.passportNumber = passportNumber
    }

    constructor()
}