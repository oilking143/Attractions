package com.example.attractions.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attractions.GlobalData
import com.example.attractions.Model.AttrModel.AttrModel
import com.example.attractions.Model.ThemeModel.ThemeModel
import com.example.attractions.R
import com.example.attractions.databinding.FragThemeBinding
import com.example.attractions.databinding.ItemAttrBinding
import com.example.attractions.databinding.ItemThemeBinding
import com.example.attractions.viewModel.VMFragMain
import kotlinx.coroutines.launch

class FragmentTours:BaseFragment() {
    private lateinit var binding: FragThemeBinding
    private lateinit var vm: VMFragMain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_theme, container, false)

        vm = ViewModelProvider(this,VMFragMain.VMFragMainC(requireActivity()))[VMFragMain::class.java]
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getLang(requireActivity())?.let { vm.setRetroFit(it) }
        vm.setRetroFit("zh-cn")
        vm.getAllTheme()
        vm.status.observe(viewLifecycleOwner, Observer { status ->
            when(status){

                3->{
                    binding.loadingAnim.visibility = View.VISIBLE
                    getLang(requireActivity())?.let { vm.setRetroFit(it) }
                    vm.getAllAttractions()
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {

            vm.ThemeData.collect{

                GlobalData.setTheme(it)
                val adapter=ThemeAdapter(it)
                val linearLayoutManager= LinearLayoutManager(context)
                binding.recycler.layoutManager = linearLayoutManager
                binding.recycler.adapter=adapter
                binding.loadingAnim.visibility = View.GONE
            }

        }

    }

    inner class ThemeAdapter(var data:ThemeModel): RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemThemeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun getItemCount(): Int {
            return data.data.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.infoTxt.text = data.data[position].description
            holder.titleTxt.text = data.data[position].title
            val bundle = Bundle()
            bundle.putParcelable("themeModel",data.data[position])
            holder.nextIcon.setOnClickListener {
                val fragment = FragmenThemeViewer()
                fragment.arguments = bundle
                val action = FragmentToursDirections.actionFragmentToursToFragmenThemeViewer()
                findNavController().navigate(action)
            }
        }

        inner class ViewHolder(binding: ItemThemeBinding) :
            RecyclerView.ViewHolder(binding.root) {
                val titleTxt = binding.titleTxt
                val infoTxt = binding.infoTxt
                val nextIcon = binding.nextIcon
        }
    }
}