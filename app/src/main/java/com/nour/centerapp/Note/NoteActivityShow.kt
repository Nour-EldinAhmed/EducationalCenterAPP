package com.nour.centerapp.Note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nour.centerapp.R
import kotlinx.android.synthetic.main.activity_note_show.*

class NoteActivityShow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_show)

        var titlenote=intent.extras!!.getString("Title_key")
        var noteshow=intent.extras!!.getString("Note_key")

        TitleNoteShow.text=titlenote
        NoteShow.text=noteshow

    }
}