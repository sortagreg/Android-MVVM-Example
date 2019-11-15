package ladd.marshall.androidmvvmexample.model.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import ladd.marshall.androidmvvmexample.model.models.Employee

@Dao
interface EmployeeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(employee: Employee)

    @Delete
    fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employee_table")
    fun getAllEmployeesLiveData(): LiveData<List<Employee>>
}
