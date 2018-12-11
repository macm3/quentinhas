package com.ufpe.if710.quentinhas.client
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ufpe.if710.quentinhas.R

class ClientOrderFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_client_profile, container, false)
    }

    companion object {
        fun newInstance(): ClientOrderFragment = ClientOrderFragment()
    }
}