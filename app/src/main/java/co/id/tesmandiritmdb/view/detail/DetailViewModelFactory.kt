package co.id.tesmandiritmdb.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.tesmandiritmdb.network.Repository

class DetailViewModelFactory (
        val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}