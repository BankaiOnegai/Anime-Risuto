package com.google.codelabs.mdc.kotlin.shrine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry.Companion.initProductEntryList


class AnimeListViewModel: ViewModel(){

    val animeList : MutableLiveData<List<ProductEntry>> = MutableLiveData()

    init {
        callApi()
    }

    private fun callApi() {
        animeList.value = initProductEntryList()
    }
}