package com.example.attractions.viewModel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.attractions.Api.ApiServer
import com.example.attractions.Api.RetrofitManager
import com.example.attractions.Model.AttrModel.AttrModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class VMFragMain : ViewModel(){

    var title = MutableLiveData<String>()

    var status  = MutableLiveData<Int>()
    var detail  = MutableLiveData<Int>()
    var AttrData = MutableSharedFlow<AttrModel>()
    private val _data = MutableLiveData<AttrModel>()
    val data: LiveData<AttrModel>
        get() = _data
    private lateinit var retroFit:Retrofit
    private lateinit var api: ApiServer


    fun setRetroFit(lang:String,){
        retroFit = RetrofitManager().getRetrofit(lang)
        api = retroFit.create(ApiServer::class.java)
    }


    fun setData(model: AttrModel) {
        _data.value = model
    }

    fun callDialog(){
        status.postValue(2)
    }

    fun backstack(){
        status.postValue(20)
    }

    fun gowebview(){
        status.postValue(21)
    }

    fun backwebstack(){
        status.postValue(22)
    }

    fun getAllAttractions(){
        api.getAllAttractions("application/json",0.0,0.0,1).enqueue(object : Callback<AttrModel> {
            override fun onResponse(call: Call<AttrModel>, response: Response<AttrModel>) {
                viewModelScope.launch {
                    if(response.isSuccessful)
                    {
                        val data: AttrModel =response.body()!!
                        AttrData.emit(data)
                    }
                    else{

                    }
                }

            }

            override fun onFailure(call: Call<AttrModel>, t: Throwable) {

            }

        })
    }


    class VMFragMainC(act:Activity) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VMFragMain() as T
        }
    }
}