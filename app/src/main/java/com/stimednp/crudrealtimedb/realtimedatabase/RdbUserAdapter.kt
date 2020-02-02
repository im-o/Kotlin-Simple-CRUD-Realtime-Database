package com.stimednp.crudrealtimedb.realtimedatabase

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.stimednp.crudrealtimedb.R
import com.stimednp.crudrealtimedb.model.Users
import kotlinx.android.synthetic.main.item_list_user.view.*

/**
 * Created by rivaldy on 2/2/2020.
 * Find me on my lol Github :D -> https://github.com/im-o
 */

class RdbUserAdapter(private val context: Context, private val myref: DatabaseReference, options: FirebaseRecyclerOptions<Users>) :
    FirebaseRecyclerAdapter<Users, RdbUserAdapter.RdbUserViewHolder>(options) {

    class RdbUserViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(users: Users) {
            view.apply {
                val name = "Nama   : ${users.strName}"
                val addr = "Alamat : ${users.strAddress}"
                val age = "Umur    : ${users.intAge.toString()}"

                tv_name.text = name
                tv_adress.text = addr
                tv_age.text = age
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RdbUserViewHolder {
        return RdbUserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false))
    }

    override fun onBindViewHolder(holder: RdbUserViewHolder, position: Int, users: Users) {
        holder.bindItem(users)
        holder.itemView.setOnClickListener {
            showDialogMenu(users)
        }
    }

    private fun showDialogMenu(users: Users) {
        //dialog popup edit hapus
        val builder = AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val option = arrayOf("Edit", "Hapus")
        builder.setItems(option) { dialog, which ->
            when (which) {
                0 -> context.startActivity(Intent(context, AddEditRdbActivity::class.java).apply {
                    putExtra(AddEditRdbActivity.REQ_EDIT, true)
                    putExtra(AddEditRdbActivity.EXTRA_DATA, users)
                })
                1 -> showDialogDel(users)
            }
        }

        builder.create().show()
    }

    private fun showDialogDel(users: Users) {
        val builder = AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
            .setTitle("Hapus Data ?")
            .setMessage("Yakin Mau Hapus ${users.strName} ?")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                deleteById(users.strId)
            }.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }

    private fun deleteById(strId: String) {
        myref.child(strId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Success Delete data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failute Delete data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}