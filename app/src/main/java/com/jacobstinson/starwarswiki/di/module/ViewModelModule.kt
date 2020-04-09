package com.jacobstinson.starwarswiki.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacobstinson.starwarswiki.di.factory.MyViewModelFactory
import com.jacobstinson.starwarswiki.di.qualify.ViewModelKey
import com.jacobstinson.starwarswiki.view_viewmodel.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindJokesViewModel(homeViewModel: HomeViewModel): ViewModel
}