package ladd.marshall.androidmvvmexample.viewModel.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ladd.marshall.androidmvvmexample.api.EmployeeEndpoints
import ladd.marshall.androidmvvmexample.api.RetroFitInstance
import ladd.marshall.androidmvvmexample.model.models.Employee
import timber.log.Timber

class DetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val employeeCalls = RetroFitInstance.getInstance().create(EmployeeEndpoints::class.java)

    fun employeeLiveData(id: Int): LiveData<Employee> = liveData(Dispatchers.IO) {
        try {
            val employee = employeeCalls.getEmployeeById(id)
            emit(employee)
        } catch (exception: Throwable) {
            Timber.e(exception)
        }
    }
}
