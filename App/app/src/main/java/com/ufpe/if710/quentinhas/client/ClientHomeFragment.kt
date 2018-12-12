package com.ufpe.if710.quentinhas.client

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ufpe.if710.quentinhas.R

class ClientHomeFragment : Fragment(){

    private var mView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_client_home, container, false)
//        val btn = mView!!.findViewById<Button>(R.id.new_request)

//        btn.setOnClickListener {
//            //goToFirstOrder()
//        }

        return inflater.inflate(R.layout.fragment_client_home, container, false)
    }

    companion object {
        fun newInstance(): ClientHomeFragment = ClientHomeFragment()
    }
}