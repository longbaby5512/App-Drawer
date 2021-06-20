package com.viettel.appdrawer.adapter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import com.viettel.appdrawer.R
import com.viettel.appdrawer.model.AppInfo
import java.util.*


class AppAdapter(context : Context,
                 @LayoutRes private val resourceId : Int,
                 private var apps: List<AppInfo>)
    : ArrayAdapter<AppInfo>(context, resourceId, apps), Filterable {
    private val packageManager = context.packageManager
    private val appsClone = apps
    override fun getCount(): Int {
        return apps.size
    }

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                if (constraint.isNullOrEmpty()) {
                    results.values = appsClone
                    results.count = appsClone.size
                } else {
                    val searchString = constraint.toString().lowercase(Locale.getDefault())
                    val list = mutableListOf<AppInfo>()
                    for (app in appsClone) {
                        if (app.label.lowercase(Locale.getDefault()).contains(searchString)) {
                                list.add(app)
                        }
                    }
                    results.values = list
                    results.count = list.size
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                Log.d("CONSTAINT", constraint.toString())
                when {
                    results.count == 0 -> {
                        apps = mutableListOf()
                        notifyDataSetChanged()
                    }
                    constraint == "" -> {
                        apps = appsClone
                        notifyDataSetChanged()
                    }
                    else -> {
                        apps = results.values as List<AppInfo>
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}