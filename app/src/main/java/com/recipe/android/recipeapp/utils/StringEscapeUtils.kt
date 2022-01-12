package com.recipe.android.recipeapp.utils

object StringEscapeUtils {
    fun escapeHtml(input: String):String {
        var output = input
        output = output.replace("&#39;", "'")
        output = output.replace("&quot;", "\"")
        output = output.replace("&amp;", "&")
        output = output.replace("&nbsp;", " ")
        output = output.replace("&#035;", "#")
        output = output.replace("&gt;", ">")
        output = output.replace("&lt;", "<")
        return output
    }
}