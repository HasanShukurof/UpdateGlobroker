package com.hasanshukurov.globroker

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hasanshukurov.globroker.Constant.aksiz
import com.hasanshukurov.globroker.Constant.deyerAzn
import com.hasanshukurov.globroker.Constant.deyerUsd
import com.hasanshukurov.globroker.Constant.edv
import com.hasanshukurov.globroker.Constant.idxalRusumu
import com.hasanshukurov.globroker.Constant.ilFerqi
import com.hasanshukurov.globroker.Constant.kohneUygunluq
import com.hasanshukurov.globroker.Constant.mator
import com.hasanshukurov.globroker.Constant.result
import com.hasanshukurov.globroker.Constant.tarix
import com.hasanshukurov.globroker.Constant.utilizasiya
import com.hasanshukurov.globroker.Constant.vesiqePulu
import com.hasanshukurov.globroker.Constant.xidmetHaqqi
import com.hasanshukurov.globroker.Constant.yeniUygunluq
import com.hasanshukurov.globroker.Constant.yigim
import com.hasanshukurov.globroker.databinding.FragmentMinikAvtomobilBinding
import java.text.SimpleDateFormat
import java.util.*

class MinikAvtomobiliFragment : Fragment() {

    private var binding: FragmentMinikAvtomobilBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMinikAvtomobilBinding.inflate(inflater,container,false)
        return binding!!.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




  //      binding!!.minikAvtomobiliToolbar.title = "Gömrük Kalkulyatoru (Minik)"


