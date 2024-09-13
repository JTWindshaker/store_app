package co.edu.unab.damo.jatt.storeapp.login

sealed interface LoginUIState {
    data object Loading : LoginUIState
    data object Default : LoginUIState
    data object Success : LoginUIState
    data object Error : LoginUIState
}