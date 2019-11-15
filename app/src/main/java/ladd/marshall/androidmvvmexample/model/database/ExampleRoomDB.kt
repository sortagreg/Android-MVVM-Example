package ladd.marshall.androidmvvmexample.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ladd.marshall.androidmvvmexample.model.database.daos.EmployeeDAO
import ladd.marshall.androidmvvmexample.model.models.Employee
import ladd.marshall.androidmvvmexample.utils.ROOM_DB_NAME

@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class ExampleRoomDB : RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDAO

    companion object {
        fun getDatabase(context: Context): ExampleRoomDB = Room.databaseBuilder(
            context.applicationContext,
            ExampleRoomDB::class.java,
            ROOM_DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
