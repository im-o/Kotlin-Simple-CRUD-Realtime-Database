package com.stimednp.crudrealtimedb.realtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.stimednp.crudrealtimedb.R
import com.stimednp.crudrealtimedb.model.Users
import com.stimednp.crudrealtimedb.utils.Const
import com.stimednp.crudrealtimedb.utils.Const.PATH_NAME
import kotlinx.android.synthetic.main.activity_rdb_user.*

class RdbUserActivity : AppCompatActivity() {
    private val mFirebase = FirebaseDatabase.getInstance()
    private val myref = mFirebase.getReference(Const.PATH_COLLECTION)
    private val query = myref.orderByChild(PATH_NAME)

    private lateinit var adapter: FirebaseRecyclerAdapter<Users, RdbUserAdapter.RdbUserViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rdb_user)
        initView()
        setupAdapter()

        fab_firedb.setOnClickListener {
            startActivity(Intent(this, AddEditRdbActivity::class.java).apply {
                putExtra(AddEditRdbActivity.REQ_EDIT, false)
            })
        }
    }

    private fun initView() {
        supportActionBar?.title = "Simple CRUD Realtime Database"
        rv_firedb.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RdbUserActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupAdapter() {
        val option = FirebaseRecyclerOptions.Builder<Users>()
            .setQuery(query, Users::class.java)
            .build()

        adapter = RdbUserAdapter(this, myref, option)
        adapter.notifyDataSetChanged()
        rv_firedb.adapter = adapter
    }

    override fun onStart() {
        adapter.startListening()
        super.onStart()
    }

    override fun onStop() {
        adapter.stopListening()
        super.onStop()
    }
}
