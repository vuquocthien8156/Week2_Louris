package com.example.qthien.articlesearching.Modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.Nullable

@Parcelize
data class ByLine (
    var organization : String?,
    var original : String?,
    var person : ArrayList<Person?>
) : Parcelable
