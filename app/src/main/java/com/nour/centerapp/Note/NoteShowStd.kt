package com.nour.centerapp.Note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.database.*
import com.nour.centerapp.R
import kotlinx.android.synthetic.main.activity_note_kotlin.*
import kotlinx.android.synthetic.main.activity_note_show_std.*

class NoteShowStd : AppCompatActivity() {

    var reference: DatabaseReference? = null
    var noteList: ArrayList<NoteItems>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_show_std)

        val database = FirebaseDatabase.getInstance()
        reference = database.getReference("Notes")
        noteList = ArrayList()

        list_item_Std.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            var myNote = noteList?.get(position)!!

            var NoteIntent = Intent(this, NoteActivityShowStd::class.java)

            NoteIntent.putExtra("Title_Note", myNote.title)
            NoteIntent.putExtra("Note_content", myNote.note)
            startActivity(NoteIntent)
        }
    }
    override fun onStart() {
        super.onStart()
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(applicationContext, "Error!!", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                noteList!!.clear()
                for (i in snapshot.children) {
                    val note = i.getValue(NoteItems::class.java)
                    noteList!!.add(0, note!!)

                }

                val noteadapter = NoteAdapter(applicationContext, noteList)
                list_item_Std.adapter = noteadapter
            }
        })
    }

}