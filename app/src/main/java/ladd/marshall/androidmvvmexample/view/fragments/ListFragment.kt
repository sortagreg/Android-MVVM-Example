package ladd.marshall.androidmvvmexample.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.list_fragment.*
import ladd.marshall.androidmvvmexample.R
import ladd.marshall.androidmvvmexample.utils.EMPLOYEE_ID
import ladd.marshall.androidmvvmexample.utils.EMPLOYEE_NAME
import ladd.marshall.androidmvvmexample.view.adapters.EmployeeRecyclerViewAdapter
import ladd.marshall.androidmvvmexample.viewModel.viewModels.ListViewModel

class ListFragment : Fragment(R.layout.list_fragment) {

    private val viewModel by viewModels<ListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = EmployeeRecyclerViewAdapter { employee ->
                val bundle = bundleOf(EMPLOYEE_ID to employee.id, EMPLOYEE_NAME to employee.employeeName)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
            }
            this.addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (this.layoutManager as LinearLayoutManager).orientation
                )
            )
        }

        viewModel.employeeListLiveData.observe(this) { employeeList ->
            (recyclerView.adapter as EmployeeRecyclerViewAdapter).submitList(employeeList)
        }
    }
}
