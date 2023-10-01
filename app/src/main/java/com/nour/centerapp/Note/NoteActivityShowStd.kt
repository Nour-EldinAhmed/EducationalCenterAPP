package com.nour.centerapp.Note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nour.centerapp.R
import kotlinx.android.synthetic.main.activity_note_show.*
import kotlinx.android.synthetic.main.activity_note_show_std2.*

class NoteActivityShowStd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_show_std2)
        var titlenote=intent.extras!!.getString("Title_Note")
        var noteshow=intent.extras!!.getString("Note_content")

        TitleNoteShowStd.text=titlenote
        NoteShowStd.text=noteshow
    }
}