package com.infidelrahul.antigravitymobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infidelrahul.antigravitymobile.antigravity.AgentActivity
import com.infidelrahul.antigravitymobile.antigravity.AuthState

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            background = Color(0xFFF7F7F5),
            surface = Color.White,
            surfaceContainer = Color(0xFFF0F0ED),
            primary = Color(0xFF1D1D1F),
            onPrimary = Color.White
        ),
        content = content
    )
}

@Composable
fun AntigravityApp(s: AppState, onOpen: (String) -> Unit) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White.copy(alpha = 0.95f)) {
                listOf(
                    MainTab.Home to Icons.Outlined.Home,
                    MainTab.Projects to Icons.Outlined.Folder,
                    MainTab.Agent to Icons.Outlined.AutoAwesome,
                    MainTab.Changes to Icons.Outlined.Merge,
                    MainTab.Terminal to Icons.Outlined.Terminal,
                    MainTab.More to Icons.Outlined.MoreHoriz
                ).forEach { (t, i) ->
                    NavigationBarItem(
                        selected = (s.tab == t),
                        onClick = { s.tab = t },
                        icon = { Icon(i, contentDescription = null) },
                        label = { Text(t.name) }
                    )
                }
            }
        }
    ) { p ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
        ) {
            when (s.tab) {
                MainTab.Home -> Home(s, onOpen)
                MainTab.Projects -> ListScreen(
                    "Projects",
                    listOf("DAntigravity" to "~/Projects/DAntigravity", "LinuxDroid" to "~/Projects/LinuxDroid")
                )
                MainTab.Agent -> Agent(s)
                MainTab.Changes -> ListScreen(
                    "Changes",
                    listOf(
                        "Working tree" to "Linux Git repository",
                        "Diff" to "Review changed files",
                        "Branches" to "Git branches",
                        "Commit" to "Commit from Linux"
                    )
                )
                MainTab.Terminal -> Terminal()
                MainTab.More -> ListScreen(
                    "More",
                    listOf(
                        "Agents & subagents" to "Running and background agents",
                        "Tasks" to "Background and scheduled work",
                        "Artifacts" to "Plans and generated artifacts",
                        "Files" to "Linux filesystem",
                        "Approvals" to "Tool permissions",
                        "Browser" to "Antigravity browser",
                        "MCP" to "MCP servers",
                        "Skills" to "Antigravity skills",
                        "Plugins" to "Customizations",
                        "Settings" to "Global and project settings",
                        "Diagnostics" to "Linux, PRoot, bridge and agy health"
                    )
                )
            }
        }
    }
}

@Composable
fun Home(s: AppState, onOpen: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Antigravity", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
            Text("Agent workspace", color = Color.Gray)
        }
        item {
            Surface(
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("DAntigravity", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                    Text("~/Projects/DAntigravity", color = Color.Gray, fontSize = 13.sp)
                    Text("● " + s.agy.auth.name.lowercase().replace('_', ' '))
                    s.agy.authUrl?.let { url ->
                        Button(onClick = { onOpen(url) }) {
                            Text("Continue sign-in")
                        }
                    }
                    if (s.agy.auth == AuthState.CODE_REQUIRED) {
                        OutlinedTextField(
                            value = s.code,
                            onValueChange = { s.code = it },
                            label = { Text("Authorization code") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(onClick = {
                            s.agy = s.agy.copy(auth = AuthState.AUTHENTICATING)
                        }) {
                            Text("Submit code")
                        }
                    }
                }
            }
        }
        item {
            OutlinedTextField(
                value = s.prompt,
                onValueChange = { s.prompt = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                placeholder = { Text("Ask anything…") },
                shape = RoundedCornerShape(22.dp)
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    onClick = { s.showModel = true },
                    label = { Text("${s.agy.model} · ${s.agy.effort}") },
                    leadingIcon = { Icon(Icons.Outlined.Tune, contentDescription = null) }
                )
                Button(onClick = { s.tab = MainTab.Agent }) {
                    Icon(Icons.Outlined.ArrowUpward, contentDescription = null)
                }
            }
        }
        item {
            Text("Recent activity", fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
        }
        items(s.agy.activities) { activity ->
            ActivityCard(activity)
        }
    }

    if (s.showModel) {
        ModelSheet(s)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelSheet(s: AppState) {
    ModalBottomSheet(onDismissRequest = { s.showModel = false }) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Model & thinking", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            listOf("Auto", "Gemini", "Claude").forEach { name ->
                ListItem(
                    headlineContent = { Text(name) },
                    modifier = Modifier.clickable {
                        s.agy = s.agy.copy(model = name)
                        s.showModel = false
                    }
                )
            }
            HorizontalDivider()
            Text("Thinking effort", fontWeight = FontWeight.Medium)
            listOf("Low", "Medium", "High").forEach { effort ->
                ListItem(
                    headlineContent = { Text(effort) },
                    modifier = Modifier.clickable {
                        s.agy = s.agy.copy(effort = effort)
                        s.showModel = false
                    }
                )
            }
        }
    }
}

@Composable
fun Agent(s: AppState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Agent", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
        }
        item {
            Text("Conversation ${s.agy.conversationId ?: "not started"}", color = Color.Gray)
        }
        items(s.agy.activities) { activity ->
            ActivityCard(activity)
        }
        if (s.agy.response.isNotBlank()) {
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    tonalElevation = 1.dp
                ) {
                    Text(s.agy.response, modifier = Modifier.padding(18.dp))
                }
            }
        }
    }
}

@Composable
fun Terminal() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111111))
    ) {
        Text(
            "Terminal",
            color = Color.White,
            modifier = Modifier.padding(18.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            "Real PTY via NativePty",
            color = Color(0xFFB8B8B8),
            modifier = Modifier.padding(horizontal = 18.dp)
        )
        Text(
            "root@linux:~$ ",
            color = Color.White,
            modifier = Modifier.padding(18.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("CTRL", "ALT", "ESC", "TAB", "↑", "↓", "←", "→").forEach { key ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF222222)
                ) {
                    Text(
                        key,
                        color = Color.White,
                        modifier = Modifier.padding(9.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ListScreen(title: String, entries: List<Pair<String, String>>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(title, fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
        }
        items(entries) { (primary, secondary) ->
            ListItem(
                headlineContent = { Text(primary, fontWeight = FontWeight.Medium) },
                supportingContent = { Text(secondary, color = Color.Gray) }
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun ActivityCard(a: AgentActivity) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                if (a.running) "●" else "○",
                color = if (a.running) Color(0xFF4B7BEC) else Color.Gray
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(a.title, fontWeight = FontWeight.Medium)
                a.detail?.let { detail ->
                    Text(detail, color = Color.Gray, fontSize = 13.sp)
                }
            }
        }
    }
}
