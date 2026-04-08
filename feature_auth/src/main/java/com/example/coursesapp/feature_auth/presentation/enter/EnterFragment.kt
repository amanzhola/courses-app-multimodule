package com.example.coursesapp.feature_auth.presentation.enter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.coursesapp.feature_auth.R

class EnterFragment : Fragment(R.layout.fragment_enter) {

    private var navigation: EnterNavigation? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation = activity as? EnterNavigation

        val email = view.findViewById<EditText>(R.id.emailEdit)
        val password = view.findViewById<EditText>(R.id.passwordEdit)
        val button = view.findViewById<AppCompatButton>(R.id.loginButton)
        val vkButton = view.findViewById<View>(R.id.vkButton)
        val okButton = view.findViewById<View>(R.id.okButton)
        val registrationLink = view.findViewById<View>(R.id.registrationLinkTextView)
        val forgotPassword = view.findViewById<View>(R.id.forgotPasswordTextView)

        email.filters = arrayOf(noCyrillicFilter())

        email.setOnFocusChangeListener { _, hasFocus ->
            email.hint = if (hasFocus) "" else "example@gmail.com"
        }

        password.setOnFocusChangeListener { _, hasFocus ->
            password.hint = if (hasFocus) "" else "Введите пароль"
        }

        registrationLink.isEnabled = false
        registrationLink.isClickable = false

        forgotPassword.isEnabled = false
        forgotPassword.isClickable = false

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isValid =
                    android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() &&
                            password.text.toString().isNotBlank()

                button.isEnabled = isValid
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        }

        email.addTextChangedListener(watcher)
        password.addTextChangedListener(watcher)

        button.setOnClickListener {
            if (button.isEnabled) {
                navigation?.openMainScreen()
            }
        }

        vkButton.setOnClickListener {
            openUrl("https://vk.com/")
        }

        okButton.setOnClickListener {
            openUrl("https://ok.ru/")
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun noCyrillicFilter(): InputFilter {
        return InputFilter { source, _, _, _, _, _ ->
            if (source.any { it in 'А'..'я' || it == 'Ё' || it == 'ё' }) {
                ""
            } else {
                null
            }
        }
    }

    override fun onDestroyView() {
        navigation = null
        super.onDestroyView()
    }
}