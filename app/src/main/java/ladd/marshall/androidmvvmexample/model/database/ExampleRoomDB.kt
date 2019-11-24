package ladd.marshall.androidmvvmexample.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ladd.marshall.androidmvvmexample.model.database.daos.EmployeeDAO
import ladd.marshall.androidmvvmexample.model.models.Employee
import ladd.marshall.androidmvvmexample.utils.ROOM_DB_NAME

/**
 * Room Database class. Refer to README for more information
 */
@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class ExampleRoomDB : RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDAO

    /**
     *  Creates a way to ensure that the database accessed in different locations is the same
     *  instance. Also known as a Singleton pattern. Further explained in the Employee Repository.
     */
    companion object {
        private var INSTANCE: ExampleRoomDB? = null

        fun getDatabase(context: Context) = INSTANCE ?: kotlin.run {
            Room.databaseBuilder(
                context.applicationContext,
                ExampleRoomDB::class.java,
                ROOM_DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
