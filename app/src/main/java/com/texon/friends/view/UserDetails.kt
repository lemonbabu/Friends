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

class UserDetails : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        supportStartPostponedEnterTransition()

        val results: UserData =
            intent.extras?.getSerializable(MainActivity.EXTRA_RESULT_ITEM) as UserData

        username_detail.text = "${results.name.title} ${results.name.first} ${results.name.last}"
        email_detail.text = results.email
        address_detail.text = "${results.location.city} / ${results.location.state}"

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

        supportActionBar?.title = "${results.name.title} ${results.name.last}"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

