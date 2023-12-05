package com.example.eatelicious.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.eatelicious.ui.theme.auth.AuthNavHost
import com.example.eatelicious.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                AuthNavHost()
            }
        }
    }
}