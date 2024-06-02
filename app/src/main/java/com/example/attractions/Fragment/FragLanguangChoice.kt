package com.example.attractions.Fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.attractions.R
import com.example.attractions.databinding.DialogLanguageBinding
import com.example.attractions.viewModel.VMFragMain


class FragLanguangChoice(vm: VMFragMain): DialogFragment() {

    var lang=""
    lateinit var binding: DialogLanguageBinding
    var vm = vm

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogLanguageBinding.inflate(inflater, container, false)


        binding.frameTw.setOnClickListener {

            setLanguage("zh-tw")
        }
        binding.frameCn.setOnClickListener {

            setLanguage("zh-cn")
        }

        binding.frameEn.setOnClickListener {
            setLanguage("en")
        }

        binding.frameJa.setOnClickListener {
            setLanguage("ja")
        }

        binding.frameKo.setOnClickListener {
            setLanguage("ko")
        }

        binding.frameEs.setOnClickListener {
            setLanguage("es")
        }

        binding.frameId.setOnClickListener {
            setLanguage("id")
        }

        binding.frameTh.setOnClickListener {
            setLanguage("th")
        }

        binding.frameVi.setOnClickListener {
            setLanguage("vi")
        }


        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onResume() {
        // Set the width of the dialog proportional to 90% of the screen width
        super.onResume()
        dialog?.window?.let { window ->
            val displayMetrics = resources.displayMetrics
            val width = (displayMetrics.widthPixels * 0.9).toInt()
            val height = (displayMetrics.heightPixels * 0.7).toInt()
            window.setLayout(width, height)
        }
    }

    fun setLanguage(lang:String){
        requireContext()
            .getSharedPreferences("Attr",0)
            .edit()
            .putString("language", lang)
            .apply()
        vm.status.postValue(3)
        dismiss()
    }


}