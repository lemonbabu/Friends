package com.texon.friends.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.texon.friends.R
import com.texon.friends.gone
import com.texon.friends.model.UserApiResponse
import com.texon.friends.model.api.UserApiModel
import com.texon.friends.model.data.UserData
import com.texon.friends.model.api.UserInterface
import com.texon.friends.viewModel.UserAdapter
import com.texon.friends.visible
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {

    private var backPressedTime: Long = 0
    private lateinit var toast: Toast

    private val apiClient: UserInterface by lazy { UserApiModel.getApiClient() }
    private lateinit var adapter: UserAdapter

    companion object {
        const val EXTRA_RESULT_ITEM = "extra_result_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView.layoutManager = GridLayoutManager(this@MainActivity,2)
        recycleView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        progressBar.visible()
        recycleView.gone()
        getUsers()

        swfRefresh.setOnRefreshListener { getUsers() }
    }

    // API calling
    private fun getUsers() {
        apiClient.getUsers(10).enqueue(object : Callback<UserApiResponse> {
            override fun onFailure(call: Call<UserApiResponse>, t: Throwable) {
                Log.d("MainActivity", t.message.toString())
            }

            override fun onResponse(call: Call<UserApiResponse>, response: Response<UserApiResponse>) {
                if (response.isSuccessful) {
                    adapter = UserAdapter(response.body()?.userList!!, this@MainActivity)
                    recycleView.adapter = adapter
                    progressBar.gone()
                    recycleView.visible()
                    swfRefresh.isRefreshing = false
                }
            }
        })
    }

    // Item onclick listener
    override fun onUserClickListener(results: UserData, sharedImageView: ImageView) {
        val intent = Intent(this, UserDetails::class.java)
        intent.putExtra(EXTRA_RESULT_ITEM, results)

        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            sharedImageView,
            ViewCompat.getTransitionName(sharedImageView)!!
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    // Clear local data
    private fun deleteAppData() {
        try {
            Runtime.getRuntime().exec("pm clear $packageName")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Back or exit confirmation
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            toast.cancel()
            deleteAppData()
            super.onBackPressed()
            return
        } else {
            toast = Toast.makeText(this, "Tap 1 more time to exit", Toast.LENGTH_SHORT)
            toast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

}

