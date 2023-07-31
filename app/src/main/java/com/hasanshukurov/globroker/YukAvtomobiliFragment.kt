package com.hasanshukurov.globroker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hasanshukurov.globroker.databinding.FragmentMinikAvtomobilBinding
import com.hasanshukurov.globroker.databinding.FragmentYukAvtomobiliBinding


class YukAvtomobiliFragment : Fragment() {

    private lateinit var binding: FragmentYukAvtomobiliBinding

    var deyerUsd : Double? = null
    var mator : Int? = null
    var yigim : Int = 0
    var idxalRusumu : Double = 0.00
    var vesiqePulu : Double = 30.00
    var xidmetHaqqi : Double = 35.40
    var deyerAzn : Double = 0.00
    var edv : Double = 0.00

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

        binding.yukAvtomobiliToolbar.title = "Gömrük Kalkulyatoru (Yük)"



        binding.hesablaId.setOnClickListener {

            deyerUsd = binding.deyerText.text.toString().toDoubleOrNull()
            mator = binding.matorText.text.toString().toIntOrNull()


            if (binding.dartici.isChecked){
                if (mator == null) {
                    binding.textView.text = "Mühərrik Həcmini Qeyd Edin"
                }else if (deyerUsd == null) {
                    binding.textView.text = "Dəyəri Daxil Edin"
                }else{
                    dartici()
                }
            }

            if (binding.qoshqu.isChecked){
                if (mator != 0 && mator == null) {
                    binding.textView.text = "Mühərrik Həcmini 0 Qeyd Edin"
                }else if (deyerUsd == null) {
                    binding.textView.text = "Dəyəri Daxil Edin"
                }else{
                    qoshqu()
                }
            }

            if (binding.yuk.isChecked){
                if (mator == null) {
                    binding.textView.text = "Mühərrik Həcmini Qeyd Edin"
                }else if (deyerUsd == null) {
                    binding.textView.text = "Dəyəri Daxil Edin"
                }else{
                    yuk()
                }

            }
        }

    }

    fun dartici() {

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

        val edv = ((deyerAzn + vesiqePulu ) * 18) / 100





        val result = yigim + vesiqePulu + edv + xidmetHaqqi

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

        vesiqePulu = 25.00
        val edv = ((deyerAzn + vesiqePulu + idxalRusumu ) * 18) / 100





        val result = yigim + vesiqePulu + idxalRusumu + edv + xidmetHaqqi

        val changeFormatResult = String.format("%.2f",result)


        binding.textView.text = "Kassa: $changeFormatResult  AZN "


    }

    fun yuk() {

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
            idxalRusumu = mator!! * 0.7 * 1.7
        }



        //   EDV

        var edv = ((deyerAzn + vesiqePulu + idxalRusumu ) * 18) / 100





        val result = yigim + vesiqePulu + idxalRusumu + edv + xidmetHaqqi

        val changeFormatResult = String.format("%.2f",result)



        binding.textView.text = "Kassa: $changeFormatResult  AZN "


    }


}

















