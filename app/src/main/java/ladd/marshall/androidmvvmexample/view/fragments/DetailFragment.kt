package ladd.marshall.androidmvvmexample.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.detail_fragment.*
import ladd.marshall.androidmvvmexample.R
import ladd.marshall.androidmvvmexample.utils.EMPLOYEE_ID
import ladd.marshall.androidmvvmexample.viewModel.viewModels.DetailViewModel

class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            viewModel.employeeLiveData(bundle.getInt(EMPLOYEE_ID)).observe(this) { employee ->
                textViewName.text = employee.employeeName
                textViewId.text = employee.id.toString()
            }
        }
    }
}