        binding!!.istehsalTarixiText.setOnClickListener {

            val calendar = Calendar.getInstance()

            val il = calendar.get(Calendar.YEAR)
            val ay = calendar.get(Calendar.MONTH)
            val gun = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, i, a, g ->
                    tarix = binding!!.istehsalTarixiText.setText("$g.${a+1}.$i").toString()
                },il,ay,gun)

            datePicker.setTitle("Tarix Seçin")
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE,"Ok",datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",datePicker)

            datePicker.show()
        }



        binding!!.hesablaId.setOnClickListener {

                deyerUsd = binding!!.deyerText.text.toString().toDoubleOrNull()
                mator = binding!!.matorText.text.toString().toIntOrNull()



            if (binding!!.benzin.isChecked){
                if (deyerUsd == null){
                    binding!!.textView.text = "Dəyəri Daxil Edin"
                }else if (mator == null){
                    binding!!.textView.text = "Mühərrik Həcmini Qeyd Edin"
                }else if (tarix == null){
                    binding!!.textView.text = "Tarixi Qeyd Edin"
                }else{
                    benzin()
                }
            }

            if (binding!!.dizel.isChecked){
                if (deyerUsd == null){
                    binding!!.textView.text = "Dəyəri Daxil Edin"
                }else if (mator == null){
                    binding!!.textView.text = "Mühərrik Həcmini Qeyd Edin"
                }else if (tarix == null){
                    binding!!.textView.text = "Tarixi Qeyd Edin"
                }else{
                    dizel()
                }

            }

            if (binding!!.hybrid.isChecked){
                if (deyerUsd == null){
                    binding!!.textView.text = "Dəyəri Daxil Edin"
                }else if (mator == null){
                    binding!!.textView.text = "Mühərrik Həcmini Qeyd Edin"
                }else if (tarix == null){
                    binding!!.textView.text = "Tarixi Qeyd Edin"
                }else{
                    hyibrid()
                }
            }

            if (binding!!.elektrik.isChecked){
                if (deyerUsd == null){
                    binding!!.textView.text = "Dəyəri Daxil Edin"
                }else if (mator == null || mator!! > 0){
                    binding!!.textView.text = "Mühərrik Həcmini 0 Qeyd Edin"
                }else if (tarix == null){
                    binding!!.textView.text = "Tarixi Qeyd Edin"
                }else{
                    elektrik()
                }
            }
        }
    }

    fun benzin() {

        gunFerqi()
        println("IL FERQI: $ilFerqi")

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
                if (Constant.gunFerqi <= 365){
                    idxalRusumu = mator!! * 0.68
                }else{
                    idxalRusumu = mator!! * 1.19
                }
            } else {
                if (Constant.gunFerqi <= 365){
                    idxalRusumu = mator!! * 1.19
                }else{
                    idxalRusumu = mator!! * 2.04
                }
            }
        }




        // ------- Aksiz vergisi -------

        if (mator != null) {
            if (mator!! <= 2000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = mator!! * 0.30 * 1.2
                }else{
                    aksiz = mator!! * 0.30
                }
            } else if (mator!! <= 3000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 2000.00) * 5.00 + 600.00) * 1.2
                }else{
                    aksiz = (mator!! - 2000.00) * 5.00 + 600.00
                }
            } else if (mator!! <= 4000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 3000.00)*15.00 +5600.00) * 1.2
                }else if (Constant.gunFerqi >= 1095){
                    aksiz = (mator!! - 3000.00)*15.00 +5600.00
                }else{
                    aksiz = (mator!! - 3000.00)*13.00 +5600.00
                }
            }else if (mator!! <= 5000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 4000.00)*40.00 +20600.00) * 1.2
                }else if (Constant.gunFerqi >= 1095){
                    aksiz = (mator!! - 4000.00)*40.00 +20600.00
                }else{
                    aksiz = (mator!! - 4000.00)*35.00 +18600.00
                }
            }else if (mator!! > 5000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 5000.00)*80.00 +60600.00) * 1.2
                }else if(Constant.gunFerqi >= 1095){
                    aksiz = (mator!! - 5000.00)*80.00 +60600.00
                }else{
                    aksiz = (mator!! - 5000.00)*70.00 +53600.00
                }
            }
        }



        edv = ((deyerAzn + idxalRusumu + aksiz + vesiqePulu) * 18) / 100


        // Utilizasiya
        if (ilFerqi >= 4 && ilFerqi < 7) {
            utilizasiya = 400
        } else if (ilFerqi >= 7) {
            utilizasiya = 700
        } else {
            utilizasiya = 0
        }

        if (Constant.gunFerqi >= 365){
            result = yigim + kohneUygunluq + vesiqePulu + idxalRusumu + aksiz + edv + xidmetHaqqi + utilizasiya
        }else{
            result = yigim + yeniUygunluq + vesiqePulu + idxalRusumu + aksiz + edv + xidmetHaqqi + utilizasiya
        }



        val changeFormatResult = String.format("%.2f",result)


        binding!!.textView.text = "Kassa: $changeFormatResult  AZN "


    }

    fun dizel() {

        gunFerqi()

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
                if (Constant.gunFerqi <= 365){
                    idxalRusumu = mator!! * 0.68
                }else{
                    idxalRusumu = mator!! * 1.19
                }
            } else {
                if (Constant.gunFerqi <= 365){
                    idxalRusumu = mator!! * 1.19
                }else{
                    idxalRusumu = mator!! * 2.04
                }
            }
        }




        // ------- Aksiz vergisi -------

        if (mator != null) {
            if (mator!! <= 2000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = mator!! * 0.30 * 1.5
                }else{
                    aksiz = mator!! * 0.30
                }
            } else if (mator!! <= 3000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 2000.00) * 5.00 + 600.00) * 1.5
                }else{
                    aksiz = (mator!! - 2000.00) * 5.00 + 600.00
                }
            } else if (mator!! <= 4000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 3000.00)*15.00 +5600.00) * 1.5
                }else if (Constant.gunFerqi >= 1095){
                    aksiz = (mator!! - 3000.00)*15.00 +5600.00
                }else{
                    aksiz = (mator!! - 3000.00)*13.00 +5600.00
                }
            }else if (mator!! <= 5000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 4000.00)*40.00 +20600.00) * 1.5
                }else if (Constant.gunFerqi >= 1095){
                    aksiz = (mator!! - 4000.00)*40.00 +20600.00
                }else{
                    aksiz = (mator!! - 4000.00)*35.00 +18600.00
                }
            }else if (mator!! > 5000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 5000.00)*80.00 +60600.00) * 1.5
                }else if(Constant.gunFerqi >= 1095){
                    aksiz = (mator!! - 5000.00)*80.00 +60600.00
                }else{
                    aksiz = (mator!! - 5000.00)*70.00 +53600.00
                }
            }
        }


        edv = ((deyerAzn + idxalRusumu + aksiz + vesiqePulu) * 18) / 100



        // Utilizasiya
        if (ilFerqi >= 4 && ilFerqi < 7) {
            utilizasiya = 400
        } else if (ilFerqi >= 7) {
            utilizasiya = 700
        } else {
            utilizasiya = 0
        }



        if (Constant.gunFerqi >= 365){
            result = yigim + kohneUygunluq + vesiqePulu + idxalRusumu + aksiz + edv + xidmetHaqqi + utilizasiya
        }else{
            result = yigim + yeniUygunluq + vesiqePulu + idxalRusumu + aksiz + edv + xidmetHaqqi + utilizasiya
        }


        val changeFormatResult = String.format("%.2f",result)

        binding!!.textView.text = "Kassa: $changeFormatResult  AZN "


    }

    fun hyibrid() {

        gunFerqi()


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
                if (Constant.gunFerqi <= 365){
                    idxalRusumu = mator!! * 0.68
                }else{
                    idxalRusumu = mator!! * 1.19
                }
            } else {
                if (Constant.gunFerqi <= 365){
                    idxalRusumu = mator!! * 1.19
                }else{
                    idxalRusumu = mator!! * 2.04
                }
            }
        }




        // ------- Aksiz vergisi -------

        if (mator != null) {
            if (mator!! <= 2000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = mator!! * 0.30 * 1.2
                }else{
                    aksiz = mator!! * 0.30
                }
            } else if (mator!! <= 3000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 2000.00) * 5.00 + 600.00) * 1.2
                }else{
                    aksiz = (mator!! - 2000.00) * 5.00 + 600.00
                }
            } else if (mator!! <= 4000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 3000.00)*15.00 +5600.00) * 1.2
                }else if (Constant.gunFerqi >= 1095){
                    aksiz = (mator!! - 3000.00)*15.00 +5600.00
                }else{
                    aksiz = (mator!! - 3000.00)*13.00 +5600.00
                }
            }else if (mator!! <= 5000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 4000.00)*40.00 +20600.00) * 1.2
                }else if (Constant.gunFerqi >= 1095){
                    aksiz = (mator!! - 4000.00)*40.00 +20600.00
                }else{
                    aksiz = (mator!! - 4000.00)*35.00 +18600.00
                }
            }else if (mator!! > 5000) {
                if (Constant.gunFerqi >= 2555){
                    aksiz = ((mator!! - 5000.00)*80.00 +60600.00) * 1.2
                }else if(Constant.gunFerqi >= 1095){
                    aksiz = (mator!! - 5000.00)*80.00 +60600.00
                }else{
                    aksiz = (mator!! - 5000.00)*70.00 +53600.00
                }
            }
        }


        // EDV

        if (mator!! <= 2500 && Constant.gunFerqi <= 1095) {
            edv = 0.00
        }else{
            edv = ((deyerAzn + idxalRusumu + aksiz + vesiqePulu) * 18) / 100
        }


        // Utilizasiya
        if (ilFerqi >= 4 && ilFerqi < 7) {
            utilizasiya = 400
        } else if (ilFerqi >= 7) {
            utilizasiya = 700
        } else {
            utilizasiya = 0
        }


        if (Constant.gunFerqi >= 365){
            result = yigim + kohneUygunluq + vesiqePulu + idxalRusumu + aksiz + edv + xidmetHaqqi + utilizasiya
        }else{
            result = yigim + yeniUygunluq + vesiqePulu + idxalRusumu + aksiz + edv + xidmetHaqqi + utilizasiya
        }




        val changeFormatResult = String.format("%.2f",result)

        binding!!.textView.text = "Kassa: $changeFormatResult  AZN "


    }


    fun elektrik() {

        gunFerqi()

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

        if(Constant.gunFerqi >= 1095){
            idxalRusumu = deyerAzn * 15/100
        }else{
            idxalRusumu = 0.00
        }


        // Utilizasiya
        if (ilFerqi >= 4 && ilFerqi < 7) {
            utilizasiya = 400
        } else if (ilFerqi >= 7) {
            utilizasiya = 700
        } else {
            utilizasiya = 0
        }

        result = yigim + vesiqePulu + idxalRusumu + xidmetHaqqi + utilizasiya

        val changeFormatResult = String.format("%.2f",result)


        binding!!.textView.text = "Kassa: $changeFormatResult  AZN "


    }




    private fun gunFerqi () {

        var toDay = Date()
        var editDate = binding!!.istehsalTarixiText.text.toString()
        var makeFormat = SimpleDateFormat("dd.MM.yyyy")
        var tarix : Date = makeFormat.parse(editDate)


        ilFerqi = toDay.year - tarix.year
        Constant.gunFerqi = (toDay.time - tarix.time) / 86400000

    }


    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}