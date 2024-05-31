package com.example.attractions.viewModel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VMFragMain : ViewModel(){

    val title = MutableLiveData<String>()
    class VMFragMainC(act:Activity) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VMFragMain() as T
        }
    }
}