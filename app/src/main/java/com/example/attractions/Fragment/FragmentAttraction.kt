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
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.attractions.GlobalData
import com.example.attractions.Model.AttrModel.AttrModel
import com.example.attractions.R
import com.example.attractions.databinding.FragAttractionBinding
import com.example.attractions.databinding.ItemAttrBinding
import com.example.attractions.viewModel.VMFragMain
import kotlinx.coroutines.launch

class FragmentAttraction:BaseFragment(){
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

        getLang(requireActivity())?.let { vm.setRetroFit(it) }
        vm.getAllAttractions()
        vm.status.observe(viewLifecycleOwner, Observer { status ->
            when(status){


                2->{
                    requireActivity().supportFragmentManager.let {
                        FragLanguangChoice(vm).show(it, "")
                    }
                }

                3->{
                    binding.loadingAnim.visibility = View.VISIBLE
                    getLang(requireActivity())?.let { vm.setRetroFit(it) }
                    vm.getAllAttractions()
                }

            }

            })

        viewLifecycleOwner.lifecycleScope.launch {

            vm.AttrData.collect{

                GlobalData.setAttr(it)
                val adapter=AttrAdapter(it)
                val linearLayoutManager= LinearLayoutManager(context)
                binding.recycler.layoutManager = linearLayoutManager
                binding.recycler.adapter=adapter
                binding.loadingAnim.visibility = View.GONE
            }

        }


    }


    inner class AttrAdapter(var data:AttrModel): RecyclerView.Adapter<AttrAdapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttrAdapter.ViewHolder {
            return ViewHolder(ItemAttrBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val options: RequestOptions = RequestOptions()
                .transform(MultiTransformation(CenterCrop(), CircleCrop()))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.NORMAL)

            if(data.data[position].images.isNotEmpty()){
                context?.let {
                    Glide.with(it)
                        .load(data.data[position].images[0].src)
                        .apply(options)
                        .into(holder.picItem)
                }
            }else {
                context?.let {
                    Glide.with(it)
                        .load(R.mipmap.ic_placeholder)
                        .apply(options)
                        .into(holder.picItem)
                }

            }
            holder.infoTxt.text = data.data[position].introduction

            holder.toNext.setOnClickListener {
                val action = FragmentAttractionDirections.actionFragmentAttractionToFragmentDetail(position)
                findNavController().navigate(action)
            }

            holder.title.text = data.data[position].name

        }

        override fun getItemCount(): Int {
           return data.data.size
        }

        inner class ViewHolder(binding: ItemAttrBinding) :
            RecyclerView.ViewHolder(binding.root) {

            val picItem= binding.picItem
            val infoTxt = binding.infoTxt
            val toNext = binding.nextIcon
            val title = binding.titleTxt

        }

    }

}