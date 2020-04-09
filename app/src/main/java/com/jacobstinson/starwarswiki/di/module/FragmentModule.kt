package com.jacobstinson.starwarswiki.di.module

import androidx.fragment.app.Fragment
import com.jacobstinson.starwarswiki.di.qualify.FragmentKey
import com.jacobstinson.starwarswiki.view_viewmodel.home.HomeFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun bindJokesFragment(homeFragment: HomeFragment): Fragment
}