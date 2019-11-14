package ladd.marshall.androidmvvmexample.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.list_fragment.*
import ladd.marshall.androidmvvmexample.R
import ladd.marshall.androidmvvmexample.adapters.EmployeeRecyclerViewAdapter
import ladd.marshall.androidmvvmexample.models.Employee
import ladd.marshall.androidmvvmexample.utils.EMPLOYEE_ID

class ListFragment : Fragment(R.layout.list_fragment) {

    private val viewModel by viewModels<ListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = EmployeeRecyclerViewAdapter { employee ->
                val bundle = bundleOf(EMPLOYEE_ID to employee.id)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
            }
        }

        (recyclerView.adapter as EmployeeRecyclerViewAdapter).submitList(listOf(Employee(1, "Test name", 123456, 32, "http://testimage.jpg")))
    }
}
