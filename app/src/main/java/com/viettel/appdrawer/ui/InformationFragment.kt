package com.viettel.appdrawer.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.viettel.appdrawer.R

class InformationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_information, container, false)
        val textView = view.findViewById<TextView>(R.id.information_text)
        textView.text = getHardwareAndSoftwareInfo()
        return view
    }

    private fun getHardwareAndSoftwareInfo() : String {
        return "Model: " + Build.MODEL + "\n" +
                "ID: " + Build.ID + "\n" +
                "Manufacturer: " + Build.MANUFACTURER + "\n" +
                "Brand: " + Build.BRAND + "\n" +
                "Type: " + Build.TYPE + "\n" +
                "User: " + Build.USER + "\n" +
                "Base: " + Build.VERSION_CODES.BASE + "\n" +
                "Incremental: " + Build.VERSION.INCREMENTAL + "\n" +
                "SDK: " + Build.VERSION.SDK_INT + "\n" +
                "Board: " + Build.BOARD + "\n" +
                "Host: " + Build.HOST + "\n" +
                "Fingerprint: " + Build.FINGERPRINT + "\n" +
                "Version code: " + Build.VERSION.RELEASE;

    }

}