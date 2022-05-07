package ru.extroot.newcubeguide

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment


/**
 * Easy3 text method fragment
 * In future will handle few methods
 */
class Easy3Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_easy3, container, false)
    }
}