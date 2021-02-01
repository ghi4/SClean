package com.project.laundryapp.ui.zfragment.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.laundryapp.R
import com.project.laundryapp.core.data.local.User
import com.project.laundryapp.databinding.FragmentProfileBinding
import com.project.laundryapp.ui.login.LoginActivity
import com.project.laundryapp.utils.Utils
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by inject()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        user = Utils.getSharedPref(requireActivity())

        Log.d("PROFILE TAG", """
            $user
        """.trimIndent())

        setupUI()
    }

    private fun setupUI() {
        with(binding){
            tvProfileFullName.text = user.namaLengkap
            etProfileAddress.setText(user.alamat)
            etProfilePhone.setText(user.nomorHp)
            etProfileEmail.setText(user.email)

            Picasso.get()
                    .load(user.photo)
                    .noFade()
                    .error(R.drawable.gravatar)
                    .placeholder(R.drawable.gravatar)
                    .into(binding.ivProfileImage)

            btLogout.setOnClickListener {
                Utils.putSharedPref(requireActivity(), User())
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
        }


    }

}