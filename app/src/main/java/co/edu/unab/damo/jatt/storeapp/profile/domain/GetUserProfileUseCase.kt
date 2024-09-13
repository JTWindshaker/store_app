package co.edu.unab.damo.jatt.storeapp.profile.domain

import co.edu.unab.damo.jatt.storeapp.profile.domain.model.UserProfile
import co.edu.unab.damo.jatt.storeapp.profile.model.repository.ProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(uid: String): UserProfile? {
        return profileRepository.getUserProfile(uid)
    }
}
