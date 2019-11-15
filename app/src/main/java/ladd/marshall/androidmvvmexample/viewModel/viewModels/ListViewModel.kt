package ladd.marshall.androidmvvmexample.viewModel.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ladd.marshall.androidmvvmexample.api.EmployeeEndpoints
import ladd.marshall.androidmvvmexample.api.RetroFitInstance

class ListViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val employeeCalls = RetroFitInstance.getInstance().create(EmployeeEndpoints::class.java)

    val employeeListLiveData = liveData(Dispatchers.IO) {
        val employeeList = employeeCalls.getEmployeeList()
        emit(employeeList)
    }
}
