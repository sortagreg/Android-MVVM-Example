package ladd.marshall.androidmvvmexample.api

import ladd.marshall.androidmvvmexample.model.models.Employee
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface EmployeeEndpoints {

    // Simple get request to get a list of all employees
    @GET("api/v1/employees")
    suspend fun getEmployeeList(): List<Employee>

    // A request to get an employee based on their employee id #.
    // In the URL, we wrap what could change in {} and give it a name.
    // Then, use @Path(variableName) in the arguments to the function.
    // The value to be placed in the URL can then be passed in
    // dynamically at runtime.
    @GET("api/v1/employee/{employeeId}")
    suspend fun getEmployeeById(@Path("employeeId") employeeId: Int): Employee

    // If your endpoint requires an API token, here is how the first call would look.
    // There are other, better ways to do this, such as by using an OkHttpClient.
    @GET("api/v1/employees")
    suspend fun getEmployeeListRequireToken(@Header("x-api-key") key: String): List<Employee>
}
