package ladd.marshall.androidmvvmexample.viewModel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ladd.marshall.androidmvvmexample.model.models.Employee
import ladd.marshall.androidmvvmexample.viewModel.repositories.EmployeeRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val employeeRepository = EmployeeRepository.getInstance(application)

    fun employeeLiveData(id: Int): LiveData<Employee?> = liveData(Dispatchers.IO) {
        emitSource(employeeRepository.getEmployeeByIdLiveData(id))
    }
}
