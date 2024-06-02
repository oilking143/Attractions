package com.example.attractions

import android.app.Application
import com.example.attractions.Model.AttrModel.AttrModel

class GlobalData: Application() {
    companion object {
        private lateinit var attrData: AttrModel
        fun setAttr(attData:AttrModel){
            this.attrData = attData
        }

        fun getAttr():AttrModel{
            return  attrData
        }
    }

}