/*
 * Copyright (c) 2019. This class is only created by Syed & only means to used by Syed for Development & Testing Purpose. This class can be also used by other with proper permission. Any unauthorised used is strictly prohibited.
 * $used.year
 */

package com.android.syed.gitrepo.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.syed.gitrepo.R
import com.android.syed.gitrepo.model.RepoModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoListAdapter(private var context: Context) :
    RecyclerView.Adapter<RepoListAdapter.RepoItemViewHolder>() {
    private var items = emptyList<RepoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)

        return RepoItemViewHolder(itemView)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        val data = getItem(position)

        holder.apply {
            Picasso.get().load(data.avatar)
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivRepoIcon)
            tvItemAuthor.text = data.author
            tvItemName.text = data.name
            tvItemDescription.text = data.description
            if (data.language == null) {
                ivItemLanguage.visibility = View.GONE
                tvItemLanguage.visibility = View.GONE
            } else {
                ivItemLanguage.visibility = View.VISIBLE
                tvItemLanguage.text = data.language
            }
            tvItemStars.text = data.stars.toString()
            tvItemForks.text = data.forks.toString()
            expandableLayout.visibility = View.GONE
            item.setOnClickListener { onCardClicked(context) }
        }
    }

    override fun getItemCount() = items.size

    fun setItems(newList: List<RepoModel>) {
        items = newList
        notifyDataSetChanged()
    }

    private fun getItem(position: Int) = items[position]

    class RepoItemViewHolder(val item: View) : RecyclerView.ViewHolder(item) {

        val ivRepoIcon: AppCompatImageView
        val tvItemAuthor: TextView
        val tvItemName: TextView
        val tvItemDescription: TextView
        val ivItemLanguage: AppCompatImageView
        val ivItemStar: AppCompatImageView
        val tvItemLanguage: TextView
        val tvItemStars: TextView
        val tvItemForks: TextView
        val expandableLayout: ConstraintLayout
        val cvRoot: CardView

        init {
            cvRoot = item.item_root
            ivRepoIcon = item.iv_repo_icon
            tvItemAuthor = item.tv_item_author
            tvItemName = item.tv_item_name
            tvItemDescription = item.tv_item_description
            ivItemLanguage = item.iv_item_language
            ivItemStar = item.iv_item_star
            tvItemLanguage = item.tv_item_language
            tvItemStars = item.tv_item_stars
            tvItemForks = item.tv_item_forks
            expandableLayout = item.view_details
        }

        fun onCardClicked(context: Context) {
            val cardColor: Int =
                if (expandableLayout.visibility == View.VISIBLE) context.resources.getColor(R.color.white)
                else context.resources.getColor(R.color.brdr_green)
            cvRoot.setCardBackgroundColor(cardColor)
            expandableLayout.visibility =
                if (expandableLayout.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }
}