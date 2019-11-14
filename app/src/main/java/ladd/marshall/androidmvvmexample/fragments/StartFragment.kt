package ladd.marshall.androidmvvmexample.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.start_fragment.*
import ladd.marshall.androidmvvmexample.R

class StartFragment : Fragment(R.layout.start_fragment) {

    private val viewModel by viewModels<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAllEmployees.setOnClickListener { findNavController().navigate(R.id.action_startFragment_to_listFragment) }
        buttonEmployeeSearch.setOnClickListener { findNavController().navigate(R.id.action_startFragment_to_searchFragment) }
    }
}
