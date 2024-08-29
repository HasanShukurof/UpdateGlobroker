package com.hasanshukurov.globroker

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.hasanshukurov.globroker.Constant.deyerAzn
import com.hasanshukurov.globroker.Constant.deyerUsd
import com.hasanshukurov.globroker.Constant.edv
import com.hasanshukurov.globroker.Constant.idxalRusumu
import com.hasanshukurov.globroker.Constant.kohneUygunluq
import com.hasanshukurov.globroker.Constant.mator
import com.hasanshukurov.globroker.Constant.result
import com.hasanshukurov.globroker.Constant.tarix
import com.hasanshukurov.globroker.Constant.vesiqePulu
import com.hasanshukurov.globroker.Constant.vesiqePuluQoshqu
import com.hasanshukurov.globroker.Constant.xidmetHaqqi
import com.hasanshukurov.globroker.Constant.yeniUygunluq
import com.hasanshukurov.globroker.Constant.yigim
import com.hasanshukurov.globroker.databinding.FragmentMinikAvtomobilBinding
import com.hasanshukurov.globroker.databinding.FragmentYukAvtomobiliBinding
import java.text.SimpleDateFormat
import java.util.*


class YukAvtomobiliFragment : Fragment() {

    private lateinit var binding: FragmentYukAvtomobiliBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYukAvtomobiliBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.purple_500)
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

