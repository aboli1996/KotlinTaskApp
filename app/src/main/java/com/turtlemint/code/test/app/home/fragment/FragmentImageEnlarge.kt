package com.turtlemint.code.test.app.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.turtlemint.code.test.app.databinding.LayoutImageEnlargeBinding

class FragmentImageEnlarge(userName: String,imageUrl : String) : DialogFragment() {

    private lateinit var binding : LayoutImageEnlargeBinding
    private var userImageUrl : String = ""
    private var username : String = ""

    init {
        userImageUrl = imageUrl
        username = userName
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutImageEnlargeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       Glide.with(requireContext()).load(userImageUrl).into(binding.imgUserAvatar)
        binding.txtImgTitle.text = username

        binding.txtImgTitle.setOnClickListener(View.OnClickListener {
           if(dialog != null){
               dialog!!.dismiss()
           }
        })
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
    }
}