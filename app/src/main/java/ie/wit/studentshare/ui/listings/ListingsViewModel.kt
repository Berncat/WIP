package ie.wit.studentshare.ui.listings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "My Listings"
    }
    val text: LiveData<String> = _text
}