//        binding.yukAvtomobiliToolbar.title = "Gömrük Kalkulyatoru (Yük)"


        binding.istehsalTarixiText.setOnClickListener {

            val calendar = Calendar.getInstance()

            val il = calendar.get(Calendar.YEAR)
            val ay = calendar.get(Calendar.MONTH)
            val gun = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, i, a, g ->
                    tarix = binding.istehsalTarixiText.setText("$g.${a+1}.$i").toString()
                },il,ay,gun)

            datePicker.setTitle("Tarix Seçin")
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE,"Ok",datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",datePicker)

            datePicker.show()
        }




        binding.hesablaId.setOnClickListener {

            deyerUsd = binding.deyerText.text.toString().toDoubleOrNull()
            mator = binding.matorText.text.toString().toIntOrNull()
            val istTartix = binding!!.istehsalTarixiText.text.toString()



            if (binding.dartici.isChecked){
                if (deyerUsd == null) {
                    binding.textView.text = "Dəyəri Daxil Edin"
                }else if (mator == null) {
                    binding.textView.text = "Mühərrik Həcmini Qeyd Edin"
                }else if(istTartix.isEmpty()){
                    binding.textView.text = "Tarixi Qeyd Edin"
                }else{
                    dartici()
                    println("Yigim: $yigim / Uygunluq: $kohneUygunluq / VesiqePulu: $vesiqePulu / EDV: $edv / xidmetHaqqi: $xidmetHaqqi")


                }
            }

            if (binding.qoshqu.isChecked){

                if (deyerUsd == null) {
                    binding.textView.text = "Dəyəri Daxil Edin"
                }else if (mator == null || mator!! > 0) {
                    binding.textView.text = "Mühərrik Həcmini   0   Qeyd Edin"
                }else if(istTartix.isEmpty()){
                    binding.textView.text = "Tarixi Qeyd Edin"
                }else{
                    qoshqu()
                }
            }

            if (binding.yuk.isChecked){
                if (deyerUsd == null) {
                    binding.textView.text = "Dəyəri Daxil Edin "
                }else if (mator == null) {
                    binding.textView.text = "Mühərrik Həcmini Qeyd Edin"
                }else if(istTartix.isEmpty()){
                    binding.textView.text = "Tarixi Qeyd Edin"
                }else{
                    yuk()
                }

            }
        }

    }

    fun dartici() {

        gunFerqi()

        deyerUsd = binding.deyerText.text.toString().toDoubleOrNull()
        mator = binding.matorText.text.toString().toIntOrNull()

        if (deyerUsd != null) {
            deyerAzn = deyerUsd!! * 1.70
        }


        // --------- Gomruk Yigimi ----------

        if (deyerAzn <= 1000) {
            yigim = 15
        }else if (deyerAzn <= 10000) {
            yigim = 60
        }else if (deyerAzn <= 50000) {
            yigim = 120
        }else if (deyerAzn <= 100000) {
            yigim = 200
        }else if (deyerAzn <= 500000) {
            yigim = 300
        }else if (deyerAzn <= 1000000) {
            yigim = 600
        }else {
            yigim = 1000
        }





        //   EDV

         edv = ((deyerAzn + vesiqePulu) * 18) / 100


        if (Constant.gunFerqi >= 365){
            result = yigim + kohneUygunluq + vesiqePulu + edv + xidmetHaqqi
        }else{
            result = yigim + yeniUygunluq + vesiqePulu + edv + xidmetHaqqi
        }



        val changeFormatResult = String.format("%.2f",result)


        binding.textView.text = "Kassa: $changeFormatResult  AZN "

    }

    fun qoshqu() {


        deyerUsd = binding.deyerText.text.toString().toDoubleOrNull()
        mator = binding.matorText.text.toString().toIntOrNull()


        if (deyerUsd != null) {
            deyerAzn = deyerUsd!! * 1.70
        }


        // --------- Gomruk Yigimi ----------

        if (deyerAzn <= 1000) {
            yigim = 15
        }else if (deyerAzn <= 10000) {
            yigim = 60
        }else if (deyerAzn <= 50000) {
            yigim = 120
        }else if (deyerAzn <= 100000) {
            yigim = 200
        }else if (deyerAzn <= 500000) {
            yigim = 300
        }else if (deyerAzn <= 1000000) {
            yigim = 600
        }else {
            yigim = 1000
        }




        // ------- Idxal rusumu -------
        if (deyerUsd != null) {
            idxalRusumu = deyerAzn * 5 / 100
        }



        //   EDV

        edv = ((deyerAzn + idxalRusumu + vesiqePuluQoshqu) * 18) / 100





        result = yigim + vesiqePuluQoshqu + idxalRusumu + edv + xidmetHaqqi

        val changeFormatResult = String.format("%.2f",result)


        binding.textView.text = "Kassa: $changeFormatResult  AZN "


    }

    fun yuk() {

        gunFerqi()

        deyerUsd = binding.deyerText.text.toString().toDoubleOrNull()
        mator = binding.matorText.text.toString().toIntOrNull()

        if (deyerUsd != null) {
            deyerAzn = deyerUsd!! * 1.70
        }


        // --------- Gomruk Yigimi ----------

        if (deyerAzn <= 1000) {
            yigim = 15
        }else if (deyerAzn <= 10000) {
            yigim = 60
        }else if (deyerAzn <= 50000) {
            yigim = 120
        }else if (deyerAzn <= 100000) {
            yigim = 200
        }else if (deyerAzn <= 500000) {
            yigim = 300
        }else if (deyerAzn <= 1000000) {
            yigim = 600
        }else {
            yigim = 1000
        }




        // ------- Idxal rusumu -------
        if (mator != null) {
            if (Constant.gunFerqi >= 365){
                idxalRusumu = mator!! * 0.7 * 1.7
            }else{
                idxalRusumu = deyerAzn * 5/100
            }

        }



        //   EDV

        edv = ((deyerAzn + idxalRusumu + vesiqePulu) * 18) / 100


        if (Constant.gunFerqi >= 365){
            result = yigim + kohneUygunluq + vesiqePulu + idxalRusumu + edv + xidmetHaqqi
        }else{
            result = yigim + yeniUygunluq + vesiqePulu + idxalRusumu + edv + xidmetHaqqi
        }





        val changeFormatResult = String.format("%.2f",result)



        binding.textView.text = "Kassa: $changeFormatResult  AZN "


    }

    private fun gunFerqi () {

        var toDay = Date()
        var editDate = binding.istehsalTarixiText.text.toString()
        var makeFormat = SimpleDateFormat("dd.MM.yyyy")
        var tarix : Date = makeFormat.parse(editDate)


        Constant.gunFerqi = (toDay.time - tarix.time) / 86400000

    }

}

















