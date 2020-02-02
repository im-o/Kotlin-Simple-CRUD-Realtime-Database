package com.stimednp.crudrealtimedb.realtimedatabase

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.stimednp.crudrealtimedb.R
import com.stimednp.crudrealtimedb.model.Users
import com.stimednp.crudrealtimedb.utils.Const
import kotlinx.android.synthetic.main.activity_add_edit_rdb.*

class AddEditRdbActivity : AppCompatActivity() {
    companion object {
        //key untuk intent data
        const val EXTRA_DATA = "extra_data"
        const val REQ_EDIT = "req_edit"
    }

    private var isEdit = false
    private var users: Users? = null

    private val mFirebaseDatabase = FirebaseDatabase.getInstance()
    private val myref = mFirebaseDatabase.getReference(Const.PATH_COLLECTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_rdb)
        isEdit = intent.getBooleanExtra(REQ_EDIT, false)
        users = intent.getParcelableExtra(EXTRA_DATA)

        btn_save.setOnClickListener {
            saveData()
        }
        initView()
    }

    private fun initView() {
        if (isEdit) {
            btn_save.text = getString(R.string.update)
            ti_name.text = Editable.Factory.getInstance().newEditable(users?.strName)
            ti_address.text = Editable.Factory.getInstance().newEditable(users?.strAddress)
            ti_age.text = Editable.Factory.getInstance().newEditable(users?.intAge.toString())
        }
    }

    private fun saveData() {
        setData(users?.strId)
    }

    private fun setData(strId: String?) {
        createUser(strId)
    }

    private fun createUser(strId: String?) {
        val newId = strId ?: myref.push().key.toString() // if id != null will be update data, if id == null will be creted new id and add data
        val name = ti_name.text.toString()
        val address = ti_address.text.toString()
        val age = ti_age.text.toString()

        val user = Users(newId, name, address, age.toInt())

        myref.child(newId).setValue(user)
            .addOnSuccessListener {
                if (isEdit) Toast.makeText(this, "Success update data", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "Success add data", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failure add data", Toast.LENGTH_SHORT).show()
            }
    }
}
