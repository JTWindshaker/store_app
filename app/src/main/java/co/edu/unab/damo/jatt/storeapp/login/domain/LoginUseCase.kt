package co.edu.unab.damo.jatt.storeapp.login.domain

import co.edu.unab.damo.jatt.storeapp.core.data.UserPreferencesRepository
import co.edu.unab.damo.jatt.storeapp.login.LoginUIState
import co.edu.unab.damo.jatt.storeapp.login.model.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(email: String, password: String): LoginUIState {
        val loginResult = loginRepository.login(email = email, password = password)

        return if (loginResult != null) {
            val userId = loginResult.user?.uid

            if (userId != null) {
                userPreferencesRepository.saveUserId(userId)
            }

            LoginUIState.Success
        } else {
            LoginUIState.Error
        }
    }
}
