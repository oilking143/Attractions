package com.example.attractions.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.attractions.R
import com.example.attractions.databinding.FragAttractionBinding
import com.example.attractions.viewModel.VMFragMain

class FragmentAttraction:BaseFragment() {
    private lateinit var binding: FragAttractionBinding
    private lateinit var vm: VMFragMain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_attraction, container, false)

        vm = ViewModelProvider(this,VMFragMain.VMFragMainC(requireActivity()))[VMFragMain::class.java]
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.title.postValue("Attr")
    }
}