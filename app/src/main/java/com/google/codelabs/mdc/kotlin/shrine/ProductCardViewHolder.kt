package com.google.codelabs.mdc.kotlin.shrine

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.android.volley.toolbox.NetworkImageView

class ProductCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var productId: TextView = itemView.findViewById(R.id.product_id)
    var productImage: NetworkImageView = itemView.findViewById(R.id.product_image)
    //var productCover : NetworkImageView = itemView.findViewById(R.id.product_Coverimage)
    var productTitle: TextView = itemView.findViewById(R.id.product_title)
    var productStatus: TextView = itemView.findViewById(R.id.product_status)
}