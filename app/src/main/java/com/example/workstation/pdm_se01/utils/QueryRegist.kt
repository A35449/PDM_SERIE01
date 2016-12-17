package com.example.workstation.pdm_se01.utils

/**
 * Created by workstation on 15/12/2016.
 */

class QueryRegist(_city : String, _country : String, _fav : Int = 0) {

    var city : String
    var country : String
    var fav: Int
    init{
        city = _city
        country = _country
        fav = _fav
    }
}