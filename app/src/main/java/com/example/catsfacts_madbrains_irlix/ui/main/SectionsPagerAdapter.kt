package com.example.catsfacts_madbrains_irlix.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.catsfacts_madbrains_irlix.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                AllFactsFragment.newInstance(position + 1)
            }
            else -> {
                return SavedFactsFragment.newInstance(position + 1)
            }
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {

        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}