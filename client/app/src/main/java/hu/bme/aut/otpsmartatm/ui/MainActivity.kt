package hu.bme.aut.otpsmartatm.ui

import android.os.Bundle
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import hu.bme.aut.otpsmartatm.R
import hu.bme.aut.otpsmartatm.ui.search.SearchFragment

class MainActivity : SimpleNavActivity() {

    companion object {
        private const val ACTION_INSTANT_START = "hu.bme.aut.otpsmartatm.shortcuts.ATM"

        const val SHORTCUT = "shortcut"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            when {
                this.intent.action == ACTION_INSTANT_START -> navigator.add(SearchFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean(SHORTCUT, true)
                    }
                })
                else -> navigator.add(SearchFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean(SHORTCUT, false)
                    }
                })
            }
        }
    }

}