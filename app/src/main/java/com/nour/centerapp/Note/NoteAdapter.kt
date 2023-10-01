package com.nour.centerapp.Note

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.nour.centerapp.R
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(context: Context, notes: ArrayList<NoteItems>?) :
        ArrayAdapter<NoteItems>(context, 0, notes!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = LayoutInflater.from(context).inflate(R.layout.note_item, null, false)

        val note = getItem(position)
        view.title_note.text = note!!.title
        view.date_note.text = note.timestamp.toString()
        return view
    }
}