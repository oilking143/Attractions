package com.example.attractions.Model.ThemeModel

import android.os.Parcelable
import java.io.Serializable


data class ThemeModel(
    val data: List<Data>,
    val total: Int
): Serializable