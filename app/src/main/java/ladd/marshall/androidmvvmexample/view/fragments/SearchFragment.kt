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

    // Currently not used
    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSearch.setOnClickListener {
            /**
             * A Bundle is how you can share data between Fragments in the nav graph. Bundles use
             * Pair<T, T> in a Map, where the first part is the Key, and the second is the data.
             *
             * In Kotlin, the 'to' keyword creates Pair Objects quickly.
             *
             * The Key values for the pairs come from the utils/Constants.kt file. Using a
             * constants file prevents mistakes when retrieving the data later, and makes for
             * faster refactoring, if needed.
             *
             * The first pair uses a Kotlin Extension function, which can be found in
             * utils/Extensions.kt file, to get the text value from the input field.
             */
            val bundle = bundleOf(EMPLOYEE_ID to textInputEmployeeId.toInt(), EMPLOYEE_NAME to "Searching...")
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle) }
    }
}
