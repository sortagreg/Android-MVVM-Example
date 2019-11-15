package ladd.marshall.androidmvvmexample.viewModel.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import ladd.marshall.androidmvvmexample.api.EmployeeEndpoints
import ladd.marshall.androidmvvmexample.api.RetroFitInstance
import ladd.marshall.androidmvvmexample.model.database.ExampleRoomDB
import ladd.marshall.androidmvvmexample.model.database.daos.EmployeeDAO
import ladd.marshall.androidmvvmexample.model.models.Employee
import timber.log.Timber

class EmployeeRepository private constructor(application: Application) {

    private val employeeDAO: EmployeeDAO = ExampleRoomDB.getDatabase(application).getEmployeeDao()
    private val employeeCalls = RetroFitInstance.getInstance().create(EmployeeEndpoints::class.java)

    private suspend fun insertEmployee(employee: Employee) {
        employeeDAO.insertEmployee(employee)
    }

    private suspend fun deleteEmployee(employee: Employee) {
        employeeDAO.deleteEmployee(employee)
    }

    suspend fun getAllEmployeesLiveData(): LiveData<List<Employee>> {
        try {
            val employeeList = employeeCalls.getEmployeeList()
            employeeList.forEach {
                insertEmployee(it)
            }
        } catch (exception: Throwable) {
            Timber.e(exception)
        }
        return employeeDAO.getAllEmployeesLiveData()
    }

    companion object {
        fun getInstance(application: Application) = EmployeeRepository(application)
    }
}