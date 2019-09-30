/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo.ui.adapter

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.syed.gitrepo.R
import com.android.syed.gitrepo.model.RepoModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoListAdapter(activity: Activity) :
    RecyclerView.Adapter<RepoListAdapter.RepoItemViewHolder>() {
    private var items = emptyList<RepoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)

        return RepoItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        val data = getItem(position)

        holder.apply {

            Picasso.get().load(data.avatar)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgvItem)
            tvItemAuthor.text = data.author
            tvItemName.text = data.name
            tvItemDescription.text = data.description
            if (data.language == null) {
                imgvItemLanguage.visibility = View.GONE
                tvItemLanguage.visibility = View.GONE
            } else {
                val shapeDrawable = ShapeDrawable(OvalShape())
                shapeDrawable.paint.color = Color.parseColor(data.languageColor)
                imgvItemLanguage.setImageDrawable(shapeDrawable)
                tvItemLanguage.text = data.language
            }
            tvItemStars.text = data.stars.toString()
            tvItemForks.text = data.forks.toString()
            expandableLayout.visibility = View.GONE
            item.setOnClickListener { onCardClicked() }
        }
    }

    fun setItems(newList: List<RepoModel>) {
        items = newList
        notifyDataSetChanged()
    }

    private fun getItem(position: Int) = items[position]


    class RepoItemViewHolder(val item: View) : RecyclerView.ViewHolder(item) {

        val imgvItem: ImageView
        val tvItemAuthor: TextView
        val tvItemName: TextView
        val tvItemDescription: TextView
        val imgvItemLanguage: ImageView
        val tvItemLanguage: TextView
        val tvItemStars: TextView
        val tvItemForks: TextView
        val expandableLayout: ConstraintLayout

        init {
            imgvItem = item.imgv_item
            tvItemAuthor = item.tv_item_author
            tvItemName = item.tv_item_name
            tvItemDescription = item.tv_item_description
            imgvItemLanguage = item.imgv_item_language
            tvItemLanguage = item.tv_item_language
            tvItemStars = item.tv_item_stars
            tvItemForks = item.tv_item_forks
            expandableLayout = item.view_details
        }


        fun onCardClicked() {
            expandableLayout.visibility =
                if (expandableLayout.visibility == View.GONE) View.VISIBLE else View.GONE
        }

    }

}