package com.didik.moflix.presentation.profile

import android.content.Intent
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.didik.moflix.BuildConfig
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentProfileBinding

class ProfileFragment : BindingFragment<FragmentProfileBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupUI()
        setupActionClickView()
    }

    private fun setupUI() {
        binding.userPhotoImageView.load(R.drawable.img_profile) {
            crossfade(true)
            crossfade(500)
            transformations(CircleCropTransformation())
        }

        binding.nameTextView.text = USER_NAME
        binding.emailTextView.text = USER_EMAIL
        binding.appVersionTextView.text = getString(
            R.string.label_app_version,
            BuildConfig.VERSION_NAME
        )
    }

    private fun setupActionClickView() {
        binding.changeLanguageButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    companion object {
        private const val USER_NAME = "Didik Maulana Ardiansyah"
        private const val USER_EMAIL = "didikmaulana49@gmail.com"
    }
}