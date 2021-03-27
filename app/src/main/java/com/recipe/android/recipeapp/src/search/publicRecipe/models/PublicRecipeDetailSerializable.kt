package com.recipe.android.recipeapp.src.search.publicRecipe.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PublicRecipeDetailSerializable(

    val recipeId: Int,

    val recipeName: String,

    val summary: String,

    val thumbnail: String,

    val cookingTime: String,

    val level: String,

    val recipeIngredientList: ArrayList<PublicRecipeIngredientSerial>,

    val recipeProcessList: ArrayList<PublicRecipeProcessSerial>,

    val userScrapYN: String,

    val userScrapCnt: Int
) : Serializable

data class PublicRecipeIngredientSerial(

    val recipeIngredientIdx: Int,

    val recipeIngredientName: String,

    val recipeIngredientCpcty: String
): Serializable

data class PublicRecipeProcessSerial(

    val recipeProcessIdx: Int,

    val recipeProcessNo: Int,

    val recipeProcessDc: String,

    val recipeProcessImg: String
): Serializable
