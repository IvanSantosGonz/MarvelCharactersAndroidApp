package ivansantos.marvelcharacters.ui.charactermaster

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
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
import ivansantos.marvelcharacters.ui.MainActivity
import ivansantos.marvelcharacters.ui.MarvelCharactersViewModel
import javax.inject.Inject


@AndroidEntryPoint
class MasterFragment : Fragment() {

    //We use by activityViewModels instead of by viewModels because we want to share the viewmodel
    //between master and detail fragment associated with the activity lifecycle (using by viewmodel
    // it is associated to the fragment lifecycle)
    private val marvelCharactersViewModel: MarvelCharactersViewModel by activityViewModels()
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

        fragmentMasterBinding.viewmodel = marvelCharactersViewModel
        fragmentMasterBinding.lifecycleOwner = this

        val onMarvelCharacterClickListener = View.OnClickListener { itemView ->
            navigateToDetailFragment(itemView)
        }
        setAdapter(listOf(), onMarvelCharacterClickListener)

        val existCharacters = marvelCharactersViewModel.characters.value != null
        if (!existCharacters) {
            marvelCharactersViewModel.loadCharacters()
        }

        fragmentMasterBinding.retryButton.setOnClickListener {
            marvelCharactersViewModel.loadCharacters()
        }

        marvelCharactersViewModel.characters.observe(
            viewLifecycleOwner,
            { characters ->
                viewAdapter.swapData(characters)
            }
        )

        recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            loadMoreCharacterWhenScrollToTheBottom()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(ComponentName(context,
                MainActivity::class.java)))
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String): Boolean {
                marvelCharactersViewModel.removeCharacters()
                marvelCharactersViewModel.loadCharactersBy(searchText)
                return false
            }

            override fun onQueryTextChange(searchText: String): Boolean {
                return false
            }
        })
    }

    private fun loadMoreCharacterWhenScrollToTheBottom() {
        val layoutManager: GridLayoutManager =
            fragmentMasterBinding.recyclerViewCharacters.layoutManager as GridLayoutManager
        val lastCharacterPosition = marvelCharactersViewModel.characters.value!!.size - 1
        if (lastCharacterPosition != 0 && layoutManager.findLastCompletelyVisibleItemPosition() == lastCharacterPosition) {
            marvelCharactersViewModel.loadCharacters()
        }
    }

    private fun navigateToDetailFragment(itemView: View) {
        val clickedCharacterPosition = recyclerView.getChildAdapterPosition(itemView)
        val clickedMarvelCharacter =
            marvelCharactersViewModel.characters.value?.get(clickedCharacterPosition)
        clickedMarvelCharacter?.let { marvelCharactersViewModel.setSelected(it) }
        itemView.findNavController().navigate(R.id.show_detail)
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