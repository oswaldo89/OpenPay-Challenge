package com.oswaldo.openpay.ui.location.presentation

import com.google.firebase.firestore.FirebaseFirestore
import com.oswaldo.openpay.BaseViewModel

class LocationViewModel : BaseViewModel<Unit, Unit>() {

    private val db = FirebaseFirestore.getInstance()

    fun save(latitude: Double, longitude: Double, currentTimeMillis: Long) {

        val location = hashMapOf(
            "latitude" to latitude,
            "longitude" to longitude,
            "timestamp" to currentTimeMillis
        )

        db.collection(FIREBASE_COLLECTION).add(location)
    }

    companion object {
        const val FIREBASE_COLLECTION = "locations"
    }
}