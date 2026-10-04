package com.infidelrahul.antigravitymobile.antigravity
import kotlinx.serialization.json.Json
class AntigravityParser{private val json=Json{ignoreUnknownKeys=true;isLenient=true};fun parse(line:String)=runCatching{json.decodeFromString<StreamEnvelope>(line)}.getOrNull()}
