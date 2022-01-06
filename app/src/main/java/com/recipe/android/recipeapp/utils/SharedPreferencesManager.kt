package com.recipe.android.recipeapp.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.RECENT_SEARCH_KEYWORD
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
import java.lang.reflect.Type

object SharedPreferencesManager {

    fun storeRecentSearchKeywordList(keyword: String) {
        val gson = Gson()
        val storedList = getRecentSearchKeywordList()
        storedList.remove(keyword)
        if (!storedList.isNullOrEmpty() && storedList.size >= 10) {
            storedList.removeAt(0)
        }
        storedList.add(keyword)
        val json = gson.toJson(storedList)
        sSharedPreferences.edit().putString(RECENT_SEARCH_KEYWORD, json).apply()
    }

    fun getRecentSearchKeywordList(): ArrayList<String> {
        Log.d("검색어", "SharedPreferencesManager - getRecentSearchKeywordList() : ")
        val gson = Gson()
        val json = sSharedPreferences.getString(RECENT_SEARCH_KEYWORD, "")

        Log.d("검색어", "저장된 검색어 $json")

        if (json?.length ?: 0 <= 0) return ArrayList()

        val type: Type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearAllRecentKeyword() {
        sSharedPreferences.edit().putString(RECENT_SEARCH_KEYWORD, "").apply()
    }

    fun deleteRecentKeyword(keyword: String) {
        val gson = Gson()
        val storedList = getRecentSearchKeywordList()
        storedList.remove(keyword)
        val json = gson.toJson(storedList)
        sSharedPreferences.edit().putString(RECENT_SEARCH_KEYWORD, json).apply()
    }
}