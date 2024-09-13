package co.edu.unab.damo.jatt.storeapp.users.domain

import co.edu.unab.damo.jatt.storeapp.users.domain.model.User
import co.edu.unab.damo.jatt.storeapp.users.model.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): List<User> {
        return userRepository.getUsers()
    }
}
