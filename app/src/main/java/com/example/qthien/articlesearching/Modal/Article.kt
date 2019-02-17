package com.example.qthien.articlesearching.Modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var _id : String,
    var byline : ByLine?,
    var document_type : String?,
    var headline : HeadLine?,
    var keywords : ArrayList<KeyWords?>,
    var multimedia : ArrayList<MutiMedia?>,
    var news_desk : String?,
    var print_page : Int?,
    var pub_date : String?,
    var score : Int?,
    var snippet : String?,
    var source : String?,
    var type_of_material : String?,
    var uri : String?,
    var web_url : String?,
    var word_count : Int?
) : Parcelable