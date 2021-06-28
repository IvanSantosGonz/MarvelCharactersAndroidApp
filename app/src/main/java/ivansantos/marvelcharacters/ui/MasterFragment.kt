package ivansantos.marvelcharacters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ivansantos.marvelcharacters.R
import ivansantos.marvelcharacters.databinding.FragmentMasterBinding
import ivansantos.marvelcharacters.domain.MarvelCharacter

class MasterFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MarvelCharactersRecyclerViewAdapter
    private lateinit var fragmentMasterBinding: FragmentMasterBinding

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

        val onClickListener = View.OnClickListener { itemView ->
            val clickedCharacterPosition = recyclerView.getChildAdapterPosition(itemView)
            val clickedMarvelCharacter =
                marvelCharactersViewModel.characters.value?.get(clickedCharacterPosition)
            clickedMarvelCharacter?.let { marvelCharactersViewModel.setSelected(it) }
            itemView.findNavController().navigate(R.id.show_detail)
        }
        marvelCharactersViewModel.createSampleCharacters()
        marvelCharactersViewModel.characters.value?.let { setAdapter(it, onClickListener) }
        marvelCharactersViewModel.characters.observe(
            this,
            { characters ->
                viewAdapter.swapData(characters)
            }
        )
    }

    private fun setAdapter(
        dataset: List<MarvelCharacter>,
        onClickListener: View.OnClickListener,
    ) {
        val viewManager = GridLayoutManager(context, 2)
        viewAdapter = MarvelCharactersRecyclerViewAdapter(dataset, onClickListener)
        recyclerView = fragmentMasterBinding.recyclerViewCharacters
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}