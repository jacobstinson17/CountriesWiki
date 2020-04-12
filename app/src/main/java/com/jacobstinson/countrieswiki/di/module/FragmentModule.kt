package com.jacobstinson.countrieswiki.di.module

import androidx.fragment.app.Fragment
import com.jacobstinson.countrieswiki.di.qualify.FragmentKey
import com.jacobstinson.countrieswiki.view_viewmodel.countries.CountriesFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(CountriesFragment::class)
    abstract fun bindCountriesFragment(countriesFragment: CountriesFragment): Fragment
}