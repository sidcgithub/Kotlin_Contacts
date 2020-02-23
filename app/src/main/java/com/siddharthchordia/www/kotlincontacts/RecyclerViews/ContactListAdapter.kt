package com.siddharthchordia.www.kotlincontacts.RecyclerViews

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.siddharthchordia.www.kotlincontacts.MainActivity
import com.siddharthchordia.www.kotlincontacts.ViewContactActivity
import com.siddharthchordia.www.kotlincontacts.ViewModel.ContactViewModel
import com.siddharthchordia.www.kotlincontacts.databinding.ContactItemBinding
import com.siddharthchordia.www.kotlincontacts.model.Contact


class ContactListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var contacts = emptyList<Contact>() // Cached copy of contacts
    private lateinit var contactViewModel: ContactViewModel

    inner class ContactViewHolder(binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val item = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            ContactItemBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val current: Contact? = contacts[position]
        holder.item.contact = current
        holder.item.executePendingBindings()
        Log.d("Contact List profile", current?.profileImageUrl)

        Glide
            .with(holder.item.root)
            .load(Uri.parse(current?.profileImageUrl))
            .transform(CenterCrop(), RoundedCorners(50))

            .into(holder.item.profileImageInList)
        holder.itemView.setOnClickListener {

            val intent: Intent = Intent(it.context, ViewContactActivity::class.java)
            intent.putExtra("info", current)
            (it.context as MainActivity).startActivityForResult(intent, 1)

        }
    }

    internal fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun getItemCount() = contacts.size

    fun itemDelete(index: Int, context: Context) {


        contactViewModel =
            ViewModelProvider(context as MainActivity).get(ContactViewModel::class.java)
        contactViewModel.deleteContact(contacts.get(index))
        contacts.toMutableList().removeAt(index)
        notifyDataSetChanged()


    }
}