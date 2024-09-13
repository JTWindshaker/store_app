package co.edu.unab.damo.jatt.storeapp.core.data.network

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val firestoreDB: FirebaseFirestore = Firebase.firestore
}