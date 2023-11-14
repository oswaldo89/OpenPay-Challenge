package com.oswaldo.openpay.ui.location.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.oswaldo.openpay.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LocationViewModel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val locationRequest = LocationRequest.create().apply {
        interval = 300000 // Intervalo de 5 minutos en milisegundos
        fastestInterval = 300000 // Intervalo más rápido entre actualizaciones, también 5 minutos
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            startLocationUpdates()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)

        setupLocationListener()

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@LocationFragment)

        viewModel.getLocations()

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        viewModel.locationsLiveData.observe(viewLifecycleOwner) { locations ->
            googleMap?.clear()

            locations?.forEach { location ->
                val latLng = LatLng(location.latitud, location.longitud)
                googleMap?.addMarker(MarkerOptions().position(latLng).title("location"))
            }

            // center zoom in marker -> if exist locations saved
            locations?.lastOrNull()?.let { lastLocation ->
                val latLng = LatLng(lastLocation.latitud, lastLocation.longitud)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10f)
                googleMap?.moveCamera(cameraUpdate)
            }
        }
    }


    private fun setupLocationListener() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation.let { location ->
                    location?.let {
                        viewModel.saveLocation(it.latitude, it.longitude, System.currentTimeMillis())
                    }
                }
            }
        }

        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


}