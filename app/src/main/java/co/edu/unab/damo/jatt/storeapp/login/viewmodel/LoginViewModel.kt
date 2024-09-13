package co.edu.unab.damo.jatt.storeapp.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.damo.jatt.storeapp.login.LoginUIState
import co.edu.unab.damo.jatt.storeapp.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

//    private val _isLogin = MutableLiveData<Boolean>()
//    val isLogin: LiveData<Boolean> = _isLogin
//
//    private val _isError = MutableLiveData<Boolean>()
//    val isError: LiveData<Boolean> = _isError

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Default)
    val uiState: StateFlow<LoginUIState> = _uiState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun verifyLogin() {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()

        if (emailValue.isBlank() || passwordValue.isBlank()) {
            _errorMessage.value = "Ambos datos son obligatorios"
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = LoginUIState.Loading
            try {
                _uiState.emit(
                    loginUseCase(
//                    email = email.value.toString(),
                        email = email.value!!,
                        password = password.value!!
                    ) //Se puede hacer de dos formas
                )
            } catch (e: Exception) {
                _uiState.emit(LoginUIState.Error)
            }
        }

//        val verify = email.value == "juan" && password.value == "123"
//        _isLogin.value = verify
//        _isError.value = !verify
    }

    suspend fun onChangeUIState(uiState: LoginUIState) {
        _uiState.emit(uiState)
    }

    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun onMessageChange(message: String?) {
        _errorMessage.value = message
    }
}