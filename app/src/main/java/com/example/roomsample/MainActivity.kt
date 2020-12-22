package com.example.roomsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.roomsample.recyclerview.Person
import com.example.roomsample.recyclerview.RvAdapter
import com.example.roomsample.room.Person
import com.example.roomsample.room.PersonDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.view.ed_address
import kotlinx.android.synthetic.main.dialog.view.ed_name
import kotlinx.android.synthetic.main.dialog.view.ed_phone
import kotlinx.android.synthetic.main.dialog_update.view.*

class MainActivity : AppCompatActivity(), RvAdapter.OnItemClickListener {

    private val myAdapter = RvAdapter(this)

    private lateinit var db: PersonDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = PersonDatabase.getInstance(this)

        recycler_view.adapter = myAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        val dialogItem = LayoutInflater.from(this).inflate(R.layout.dialog, null)
        val confirmButton = dialogItem.findViewById<Button>(R.id.btn_confirm)   //新增
        val deleteButton = dialogItem.findViewById<Button>(R.id.btn_delete)     //刪除
        val cancelButton = dialogItem.findViewById<Button>(R.id.btn_cancel)

        fab.setOnClickListener {
            floatingButton(dialogItem, confirmButton, cancelButton, deleteButton)
        }

        notifyAdapter()
    }

    private fun floatingButton(
        dialogItem: View,
        confirmButton: Button,
        cancelButton: Button,
        deleteButton: Button
    ) {
        val dialogBuilder = AlertDialog.Builder(this@MainActivity)
        val dialog = dialogBuilder
            .setTitle("新增/查詢")
            .setView(dialogItem)
            .setOnDismissListener {
                (dialogItem.parent as ViewGroup).removeView(dialogItem)
//                    closeFabMenu()
            }
            .show()

        confirmButton.setOnClickListener {
            val item = Person(
                dialogItem.ed_name.text.toString(),
                dialogItem.ed_phone.text.toString(),
                dialogItem.ed_address.text.toString()
            )
            if (dialogItem.ed_name.text.isNotEmpty() && dialogItem.ed_phone.text.isNotEmpty()) {
                db.personDataDao.insert(item)
                notifyAdapter()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "請輸入姓名,電話", Toast.LENGTH_SHORT).show()
            }

            dialogItem.ed_name.text.clear()
            dialogItem.ed_phone.text.clear()
            dialogItem.ed_address.text.clear()
        }

        deleteButton.setOnClickListener {
            db.personDataDao.clear()
            notifyAdapter()
            dialog.dismiss()
        }

//        updateButton.setOnClickListener {
//            val item = Person(
//                dialogItem.ed_name.text.toString(),
//                dialogItem.ed_phone.text.toString(),
//                dialogItem.ed_address.text.toString()
//            )
//            db.personDataDao.update(item)
//        }

//            取消鍵,將dialog關閉,內容清除
        cancelButton.setOnClickListener {
            dialogItem.ed_name.text.clear()
            dialogItem.ed_phone.text.clear()
            dialogItem.ed_address.text.clear()
            dialog.dismiss()
        }

    }

//    點擊修改
    override fun onItemClick(person: Person) {
        val dialogUpdate = LayoutInflater.from(this).inflate(R.layout.dialog_update, null)
        val dialogBuilder = AlertDialog.Builder(this@MainActivity)
        val cancelButton = dialogUpdate.findViewById<Button>(R.id.btn_cancel)

        val dialog = dialogBuilder
            .setTitle("修改項目")
            .setView(dialogUpdate)
//      設定當dialog被點掉時,remove原放進來的view(dialogItem),否則再次點fab時會丟exception
            .setOnDismissListener {
                (dialogUpdate.parent as ViewGroup).removeView(dialogUpdate)
            }
            .show()

        dialogUpdate.ed_name.setText(person.name, TextView.BufferType.EDITABLE)
        dialogUpdate.ed_phone.setText(person.phone, TextView.BufferType.EDITABLE)
        dialogUpdate.ed_address.setText(person.address, TextView.BufferType.EDITABLE)

        dialogUpdate.btn_update.setOnClickListener {
            val updateItem = Person(
                dialogUpdate.ed_name.text.toString(),
                dialogUpdate.ed_phone.text.toString(),
                dialogUpdate.ed_address.text.toString(),
                person.personId
            )
            db.personDataDao.update(updateItem)
            notifyAdapter()
            dialog.dismiss()
        }
    }

    private fun notifyAdapter() {
        val itemList = db.personDataDao.getAll()
        myAdapter.update(itemList)
    }
}

