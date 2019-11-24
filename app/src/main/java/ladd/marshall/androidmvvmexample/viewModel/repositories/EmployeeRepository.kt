package ladd.marshall.androidmvvmexample.viewModel.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import ladd.marshall.androidmvvmexample.api.EmployeeEndpoints
import ladd.marshall.androidmvvmexample.api.RetroFitInstance
import ladd.marshall.androidmvvmexample.model.database.ExampleRoomDB
import ladd.marshall.androidmvvmexample.model.database.daos.EmployeeDAO
import ladd.marshall.androidmvvmexample.model.models.Employee
import timber.log.Timber

/**
 * Repository classes coordinate between local and remote databases. This provides what is known as
 * a 'single source of truth'.
 */

/**
 * 'private constructor' prevents this class from being instatiated directly. A builder function
 *  is provided in a companion object instead. This is essentially a Singleton pattern from java.
 */
class EmployeeRepository private constructor(application: Application) {

    private val employeeDAO: EmployeeDAO = ExampleRoomDB.getDatabase(application).getEmployeeDao()
    private val employeeCalls = RetroFitInstance.getInstance().create(EmployeeEndpoints::class.java)

    /**
     * An 'init' block in Kotlin is called immediately after an Object is created, and never again.
     * This block is used only for demoing the Singleton pattern. EmployeeRepository is accessed
     * from many classes at different times. If you watch the Logcot messages while switching
     * between screens, and filter by this class name, you'll notice that this message only appears
     * once, implying that its constructor is only being called once.
     */
    init {
        Timber.d("${this.javaClass.name} init for the first time.")
    }

    private suspend fun insertEmployee(employee: Employee) {
        employeeDAO.insertEmployee(employee)
    }

    private suspend fun deleteEmployee(employee: Employee) {
        employeeDAO.deleteEmployee(employee)
    }

    suspend fun getAllEmployeesLiveData(): LiveData<List<Employee>> {
        return employeeDAO.getAllEmployeesLiveData().also {
            getAllEmployeesFromRemote()
        }
    }

    private suspend fun getAllEmployeesFromRemote() {
        try {
            val employeeList = employeeCalls.getEmployeeList()
            employeeList.forEach {
                insertEmployee(it)
            }
        } catch (exception: Throwable) {
            Timber.e(exception)
        }
    }

    suspend fun getEmployeeByIdLiveData(employeeId: Int): LiveData<Employee?> {
        return employeeDAO.getEmployeeByIdLiveData(employeeId).also {
            try {
                val employee = employeeCalls.getEmployeeById(employeeId)
                insertEmployee(employee)
            } catch (exception: Throwable) {
                Timber.e(exception)
            }
        }
    }

    // Singleton Pattern for Repository.
    companion object {
        /**
         *  This is where the EmployeeRepository all callers will receive. Set it to null at first
         *  and make it private so it can't be directly accessed.
        */
        private var INSTANCE: EmployeeRepository? = null

        /**
         * This method checks whether or not INSTANCE is null. If it's not null, it returns the
         * Singleton INSTANCE. If it is null, it creates a new Object, sets INSTANCE equal to that,
         * and returns INSTANCE. From here on out, this method will now return the same INSTANCE,
         * every time.
         */
        fun getInstance(application: Application): EmployeeRepository = INSTANCE ?: kotlin.run {
            INSTANCE = EmployeeRepository(application = application)
            INSTANCE!!
        }
    }
}
