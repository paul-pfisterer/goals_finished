package com.sea.goals

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_create_weekly.view.*

/**
 * A simple [Fragment] subclass.
 */
class WeeklyGoalFragment : Fragment() {
    lateinit var navBack: Button
    lateinit var navFor: Button
    lateinit var submitButton: Button
    lateinit var unit: TextView
    lateinit var value: TextView
    lateinit var daysPerWeekView: TextView
    lateinit var listener: FragmentWeeklyGoalListener

    /**
     * Interface dass vom Listner, also von Create Activity implementiert werden muss
     */
    interface FragmentWeeklyGoalListener {
        fun onSubmitWeeklyGoalSend(value: String, unit: String, daysPerWeek: Int);
        fun onChangeFrag(forward: Boolean, index: Int)
    }

    /**
     * Beim Erstellen des Fragments, wird eine View gewählt und Felder gefüllt
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_weekly, container, false)
        //Speichern von Views in Veriablen
        navBack = view.arrow_backward
        navFor = view.arrow_forward
        value = view.createValue
        unit = view.createUnit
        submitButton = view.createButton
        daysPerWeekView = view.daysPerWeek
        //Events für bestimmte Views
        submitButton.setOnClickListener {
            val value = value.text.toString()
            val unit = unit.text.toString()
            val daysPerWeek = daysPerWeekView.text.toString().toInt()
            listener.onSubmitWeeklyGoalSend(value, unit, daysPerWeek)
        }
        navBack.setOnClickListener {
            listener.onChangeFrag(false, 2)
        }
        navFor.setOnClickListener{
            listener.onChangeFrag(true, 2)
        }
        return view
    }

    /**
     * Listner wird gesetzt, hier CreateGoalActivity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        //TODO check if activity implements FragmentDailyGoalListner
        listener = context as FragmentWeeklyGoalListener
    }

    override fun onDetach() {
        super.onDetach()
        //TODO set listner to null
    }
}
