package com.siddharthchordia.www.kotlincontacts.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcelize
@Entity(tableName = "contact_table")
public data class Contact (  @PrimaryKey(autoGenerate = true)
                             var id:Int = 0,val name: String, val phone: String, val email:String,val profileImageUrl: String):Parcelable
