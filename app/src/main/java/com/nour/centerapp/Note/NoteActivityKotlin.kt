package com.nour.centerapp.Note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import com.nour.centerapp.R
import kotlinx.android.synthetic.main.activity_note_kotlin.*
import kotlinx.android.synthetic.main.add_note.view.*
import kotlinx.android.synthetic.main.delete_updatenote.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteActivityKotlin : AppCompatActivity() {

    var reference: DatabaseReference? = null
    var noteList: ArrayList<NoteItems>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_kotlin)
        val database = FirebaseDatabase.getInstance()
        reference = database.getReference("Notes")
        noteList = ArrayList()
        add_note.setOnClickListener {
            ShowDialog()
        }


        list_notes.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            var myNote = noteList?.get(position)!!

            var NoteIntent = Intent(this, NoteActivityShow::class.java)

            NoteIntent.putExtra("Title_key", myNote.title)
            NoteIntent.putExtra("Note_key", myNote.note)
            startActivity(NoteIntent)


        }

        list_notes.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->

            val alertbuilder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.delete_updatenote, null)
            alertbuilder.setView(view)
            val alertdialog = alertbuilder.create()
            alertdialog.show()

            var MyNotes = noteList?.get(position)!!
            view.TitleNote_delete_update.setText(MyNotes.title)
            view.ContentNote_delete_update.setText(MyNotes.note)

            view.btn_Update_note.setOnClickListener {

                var childReference = MyNotes.id?.let { it1 -> reference?.child(it1) }


                var afterupdate = NoteItems(MyNotes.id!!, view.TitleNote_delete_update.text.toString(),
                        view.ContentNote_delete_update.text.toString()
                        , ShowDate())


                childReference?.setValue(afterupdate)
                alertdialog.dismiss()


            }


            view.btn_delete_note.setOnClickListener {

                MyNotes.id?.let { it1 -> reference?.child(it1)!!.removeValue() }
                alertdialog.dismiss()

            }

            false
        }


    }


    // عشان اجيب البيانات من اي سرفير يفضل استخدم هذع الدالة
    override fun onStart() {
        super.onStart()
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

                makeText(applicationContext, "Error!!", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                noteList!!.clear()
                for (i in snapshot.children) {
                    val note = i.getValue(NoteItems::class.java)
                    noteList!!.add(0, note!!)

                }

                val noteadapter = NoteAdapter(applicationContext, noteList)
                list_notes.adapter = noteadapter
            }
        })


    }

    fun ShowDialog() {
        val alertbuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.add_note, null)
        alertbuilder.setView(view)
        val alertdialog = alertbuilder.create()
        alertdialog.show()
        view.btn_add_Note.setOnClickListener {
            val title = view.edit_title.text.toString()
            val note = view.edit_note.text.toString()

            if (title.isNotEmpty() && note.isNotEmpty()) {
                var id = reference!!.push().key
                var noteitem = id?.let { it1 -> NoteItems(it1, title, note, ShowDate()) }
                id?.let { it1 -> reference!!.child(it1).setValue(noteitem) }
                alertdialog.dismiss()
            } else {
                makeText(this, "Note is Empty ", Toast.LENGTH_LONG).show()
            }

        }

    }

    fun ShowDate(): String {

        val calendar = Calendar.getInstance()
        val timeformat = SimpleDateFormat("EEEE hh:mm:ss a")
        val startdate = timeformat.format(calendar.time)
        return startdate


    }

}