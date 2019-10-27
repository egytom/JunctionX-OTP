package hu.bme.aut.otpsmartatm.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.extensions.requireString
import hu.bme.aut.otpsmartatm.R
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : RainbowCakeFragment<DetailsViewState, DetailsViewModel> {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_details

    //region Arguments
    @Suppress("ConvertSecondaryConstructorToPrimary")
    @Deprecated(
        message = "Use newInstance instead",
        replaceWith = ReplaceWith("DetailsFragment.newInstance()")
    )
    constructor()

    companion object {
        private const val ATM_ID = "ATM_ID"

        @Suppress("DEPRECATION")
        fun newInstance(atmId: String): DetailsFragment {
            return DetailsFragment().applyArgs {
                putString(ATM_ID, atmId)
            }
        }
    }

    private var atmId: String = ""

    private fun initArguments() {
        atmId = requireArguments().requireString(ATM_ID)
    }

    //endregion
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArguments()
        selectAtmButton.setOnClickListener {
            viewModel.selectAtm(atmId)
        }
        // TODO Setup views
    }

    override fun onStart() {
        super.onStart()

        viewModel.load(atmId)
    }

    override fun render(viewState: DetailsViewState) {
        when (viewState) {
            is Loading -> {

            }
            is DetailsReady -> {
                personAmountText.text =
                    "${viewState.data.countOfFutureCustomers} people are waiting"
                waitingTimeText.text =
                    "${viewState.data.queueAndTravelTime} travel and waiting time"
            }
        }
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is DetailsViewModel.AtmSelected -> {
                event.position
                val gmmIntentUri =
                    Uri.parse("google.navigation:q=${event.position.latitude},${event.position.longitude}&mode=w")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }

}
