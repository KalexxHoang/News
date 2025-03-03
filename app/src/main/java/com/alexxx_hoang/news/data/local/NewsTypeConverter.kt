package com.alexxx_hoang.news.data.local

import androidx.room.TypeConverter
import com.alexxx_hoang.news.domain.model.Source
import com.google.gson.Gson

class NewsTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun sourceToString(source: Source): String{
        return gson.toJson(source)
    }

    @TypeConverter
    fun stringToSource(source: String): Source{
        return gson.fromJson(source, Source::class.java)
    }
}