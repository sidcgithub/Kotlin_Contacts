package com.siddharthchordia.www.kotlincontacts.RecyclerViews

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.siddharthchordia.www.kotlincontacts.model.Contact


@BindingAdapter("name")
fun TextView.setName(contact: Contact)
{
    text = contact.name
}

@BindingAdapter("phone")
fun TextView.setPhone(contact: Contact)
{
    text = contact.phone
}
@BindingAdapter("email")
fun TextView.setEmail(contact: Contact)
{
    text = contact.email
}