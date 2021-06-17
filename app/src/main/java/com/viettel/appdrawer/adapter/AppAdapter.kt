package com.viettel.appdrawer.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.viettel.appdrawer.R
import com.viettel.appdrawer.model.AppInfo


class AppAdapter(context : Context, @LayoutRes private val resourceId : Int, private val apps: List<AppInfo>) : ArrayAdapter<AppInfo>(context, resourceId, apps ) {
    private val packageManager = context.packageManager


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentApp = apps[position]
        val view = convertView ?: LayoutInflater.from(context).inflate(resourceId, parent, false)

        // Set application title
        val appTitle = view.findViewById<TextView>(R.id.title_app)
        appTitle.text = currentApp.label

        // Set package name of application
        if (!TextUtils.isEmpty(currentApp.info.packageName)) {
            val appSubtitle = view.findViewById<TextView>(R.id.subtitle_app)
            appSubtitle.text = currentApp.info.packageName
        }

        // Set application icon
        val imageView = view.findViewById<ImageView>(R.id.icon_image)
        val background = currentApp.info.loadIcon(packageManager)
        imageView.background = background

        return view
    }
}