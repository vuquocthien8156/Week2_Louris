package com.example.qthien.articlesearching.Modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Person (
    var firstname : String?,
    var lastname : String?,
    var middlename : String?,
    var organization : String?,
    var qualifier : String?,
    var rank : Int?,
    var role : String?,
    var title : String?
) : Parcelable