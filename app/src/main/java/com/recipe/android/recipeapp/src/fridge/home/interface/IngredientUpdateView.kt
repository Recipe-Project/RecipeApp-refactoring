package com.recipe.android.recipeapp.src.fridge.home.`interface`

interface IngredientUpdateView {
    fun onClickStorageMethod(string: String, position: Int)
    fun onClickCount(cnt: Int, position: Int)
    fun onSetExpiredAt(date: String, position: Int)
}