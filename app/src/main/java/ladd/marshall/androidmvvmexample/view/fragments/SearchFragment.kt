package ladd.marshall.androidmvvmexample.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.search_fragment.*
import ladd.marshall.androidmvvmexample.R
import ladd.marshall.androidmvvmexample.utils.EMPLOYEE_ID
import ladd.marshall.androidmvvmexample.utils.EMPLOYEE_NAME
import ladd.marshall.androidmvvmexample.utils.toInt
import ladd.marshall.androidmvvmexample.viewModel.viewModels.SearchViewModel

class SearchFragment : Fragment(R.layout.search_fragment) {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSearch.setOnClickListener {
            val bundle = bundleOf(EMPLOYEE_ID to textInputEmployeeId.toInt(), EMPLOYEE_NAME to "Searching...")
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle) }
    }
}
