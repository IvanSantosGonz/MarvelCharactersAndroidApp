package ivansantos.marvelcharacters.ui.charactermaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ivansantos.marvelcharacters.R
import ivansantos.marvelcharacters.databinding.FragmentMasterBinding
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.ThumbnailService
import ivansantos.marvelcharacters.ui.MarvelCharactersViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MasterFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MarvelCharactersRecyclerViewAdapter
    private lateinit var fragmentMasterBinding: FragmentMasterBinding

    @Inject
    lateinit var thumbnailService: ThumbnailService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragmentMasterBinding = FragmentMasterBinding.inflate(inflater, container, false)
        return fragmentMasterBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //We use by activityViewModels instead of by viewModels because we want to share the viewmodel
        //between master and detail fragment associated with the activity lifecycle (using by viewmodel
        // it is associated to the fragment lifecycle)
        val marvelCharactersViewModel: MarvelCharactersViewModel by activityViewModels()
        fragmentMasterBinding.viewmodel = marvelCharactersViewModel
        fragmentMasterBinding.lifecycleOwner = this

        val onClickListener = View.OnClickListener { itemView -> //TODO: move to function
            val clickedCharacterPosition = recyclerView.getChildAdapterPosition(itemView)
            val clickedMarvelCharacter =
                marvelCharactersViewModel.characters.value?.get(clickedCharacterPosition)
            clickedMarvelCharacter?.let { marvelCharactersViewModel.setSelected(it) }
            itemView.findNavController().navigate(R.id.show_detail)
        }

        fragmentMasterBinding.retryButton.setOnClickListener {
            marvelCharactersViewModel.createSampleCharacters()
        }

        setAdapter(listOf(), onClickListener)
        if (marvelCharactersViewModel.characters.value == null) {
            marvelCharactersViewModel.createSampleCharacters() //TODO: use same function thar load more chars
        }

        marvelCharactersViewModel.characters.observe(
            this,
            { characters ->
                viewAdapter.swapData(characters)
            }
        )

        recyclerView.setOnScrollChangeListener { _, _, _, _, _ -> //TODO: move to function
            val layoutManager: GridLayoutManager =
                fragmentMasterBinding.recyclerViewCharacters.layoutManager as GridLayoutManager
            val lastCharacterPosition = marvelCharactersViewModel.characters.value!!.size - 1
            if (lastCharacterPosition != 0 && layoutManager.findLastCompletelyVisibleItemPosition() == lastCharacterPosition) {
                marvelCharactersViewModel.loadMoreCharacters()
            }
        }
    }

    private fun setAdapter(
        dataset: List<MarvelCharacter>,
        onClickListener: View.OnClickListener,
    ) {
        val viewManager = GridLayoutManager(context, 2)
        viewAdapter =
            MarvelCharactersRecyclerViewAdapter(dataset, onClickListener, thumbnailService)
        recyclerView = fragmentMasterBinding.recyclerViewCharacters
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}