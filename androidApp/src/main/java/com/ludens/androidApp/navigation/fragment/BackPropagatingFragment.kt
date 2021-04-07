package com.ludens.androidApp.navigation.fragment

interface BackPropagatingFragment {

    /** @return true if this fragment handled the back button, false to propagate it further down the chain */
    fun back(): Boolean
}
