package com.example.attractions.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.attractions.GlobalData
import com.example.attractions.R
import com.example.attractions.databinding.FragWebviewBinding
import com.example.attractions.viewModel.VMFragMain


class FragWebView:BaseFragment() {
    private val args: FragWebViewArgs by navArgs()
    private lateinit var binding: FragWebviewBinding
    private lateinit var vm: VMFragMain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_webview, container, false)
        vm = ViewModelProvider(this,VMFragMain.VMFragMainC(requireActivity()))[VMFragMain::class.java]
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webview.apply {
           val webSettings = this.settings
            webSettings.javaScriptEnabled = true //開啟javascript功能
            this.webViewClient = WebViewClient() //新增瀏覽器客戶端
            this.loadUrl(args.urlName) //讀取url網站
        }

        vm.status.observe(viewLifecycleOwner, Observer { status ->
            when(status){
                22->{
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        })

        val model = GlobalData.getAttr()
        binding.titleName.text=args.titleName

    }
}