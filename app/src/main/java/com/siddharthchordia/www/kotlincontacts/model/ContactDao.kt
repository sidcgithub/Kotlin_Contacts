package com.siddharthchordia.www.kotlincontacts.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ContactDao {


    @Query("SELECT * from contact_table ORDER BY name ASC")
    fun getAlphabetizedContacts(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Query("DELETE FROM contact_table  WHERE id=:contactId")
    suspend fun deleteContact(contactId:Int)
}