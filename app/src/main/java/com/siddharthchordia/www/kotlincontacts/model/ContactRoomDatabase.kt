package com.siddharthchordia.www.kotlincontacts.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
public abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    private class ContactDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

    }


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ContactRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactRoomDatabase::class.java,
                    "contact_database"
                ).addCallback(ContactDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}