package co.edu.unab.damo.jatt.storeapp.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.damo.jatt.storeapp.profile.ProfileUIState
import co.edu.unab.damo.jatt.storeapp.profile.domain.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileUIState>(ProfileUIState.Loading)
    val profileState: StateFlow<ProfileUIState> = _profileState

    fun loadUserProfile(uid: String) {
        viewModelScope.launch {
            _profileState.value = ProfileUIState.Loading

            try {
                val userProfile = getUserProfileUseCase(uid)

                if (userProfile != null) {
                    _profileState.value = ProfileUIState.Success(userProfile)
                } else {
                    _profileState.value = ProfileUIState.Error("Perfil no encontrado")
                }
            } catch (e: Exception) {
                _profileState.value =
                    ProfileUIState.Error("Falló al cargar el perfil: ${e.message}")
            }
        }
    }

//    private val _userProfile = MutableStateFlow<UserProfile?>(null)
//    val userProfile: StateFlow<UserProfile?> = _userProfile
//
//    fun loadUserProfile(uid: String) {
//        viewModelScope.launch {
//            val profile = getUserProfileUseCase(uid)
//            Log.e("xDDDD", profile.toString())
//            _userProfile.value = profile
//        }
//    }
}
