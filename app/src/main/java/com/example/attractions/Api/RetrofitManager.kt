package com.example.attractions.Api

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class RetrofitManager(catalog:String) {



    val httpLoggingInterceptor = HttpLoggingInterceptor()
    private val baseUrl = "https://www.travel.taipei/open-api/$catalog/"



    private val okHttpClient =
        OkHttpClient().newBuilder().readTimeout(500, TimeUnit.SECONDS)
            .connectTimeout(500, TimeUnit.SECONDS).writeTimeout(500, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .sslSocketFactory(getSSLSocketFactory(), getX509TrustManager())
            .hostnameVerifier { _, _ ->
                true
            }
            .connectionPool(
                ConnectionPool(32, 5, TimeUnit.MINUTES)
            ).build()

    fun getRetrofit(): Retrofit {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

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