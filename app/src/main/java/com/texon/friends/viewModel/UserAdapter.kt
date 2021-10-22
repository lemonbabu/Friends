package com.texon.friends.viewModel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.texon.friends.R
import com.texon.friends.model.data.UserData
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList
import com.texon.friends.getAddress


class UserAdapter(var userList: ArrayList<UserData>, var onUserClickListener: OnUserClickListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>(),
    Filterable {

    var userFilterList = ArrayList<UserData>(userList)
    lateinit var con: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)
        con = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = "${userList[position].name.title} ${userList[position].name.first} ${userList[position].name.last}"

        val myLat = userList[position].location.coordinates.latitude.toDouble()
        val myLong = userList[position].location.coordinates.longitude.toDouble()
        val country = con.getAddress(myLat, myLong)
        if(country == null || country == "")
            holder.userCountry.text = "Not available"
        else
            holder.userCountry.text = country

        Picasso.get()
            .load(userList[position].picture.large)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.userImage)

        ViewCompat.setTransitionName(holder.userImage, "${userList[position].name.first} ${userList[position].name.last}")

        holder.itemView.setOnClickListener {
            onUserClickListener.onUserClickListener(userList[position], holder.userImage)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.txtName)
        val userCountry: TextView = itemView.findViewById(R.id.txtCountry)
        val userImage: ImageView = itemView.findViewById<CircleImageView>(R.id.imgUser)
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<UserData>()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(userFilterList)
            } else {
                val filteredStrings: String = constraint.toString().lowercase(Locale.getDefault()).trim()

                for (results: UserData in userFilterList) {
                    val fullName = "${results.name.first} ${results.name.last}"
                    if (fullName.lowercase(Locale.getDefault()).contains(filteredStrings)) {
                        filteredList.add(results)
                    }
                }
            }
            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            userList.clear()
            userList.addAll(results?.values as ArrayList<UserData>)
            notifyDataSetChanged()
        }

    }

    interface OnUserClickListener{
        fun onUserClickListener(results: UserData, sharedImageView: ImageView)
    }

}