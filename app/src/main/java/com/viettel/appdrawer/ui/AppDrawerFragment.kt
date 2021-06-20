package com.viettel.appdrawer.ui

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.viettel.appdrawer.R
import com.viettel.appdrawer.adapter.AppAdapter
import com.viettel.appdrawer.model.AppInfo

class AppDrawerFragment : Fragment() {
    private lateinit var appList: ListView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var search: TextInputEditText
    private lateinit var adapter: AppAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_app_drawer, container, false)

        appList = view.findViewById(R.id.app_list)
        swipeRefreshLayout = view.findViewById(R.id.swap_refresh)
        search = view.findViewById(R.id.action_search)

        appList.isTextFilterEnabled = true

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        appList.setOnItemClickListener { parent, _, position, _ ->
            val app = parent.getItemAtPosition(position) as AppInfo
            val intent =
                requireActivity().packageManager.getLaunchIntentForPackage(app.info.packageName)
            if (intent == null) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "No launcher attached with this app", Toast.LENGTH_SHORT
                ).show()
            } else {
                startActivity(intent)
            }
        }

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(search.text.toString())
                Log.d("SEARCH", search.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        return view
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        val apps = mutableListOf<AppInfo>()
        val packageManager = requireActivity().packageManager
        val infos = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        for (info in infos) {
            if ((info.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 1 &&
                packageManager.getLaunchIntentForPackage(info.packageName) == null
            ) {
                continue
            }
            val app = AppInfo(
                info.applicationInfo,
                info.applicationInfo.loadLabel(packageManager).toString()
            )
            apps.add(app)
        }

        apps.sortBy { it.label }
        adapter = AppAdapter(requireContext(), R.layout.app_item_layout, apps)
        appList.adapter = adapter
        Snackbar.make(
            appList, apps.size.toString() + " applications loaded",
            Snackbar.LENGTH_SHORT
        ).show()


    }
}


