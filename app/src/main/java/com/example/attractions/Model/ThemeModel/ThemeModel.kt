package com.example.attractions.Model.ThemeModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class ThemeModel(
    val data: @RawValue List<Data>,
    val total: Int
) : Parcelable
