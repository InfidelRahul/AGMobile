package com.infidelrahul.antigravitymobile.ui
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.infidelrahul.antigravitymobile.antigravity.*
class AppState{var tab by mutableStateOf(MainTab.Home);var authCallback by mutableStateOf<String?>(null);var code by mutableStateOf("");var prompt by mutableStateOf("");var showModel by mutableStateOf(false);var agy by mutableStateOf(AntigravityState(auth=AuthState.STARTING,activities=listOf(AgentActivity("runtime","Linux runtime","Waiting for bridge",true))));}
enum class MainTab{Home,Projects,Agent,Changes,Terminal,More}
