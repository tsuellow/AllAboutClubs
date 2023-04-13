package com.example.android.allaboutclubs.common

import com.example.android.allaboutclubs.R
import java.text.SimpleDateFormat
import java.util.*


fun Date.toDateString():String{
    return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(this)
}

/**
 * Extension function that enables localization of country names found in the german Api.
 * I had ChatGPT write it for me
 */
fun String.getCountryResource(): Int {
    return when (this.lowercase(Locale.ROOT)) {
        "albanien" -> R.string.albania
        "andorra" -> R.string.andorra
        "belgien" -> R.string.belgium
        "bosnien und herzegowina" -> R.string.bosnia_and_herzegovina
        "bulgarien" -> R.string.bulgaria
        "dänemark" -> R.string.denmark
        "deutschland" -> R.string.germany
        "estland" -> R.string.estonia
        "england" ->R.string.england
        "finnland" -> R.string.finland
        "frankreich" -> R.string.france
        "griechenland" -> R.string.greece
        "irland" -> R.string.ireland
        "island" -> R.string.iceland
        "italien" -> R.string.italy
        "kosovo" -> R.string.kosovo
        "kroatien" -> R.string.croatia
        "lettland" -> R.string.latvia
        "liechtenstein" -> R.string.liechtenstein
        "litauen" -> R.string.lithuania
        "luxemburg" -> R.string.luxembourg
        "malta" -> R.string.malta
        "moldawien" -> R.string.moldova
        "monaco" -> R.string.monaco
        "montenegro" -> R.string.montenegro
        "niederlande" -> R.string.netherlands
        "nordirland" -> R.string.northern_ireland
        "nordmazedonien" -> R.string.north_macedonia
        "norwegen" -> R.string.norway
        "österreich" -> R.string.austria
        "polen" -> R.string.poland
        "portugal" -> R.string.portugal
        "rumänien" -> R.string.romania
        "russland" -> R.string.russia
        "san marino" -> R.string.san_marino
        "schottland" -> R.string.scotland
        "schweden" -> R.string.sweden
        "schweiz" -> R.string.switzerland
        "serbien" -> R.string.serbia
        "slowakei" -> R.string.slovakia
        "slowenien" -> R.string.slovenia
        "spanien" -> R.string.spain
        "tschechien" -> R.string.czech_republic
        "türkei" -> R.string.turkey
        "ukraine" -> R.string.ukraine
        "ungarn" -> R.string.hungary
        "vereinigtes königreich" -> R.string.united_kingdom
        "wales" -> R.string.wales
        "weißrussland" -> R.string.belarus
        "zypern" -> R.string.cyprus
        else -> R.string.unknown // Return unknown
    }
}
