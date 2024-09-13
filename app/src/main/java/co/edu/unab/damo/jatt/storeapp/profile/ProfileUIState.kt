package co.edu.unab.damo.jatt.storeapp.profile

import co.edu.unab.damo.jatt.storeapp.profile.domain.model.UserProfile

sealed class ProfileUIState {
    data object Loading : ProfileUIState()
    data class Success(val userProfile: UserProfile) : ProfileUIState()
    data class Error(val message: String) : ProfileUIState()
}