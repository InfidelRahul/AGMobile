package com.infidelrahul.antigravitymobile.antigravity
import kotlinx.serialization.Serializable
@Serializable data class StreamEnvelope(val event:String,val conversation_id:String?=null,val init:InitPayload?=null,val step_update:StepUpdate?=null,val result:ResultPayload?=null)
@Serializable data class InitPayload(val cwd:String?=null,val tools:List<String> = emptyList(),val permission_mode:String?=null)
@Serializable data class StepUpdate(val conversation_id:String?=null,val step_index:Int?=null,val state:String?=null,val step_type:String?=null,val text_delta:String?=null,val duration_seconds:Double?=null)
@Serializable data class ResultPayload(val conversation_id:String?=null,val status:String,val response:String?=null,val error:String?=null)
data class AgentActivity(val type:String,val title:String,val detail:String?=null,val running:Boolean=false)
enum class AuthState{STARTING,AUTH_MODE_REQUIRED,AUTH_URL_READY,WAITING_FOR_BROWSER,CODE_REQUIRED,AUTHENTICATING,AUTHENTICATED,STARTING_RUNTIME,READY,ERROR,STOPPED}
data class AntigravityState(val auth:AuthState=AuthState.STARTING,val conversationId:String?=null,val model:String="Auto",val effort:String="High",val activities:List<AgentActivity> = emptyList(),val response:String="",val authUrl:String?=null,val error:String?=null)
