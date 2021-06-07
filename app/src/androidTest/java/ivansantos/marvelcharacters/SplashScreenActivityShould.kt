package ivansantos.marvelcharacters

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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
        val splashScreenImage = Espresso.onView(ViewMatchers.withId(R.id.image_splash))

        splashScreenImage.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}