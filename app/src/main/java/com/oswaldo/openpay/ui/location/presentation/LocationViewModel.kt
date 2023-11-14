package com.oswaldo.openpay.ui.location.presentation

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.oswaldo.openpay.BaseViewModel
import com.oswaldo.openpay.ui.location.domain.model.Location

class LocationViewModel : BaseViewModel<Unit, Unit>() {

    private val db = FirebaseFirestore.getInstance()
    val locationsLiveData = MutableLiveData<List<Location>>()

    fun saveLocation(latitude: Double, longitude: Double, currentTimeMillis: Long) {

        val location = hashMapOf(
            "latitude" to latitude,
            "longitude" to longitude,
            "timestamp" to currentTimeMillis
        )

        db.collection(FIREBASE_COLLECTION).add(location)
        getLocations()
    }

    fun getLocations() {
        db.collection(FIREBASE_COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                val locations = mutableListOf<Location>()
                for (document in result) {
                    val latitude = document.getDouble("latitude")
                    val longitude = document.getDouble("longitude")
                    if (latitude != null && longitude != null) {
                        locations.add(Location(latitude, longitude))
                    }
                }
                locationsLiveData.postValue(locations)
            }
    }

    companion object {
        const val FIREBASE_COLLECTION = "locations"
    }
}