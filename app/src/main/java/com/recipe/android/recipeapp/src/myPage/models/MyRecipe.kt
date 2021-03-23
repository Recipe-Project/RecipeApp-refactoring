package com.recipe.android.recipeapp.src.myPage.models


import com.google.gson.annotations.SerializedName

data class MyRecipe(
    @SerializedName("myRecipeDate")
    val myRecipeDate: String,
    @SerializedName("myRecipeIdx")
    val myRecipeIdx: Int,
    @SerializedName("myRecipeThumbnail")
    val myRecipeThumbnail: Any?,
    @SerializedName("myRecipeTitle")
    val myRecipeTitle: String
)