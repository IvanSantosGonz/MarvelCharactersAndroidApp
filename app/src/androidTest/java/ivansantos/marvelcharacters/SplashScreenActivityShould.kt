package ivansantos.marvelcharacters

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import ivansantos.marvelcharacters.presentation.SplashScreenActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SplashScreenActivityShould {

    @get:Rule
    var rule = ActivityScenarioRule(SplashScreenActivity::class.java)

    @Test
    fun display_app_logo() {
        val splashScreenImage = Espresso.onView(withId(R.id.image_splash))

        splashScreenImage.check(matches(isDisplayed()))
    }
}