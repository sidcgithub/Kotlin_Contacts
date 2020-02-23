package com.siddharthchordia.www.kotlincontacts.Repository

import androidx.lifecycle.LiveData
import com.siddharthchordia.www.kotlincontacts.model.Contact
import com.siddharthchordia.www.kotlincontacts.model.ContactDao

class ContactRepository(private val contactDao: ContactDao) {


    val allContacts: LiveData<List<Contact>> = contactDao.getAlphabetizedContacts()

    suspend fun insert(contact: Contact)
    {
        contactDao.insert(contact)

    }

    suspend fun  deleteContact(contact: Contact)
    {
        contactDao.deleteContact(contact.id)
    }
}