package com.hasanshukurov.globroker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.hasanshukurov.globroker.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

 //   private lateinit var binding: FragmentHomeBinding

    private var binding: FragmentHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)


        binding!!.minikAvtomobiliImageView.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToMinikAvtomobiliFragment())
        }

        binding!!.minikTextView.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToMinikAvtomobiliFragment())
        }

        binding!!.yukAvtomobiliImageView.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToYukAvtomobiliFragment())
        }

        binding!!.yukTextView.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToYukAvtomobiliFragment())
        }



    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}