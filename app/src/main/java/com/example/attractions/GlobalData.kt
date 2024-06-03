package com.example.attractions

import android.app.Application
import com.example.attractions.Model.AttrModel.AttrModel
import com.example.attractions.Model.ThemeModel.ThemeModel

class GlobalData: Application() {
    companion object {
        private lateinit var attrData: AttrModel
        private lateinit var themeData: ThemeModel
        fun setAttr(attData:AttrModel){
            this.attrData = attData
        }

        fun getAttr():AttrModel{
            return  attrData
        }

        fun setTheme(themeData:ThemeModel){
            this.themeData = themeData
        }

        fun getTheme():ThemeModel{
            return  themeData
        }
    }

}