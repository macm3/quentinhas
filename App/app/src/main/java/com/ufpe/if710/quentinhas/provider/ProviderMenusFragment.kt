package com.ufpe.if710.quentinhas.provider


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ufpe.if710.quentinhas.R

class ProviderMenusFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider_menus, container, false)
    }

    companion object {
        fun newInstance(): ProviderMenusFragment = ProviderMenusFragment()
    }
}
