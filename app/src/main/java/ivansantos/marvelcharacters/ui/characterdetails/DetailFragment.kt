package ivansantos.marvelcharacters.ui.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ivansantos.marvelcharacters.R
import ivansantos.marvelcharacters.databinding.FragmentDetailBinding
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.ThumbnailService
import ivansantos.marvelcharacters.ui.MarvelCharactersViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var fragmentDetailBinding: FragmentDetailBinding
    private val marvelCharactersViewModel: MarvelCharactersViewModel by activityViewModels()

    @Inject
    lateinit var thumbnailService: ThumbnailService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        val rootView = fragmentDetailBinding.root

        val currentCharacter = marvelCharactersViewModel.selectedCharacter.value
        currentCharacter?.let { character -> bindCharacterDetailsView(character) }
        return rootView
    }

    private fun bindCharacterDetailsView(currentCharacter: MarvelCharacter) {
        setToolbarTitle(currentCharacter.characterName)
        setThumbnail(currentCharacter)
        setCharacterDescription(currentCharacter.description)
    }

    private fun setToolbarTitle(characterName: String?) {
        fragmentDetailBinding.toolbarLayout.title = characterName
    }

    private fun setThumbnail(currentCharacter: MarvelCharacter) {
        val imageMarvelCharacterDetailsThumbnail =
            fragmentDetailBinding.imageMarvelCharacterDetailsThumbnail
        thumbnailService.loadLandscapeThumbnail(currentCharacter.thumbnailImage,
            imageMarvelCharacterDetailsThumbnail)

        imageMarvelCharacterDetailsThumbnail.contentDescription =
            "${currentCharacter.characterName} ${getString(R.string.thumbnail)}"
    }

    private fun setCharacterDescription(description: String) {
        if (description.isEmpty()) {
            fragmentDetailBinding.textMarvelCharacterDetailsDescription.text =
                getString(R.string.not_available)
        } else {
            fragmentDetailBinding.textMarvelCharacterDetailsDescription.text =
                description
        }
    }
}