# Android App Architecture in The App Factory
### The MVVM Design Pattern

## Introduction
This project is to provide a simple example app for Android Developers
at The App Factory to reference when starting a new app, or adding new
features to an existing app.

The architecture pattern shown here and to be used in apps, whenever
possible, is Model-View-ViewModel, or MVVM. This is the pattern
recommended and used by Google.

### Features
- Connects to a remote database
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
   
### LiveData
LiveData was mentioned a few times, so what is it? LiveData is a wrapper
class for data objects. It is observable from a View. Being observable
is a way to automatically call update UI logic, anytime the data is
changed. There's more to it than all that, but those are the very
basics. If they don't make sense now, they will after you use them.
   
### Basic Example
Inside of a Fragment such as ListFragment, get an instance of the
ViewModel needed

```kotlin
private val viewModel by viewModels<ListViewModel>()
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
viewModel.getAllEmployees().observe(this, Observer { employeeList ->
    employeeList?.let {
        recyclerViewAdapter.submitList(it)
    }
})
```

## Navigation

## Styles & Themes

## Splash Screens



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
