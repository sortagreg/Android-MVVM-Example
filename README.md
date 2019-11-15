# Android App Architecture in The App Factory
### The MVVM Design Pattern

## Introduction
This project is to provide a simple example app for Android Developers
at The App Factory who are starting a new app, or adding new features to
reference.

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
Activities, Fragments, and Lifecycle.

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

## How to use this guide

## MVVM
### What is it?
MVVM is a flexible guide and set of libraries used to standardize an
app's architecture. What this means is that your code is split into
distinct parts that hold specific pieces of code in specific areas.
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