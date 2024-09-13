package co.edu.unab.damo.jatt.storeapp.register.domain

import co.edu.unab.damo.jatt.storeapp.core.ui.model.User
import co.edu.unab.damo.jatt.storeapp.register.data.repository.RegisterRepository
import javax.inject.Inject

class CreateUserFirestoreUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    suspend operator fun invoke(user: User): Boolean {
        return registerRepository.createUserFirestore(user)
    }
}