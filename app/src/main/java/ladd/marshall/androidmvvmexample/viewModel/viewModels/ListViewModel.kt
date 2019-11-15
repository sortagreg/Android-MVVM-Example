package ladd.marshall.androidmvvmexample.viewModel.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ladd.marshall.androidmvvmexample.api.EmployeeEndpoints
import ladd.marshall.androidmvvmexample.api.RetroFitInstance
import ladd.marshall.androidmvvmexample.viewModel.repositories.EmployeeRepository
import timber.log.Timber

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val employeeRepository = EmployeeRepository.getInstance(application)

    val employeeListLiveData = liveData(Dispatchers.IO) {
        emitSource(employeeRepository.getAllEmployeesLiveData())
    }
}
