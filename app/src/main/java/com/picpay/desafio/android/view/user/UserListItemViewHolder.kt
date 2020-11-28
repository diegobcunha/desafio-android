package com.picpay.desafio.android.view.user

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.extensions.view.loadImage
import com.picpay.desafio.android.model.data.User
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        itemView.name.text = user.name
        itemView.username.text = user.username
        itemView.progressBar.visibility = View.VISIBLE
        itemView.picture.loadImage(user.img, loadView = itemView.progressBar)
    }
}