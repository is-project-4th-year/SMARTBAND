package com.example.smartband.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AvatarViewModel : ViewModel() {
    val _selectedAvatar = MutableStateFlow<Int?>(null)
    val selectedAvatar: StateFlow<Int?> = _selectedAvatar

    fun selectAvatar(avatar: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        _selectedAvatar.value = avatar

        // Save to Firestore
        val db = Firebase.firestore
        val userRef = db.collection("users").document(userId)
        userRef.set(mapOf("selectedAvatar" to avatar), SetOptions.merge())
    }

    fun loadAvatar() {

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = Firebase.firestore
        val userRef = db.collection("users").document(userId)

        userRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val avatar = document.getLong("selectedAvatar")?.toInt()
                _selectedAvatar.value = avatar
            }
        }.addOnFailureListener { e ->
            Log.e("AvatarViewModel", "Failed to load avatar: ${e.message}")
        }
    }

}

