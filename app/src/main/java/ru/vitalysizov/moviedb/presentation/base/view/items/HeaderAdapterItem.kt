package ru.vitalysizov.moviedb.presentation.base.view.items

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_header_genres.view.*
import ru.vitalysizov.moviedb.R

class HeaderAdapterItem : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            tv_header_title.text = resources.getString(R.string.genres_header)
        }
    }

    override fun getLayout(): Int = R.layout.item_header_genres
}