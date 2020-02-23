package com.siddharthchordia.www.kotlincontacts.ViewModel

import android.app.Application
import android.service.autofill.RegexValidator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.siddharthchordia.www.kotlincontacts.model.Contact
import com.siddharthchordia.www.kotlincontacts.model.ContactRoomDatabase
import com.siddharthchordia.www.kotlincontacts.Repository.ContactRepository
import kotlinx.coroutines.launch

public class ContactViewModel(application: Application): AndroidViewModel(application) {

    private val repository:ContactRepository
    val allContacts: LiveData<List<Contact>>
    val invalidContact  = MediatorLiveData<Boolean>()

    init {

        val contactDao= ContactRoomDatabase.getDatabase(application,viewModelScope).contactDao()
        repository = ContactRepository(contactDao)
        allContacts = repository.allContacts
        invalidContact.value = false


    }
    fun insertContact(contact:Contact) = viewModelScope.launch {
        

        if(android.util.Patterns.PHONE.matcher(contact.phone.toString()).matches()&&android.util.Patterns.EMAIL_ADDRESS.matcher(contact.email.toString()).matches()) {
            repository.insert(contact)
            invalidContact.value = false
        }
        else
        {
            invalidContact.value = true;
        }

    }

    fun deleteContact(contact:Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }

}