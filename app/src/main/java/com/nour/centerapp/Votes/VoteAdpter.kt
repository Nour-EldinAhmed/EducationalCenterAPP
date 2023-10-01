package com.nour.centerapp.Votes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.nour.centerapp.Note.NoteItems
import com.nour.centerapp.R
import kotlinx.android.synthetic.main.note_item.view.*
import kotlinx.android.synthetic.main.vote_item.view.*

class VoteAdpter (context: Context, votes: ArrayList<ListVotes>?) :
        ArrayAdapter<ListVotes>(context, 0, votes!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var count=1;
        val view = LayoutInflater.from(context).inflate(R.layout.vote_item, null, false)

        val votesItems = getItem(position)

        view.txt_vote.text = votesItems!!.vote
        view.txt_date.text = votesItems.timestamp.toString()

        view.txt_vote.setOnClickListener {
         view.txt_count.text= count++.toString()
        }
        return view
    }
}