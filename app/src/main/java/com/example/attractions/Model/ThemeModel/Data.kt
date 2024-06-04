package com.example.attractions.Model.ThemeModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Data(
    val author: String,
    val category: @RawValue Any,
    val consume: String,
    val days: Int,
    val description: String,
    val files: @RawValue List<Any>,
    val id: Int,
    val modified: String,
    val months: List<String>,
    val note: String,
    val remark: String,
    val seasons: List<String>,
    val title: String,
    val transport: @RawValue Any,
    val url: String,
    val users: @RawValue Any
): Parcelable