package com.android.news.di

import android.app.Application
import androidx.room.Room
import com.android.news.data.local.NewsDao
import com.android.news.data.local.NewsDatabase
import com.android.news.data.remote.NewsApi
import com.android.news.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): NewsDatabase
        = Room.databaseBuilder(context = application, klass = NewsDatabase::class.java, name = "news_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao
        = newsDatabase.newsDao
}