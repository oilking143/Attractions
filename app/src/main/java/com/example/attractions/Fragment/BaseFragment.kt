package com.example.attractions.Fragment

import android.app.Activity
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

     fun setLang(lang:String,act: Activity) {
         val settings = act.getSharedPreferences("Attr", 0)
         settings.edit()
             .putString("language", lang)
             .apply()
    }

    fun getLang(act: Activity): String? {
        val settings = act.getSharedPreferences("Attr",0)
        if(settings.getString("language", "zh-tw")?.isEmpty() == true){
            return "zh-tw"
        }else{
            return settings.getString("language","zh-tw")
        }

    }

}