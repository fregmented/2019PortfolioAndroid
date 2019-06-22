package hanwool.lotto.data

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

object ApiWorker {
    private val TAG = ApiWorker::class.java.simpleName
    private var mOkHttpClient: OkHttpClient? = null
    private var mGsonConverter: GsonConverterFactory? = null

    val client: OkHttpClient
        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        get() {
            if (mOkHttpClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                mOkHttpClient = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor {
                        val originalHttpUrl = it.request().url()
                        val url = originalHttpUrl.newBuilder()
                            .addQueryParameter("method", "getLottoNumber")
                            .build()
                        it.proceed(it.request()
                            .newBuilder()
                            .url(url)
                            .build())
                    }
                    .addInterceptor {
                        val request = it.request()
                        var response = doRequest(it, request)
                        var tryCount = 0
                        while (response == null && tryCount < 5) {
                            Log.e(TAG, "SocketTimeout... retry($tryCount)")
                            response = doRequest(it, request)
                            tryCount++
                        }
                        if (response == null) {//important ,should throw an exception here
                            throw IOException()
                        }
                        response
                    }
                    .build()
            }
            return mOkHttpClient!!
        }

    private fun doRequest(chain: Interceptor.Chain, request: Request): Response? {
        var response: Response? = null
        try {
            response = chain.proceed(request)
        } catch (e: SocketTimeoutException) {
        }
        return response
    }
    val gsonConverter: GsonConverterFactory
        get() {
            if (mGsonConverter == null) {
                mGsonConverter = GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .disableHtmlEscaping()
                        .setDateFormat("yyyy-MM-dd")
                        .create()
                )
            }
            return mGsonConverter!!
        }
}