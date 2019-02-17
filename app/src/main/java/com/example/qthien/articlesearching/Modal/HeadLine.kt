package com.example.qthien.articlesearching.Modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HeadLine (
    var content_kicker : String?,
    var kicker : String?,
    var main : String?,
    var name : String?,
    var print_headline : String?,
    var seo : String?,
    var sub : String?
): Parcelable
