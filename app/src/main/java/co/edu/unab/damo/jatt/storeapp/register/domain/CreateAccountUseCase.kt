package co.edu.unab.damo.jatt.storeapp.register.domain

import co.edu.unab.damo.jatt.storeapp.core.ui.model.User
import co.edu.unab.damo.jatt.storeapp.register.data.repository.RegisterRepository
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val createUserFirestoreUseCase: CreateUserFirestoreUseCase
) {
    suspend operator fun invoke(user: User, password: String): Boolean {
        val accountResult = registerRepository.createAccount(user.email, password)
        return if (accountResult != null) {
            println("Usuario Creado con ID ${accountResult.user?.uid}")
            createUserFirestoreUseCase(user.copy(id = accountResult.user?.uid.toString()))
        } else {
            false
        }
    }
}