package com.example.attractions.Activity

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.attractions.Api.ApiServer
import com.example.attractions.Api.RetrofitManager
import com.example.attractions.Model.AttrModel.AttrModel
import com.example.attractions.R
import com.example.attractions.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationBarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MainActivity : AppCompatActivity(){
    private lateinit var binding:ActivityMainBinding
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SSLHandler()
        initView()
    }

   fun initView(){

       binding.navigation.itemTextColor = ColorStateList.valueOf(Color.BLACK)
       binding.navigation.setupWithNavController(findNavController(R.id.main_fragment))

      val retrofit = RetrofitManager("zh-tw").getRetrofit()
      val api:ApiServer=retrofit.create(ApiServer::class.java)
       api.getAllAttractions("application/json",0.0,0.0,1).enqueue(object : Callback<AttrModel>{
           override fun onResponse(call: Call<AttrModel>, response: Response<AttrModel>) {
               if(response.isSuccessful)
               {
                   val data: AttrModel =response.body()!!
                   Log.d("response", data.data[0].introduction)
               }
               else{

               }
           }

           override fun onFailure(call: Call<AttrModel>, t: Throwable) {

           }

       })

    }

    fun SSLHandler(){

        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return emptyArray()
                }
            }
        )
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)

        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }

        Log.d("HttpsURLConnection","Finish")

    }
}