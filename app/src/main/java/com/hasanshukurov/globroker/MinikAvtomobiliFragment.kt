package com.hasanshukurov.globroker

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hasanshukurov.globroker.databinding.FragmentMinikAvtomobilBinding
import java.text.SimpleDateFormat
import java.util.*

class MinikAvtomobiliFragment : Fragment() {

    private lateinit var binding: FragmentMinikAvtomobilBinding

    var deyerUsd : Double? = null
    var mator : Int? = null
    var yigim : Int = 0
    val vesiqePulu : Double = 30.00
    var idxalRusumu : Double = 0.00
    var aksiz : Double = 0.0
    var xidmetHaqqi : Double = 35.40
    var gunFerqi : Long = 0
    var deyerAzn : Double = 0.00
    var edv : Double = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMinikAvtomobilBinding.inflate(inflater,container,false)
        return binding.root
    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.minikAvtomobiliToolbar.title = "Gömrük Kalkulyatoru (Minik)"


        binding.istehsalTarixiText.setOnClickListener {
            val calendar = Calendar.getInstance()

            val il = calendar.get(Calendar.YEAR)
            val ay = calendar.get(Calendar.MONTH)
            val gun = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, i, a, g ->

                    binding.istehsalTarixiText.setText("$g.${a+1}.$i")

                },il,ay,gun)

            datePicker.setTitle("Tarix Seçin")
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE,"Ok",datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",datePicker)

            datePicker.show()
        }


        binding.hesablaId.setOnClickListener {

            if (mator == null) {
                binding.textView.text = "Bosh xanalari doldurun"
            }else if (deyerUsd == null) {
                binding.textView.text = "Bosh xanalari doldurun"
            }



            if (binding.benzin.isChecked){
                benzinFun()
            }

            if (binding.dizel.isChecked){
                dizel()
            }

            if (binding.hybrid.isChecked){
                hyibrid()
            }
        }




    }

    fun benzinFun() {

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
            if (mator!! <= 1500) {
                if (gunFerqi <= 365){
                    idxalRusumu = mator!! * 0.68
                }else{
                    idxalRusumu = mator!! * 1.19
                }
            } else {
                if (gunFerqi <= 365){
                    idxalRusumu = mator!! * 1.19
                }else{
                    idxalRusumu = mator!! * 2.04
                }
            }
        }




        // ------- Aksiz vergisi -------

        if (mator != null) {
            if (mator!! <= 2000) {
                if (gunFerqi>=2555){
                    aksiz = mator!! * 0.30 * 1.2
                }else{
                    aksiz = mator!! * 0.30
                }
            } else if (mator!! <= 3000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 2000.00) * 5.00 + 600.00) * 1.2
                }else{
                    aksiz = (mator!! - 2000.00) * 5.00 + 600.00
                }
            } else if (mator!! <= 4000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 3000.00)*15.00 +5600.00) * 1.2
                }else if (gunFerqi>=1095){
                    aksiz = (mator!! - 3000.00)*15.00 +5600.00
                }else{
                    aksiz = (mator!! - 3000.00)*13.00 +5600.00
                }
            }else if (mator!! <= 5000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 4000.00)*40.00 +20600.00) * 1.2
                }else if (gunFerqi>=1095){
                    aksiz = (mator!! - 4000.00)*40.00 +20600.00
                }else{
                    aksiz = (mator!! - 4000.00)*35.00 +18600.00
                }
            }else if (mator!! > 5000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 5000.00)*80.00 +60600.00) * 1.2
                }else if(gunFerqi>=1095){
                    aksiz = (mator!! - 5000.00)*80.00 +60600.00
                }else{
                    aksiz = (mator!! - 5000.00)*70.00 +53600.00
                }
            }
        }



        var edv = ((deyerAzn + vesiqePulu + idxalRusumu + aksiz) * 18) / 100





            val result = yigim + vesiqePulu + idxalRusumu + aksiz + edv + xidmetHaqqi

            val changeFormatResult = String.format("%.2f",result)


            binding.textView.text = "Kassa: $changeFormatResult  AZN "


    }

    fun dizel() {

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
            if (mator!! <= 1500) {
                if (gunFerqi <= 365){
                    idxalRusumu = mator!! * 0.68
                }else{
                    idxalRusumu = mator!! * 1.19
                }
            } else {
                if (gunFerqi <= 365){
                    idxalRusumu = mator!! * 1.19
                }else{
                    idxalRusumu = mator!! * 2.04
                }
            }
        }




        // ------- Aksiz vergisi -------

        if (mator != null) {
            if (mator!! <= 2000) {
                if (gunFerqi>=2555){
                    aksiz = mator!! * 0.30 * 1.5
                }else{
                    aksiz = mator!! * 0.30
                }
            } else if (mator!! <= 3000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 2000.00) * 5.00 + 600.00) * 1.5
                }else{
                    aksiz = (mator!! - 2000.00) * 5.00 + 600.00
                }
            } else if (mator!! <= 4000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 3000.00)*15.00 +5600.00) * 1.5
                }else if (gunFerqi>=1095){
                    aksiz = (mator!! - 3000.00)*15.00 +5600.00
                }else{
                    aksiz = (mator!! - 3000.00)*13.00 +5600.00
                }
            }else if (mator!! <= 5000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 4000.00)*40.00 +20600.00) * 1.5
                }else if (gunFerqi>=1095){
                    aksiz = (mator!! - 4000.00)*40.00 +20600.00
                }else{
                    aksiz = (mator!! - 4000.00)*35.00 +18600.00
                }
            }else if (mator!! > 5000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 5000.00)*80.00 +60600.00) * 1.5
                }else if(gunFerqi>=1095){
                    aksiz = (mator!! - 5000.00)*80.00 +60600.00
                }else{
                    aksiz = (mator!! - 5000.00)*70.00 +53600.00
                }
            }
        }



        val edv = ((deyerAzn + vesiqePulu + idxalRusumu + aksiz) * 18) / 100





        val result = yigim + vesiqePulu + idxalRusumu + aksiz + edv + xidmetHaqqi

        val changeFormatResult = String.format("%.2f",result)

        binding.textView.text = "Kassa: $changeFormatResult  AZN "


    }

    fun hyibrid() {

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
            if (mator!! <= 1500) {
                if (gunFerqi <= 365){
                    idxalRusumu = mator!! * 0.68
                }else{
                    idxalRusumu = mator!! * 1.19
                }
            } else {
                if (gunFerqi <= 365){
                    idxalRusumu = mator!! * 1.19
                }else{
                    idxalRusumu = mator!! * 2.04
                }
            }
        }




        // ------- Aksiz vergisi -------

        if (mator != null) {
            if (mator!! <= 2000) {
                if (gunFerqi>=2555){
                    aksiz = mator!! * 0.30 * 1.5
                }else{
                    aksiz = mator!! * 0.30
                }
            } else if (mator!! <= 3000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 2000.00) * 5.00 + 600.00) * 1.2
                }else{
                    aksiz = (mator!! - 2000.00) * 5.00 + 600.00
                }
            } else if (mator!! <= 4000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 3000.00)*15.00 +5600.00) * 1.2
                }else if (gunFerqi>=1095){
                    aksiz = (mator!! - 3000.00)*15.00 +5600.00
                }else{
                    aksiz = (mator!! - 3000.00)*13.00 +5600.00
                }
            }else if (mator!! <= 5000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 4000.00)*40.00 +20600.00) * 1.2
                }else if (gunFerqi>=1095){
                    aksiz = (mator!! - 4000.00)*40.00 +20600.00
                }else{
                    aksiz = (mator!! - 4000.00)*35.00 +18600.00
                }
            }else if (mator!! > 5000) {
                if (gunFerqi>=2555){
                    aksiz = ((mator!! - 5000.00)*80.00 +60600.00) * 1.2
                }else if(gunFerqi>=1095){
                    aksiz = (mator!! - 5000.00)*80.00 +60600.00
                }else{
                    aksiz = (mator!! - 5000.00)*70.00 +53600.00
                }
            }
        }


        // EDV

        if (mator!! <= 2500 && gunFerqi <= 1095) {
            edv = 0.00
        }else{
            edv = ((deyerAzn + vesiqePulu + idxalRusumu + aksiz) * 18) / 100
        }


        val result = yigim + vesiqePulu + idxalRusumu + aksiz + edv + xidmetHaqqi

        val changeFormatResult = String.format("%.2f",result)

        binding.textView.text = "Kassa: $changeFormatResult  AZN "


    }

    private fun gunFerqi () {

        var toDay = Date()
        var editDate = binding.istehsalTarixiText.text.toString()
        var makeFormat = SimpleDateFormat("dd.MM.yyyy")
        var tarix : Date = makeFormat.parse(editDate)


        gunFerqi = (toDay.time - tarix.time) / 86400000

    }

}