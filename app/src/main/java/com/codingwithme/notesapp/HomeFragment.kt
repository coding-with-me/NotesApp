package com.codingwithme.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codingwithme.notesapp.adapter.NotesAdapter
import com.codingwithme.notesapp.database.NotesDatabase
import com.codingwithme.notesapp.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    var notesAdapter: NotesAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.setHasFixedSize(true)

        recycler_view.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                recycler_view.adapter = NotesAdapter(notes)
            }
        }

        fabBtnCreateNote.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(),false)
        }
    }


    fun replaceFragment(fragment:Fragment, istransition:Boolean){
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        if (istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }


}