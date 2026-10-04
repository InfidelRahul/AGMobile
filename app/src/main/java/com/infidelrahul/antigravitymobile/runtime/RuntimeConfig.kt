package com.infidelrahul.antigravitymobile.runtime
data class RuntimeConfig(val prootPath:String="proot",val rootfs:String="",val workingDirectory:String="/home/user",val shell:String="",val agyPath:String="agy"){fun prootCommand(vararg cmd:String)=buildList{add(prootPath);if(rootfs.isNotBlank()){add("-R");add(rootfs)};addAll(cmd)}}
