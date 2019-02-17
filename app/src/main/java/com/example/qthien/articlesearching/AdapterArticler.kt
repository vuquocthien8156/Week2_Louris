package com.example.qthien.articlesearching

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.qthien.articlesearching.Modal.Article
import com.example.qthien.week_1.GlideApp
import kotlinx.android.synthetic.main.item_recy1.view.*
import kotlinx.android.synthetic.main.item_recy2.view.*

class AdapterArticler(var con : Context , var arr : ArrayList<Article>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val HaveImg = 1
    val NoImg = 2


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == HaveImg){
            return ViewHolder(LayoutInflater.from(con).inflate(R.layout.item_recy1 , null))
        }
        else
            return ViewHolder2(LayoutInflater.from(con).inflate(R.layout.item_recy2 , null))

    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun getItemViewType(position: Int): Int {
        var a = arr.get(position)
        var url = (a.multimedia.find { it?.subtype == "thumbnail" })?.url
         if(url != null)
             return HaveImg
         return NoImg
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        var a = arr.get(p1)
        var url = (a.multimedia.find { it?.subtype == "thumbnail" })?.url

        when(p0){
            is ViewHolder -> {
                GlideApp.with(con).load("https://static01.nyt.com/$url").into(p0.img)

                p0.txt.setText(a.headline?.main)

                p0.linear.setOnClickListener({
//                    var intent = Intent(con , DetailActivity::class.java)
//                    intent.putExtra("url" , a)
//                    con.startActivity(intent)
                    loadWeb(a.web_url)

                })
            }
            is ViewHolder2->{
                p0.txt.setText(a.headline?.main)

                p0.linear.setOnClickListener({
//                    var intent = Intent(con , DetailActivity::class.java)
//                    intent.putExtra("url" , a.web_url)
//                    con.startActivity(intent)
                    loadWeb(a.web_url)

                })
            }
        }


    }

    fun loadWeb(url :String?){
        val builder = CustomTabsIntent.Builder()
        // set toolbar color and/or setting custom actions before invoking build()
        // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        val customTabsIntent = builder.build()
        builder.addDefaultShareMenuItem();
        // and launch the desired Url with CustomTabsIntent.launchUrl()
        customTabsIntent.launchUrl(con, Uri.parse(url))
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var img : ImageView = itemView.img
        var txt : TextView = itemView.txtHead_Line
        var linear : LinearLayout = itemView.linear
    }
    inner class ViewHolder2(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var txt : TextView = itemView.txtHead_Linee
        var linear : LinearLayout = itemView.linearr
    }
}