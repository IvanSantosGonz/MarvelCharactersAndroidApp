package ivansantos.marvelcharacters.ui.charactermaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ivansantos.marvelcharacters.R
import ivansantos.marvelcharacters.domain.MarvelCharacter
import ivansantos.marvelcharacters.domain.ThumbnailService
import ivansantos.marvelcharacters.ui.charactermaster.MarvelCharactersRecyclerViewAdapter.MarvelCharactersViewHolder

class MarvelCharactersRecyclerViewAdapter(
    private var marvelCharacters: List<MarvelCharacter>,
    private val onClickListener: View.OnClickListener,
    private val thumbnailService: ThumbnailService,
) :
    RecyclerView.Adapter<MarvelCharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharactersViewHolder {
        return MarvelCharactersViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.marvel_character_master_item, parent, false)
        )
    }

    override fun getItemCount() = marvelCharacters.size

    override fun onBindViewHolder(holder: MarvelCharactersViewHolder, position: Int) =
        holder.bind(marvelCharacters[position], onClickListener)

    fun swapData(data: List<MarvelCharacter>) {
        this.marvelCharacters = data
        notifyDataSetChanged()
    }

    inner class MarvelCharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MarvelCharacter, onClickListener: View.OnClickListener) = with(itemView) {
            val marvelCharacterName =
                itemView.findViewById<TextView>(R.id.text_marvel_character_name)
            marvelCharacterName.text = item.characterName

            val marvelCharacterThumbnail =
                itemView.findViewById<ImageView>(R.id.image_marvel_character_thumbnail)
            thumbnailService.loadPortraitThumbnail(item.thumbnailImage, marvelCharacterThumbnail)

            marvelCharacterThumbnail.contentDescription =
                "${item.characterName} ${context.getString(R.string.thumbnail)}"

            setOnClickListener(onClickListener)
        }
    }
}