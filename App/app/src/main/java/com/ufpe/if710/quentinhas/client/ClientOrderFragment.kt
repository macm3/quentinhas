package com.ufpe.if710.quentinhas.client
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.model.User
import com.ufpe.if710.quentinhas.order.ProviderAdapter
import kotlinx.android.synthetic.main.fragment_client_order.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ClientOrderFragment: Fragment() {
    private var mView: View? = null
    private var recyclerView: RecyclerView? = null

    private var providersList: ArrayList<User> = arrayListOf()
    private var usersRef: DatabaseReference? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_client_order, container, false)

        recyclerView = mView!!.findViewById(R.id.recycler_view_providers)
        usersRef = FirebaseDatabase.getInstance().reference.child("users")
        findProviders()

        return mView
    }

    private fun updateUI(){
        if (providersList.isEmpty()){
            no_provider.visibility = View.VISIBLE
        } else {
            no_provider.visibility = View.GONE
        }

        try {
            doAsync {
                val adapter = ProviderAdapter(providersList)
                uiThread {
                    recyclerView!!.adapter = adapter
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun findProviders(){
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        var dateThen: Date
        val endTime = Calendar.getInstance()
        val now = Calendar.getInstance()
        val query = usersRef!!.orderByKey()

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                providersList.clear()
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        val provider = i.getValue(User::class.java)
                        if(provider != null && provider.provider!!){
                            if (provider.endTime != null && provider.endTime != ""){
                                dateThen = sdf.parse(provider.endTime!!)
                                endTime.time = dateThen
                                Log.d("xablau", "endTime ${endTime.get(Calendar.HOUR_OF_DAY)}")
                                Log.d("xablau", "now ${now.get(Calendar.HOUR_OF_DAY)}")
                                if (endTime.get(Calendar.HOUR_OF_DAY) >= now.get(Calendar.HOUR_OF_DAY)){
                                    providersList.add(provider)
                                }
                            } else {
                                providersList.add(provider)
                            }
                            updateUI()
                        }
                    }
                }
            }
        })
    }

    companion object {
        fun newInstance(): ClientOrderFragment = ClientOrderFragment()
        val PROVIDER = "Fornecedor"
        val MENU = "Cardápio"
        val PROTEIN = "Proteina"
        val SIDES = "Acompanhamentos"
        val SIZE = "Tamanho"
        val NOTES = "Observações"
        val PAYMENT = "Método de pagamento"
    }
}