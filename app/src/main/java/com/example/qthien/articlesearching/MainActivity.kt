package com.example.qthien.articlesearching

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.support.v7.widget.*
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.Toast
import com.example.qthien.articlesearching.Modal.Article
import com.example.qthien.articlesearching.Modal.Result
import com.example.qthien.week_1.GlideApp
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var Adapter : AdapterArticler? = null
    private var arrMain : ArrayList<Article>? = null
    private var query : String? = ""
    private var listenerScroll : EndlessRecyclerViewScrollListener? = null

    private val PAGE = "page"
    private val NY_BEGIN_DATE = "begin_date"
    private val NY_NEWS_DESK = "fq"
    private val NY_SORT_ORDER = "sort"
    private val QUERY = "q"

    var pagee = 1
    fun reloadData(){
        Log.d("query" , "query: $query")
        arrMain?.clear()
        searchArticle(query!! , 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrMain = ArrayList()

        Adapter = AdapterArticler(this@MainActivity , arrMain!!)
        var staggeredGridLayoutManager = StaggeredGridLayoutManager(3 , LinearLayout.VERTICAL)
        recyler?.layoutManager = staggeredGridLayoutManager
        recyler.addItemDecoration(DividerItemDecoration(applicationContext , LinearLayout.VERTICAL))
        recyler?.adapter = Adapter



        listenerScroll = object : EndlessRecyclerViewScrollListener(staggeredGridLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                loadNextData(page)
                Log.d("page" , page.toString())
            }
        }

        recyler.addOnScrollListener(listenerScroll as EndlessRecyclerViewScrollListener)

        if(isNetworkAvailable()){
            Toast.makeText(applicationContext , "Connect" , Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(applicationContext , "No connect" , Toast.LENGTH_LONG).show()

    }

    fun loadNextData(offset : Int){
        progress.visibility = View.VISIBLE
        searchArticle(query!! , offset)

    }

    fun createQuery(searchText : String , page : Int) : Map<String , String>{
        val share = getSharedPreferences("Filter" , Context.MODE_PRIVATE)

        val date = share?.getString("Date" , "")
        val sort : Int = share.getInt("Sort" , 0)

        var params = HashMap<String,String>()

        if(!date.equals("")){
            Log.d("srtDate" , date)
            var da = date?.split("/")
            Log.d("srtDate" , da.toString())
            var strDate = "${da!![2]}${da[1]}${da[0]}"
            Log.d("srtDate" , strDate)
            params.put(NY_BEGIN_DATE , strDate)
        }
        if(sort >= 0){
            params.put(NY_SORT_ORDER , when(sort) {
                1 -> "newest"
                else -> "oldest"
            })
        }
        params.put(QUERY , searchText)
        params.put(PAGE , page.toString())

        Log.d("paramss" , params.toString())

        return params
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main , menu)
        var menuItem = menu?.findItem(R.id.menuSearch)
        val searchView : SearchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                query = searchView.query.toString()
                progress.visibility = View.GONE
                arrMain?.clear()
                searchArticle(searchView.query.toString() , 0)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId.equals(R.id.menuFilter))
        {
            var tran = supportFragmentManager.beginTransaction()
            var myDialog     = MyDialogFilter()
            myDialog.show(tran , "dialog")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    fun searchArticle(searchText : String , page : Int){
        val re = Retrofit2.getRetrofit
        val call = re.getDataSearch(createQuery(searchText , page), createNewDesks())
        call.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.d("Errorr" , t.toString())
            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                var r = response.body()
                if(r != null && r.response?.docs.size > 0){
                    txtNotFound.visibility = View.GONE
                    arrMain?.addAll(r.response?.docs)

                    Adapter?.notifyItemRangeInserted( Adapter!!.itemCount ,r.response.docs.size - 1)
                    Adapter?.notifyDataSetChanged()
                    progress.visibility = View.GONE
                }
                else {
                    if(r != null && r.response.docs.size <= 0)
                        txtNotFound.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    Adapter?.notifyDataSetChanged()
                    listenerScroll?.resetState()
                }

            }

        })
    }

    private fun createNewDesks(): ArrayList<String> {
        val share = getSharedPreferences("Filter" , Context.MODE_PRIVATE)
        val checkA : Boolean = share.getBoolean("SelectA" , false)
        val checkF : Boolean= share.getBoolean("SelectF" , false)
        val checkS : Boolean= share.getBoolean("SelectS" , false)
        var arrayFilter = arrayListOf<String>()
        if(checkF) arrayFilter.add("fashion & style")
        if(checkA) arrayFilter.add("arts")
        if(checkS) arrayFilter.add("sport")
        return arrayFilter
    }


}
