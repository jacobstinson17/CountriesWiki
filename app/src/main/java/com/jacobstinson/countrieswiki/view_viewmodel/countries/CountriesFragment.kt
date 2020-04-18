package com.jacobstinson.countrieswiki.view_viewmodel.countries

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.tabs.TabLayout
import com.jacobstinson.countrieswiki.R
import com.jacobstinson.countrieswiki.databinding.FragmentCountriesBinding
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.countries.models.CountrySortByFields
import com.jacobstinson.countrieswiki.model.util.Resource
import com.jacobstinson.countrieswiki.model.util.Status
import com.jacobstinson.countrieswiki.util.Dialog
import kotlinx.android.synthetic.main.fragment_countries.*
import kotlinx.android.synthetic.main.fragment_countries.view.*
import javax.inject.Inject

class CountriesFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory): Fragment() {

    private lateinit var viewModel: CountriesViewModel
    private val countriesAdapter = CountriesAdapter(emptyList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(activity!!, viewModelFactory).get(CountriesViewModel::class.java)
        val binding = FragmentCountriesBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.recyclerviewCountries.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recyclerviewCountries.adapter = countriesAdapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val continentCodes = resources.getStringArray(R.array.array_continent_codes)
                viewModel.continentCode.value = continentCodes[tab.position]

                viewModel.refresh(false)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {}
        })
        viewModel.continentCode.observe(viewLifecycleOwner) { continentCode ->
            val tabPosition = resources.getStringArray(R.array.array_continent_codes).indexOf(continentCode)
            val tab = binding.tabLayout.getTabAt(tabPosition)
            binding.tabLayout.selectTab(tab)
        }

        viewModel.countries.observe(this) { resource : Resource<List<Country>?>? ->
            when(resource?.status) {
                Status.SUCCESS -> {
                    countriesAdapter.countries = resource.data!!
                    countriesAdapter.notifyDataSetChanged()
                    progress_bar.visibility = View.GONE
                    recyclerview_countries.visibility = View.VISIBLE
                    text_error.visibility = View.GONE
                    swipe_refresh.isRefreshing = false
                }
                Status.ERROR -> {
                    progress_bar.visibility = View.GONE
                    recyclerview_countries.visibility = View.GONE
                    text_error.visibility = View.VISIBLE
                    swipe_refresh.isRefreshing = false
                }
                Status.LOADING -> {
                    if(!swipe_refresh.isRefreshing) {
                        progress_bar.visibility = View.VISIBLE
                        recyclerview_countries.visibility = View.GONE
                        text_error.visibility = View.GONE
                    }
                }
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh(true)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_countries, menu)

        val directionMenuItem = menu.findItem(R.id.action_direction)
        viewModel.isDescending.observe(viewLifecycleOwner) { isDescending ->
            directionMenuItem.icon = if(isDescending) {
                resources.getDrawable(R.drawable.ic_arrow_up)
            } else {
                resources.getDrawable(R.drawable.ic_arrow_down)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_sort -> {
                Dialog.getRadioButtonsDialog(activity!!, resources.getString(R.string.dialog_title_sort),
                    resources.getStringArray(R.array.array_sort_by_fields), CountrySortByFields.getIndex(viewModel.orderByField)) { selection ->
                    viewModel.orderByField = selection

                    viewModel.refresh(false)
                }.show()
            }
            R.id.action_direction -> {
                viewModel.reverseSortDirection()

                viewModel.refresh(false)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}