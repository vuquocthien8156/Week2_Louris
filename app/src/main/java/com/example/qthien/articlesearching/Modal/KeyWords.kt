package com.example.qthien.articlesearching.Modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class KeyWords(
    var major : String?,
    var name : String?,
    var rank : Int?,
    var value : String?
) : Parcelable
