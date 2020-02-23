package com.siddharthchordia.www.kotlincontacts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.siddharthchordia.www.kotlincontacts.model.Contact
import com.siddharthchordia.www.kotlincontacts.RecyclerViews.ContactListAdapter
import com.siddharthchordia.www.kotlincontacts.RecyclerViews.SwipeToDeleteCallback
import com.siddharthchordia.www.kotlincontacts.ViewModel.ContactViewModel
import com.siddharthchordia.www.kotlincontacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val newContactActivityRequestCode = 1
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        val adapter = ContactListAdapter(this)

        val swipeHandler = object : SwipeToDeleteCallback(this,adapter) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        val itemTouchHelper  = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)
        with(binding)
        {

            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)


            fab.setOnClickListener {
                val intent = Intent(this@MainActivity, NewContactActivity::class.java)
                startActivityForResult(intent, newContactActivityRequestCode)
            }
        }



        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.allContacts.observe(this, Observer { contacts ->
            // Update the cached copy of the contacts in the adapter.
            contacts?.let { adapter.setContacts(it) }
        })

        contactViewModel.invalidContact.observe(this, Observer { snackInsert ->
            if(snackInsert)
            {
                val mySnackbar = Snackbar.make(binding.root,
                    "Please retry. Invalid entry!", Snackbar.LENGTH_LONG)
                mySnackbar.show()
            }
            else
            {

            }
        })



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newContactActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val contact:Contact? = data?.getParcelableExtra(NewContactActivity.EXTRA_CONTACT)

            if(contact?.name!=null&&contact?.phone!=null&&contact?.email!=null&&contact?.profileImageUrl!=null)
            {

                contactViewModel.insertContact(contact)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Not saved",
                Toast.LENGTH_LONG).show()
        }
    }
}
