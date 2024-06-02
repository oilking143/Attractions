package com.example.attractions.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.attractions.GlobalData
import com.example.attractions.Model.AttrModel.Image
import com.example.attractions.R
import com.example.attractions.databinding.FragDetailBinding
import com.example.attractions.viewModel.VMFragMain


class FragmentDetail:BaseFragment() {
    private lateinit var vm: VMFragMain
    private lateinit var binding: FragDetailBinding
    private val args: FragmentDetailArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_detail, container, false)
        vm = ViewModelProvider(this,VMFragMain.VMFragMainC(requireActivity()))[VMFragMain::class.java]
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = GlobalData.getAttr()
        binding.descTxt.text = model.data[args.position].introduction
        binding.urlContent.text = model.data[args.position].url
        if(model.data[args.position].images.isNotEmpty()){
            val adapter = PhotoAdapter(model.data[args.position].images)
            binding.banner.adapter = adapter
        }
        binding.titleName.text = model.data[args.position].name

        vm.status.observe(viewLifecycleOwner, Observer { status ->

            when(status){
                20->{
                    requireActivity().supportFragmentManager.popBackStack()
                }

                21->{
                    val action = FragmentDetailDirections.actionFragmentDetailToFragWebView(model.data[args.position].url)
                    findNavController().navigate(action)
                    vm.status.postValue(0)
                }
            }

        })



    }


    inner class PhotoAdapter(private val photoList: List<Image>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

        inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
            return PhotoViewHolder(view)
        }

        override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
            val options: RequestOptions = RequestOptions()
                .transform(MultiTransformation(CenterCrop(), CircleCrop()))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.NORMAL)

            context?.let {
                Glide.with(it)
                    .load(photoList[position].src)
                    .apply(options)
                    .into(holder.imageView)
            }
        }

        override fun getItemCount(): Int {
            return photoList.size
        }
    }

}