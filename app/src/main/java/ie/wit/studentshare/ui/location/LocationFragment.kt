package ie.wit.studentshare.ui.location

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.studentshare.R
import ie.wit.studentshare.databinding.FragmentLocationBinding
import ie.wit.studentshare.helpers.checkLocationPermissions
import timber.log.Timber

class LocationFragment : Fragment() {
    private var _binding: FragmentLocationBinding? = null
    private lateinit var locationService: FusedLocationProviderClient
    private val binding get() = _binding!!
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationService = LocationServices.getFusedLocationProviderClient(requireContext())
        if (checkLocationPermissions(requireActivity())) {
            doSetCurrentLocation()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mapViewModel =
            ViewModelProvider(this).get(LocationViewModel::class.java)

        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    @SuppressLint("MissingPermission")
    private fun doSetCurrentLocation() {
        Timber.i("setting location from doSetLocation")
        locationService.lastLocation.addOnSuccessListener {
            Toast.makeText(context, it.latitude.toString() + it.longitude.toString(), Toast.LENGTH_LONG).show()
        }
    }

}