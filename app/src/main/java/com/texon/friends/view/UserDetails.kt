package com.texon.friends.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.texon.friends.R
import com.texon.friends.model.data.UserData
import kotlinx.android.synthetic.main.activity_user_details.*
import java.lang.Exception
import com.texon.friends.getAddress


class UserDetails : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        supportStartPostponedEnterTransition()

        // Getting Intent extra data from main activity
        val results: UserData = intent.extras?.getSerializable(MainActivity.resultItem) as UserData
        // Data set on text & image view
        txtUserName.text = "${results.name.title} ${results.name.first} ${results.name.last}"
        txtUserEmail.text = results.email
        txtUserPhone.text = "${results.phone} / ${results.cell}"

        // country getting from latitude and longitude
        val myLat = results.location.coordinates.latitude.toDouble()
        val myLong = results.location.coordinates.longitude.toDouble()
        txtUserAddress.text = "${results.location.city} / ${results.location.state}, ${this.getAddress(myLat, myLong)}"

        // image loading function
        Picasso.get()
            .load(results.picture.large)
            .into(circleImageView_detail, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }
                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }
            })

        // Title bar customizations
        supportActionBar?.title = "${results.name.title} ${results.name.last}"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}

