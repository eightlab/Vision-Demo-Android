package co.eightlab.sample.view.country

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import co.eightlab.sample.R
import co.eightlab.sample.base.AppBaseActivity
import co.eightlab.sample.model.CountryData
import co.eightlab.sample.model.CountryDataReaderCallback
import co.eightlab.sample.utils.AssetsFileReader
import co.eightlab.sample.view.country.adapter.CountryListAdapter
import co.eightlab.sample.view.id_options.IdOptionsActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_country_selection.*
import kotlinx.android.synthetic.main.toolbar_main.*


class CountrySelectionActivity : AppBaseActivity(), CountryDataReaderCallback {
    private var searchView: SearchView? = null
    private var countryListAdapter: CountryListAdapter? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_country_selection

    override fun initView() {
        setupToolbar()
        readAssetsCountries()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_country, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView?.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView?.maxWidth = Int.MAX_VALUE

        // listening to search query text change
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { // filter recycler view when query submitted
                countryListAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean { // filter recycler view when text is changed
                countryListAdapter?.filter?.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        //noinspection SimplifiableIfStatement
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun readCountryJsonSuccess(data: String?) {
        if (data?.isNullOrEmpty() == false) {
            val myType = object : TypeToken<List<CountryData>>() {}.type
            val countries = Gson().fromJson<List<CountryData>>(data, myType)
            setupRecyclerView(countries)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        titleTextView.text = getString(R.string.select_country)
        backImageView.setOnClickListener { onBackPressed() }
    }

    private fun readAssetsCountries() {
        var task = AssetsReadFileTask(this)
        task.countryDataReaderCallback = this
        task.execute()
    }

    private fun setupRecyclerView(countries: List<CountryData>) {
        countryRecyclerView.layoutManager = LinearLayoutManager(this)
        countryRecyclerView.setHasFixedSize(true)
        countryListAdapter = CountryListAdapter(countries) {
            //Open Id Options Activity
            IdOptionsActivity.start(this@CountrySelectionActivity, it)
        }
        countryRecyclerView.adapter = countryListAdapter
    }

    private class AssetsReadFileTask(context: Context?) : AsyncTask<Void?, Void?, String?>() {
        var mContext = context
        var countryDataReaderCallback: CountryDataReaderCallback? = null
        override fun doInBackground(vararg params: Void?): String? {

            return AssetsFileReader.loadAssetTextAsString("countries.json", mContext)
        }

        override fun onPostExecute(result: String?) {
            countryDataReaderCallback?.readCountryJsonSuccess(result)
        }
    }

    override fun onBackPressed() {
        if (searchView?.isIconified == false) {
            searchView?.isIconified = true
            return
        }
        super.onBackPressed();
    }

    companion object {
        fun start(context: Context?) {
            var intent = Intent(context, CountrySelectionActivity::class.java)
            context?.startActivity(intent)
        }
    }
}