package com.example.qthien.articlesearching

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import android.view.View.SCROLLBARS_INSIDE_OVERLAY
import android.widget.Toast
import com.example.qthien.articlesearching.Modal.Article
import kotlinx.android.synthetic.main.activity_detail.*
import android.support.customtabs.CustomTabsIntent




class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setTitle("Read more...")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var a = intent.extras.getParcelable("url") as Article

//          var url = intent.extras.getString("url")

//        myWebView.getSettings().setLoadsImagesAutomatically(true)
//        myWebView.getSettings().setJavaScriptEnabled(true)
//        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
//        // Configure the client to use when opening URLs
//        myWebView.setWebViewClient(WebViewClient())
//        // Load the initial URL
//        myWebView.loadUrl(a.web_url)

        // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail , menu)

        val item = menu?.findItem(R.id.menuShare)
        val share : ShareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider
        val i = Intent(Intent.ACTION_SEND)
        i.putExtra(Intent.EXTRA_TEXT , myWebView.url)
        i.setType("text/plain");
        share.setShareIntent(i)

        return true

    }

    override fun onRestart() {
        super.onRestart()
        onBackPressed()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.menuShare)
        {
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
