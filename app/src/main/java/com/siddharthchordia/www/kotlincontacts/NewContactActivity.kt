package com.siddharthchordia.www.kotlincontacts

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.siddharthchordia.www.kotlincontacts.databinding.ActivityNewContactBinding
import com.siddharthchordia.www.kotlincontacts.model.Contact
import java.io.FileNotFoundException
import java.io.InputStream


class NewContactActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewContactBinding
    var imageUrl: String  = ""


    private val newContactActivityRequestCode = 1

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var editContact:Contact? = intent.getParcelableExtra("info")




        binding = DataBindingUtil.setContentView(this,R.layout.activity_new_contact)
        if(editContact!=null)
        {

            binding.editContact = editContact
            Glide
                .with(this)
                .load(Uri.parse(editContact.profileImageUrl))
                .transform(CenterCrop(), RoundedCorners(50))

                .into(binding.profileImageView)


        }
        binding.profileImageView.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
        binding.buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.nameentry.text)||TextUtils.isEmpty(binding.emailentry.text)||TextUtils.isEmpty(binding.phoneentry.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                if(editContact!=null) {

                    val sameId:Int = editContact!!.id
                    val url:String = editContact!!.profileImageUrl


                        editContact = Contact(
                            id = sameId,
                            name = binding.nameentry.text.toString(),
                            email = binding.emailentry.text.toString(),
                            phone = binding.phoneentry.text.toString(),
                            profileImageUrl = if(imageUrl.isEmpty())url else imageUrl
                        )


                    replyIntent.putExtra(EXTRA_CONTACT, editContact)
                }


                else {

                    val contact: Contact = Contact(
                        name = binding.nameentry.text.toString(),
                        email = binding.emailentry.text.toString(),
                        phone = binding.phoneentry.text.toString(),
                        profileImageUrl = imageUrl
                    )
                    replyIntent.putExtra(EXTRA_CONTACT, contact)
                }






                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                imageUrl = imageUri.toString()
                val imageStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                binding.profileImageView.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this@NewContactActivity, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this@NewContactActivity, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        const val EXTRA_CONTACT = "com.example.android.contactlistsql.CONTACT"
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1

    }




}