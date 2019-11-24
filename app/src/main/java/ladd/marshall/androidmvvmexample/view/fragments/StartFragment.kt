package ladd.marshall.androidmvvmexample.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.start_fragment.*
import ladd.marshall.androidmvvmexample.R

/**
 * This is the first Fragment that is loaded by the NavController.  It implements a simple
 * navigation using the Navigation component.
 */
class StartFragment : Fragment(R.layout.start_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAllEmployees.setOnClickListener { findNavController().navigate(R.id.action_startFragment_to_listFragment) }
        buttonEmployeeSearch.setOnClickListener { findNavController().navigate(R.id.action_startFragment_to_searchFragment) }
    }
}
