package ladd.marshall.androidmvvmexample.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ladd.marshall.androidmvvmexample.R
import ladd.marshall.androidmvvmexample.model.models.Employee

/**
 * Uses a ListAdapter<DataClass, ViewHolder> instead of a standard RecyclerViewAdapter. This class
 * offers built in animations when updating a data set, like in a LiveData Observer.
 *
 * More info here:
 * https://medium.com/@trionkidnapper/recyclerview-more-animations-with-less-code-using-support-library-listadapter-62e65126acdb
 */
class EmployeeRecyclerViewAdapter(private val onClickListener: (Employee) -> Unit) :
    ListAdapter<Employee, EmployeeViewHolder>(EmployeeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_employee, parent, false)
        return EmployeeViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.item = getItem(position)
    }
}

class EmployeeViewHolder(private val view: View, private val onClickListener: (Employee) -> Unit) :
    RecyclerView.ViewHolder(view) {

    var item: Employee? = null
        set(value) {
            value?.let { newValue ->
                field = newValue
                view.setOnClickListener { onClickListener(newValue) }
                view.findViewById<TextView>(R.id.textViewName).text = newValue.employeeName
                view.findViewById<TextView>(R.id.textViewId).text = "${newValue.id}"
            }
        }
}

class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {

    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }
}
