package com.example.workstation.pdm_se01.AWA

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import com.example.workstation.pdm_se01.network.AWA

/**
 * Created by workstation on 14/12/2016.
 */

class AWALoader(awa : AWA) : LoaderManager.LoaderCallbacks<Cursor> {

    val _awa:AWA
    init{
        _awa = awa
    }
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}