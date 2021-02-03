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
import com.project.laundryapp.ui.MainActivity
import com.project.laundryapp.ui.address.AddressActivity
import com.project.laundryapp.ui.login.LoginActivity
import com.project.laundryapp.utils.Anim
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

        setupUI()
    }

    private fun setupUI() {
        with(binding){
            root.visibility = View.INVISIBLE
            MainActivity.clearStatusInformation()

            tvProfileFullName.setText(user.namaLengkap)
            etProfileAddress.setText(Utils.parseFullAddress(user))
            etProfilePhone.setText(user.nomorHp)
            etProfileEmail.setText(user.email)

            Picasso.get()
                    .load(user.photo)
                    .noFade()
                    .error(R.drawable.gravatar)
                    .placeholder(R.drawable.gravatar)
                    .into(binding.ivProfileImage)

            btProfileEditAddress.setOnClickListener {
                val intent = Intent(requireActivity(), AddressActivity::class.java)
                intent.putExtra(AddressActivity.ADDRESS_CHANGE_KEY, 1)
                startActivity(intent)
            }

            btLogout.setOnClickListener {
                Utils.putSharedPref(requireActivity(), User())
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }

            Anim.crossFade(root)
        }


    }

}