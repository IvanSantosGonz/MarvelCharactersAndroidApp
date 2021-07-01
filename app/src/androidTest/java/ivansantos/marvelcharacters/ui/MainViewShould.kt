package ivansantos.marvelcharacters.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ivansantos.marvelcharacters.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainViewShould {

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun show_master_view_with_loaded_characters() {
        val recyclerView = onView(withId(R.id.recycler_view_characters))

        recyclerView.check(RecyclerViewItemCountAssertion(4))
    }

    @Test
    fun navigate_from_master_to_detail_and_show_character_details() {
        val hulkCharacterView = onView(withText("Hulk"))
        hulkCharacterView.perform(ViewActions.click())

        val detailFragmentText = onView(withId(R.id.item_detail))
        detailFragmentText.check(ViewAssertions.matches(withText("Hulk")))
    }
}

class RecyclerViewItemCountAssertion(expectedCount: Int) : ViewAssertion {
    private val matcher: Matcher<Int?>? = CoreMatchers.`is`(expectedCount)

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView?
        val adapter = recyclerView!!.adapter!!
        ViewMatchers.assertThat(adapter.itemCount, matcher)
    }
}