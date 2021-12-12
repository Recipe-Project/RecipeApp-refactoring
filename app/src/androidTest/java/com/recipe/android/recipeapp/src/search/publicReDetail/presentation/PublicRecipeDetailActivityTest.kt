package com.recipe.android.recipeapp.src.search.publicReDetail.presentation

import android.content.Context
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.src.search.publicRecipe.publicReDetail.presentation.PublicRecipeDetailActivity
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PublicRecipeDetailActivityTest : TestCase() {

    @get:Rule()
    val activityRule = ActivityTestRule(PublicRecipeDetailActivity::class.java)
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun getInfo(){
        activityRule.launchActivity(idxIntent())

        Espresso.onView(withId(R.id.tv_title_after)).check(matches(withText("냉면")))

        Espresso.onView(withId(R.id.btn_scrap)).perform(ViewActions.click())
        Thread.sleep(1000)
    }

    private fun idxIntent(): Intent {
        val intent = Intent()
        intent.putExtra("index", 11)
        return intent
    }
}