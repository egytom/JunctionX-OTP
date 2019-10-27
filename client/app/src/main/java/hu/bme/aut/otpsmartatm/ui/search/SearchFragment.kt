package hu.bme.aut.otpsmartatm.ui.search

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import hu.bme.aut.otpsmartatm.ui.MainActivity
import hu.bme.aut.otpsmartatm.ui.details.DetailsFragment
import hu.bme.aut.otpsmartatm.util.AtmClusterItem
import hu.bme.aut.otpsmartatm.util.CustomRenderer
import kotlinx.android.synthetic.main.fragment_search.*
import org.threeten.bp.LocalDateTime
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class SearchFragment : OnMapReadyCallback, RainbowCakeFragment<SearchViewState, SearchViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = hu.bme.aut.otpsmartatm.R.layout.fragment_search

    private lateinit var mMap: GoogleMap
    private lateinit var currentPos: LatLng

    //region Get current location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var startCallback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = FusedLocationProviderClient(requireContext().applicationContext)
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun registerForLocation() {
        startCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                result?.lastLocation?.let {
                    currentPos = LatLng(it.latitude, it.longitude)
                    mMap.addMarker(
                        MarkerOptions().position(currentPos)
                            .title("ÖN ITT ÁLL")
                    ).apply {
                        showInfoWindow()
                    }
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 15f))

                    if (arguments!!.getBoolean(MainActivity.SHORTCUT)) {
                        viewModel.sendData(currentPos, getCurrentOffset(), false)
                    } else {
                        viewModel.load()
                    }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }, startCallback!!, null)
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMap()
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(hu.bme.aut.otpsmartatm.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(startCallback)
    }

    override fun render(viewState: SearchViewState) {
        when (viewState) {
            is Loading -> {
                mapLoadingProgressBar.visibility = View.VISIBLE
                mapLoadingCover.visibility = View.VISIBLE
            }
            is SearchReady -> {
                mapLoadingProgressBar.visibility = View.GONE
                mapLoadingCover.visibility = View.GONE

                val clusterItems = viewState.atms.map {
                    AtmClusterItem(
                        "",
                        "",
                        LatLng(it.coordinate.lat, it.coordinate.lng),
                        it
                    )
                }
                clusterManager?.addItems(clusterItems)

                setupSearchButton()
            }
        }
    }

    private fun setupSearchButton() {
        searchButton.setOnClickListener {
            val offset = getCurrentOffset()
            viewModel.sendData(currentPos, offset, isDepositCheckBox.isChecked)

            mapLoadingProgressBar.visibility = View.VISIBLE
            mapLoadingCover.visibility = View.VISIBLE
        }
    }

    private fun getCurrentOffset(): Int {
        val now = LocalDateTime.now()
        val extra = if (now.minute > 30) {
            1
        } else {
            0
        }
        return now.hour * 2 - 1 + extra
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is SearchViewModel.AtmFound -> {
                navigator?.add(DetailsFragment.newInstance(event.id))
            }
            is SearchViewModel.SearchResultReady -> {
                mMap.clear()
                clusterManager?.clearItems()

                mapLoadingProgressBar.visibility = View.GONE
                mapLoadingCover.visibility = View.GONE

                mMap.addMarker(
                    MarkerOptions().position(currentPos)
                        .title("ÖN ITT ÁLL")
                )

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 15f))

                val items = event.atms.map {
                    AtmClusterItem(
                        "",
                        it.queueAndTravelTime,
                        LatLng(it.coordinate.lat, it.coordinate.lng),
                        it
                    )
                }
                clusterManager?.addItems(items)
            }
        }
    }

    private var clusterManager: ClusterManager<AtmClusterItem>? = null

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        clusterManager = ClusterManager(requireContext(), mMap)
        clusterManager?.renderer = CustomRenderer(requireContext(), mMap, clusterManager!!)

        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)

        mMap.setOnInfoWindowLongClickListener {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=${it.position.latitude},${it.position.longitude}&mode=w")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        mMap.setOnInfoWindowClickListener {
            viewModel.getAtmByLatLng(it.position)
        }

        registerForLocationWithPermissionCheck()
    }
}
