package co.edu.unab.damo.jatt.storeapp.register.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.damo.jatt.storeapp.core.ui.model.User
import co.edu.unab.damo.jatt.storeapp.register.domain.CreateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val createAccountUseCase: CreateAccountUseCase) :
    ViewModel() {

    private val _onCreateAccount = MutableStateFlow<Boolean>(false)
    val onCreateAccount: StateFlow<Boolean> = _onCreateAccount

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun createAccount(
        name: String,
        document: String,
        email: String,
        password: String,
        image: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            _onCreateAccount.emit(
                createAccountUseCase.invoke(
                    User(
                        id = "",
                        name = name,
                        document = document,
                        email = email,
                        image = image
                    ),
                    password = password
                )
            )

            _isLoading.value = false
        }
    }

    fun onChangeCreateAccount(b: Boolean) {
        _onCreateAccount.value = b
    }
}