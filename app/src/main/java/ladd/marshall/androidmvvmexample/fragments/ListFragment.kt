package ladd.marshall.androidmvvmexample.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.list_fragment.*
import ladd.marshall.androidmvvmexample.R
import ladd.marshall.androidmvvmexample.adapters.EmployeeRecyclerViewAdapter
import ladd.marshall.androidmvvmexample.models.Employee
import ladd.marshall.androidmvvmexample.utils.EMPLOYEE_ID
import ladd.marshall.androidmvvmexample.utils.EMPLOYEE_NAME

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

        (recyclerView.adapter as EmployeeRecyclerViewAdapter).submitList(
            listOf(
                Employee(1, "Test name 1", 123456, 32, "http://testimage.jpg"),
                Employee(2, "Test name 2", 123456, 32, "http://testimage.jpg"),
                Employee(3, "Test name 3", 123456, 32, "http://testimage.jpg"),
                Employee(4, "Test name 4", 123456, 32, "http://testimage.jpg")
            )
        )
    }
}
