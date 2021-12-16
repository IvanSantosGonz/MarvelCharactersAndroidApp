package ivansantos.marvelcharacters.ui

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import ivansantos.marvelcharacters.R
import ivansantos.marvelcharacters.data.*
import ivansantos.marvelcharacters.data.network.MarvelAPI
import ivansantos.marvelcharacters.data.network.PicassoThumbnailService
import ivansantos.marvelcharacters.data.repositories.DefaultMarvelCharactersRepository
import ivansantos.marvelcharacters.di.MarvelCharactersModule
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.MarvelCharactersRepository
import ivansantos.marvelcharacters.domain.ThumbnailImage
import ivansantos.marvelcharacters.domain.ThumbnailService
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@UninstallModules(MarvelCharactersModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainViewShould {

    @BindValue
    lateinit var marvelAPI: MarvelAPI

    @BindValue
    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    @BindValue
    lateinit var marvelCharactersRepository: MarvelCharactersRepository

    @BindValue
    var thumbnailService: ThumbnailService = PicassoThumbnailService()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun show_master_view_with_loaded_characters() {
        coEvery {
            remoteDataSource.getCharacters(any(), any(), any())
        } returns fakeMarvelAPIResponseDTO
        initMarvelCharactersRepositoryWith(remoteDataSource)
        launchMainActivity()

        val recyclerView = onView(withId(R.id.recycler_view_characters))

        recyclerView.check(RecyclerViewItemCountAssertion(1))
    }

    @Test
    fun show_master_view_with_correct_details_for_loaded_characters() {
        coEvery {
            remoteDataSource.getCharacters(any(), any(), any())
        } returns fakeMarvelAPIResponseDTO
        initMarvelCharactersRepositoryWith(remoteDataSource)

        launchMainActivity()

        val expectedThumbnail =
            ThumbnailImage("https://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73", "jpg")
        val expectedCharacter = MarvelCharacter("Fake Hero", expectedThumbnail)
        checkCharacterCardIsLoaded(expectedCharacter)
    }

    private fun checkCharacterCardIsLoaded(marvelCharacter: MarvelCharacter) {
        val marvelCharacterName = onView(withId(R.id.text_marvel_character_name))
        marvelCharacterName.check(matches(withText(marvelCharacter.characterName)))
        val marvelCharacterImage = onView(withId(R.id.image_marvel_character_thumbnail))
        marvelCharacterImage.check(matches(withContentDescription("${marvelCharacter.characterName} thumbnail")))
    }

    @Test
    fun load_character_thumbnail() {
        coEvery {
            remoteDataSource.getCharacters(any(), any(), any())
        } returns fakeMarvelAPIResponseDTO
        initMarvelCharactersRepositoryWith(remoteDataSource)
        launchMainActivity()

        val characterView = onView(withText("Fake Hero"))
        characterView.perform(ViewActions.click())

        val marvelCharacterImage = onView(withId(R.id.image_marvel_character_details_thumbnail))
        marvelCharacterImage.check(matches(withContentDescription("Fake Hero thumbnail")))
    }

    @Test
    fun load_character_description() {
        coEvery {
            remoteDataSource.getCharacters(any(), any(), any())
        } returns fakeMarvelAPIResponseDTO
        initMarvelCharactersRepositoryWith(remoteDataSource)
        launchMainActivity()

        val characterView = onView(withText("Fake Hero"))
        characterView.perform(ViewActions.click())

        val marvelCharacterDescription =
            onView(withId(R.id.text_marvel_character_details_description))
        marvelCharacterDescription.check(matches(withText("This is a Fake marvel character")))
    }

    @Test
    fun load_character_without_any_available_description() {
        coEvery {
            remoteDataSource.getCharacters(any(), any(), any())
        } returns fakeMarvelAPIResponseDTOWithEmptyDescription
        initMarvelCharactersRepositoryWith(remoteDataSource)
        launchMainActivity()

        val characterView = onView(withText("Fake Hero"))
        characterView.perform(ViewActions.click())

        val marvelCharacterDescription =
            onView(withId(R.id.text_marvel_character_details_description))
        marvelCharacterDescription.check(matches(withText("N/A")))
    }

    @Test
    fun show_error_page_when_api_fails() {
        coEvery {
            remoteDataSource.getCharacters(any(), any(), any())
        } throws Exception()
        initMarvelCharactersRepositoryWith(remoteDataSource)
        launchMainActivity()

        val errorPage = onView(withId(R.id.error_page_layout))
        errorPage.check(matches(isDisplayed()))
    }

    private fun initMarvelCharactersRepositoryWith(remoteDataSource: RemoteDataSource) {
        marvelCharactersRepository = DefaultMarvelCharactersRepository(
            remoteDataSource,
            MarvelAPI(apiKey = "", privateKey = "")
        )
    }

    private var fakeMarvelAPIResponseDTO = MarvelAPIResponseDTO(
        "200",
        Data(
            "1",
            "1",
            "1",
            listOf(ResultDTO(
                "This is a Fake marvel character",
                "1",
                "a modified date",
                "Fake Hero",
                Thumbnail(
                    "jpg",
                    "http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73"
                )
            )),
            "1000"
        ),
        "Ok")

    private var fakeMarvelAPIResponseDTOWithEmptyDescription = MarvelAPIResponseDTO(
        "200",
        Data(
            "1",
            "1",
            "1",
            listOf(ResultDTO(
                "",
                "1",
                "a modified date",
                "Fake Hero",
                Thumbnail(
                    "jpg",
                    "http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73"
                )
            )),
            "1000"
        ),
        "Ok")

    private fun launchMainActivity() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)
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
        assertThat(adapter.itemCount, matcher)
    }
}