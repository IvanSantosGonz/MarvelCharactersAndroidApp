package ivansantos.marvelcharacters.ui.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ivansantos.marvelcharacters.R
import ivansantos.marvelcharacters.databinding.FragmentDetailBinding
import ivansantos.marvelcharacters.domain.ThumbnailService
import ivansantos.marvelcharacters.ui.MarvelCharactersViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var itemDetailTextView: TextView

    private var fragmentDetailBinding: FragmentDetailBinding? = null
    private val marvelCharactersViewModel: MarvelCharactersViewModel by activityViewModels()

    @Inject
    lateinit var thumbnailService: ThumbnailService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        val rootView = fragmentDetailBinding!!.root

        val characterName = marvelCharactersViewModel.selectedCharacter.value?.characterName
        fragmentDetailBinding!!.toolbarLayout.title = characterName

        itemDetailTextView = fragmentDetailBinding!!.textMarvelCharacterDetailsName

        // Show the placeholder content as text in a TextView.
        characterName?.let {
            itemDetailTextView.text = it
        }
        val thumbnail = marvelCharactersViewModel.selectedCharacter.value?.thumbnailImage
        val imageMarvelCharacterDetailsThumbnail =
            fragmentDetailBinding!!.imageMarvelCharacterDetailsThumbnail
        thumbnail?.let {
            thumbnailService.loadLandscapeThumbnail(it,
                imageMarvelCharacterDetailsThumbnail)
        }
        imageMarvelCharacterDetailsThumbnail.contentDescription =
            "$characterName ${getString(R.string.thumbnail)}"

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDetailBinding = null
    }
}