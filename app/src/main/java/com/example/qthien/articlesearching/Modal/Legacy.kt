package com.example.qthien.articlesearching.Modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Legacy (
    var xlarge : String?,
    var xlargeheight : Int?,
    var xlargewidth : Int?
) : Parcelable