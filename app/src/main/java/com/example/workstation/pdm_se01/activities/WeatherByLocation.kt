package com.example.workstation.pdm_se01.activities

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.content.res.Configuration
import android.database.Cursor
import android.support.design.widget.TabLayout

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.model.Forecast.Wrapper
import com.example.workstation.pdm_se01.provider.contract.ForecastContract
import com.example.workstation.pdm_se01.utils.Converter


import java.text.SimpleDateFormat
import java.util.*



class WeatherByLocation : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    companion object {

        var LOADER_ID=1

    }
    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
                mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

                // Set up the ViewPager with the sections adapter.
                mViewPager = findViewById(R.id.container) as ViewPager
                mViewPager!!.adapter = mSectionsPagerAdapter

                val tabLayout = findViewById(R.id.tabs) as TabLayout
                tabLayout.setupWithViewPager(mViewPager)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = ForecastContract.CONTENT_URI
        val parsed = intent.getStringExtra("location").split(",")
        val cursor = CursorLoader(this,uri,null,"city = ? AND country = ?",parsed.toTypedArray(),ForecastContract.DEFAULT_SORT_ORDER)
        return cursor
    }

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_by_location)

        loaderManager.initLoader(LOADER_ID,savedInstanceState,this)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val i = intent
        val location = i.getSerializableExtra("location") as String
        supportActionBar!!.title=location
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)*/

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_weather_by_location, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater!!.inflate(R.layout.fragment_weather_by_location, container, false)
            val minTemperatureView = rootView.findViewById(R.id.minTemperatureWeather) as TextView
            minTemperatureView.text = arguments["minTemperature"].toString()

            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                //val wrapper =  WeatherByLocation.getForecastWrapper()
                val mintemperature = 1//wrapper?.list?.get(sectionNumber)?.temp.toString()

                //wrapper?.list?.get(sectionNumber)?.temp
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                args.putInt("minTemperature", sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 7
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, position)
            val date = calendar.time
            val sdf : SimpleDateFormat
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) sdf = SimpleDateFormat("EEEE")
            else sdf = SimpleDateFormat("EEE")
            return sdf.format(date)
        }
    }
}
