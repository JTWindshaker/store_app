package co.edu.unab.damo.jatt.storeapp.core.data

import android.content.Context
import co.edu.unab.damo.jatt.storeapp.R
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.storeapp_prefs),
        Context.MODE_PRIVATE
    )

    fun saveUserId(userId: String?) {
        sharedPreferences.edit()
            .putString("uid", userId)
            .apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("uid", null)
    }
}
