package ladd.marshall.androidmvvmexample.viewModel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ladd.marshall.androidmvvmexample.model.models.Employee
import ladd.marshall.androidmvvmexample.viewModel.repositories.EmployeeRepository

class ListViewModel(application: Application) : AndroidViewModel(application) {

    // coordinates between the local and remote databases
    private val employeeRepository = EmployeeRepository.getInstance(application)

    // This LiveData is created using a ktx library shortcut
    val employeeListLiveData: LiveData<List<Employee>> = liveData(Dispatchers.IO) {
        emitSource(employeeRepository.getAllEmployeesLiveData())
    }
}
