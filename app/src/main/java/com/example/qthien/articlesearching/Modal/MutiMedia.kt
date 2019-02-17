package com.example.qthien.articlesearching.Modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MutiMedia (
    var caption : String?,
    var credit : String?,
    var crop_name : String?,
    var height : Int?,
    var legacy : Legacy?,
    var rank : Int?,
    var subtype : String?,
    var type : String?,
    var url: String?,
    var width : Int?
) : Parcelable
