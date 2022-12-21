package ie.wit.studentshare.ui.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.studentshare.R
import ie.wit.studentshare.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args by navArgs<MapFragmentArgs>()
    private lateinit var callback: OnMapReadyCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        callback = OnMapReadyCallback { googleMap ->
            val loc = LatLng(args.coordinates.lat, args.coordinates.lng)

            val iMarker = MarkerOptions()
                .title(args.coordinates.title)
                .position(loc)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            val circle = CircleOptions()
                .center(loc)
                .radius(args.coordinates.radius)
            googleMap.addCircle(circle)
            googleMap.addMarker(iMarker)?.showInfoWindow()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, args.coordinates.zoom))
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}