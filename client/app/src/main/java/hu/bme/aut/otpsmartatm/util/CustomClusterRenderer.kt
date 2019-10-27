package hu.bme.aut.otpsmartatm.util

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class CustomRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<AtmClusterItem>
) : DefaultClusterRenderer<AtmClusterItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: AtmClusterItem?, markerOptions: MarkerOptions?) {
        val color = if (item!!.atm.isDepositAvailable.not()) {
            BitmapDescriptorFactory.HUE_GREEN
        } else {
            213.5f
        }

        val desc = BitmapDescriptorFactory.defaultMarker(color)

        markerOptions?.icon(desc)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<AtmClusterItem>): Boolean {
        return cluster.size > 10
    }

}