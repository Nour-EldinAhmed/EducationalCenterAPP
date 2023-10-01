package com.nour.centerapp.Votes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import com.nour.centerapp.Note.NoteAdapter
import com.nour.centerapp.Note.NoteItems
import com.nour.centerapp.R
import kotlinx.android.synthetic.main.activity_note_kotlin.*
import kotlinx.android.synthetic.main.activity_vote_kotlin.*
import kotlinx.android.synthetic.main.layout_vote.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VoteActivityKotlin : AppCompatActivity() {

    var reference: DatabaseReference? = null
    var votelist: ArrayList<ListVotes>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_kotlin)

        val database= FirebaseDatabase.getInstance()
        votelist=ArrayList()
        reference = database.getReference("Votes")


        add_vote.setOnClickListener {
            ShowDialog()
        }

    }

    override fun onStart() {
        super.onStart()
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(applicationContext, "Error!!", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                votelist!!.clear()
                for (i in snapshot.children) {
                    val Vote_Items = i.getValue(ListVotes::class.java)
                    votelist!!.add(0, Vote_Items!!)

                }

                val voteadapter = VoteAdpter(applicationContext, votelist)
                list_votes.adapter = voteadapter
            }
        })


    }

    fun  ShowDialog(){
        val alertbuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.layout_vote, null)
        alertbuilder.setView(view)
        val alertdialog = alertbuilder.create()
        alertdialog.show()



        view.btn_add_Vote.setOnClickListener {
            val titlevote=view.edit_vote.text.toString()

            if(titlevote.isNotEmpty())
            {
              var id = reference!!.push().key
              val vote= id?.let { it1 -> ListVotes(it1,titlevote,ShowDate()) }
                votelist?.add(vote!!)
                id?.let { it1 -> reference!!.child(it1).setValue(vote) }
                alertdialog.dismiss()
            }else{
                Toast.makeText(this,"Vote is Empty",Toast.LENGTH_LONG).show()
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