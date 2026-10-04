package com.infidelrahul.antigravitymobile.terminal
object NativePty{init{System.loadLibrary("antigravity_native")};external fun nativeOpen(command:String,argv:Array<String>,rows:Int,cols:Int):Int;external fun nativeResize(fd:Int,rows:Int,cols:Int):Int;external fun nativeWrite(fd:Int,data:ByteArray):Int;external fun nativeRead(fd:Int,buffer:ByteArray):Int;external fun nativeClose(fd:Int)}
