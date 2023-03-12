package co.id.tesmandiritmdb.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.tesmandiritmdb.network.Repository

class MainViewModelFactory (
        val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}