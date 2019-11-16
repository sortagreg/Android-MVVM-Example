# Android App Architecture in The App Factory
### The MVVM Design Pattern

## Introduction
This project is to provide a simple example app for Android Developers
at The App Factory to reference when starting a new app, or adding new
features to an existing app.

The architecture pattern shown here and to be used in apps, whenever
possible, is Model-View-ViewModel, or MVVM. This is the pattern
recommended and used by Google.

### App Features
- Connects to a remote database - 
  [documentation](http://dummy.restapiexample.com/)
- Downloads JSON(Employee) data from remote database
- Stores data in Room Database in app
- Displays list of Employees from database
- Allows searching of database
- Select a single Employee and show details of

### Language and IDE
This project was written using Kotlin 1.3.60 in Android Studio 3.5.2.
All Android apps in the App Factory will be written in Kotlin.

### Prerequisites
This project assumes a base knowledge of Kotlin and Android, such as
Activities, Fragments, and the Manifest.

## What is MVVM?
MVVM is a flexible guide and set of libraries used to standardize an
app's architecture. What this means is that your code is split into
distinct components that hold specific parts of code in specific areas.
These parts then interact with each other in a set order. These parts
are:

#### Model
The Model consists of two parts

- Database Classes
- Data Model Classes

In this app, our local database is Room. In other apps, Firebase may be
the database. The flexibility of MVVM allows for these differences, and
does not specify **what** to use, just **where** to put the code.

#### View
The View is the UI presentation logic. It consists of

- Activities
- Fragments
- Adapters
- XML

The View should only be responsible for displaying values and state.
Another way to say that is you should not make database requests and/or
network requests in the View. These will be done elsewhere. This means
the Views don't care where the data comes from or how it gets there, it
just shows whatever data there is. This way, if a change needs to be
made to the database, the View should not need to be changed at all.

#### View Model
The View Model is responsible for interacting with the Database and
coordinating between any remote sources. It is then responsible for
representing the state of the data to the View. This coordination
creates what is known as a **single source of truth** and exposes it to
the Views. This way, you can be as sure as you can be, that what is
being shown to the user is accurate, and where it came from.

ViewModels provided by the Jetpack components are also Lifecycle aware,
and can survive configuration changes. This helps:
- Prevent memory leaks
- Prevent errant network calls
- Prevent null pointer exceptions due to UI changes
- Solves the issue of what happens when you rotate an app

## How it works
As mentioned before MVVM breaks the app into components and they
interact in a certain way. This interaction follows the pattern:
1. The View subscribes to a LiveData from a ViewModel
2. The ViewModel connects to a repository
3. The Repository connects to databases, both remote and local, if there
   are both
4. The Repository returns the requested data to the the ViewModel
5. The ViewModel formats the data and exposes it through a LiveData
6. The View's subscriptions are notified of any changes through the
   LIveData, and updates the UI to match
   
#### LiveData
LiveData was mentioned a few times, so what is it? LiveData is a wrapper
class for data objects. It is observable from a View. Being observable
is a way to automatically call update UI logic, anytime the data is
changed. There's more to it than all that, but those are the very
basics. If they don't make sense now, they will after you use them.
   
### Basic MVVM Example
Inside of a Fragment such as ListFragment, get an instance of the
ViewModel needed. Here we are using a shortcut method, thanks to a KTX
library.

```kotlin
private val listViewModel by viewModels<ListViewModel>()
```

The ViewModel will have a Function or Object that triggers background
database and network requests, and immediately returns a LiveData Object
to be observed.

```kotlin
fun getAllEmployees(): LiveData<List<Employee>> {
    // Database and or network logic happens
    // return LiveData<List<Employee>>
}
```

Then, back in the Fragment, you can call to this method, and observe the
returned LiveData and update your UI with the contained data. When the
background database or network requests finish, they post their updates
to this Object, triggering a UI update.

```kotlin
listViewModel.getAllEmployees().observe(this) { employeeList ->
    employeeList?.let {
        recyclerViewAdapter.submitList(it)
        // other operations related to getting new data
    }
}
```

## Room Database
This app uses the Room Database library, provided by Google as part of
Android Jetpack. Room is a wrapper for Android's SQLite DB. Essentially,
it operates like Retrofit, but for database calls. It relies heavily on
annotations to generate code for you. Room also natively supports
LiveData.

Room consist of three main parts
- The Database Class
- Data Access Object Interfaces
- Annotated Data Classes

### Basic Room Example
Create a Data class to store in Room and add the **@Entity** and
**@PrimaryKey** annotations where needed.

```kotlin
@Entity(tableName = "employee_table")
data class Employee(@PrimaryKey val id: Int, val name: String)
```

Next, create a Data Access Object Interface or DAO. This is where you
will define all of your database CRUD actions. Note all the annotations
and that this is an interface, and you do not write any function bodies.
Room will write these for you.

```kotlin
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
```

Finally, we create our database class by extending RoomDatabase and
marking it with some more annotations.

```kotlin
@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class ExampleRoomDB : RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDAO
}
```

In the above code, you tell Room which classes are going to be stored in
the DB, what version the DB is on, and another flag that isn't important
now. Keep it false. We also define an abstract function that return the
DAO we created earlier. Do this for all DAOs in your app.

When using Room, we also need to make a slight change to our ViewModels.
If the ViewModel needs to access the Room Database, either directly or
through a repository, it will need to extend **AndroidViewModel**
instead of the normal **ViewModel**.

##### ViewModel Used WITHOUT Room
```kotlin
class DetailViewModel : ViewModel() {
    // ViewModel methods here
}
```
##### ViewModel Used WITH Room
```kotlin
class DetailViewModel(application: Application) : AndroidViewModel(application) {
    // ViewModel methods here
}
```

At this point, you can get an instance of your DB and get an instance of
the DAO, and start making calls to and storing data in your DB. These
calls could be made directly in the Fragment, but that would be mixing
components in MVVM(calling DB logic from the View). 

We could call the DAO methods from the ViewModel and expose the results
to the UI through that. This is an acceptable approach, and a relatively
good one. However, there is a better way.

## Repositories
Repositories, while not required and aren't an official component of
MVVM, they are considered a good practice. Especially when you have data
being stored locally and data available on a remote API. The repository
provides what is known as a **single source of truth**. More simply put,
when data is coming possibly coming from multiple locations, the
repository decides what is valid, what to show, where to call, and when
to make those calls.

### Basic Repository Example
Let's assume that our app has a Room Database completely set up,
Retrofit for API calls, and an AndroidViewModel as described in previous
sections. Create a new repository class. In this class, get an instance
of the DAO and RetroFit interfaces.

```kotlin
class EmployeeRepository private constructor(application: Application) {
    private val employeeDAO: EmployeeDAO = ExampleRoomDB.getDatabase(application).getEmployeeDao()
    private val employeeCalls = RetroFitInstance.getInstance().create(EmployeeEndpoints::class.java)
}
```

Suppose we want to show the entire list of Employees. We need to check
the API for current data, store this data, and show it to the user. But
what if there is an update to the data, or what if the network is down.
This is where the repository comes into play.

You will create a method that calls to the API **AND** to the local
database.

```kotlin
suspend fun getAllEmployeesLiveData(): LiveData<List<Employee>> {
        return employeeDAO.getAllEmployeesLiveData().also {
            try {
                val employeeList = employeeCalls.getEmployeeList()
                employeeList.forEach {
                    insertEmployee(it)
                }
            } catch (exception: Throwable) {
                Timber.e(exception)
            }
        }
    }
```

The data in the database will be shown to the user immediately, through
LiveData, while, in the background, a request to the API for new and
current data has been made. When that request comes back, each result is
inserted into the database. Since we returned a LiveData, any updates to
the Database will notify the LiveData, which will update itself with the
current information.

Finally, update our ViewModel to use our repository, instead of calling
to the API or to the database directly.

```kotlin
class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val employeeRepository = EmployeeRepository.getInstance(application)

    val employeeListLiveData: LiveData<List<Employee>> = liveData(Dispatchers.IO) {
        emitSource(employeeRepository.getAllEmployeesLiveData())
    }
}
```

Now your UI should update with the most current data, and also be able
to show data that was stored if there is no network.

If you follow the steps in this guide, you'll notice that once we set up
our Fragment, and subscribed to a LiveData object from a ViewModel, we
never had to change anything in our Fragment. It just kept on working.
This is what MVVM does for you. When you need to change a component, it
doesn't affect other components.

For example, it's a week before this app is deploying and we decided to
change our Database from a REST API, like this app does now, to
Firebase's Firestore. What would need to be changed? Firebase provides
it's own local backup, so we won't need Room anymore. Firebase also has
it's own calls to the API, so we can't use Retrofit calls either.
Thankfully, all of that is in one place, the repository. We just change
the functions in the repository to use Firebase, remove the unneeded
code, and everything else remains the same. As long as we keep returning
LiveData, the UI won't care where it came from, just as that it gets
there.

## Final Thoughts on MVVM
This README is meant to be a general summary of the MVVM concept and
give you an idea of how to implement it in your app. It is not a perfect
example. For instance, MVVM recommends the use of DataBinding or
ViewBinding and I did not use any of it in this app. This app is meant
to be a starting point. Any improvements and further refinement to the
design should be encouraged, however, deviations from the main design
pattern should be kept to a minimum whenever possible.

## Navigation
This app uses the Navigation Component from Jetpack. More can be read
about it from the [official documentation](https://developer.android.com/guide/navigation/navigation-getting-started) from Google, as well a
[simple tutorial](https://codelabs.developers.google.com/codelabs/android-navigation/#0)
to show you the basics.

This app does not use the SafeArgs plugin as it was meant to be very
simple. This plugin, described in the tutorial and documentation should
be used when possible.

## Splash Screens
This app shows an example on how to properly implement a Splash Screen.
This method is from an
[article](https://www.bignerdranch.com/blog/splash-screens-the-right-way/)
by BigNerdRanch. It is written in Java and from 2015, but still applies.
The Java translates directly to Kotlin and the rest stays basically the
same.

### Disclaimer
This app is meant to show architecture examples. It is **NOT** meant to
look good, or to show how to design a UI. There are some good practices
shown, such as using Material Theme and how to implement a proper splash
screen, but overall, **DO NOT MAKE YOUR APP LOOK LIKE THIS**.

This is a very basic example and is **NOT** meant to show how to do
everything, just a general structure to follow. For a more advanced
example of MVVM implemented in a larger app, reference the Unico Reports
Android App.

This app is **NOT** to be treated as the absolute only way to do things.
For instance, this app uses Room Database and Retrofit. If using
something else, such as Firebase, this pattern can still be followed. 
