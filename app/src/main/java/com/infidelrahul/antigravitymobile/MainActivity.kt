package com.infidelrahul.antigravitymobile
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.infidelrahul.antigravitymobile.runtime.LinuxRuntimeService
import com.infidelrahul.antigravitymobile.ui.AppState
import com.infidelrahul.antigravitymobile.ui.AntigravityApp
import com.infidelrahul.antigravitymobile.ui.AppTheme
class MainActivity:ComponentActivity(){private val state=AppState();override fun onCreate(b:Bundle?){super.onCreate(b);enableEdgeToEdge();LinuxRuntimeService.start(this);intent?.data?.let{state.authCallback=it.toString()};setContent{AppTheme{AntigravityApp(state){startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))}}}}override fun onNewIntent(i:Intent){super.onNewIntent(i);setIntent(i);i.data?.let{state.authCallback=it.toString()}}}
