package ivansantos.marvelcharacters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ivansantos.marvelcharacters.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var itemDetailTextView: TextView

    private var fragmentDetailBinding: FragmentDetailBinding? = null
    private val marvelCharactersViewModel: MarvelCharactersViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDetailBinding = null
    }
}