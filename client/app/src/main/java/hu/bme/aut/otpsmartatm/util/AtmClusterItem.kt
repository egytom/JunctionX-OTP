package hu.bme.aut.otpsmartatm.util

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import hu.bme.aut.otpsmartatm.network.model.AtmPreview

class AtmClusterItem(
    private val snippet: String,
    private val title: String,
    private val position: LatLng,
    val atm: AtmPreview
) : ClusterItem {
    override fun getSnippet() = snippet
    override fun getTitle() = title
    override fun getPosition() = position
}