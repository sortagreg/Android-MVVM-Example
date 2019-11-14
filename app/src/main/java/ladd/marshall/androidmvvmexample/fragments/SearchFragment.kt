package ladd.marshall.androidmvvmexample.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.search_fragment.*
import ladd.marshall.androidmvvmexample.R

class SearchFragment : Fragment(R.layout.search_fragment) {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSearch.setOnClickListener { findNavController().navigate(R.id.action_searchFragment_to_detailFragment) }
    }
}
