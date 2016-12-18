package com.example.workstation.pdm_se01.activities

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Intent
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
import android.widget.ImageView

import android.widget.TextView

import com.example.workstation.pdm_se01.R
import com.example.workstation.pdm_se01.model.Forecast.Forecast
import com.example.workstation.pdm_se01.model.Forecast.Wrapper
import com.example.workstation.pdm_se01.network.Syncronizer
import com.example.workstation.pdm_se01.network.api.API_Forecast
import com.example.workstation.pdm_se01.provider.contract.ForecastContract
import com.example.workstation.pdm_se01.utils.Converter
import com.example.workstation.pdm_se01.utils.QueryRegist
import com.example.workstation.pdm_se01.utils.Utils
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class WeatherByLocation : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object{
        val LOADER_ID = 1
        var dataFragment : Wrapper? = null
    }

    fun initFragment(data : Wrapper?){
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,data)

        mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        var wrapper_forecast : Wrapper ?= null
        if(data != null){
            if(data.moveToNext())
                wrapper_forecast = Converter.convertToForecast(data.getString(data.getColumnIndex(ForecastContract.DATA)))

        }
        initFragment(wrapper_forecast)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val uri = ForecastContract.CONTENT_URI
        val parsed = intent.getStringExtra("location").split(",")
        val cursor = CursorLoader(this,uri,null,"city = ? AND country = ?",parsed.toTypedArray(),ForecastContract.DEFAULT_SORT_ORDER)
        return cursor
    }

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private var mViewPager: ViewPager? = null
    private var location :String ?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_by_location)
        loaderManager.initLoader(LOADER_ID,null,this)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val i = intent
        location = i.getSerializableExtra("location") as String
        supportActionBar!!.title=location

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_with_toggle_favorite, menu)
        markFavoriteIcon(location,menu)
        return true
    }

    private fun  markFavoriteIcon(location: String?, menu: Menu) {
        val shared= getSharedPreferences("Location", MODE_PRIVATE)
        val favs =shared!!.getString("locals", null)
        if(favs.contains(location as CharSequence,true)){
            menu.findItem(R.id.toggleFavorite).setIcon(android.R.drawable.star_big_on)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            val myIntent = Intent(this, SettingsActivity::class.java)
            this.startActivity(myIntent)
            return true
        }
        if(id==R.id.favorites){
            val myIntent = Intent(this, PreferencesActivity::class.java)
            this.startActivity(myIntent)
            return true
        }
        if(id==R.id.toggleFavorite){
            toggleFavorite(intent.getStringExtra("location"))
            if (item.icon.constantState.equals(
                    resources.getDrawable(android.R.drawable.star_big_off).constantState
            )) {
                item.setIcon(android.R.drawable.star_big_on)
            }else item.setIcon(android.R.drawable.star_big_off)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun  toggleFavorite(location: String) {
        val synchronizer = Syncronizer(applicationContext, API_Forecast(applicationContext) )
        var parse = location.split("/".toRegex()).dropLastWhile({ it.isEmpty()})
        var sharedPrefLocation = getSharedPreferences("Location", MODE_PRIVATE)
        var rawlocations = sharedPrefLocation!!.getString("locals", null)

        if (rawlocations.contains(location as CharSequence,true)) {
            synchronizer.syncronizeSingle(QueryRegist(parse[0], parse[1], 0))
            var splitedString=rawlocations.split(location+"/")
            rawlocations=splitedString[0]+splitedString[1]
        }else {
            synchronizer.syncronizeSingle(QueryRegist(parse[0], parse[1], 1))
            rawlocations+=location+"/"
        }
        val editor = sharedPrefLocation!!.edit()
        editor.clear()
        editor.putString("locals", rawlocations)
        editor.commit()
    }
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater!!.inflate(R.layout.fragment_weather_by_location, container, false)
            val arg = arguments
            if(arguments != null && rootView != null){
                val myIcon = rootView.findViewById(R.id.weatherImage) as ImageView
                val myPicasso = Picasso.with(context)
                myPicasso.setIndicatorsEnabled(true)
                myPicasso.load("http://openweathermap.org/img/w/" + arg.getString("icon")+ ".png").into(myIcon)

                val maxTemperature = rootView.findViewById(R.id.maxTemperatureWeather) as TextView
                maxTemperature.text = arg.getDouble("maxTemp").toString()
                maxTemperature.append(" ºC")

                val minTemp = rootView.findViewById(R.id.minTemperatureWeather) as TextView
                minTemp.text = arg.getDouble("minTemp").toString()
                minTemp.append(" ºC")

                val wind_speed = rootView.findViewById(R.id.windSpeedWeather) as TextView
                wind_speed.text = arg.getDouble("windSpeed").toString()
                wind_speed.append(" mps")

                val pressure = rootView.findViewById(R.id.pressure) as TextView
                pressure.text = arg.getDouble("pressure").toString()
                pressure.append(" hpa")

                val humidity = rootView.findViewById(R.id.humidity) as TextView
                humidity.text = arg.getInt("humidity").toString()
                humidity.append(" %")
            }
            return rootView
        }
        companion object {

            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int, forecast : Forecast?): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                if(forecast!= null)
                    loadArguments(args,forecast)
                fragment.arguments = args
                return fragment
            }

            private fun loadArguments(args : Bundle, data:Forecast){
                args.putDouble("minTemp",data.temp.min)
                args.putDouble("maxTemp",data.temp.max)
                args.putString("icon",data.weather[0].icon)
                args.putString("description",data.weather[0].description)
                args.putDouble("windSpeed",data.speed)
                args.putInt("humidity",data.humidity)
                args.putDouble("pressure",data.pressure)
            }
        }
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, _data : Wrapper?) : FragmentPagerAdapter(fm) {

        init {
            dataFragment = _data
        }

        override fun getItem(position: Int): Fragment {
            val forecast : Forecast?
            if(dataFragment!= null){
                forecast = dataFragment!!.list[position]
            }
            else throw Exception()

            return PlaceholderFragment.newInstance(position + 1,forecast)
        }

        override fun getCount(): Int {
            val NUMBER_OF_DAYS = 7
            return NUMBER_OF_DAYS
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
