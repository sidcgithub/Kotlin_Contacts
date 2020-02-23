package com.siddharthchordia.www.kotlincontacts

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.siddharthchordia.www.kotlincontacts.databinding.ActivityViewContactBinding
import com.siddharthchordia.www.kotlincontacts.model.Contact
import org.parceler.Parcels

class ViewContactActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val viewContact:Contact? = intent.getParcelableExtra("info")
//        Toast.makeText(this,"${viewContact?.name} ${viewContact?.email} ${viewContact?.phone}",Toast.LENGTH_LONG).show()

        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_contact)

        Glide
            .with(this)
            .load(Uri.parse(viewContact?.profileImageUrl))
            .transform(CenterCrop(), RoundedCorners(50))

            .into(binding.displayProfileImage)
        binding.displayContact = viewContact
        val intent: Intent = Intent(this, NewContactActivity::class.java)
        binding.editDisplayContact.setOnClickListener {

            intent.putExtra("info", viewContact)
            intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)

            startActivity(intent)
            finish()

        }


    }
}
