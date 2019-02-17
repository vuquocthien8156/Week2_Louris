package com.example.qthien.articlesearching

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.abc_search_dropdown_item_icons_2line.view.*
import kotlinx.android.synthetic.main.filter_layout.*
import java.text.SimpleDateFormat
import java.util.*

class MyDialogFilter : DialogFragment() {

    var selectedDate: DatePickerDialog.OnDateSetListener? = null

    lateinit var edtDate: EditText
    lateinit var btnX : Button
    lateinit var spinner : Spinner
    lateinit var ckbA : CheckBox
    lateinit var ckbF : CheckBox
    lateinit var ckbS : CheckBox
    lateinit var btnSave : Button

    var date : String? = ""
    var spinnerSelect : Int? = 0
    var CheckedA : Boolean = false
    var CheckedF : Boolean = false
    var CheckedS : Boolean = false

    var share : SharedPreferences? = null

    interface FragmentCallActivity{
        fun sendData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.filter_layout, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        share = context?.getSharedPreferences("Filter" , Context.MODE_PRIVATE)

        edtDate = view.findViewById(R.id.edtDate)
        btnX = view.findViewById(R.id.btnX)
        spinner = view.findViewById(R.id.spinnerSort)
        ckbA  = view.findViewById(R.id.ckbArts)
        ckbF  = view.findViewById(R.id.ckbFashion)
        ckbS  = view.findViewById(R.id.ckbSports)
        btnSave = view.findViewById(R.id.btnSave)

        getData()

        setData()


        var onChecked : CompoundButton.OnCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            if(ckbA.isChecked == share?.getBoolean("SelectA" , false)!!
                && ckbF.isChecked == share?.getBoolean("SelectF" , false)!!
                && ckbS.isChecked == share?.getBoolean("SelectS" , false)!!)
                btnSave.isEnabled = false
            else
                btnSave.isEnabled = true

                CheckedA = ckbA.isChecked
                CheckedF = ckbF.isChecked
                CheckedS = ckbS.isChecked
        }

        ckbA.setOnCheckedChangeListener(onChecked)
        ckbF.setOnCheckedChangeListener(onChecked)
        ckbS.setOnCheckedChangeListener(onChecked)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == share?.getInt("Sort" , 0)){
                    btnSave.isEnabled = false
                }
                else
                    btnSave.isEnabled = true
                spinnerSelect = position
            }

        }

        edtDate.setOnClickListener({
            var year = 0
            var month = 0
            var day = 0
            var calender = Calendar.getInstance()

            if(edtDate.getText().length != 0) {
                var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                calender.time = simpleDateFormat.parse(edtDate.text.toString())
            }

            year = calender.get(Calendar.YEAR)
            month = calender.get(Calendar.MONTH)
            day = calender.get(Calendar.DAY_OF_MONTH)

            var datePickerDialog = DatePickerDialog(
                context,
                selectedDate, year, month, day
            ).show()

        })

        selectedDate = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, day: Int ->
            var sim = SimpleDateFormat("dd/MM/yyyy")
//            var d = Calendar.getInstance()

            date = sim.format(sim.parse("$day/$month/$year"))
            if(date.equals(share?.getString("Date" , ""))){
               btnSave.isEnabled = false
            }
            else
            {
                btnSave.isEnabled = true
            }
            edtDate.setText(date)
        }

        btnX.setOnClickListener({
            edtDate.setText("")
            date = ""
            if(share?.getString("Date" , null) != "")
            {
                btnSave.isEnabled = true
            }
        })

        btnSave.setOnClickListener({
            share = context?.getSharedPreferences("Filter" , Context.MODE_PRIVATE)
            var editor = share?.edit()
            editor?.putString("Date" , date)
            editor?.putInt("Sort" , spinnerSelect!!)
            editor?.putBoolean("SelectA" , CheckedA)
            editor?.putBoolean("SelectF" , CheckedF)
            editor?.putBoolean("SelectS" , CheckedS)
            editor?.apply()
            (context as MainActivity).reloadData()

            dismiss()
        })
    }

    private fun setData() {
        edtDate.setText(share?.getString("Date" , null))
        spinner.setSelection(share?.getInt("Sort" , 0)!!)
        ckbA.isChecked = share?.getBoolean("SelectA" , false)!!
        ckbF.isChecked = share?.getBoolean("SelectF" , false)!!
        ckbS.isChecked = share?.getBoolean("SelectS" , false)!!
    }

    private fun getData() {
        date = share?.getString("Date" , null)
        spinnerSelect = share?.getInt("Sort" , 0)
        CheckedA = share?.getBoolean("SelectA" , false)!!
        CheckedF = share?.getBoolean("SelectF" , false)!!
        CheckedS = share?.getBoolean("SelectS" , false)!!

    }
}

