package com.android.syed.gitrepo.di.module

import android.text.format.DateUtils
import com.android.syed.gitrepo.BuildConfig
import com.android.syed.gitrepo.TrendingRepoApplication
import com.android.syed.gitrepo.network.APIService
import com.android.syed.gitrepo.repository.MyRepository
import com.android.syed.gitrepo.repository.impl.MyRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    //fun provideOkHttpClient(cache: Cache): OkHttpClient {
    fun provideOkHttpClient(): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(
            TrendingRepoApplication().getAppComponent().context().cacheDir,
            cacheSize.toLong()
        )
        val client = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(interceptor)
        client.addNetworkInterceptor(provideCacheInterceptor())
        client.addInterceptor(provideOfflineCacheInterceptor())
        return client.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(mFactory: GsonConverterFactory, mClient: OkHttpClient)
            : Retrofit = Retrofit.Builder()
        .client(mClient)
        //.baseUrl("https://github-trending-api.now.sh")
        .baseUrl(BuildConfig.APP_API_BASE_URL)
        .addConverterFactory(mFactory)
        .build()

    @Provides
    @Singleton
    fun provideApiService(mRetrofit: Retrofit): APIService =
        mRetrofit.create(APIService::class.java)

    @Provides
    @Singleton
    fun provideApiRepository(mApiService: APIService): MyRepository = MyRepositoryImpl(mApiService)

    fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (e: Exception) {
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(2, TimeUnit.HOURS)
                    .build()

                val offlineRequest = chain.request().newBuilder()
                    .cacheControl(cacheControl)
                    .build()
                chain.proceed(offlineRequest)
            }
        }
    }

    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val originalResponse = chain.proceed(request)
            val cacheControl = originalResponse.header("Cache-Control")

            if (cacheControl == null ||
                cacheControl.contains("no-store") ||
                cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") ||
                cacheControl.contains("max-stale=0")
            ) {
                val cc = CacheControl.Builder()
                    .maxStale(2, TimeUnit.HOURS)
                    .build()
                request = request.newBuilder()
                    .cacheControl(cc)
                    .build()
                chain.proceed(request)

            } else {
                originalResponse
            }
        }
    }

}
