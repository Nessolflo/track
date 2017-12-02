package com.inteltrack.inteltrack.vehiculos.adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by nestorso on 1/12/2017.
 */

public class VehiculosAdapterFragments extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private List<String> titulos;
    public VehiculosAdapterFragments(FragmentManager fm, List<Fragment> fragments, List<String> titulos) {
        super(fm);
        this.fragments = fragments;
        this.titulos = titulos;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titulos.get(position);
    }
}
