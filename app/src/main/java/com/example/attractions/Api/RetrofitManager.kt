package com.example.attractions.Api

import android.util.Log
import okhttp3.ConnectionPool
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.Collections
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class RetrofitManager() {


   fun getRetrofit(catalog:String):Retrofit{
       val baseUrl = "https://www.travel.taipei/open-api/$catalog/"
       return Retrofit.Builder().baseUrl(baseUrl)
           .client(getOkHttpClient())
           .addConverterFactory(GsonConverterFactory.create())
           .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
           .build()
   }

    private fun getOkHttpClient(): OkHttpClient? {
        //日志显示级别
        val level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
        //新建log拦截器
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("Response", "OkHttp====Message:$message")
            }
        })
        loggingInterceptor.setLevel(level)
        //定制OkHttp


        val httpClientBuilder = OkHttpClient.Builder()
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("accept","application/json" )
                .build()
            chain.proceed(newRequest)
        }).addInterceptor(Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            Log.d("responseCode",response.code.toString())

            response
        }).addInterceptor(loggingInterceptor).connectTimeout(15, TimeUnit.SECONDS)
        return httpClientBuilder.protocols(Collections.singletonList(Protocol.HTTP_1_1)).build()
    }



    @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
    fun getSSLSocketFactory(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, getTrustManager(), SecureRandom())
        return sslContext.socketFactory
    }
    private fun getTrustManager(): Array<TrustManager> {
        val trustManager: X509TrustManager = object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        return arrayOf(trustManager)
    }



    @Throws(Exception::class)
    fun getX509TrustManager(): X509TrustManager {
        var trustManager: TrustManager? = null
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as? KeyStore)
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers: $trustManagers")
        }
        return trustManagers[0] as X509TrustManager
    }
}