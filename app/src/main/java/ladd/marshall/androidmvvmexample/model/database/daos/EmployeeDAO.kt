package ladd.marshall.androidmvvmexample.model.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import ladd.marshall.androidmvvmexample.model.models.Employee

@Dao
interface EmployeeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(vararg employee: Employee)

    @Delete
    fun deleteEmployee(vararg employee: Employee)

    @Query("SELECT * FROM employee_table")
    fun getAllEployeesLiveData(): LiveData<List<Employee>>
}
