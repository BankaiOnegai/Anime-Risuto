package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.codelabs.mdc.kotlin.shrine.network.ImageRequester
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import kotlinx.android.synthetic.main.details_product_card.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [ProductDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductDetailsFragment(private val id_product: String) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.details_product_card, container, false)
        val product = ProductEntry.getProductEntry(this.id_product)
        view.product_title.text = product.title_en
        view.product_titleEn.text = product.title_en
        view.product_titleJp.text = product.title_jp
        view.product_status.text = "status : "+ product.status
        view.product_subtype.text = "Subtype : "+ product.subtype
        view.product_start_date.text = "start date : " + product.start_date
        view.product_end_date.text = "end date : " + product.end_date
        view.product_nb_episode.text = "nb episode : " +product.nb_episode
        view.product_ratingRank.text = "Rank : " + product.ratingRank
        view.product_synopsis.text = product.synopsis
        ImageRequester.setImageFromUrl(view.product_coverImage, product.coverImage)
        return view
    }
}