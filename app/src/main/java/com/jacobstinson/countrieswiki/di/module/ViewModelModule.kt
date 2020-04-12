package com.jacobstinson.countrieswiki.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacobstinson.countrieswiki.di.factory.MyViewModelFactory
import com.jacobstinson.countrieswiki.di.qualify.ViewModelKey
import com.jacobstinson.countrieswiki.view_viewmodel.countries.CountriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    abstract fun bindCountriesViewModel(countriesViewModel: CountriesViewModel): ViewModel
}