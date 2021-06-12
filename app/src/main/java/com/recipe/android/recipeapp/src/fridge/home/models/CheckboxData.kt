package com.recipe.android.recipeapp.src.fridge.home.models


data class CheckboxData(var checkList : MutableList<CheckboxObject>)

data class CheckboxObject(
    var id: Int,
    var checked: Boolean,
    var deleteIngredient : String
)