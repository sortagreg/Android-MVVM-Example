package ladd.marshall.androidmvvmexample.model.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ladd.marshall.androidmvvmexample.model.models.Employee

@Dao
interface EmployeeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(employee: Employee)

    @Delete
    fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employee_table")
    fun getAllEmployeesLiveData(): LiveData<List<Employee>>

    @Query("SELECT * FROM employee_table WHERE id = :employeeId")
    fun getEmployeeByIdLiveData(employeeId: Int): LiveData<Employee?>
}
