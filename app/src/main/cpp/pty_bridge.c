#include <jni.h>
#include <errno.h>
#include <pty.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ioctl.h>
#include <termios.h>
#include <unistd.h>
JNIEXPORT jint JNICALL Java_com_infidelrahul_antigravitymobile_terminal_NativePty_nativeOpen(JNIEnv*e,jclass c,jstring command,jobjectArray argv,jint rows,jint cols){(void)c;const char*cmd=(*e)->GetStringUTFChars(e,command,0);int m=-1;struct winsize w={(unsigned short)rows,(unsigned short)cols,0,0};if(openpty(&m,0,0,0,&w)<0){(*e)->ReleaseStringUTFChars(e,command,cmd);return -errno;}pid_t p=fork();if(p<0){int x=errno;close(m);(*e)->ReleaseStringUTFChars(e,command,cmd);return -x;}if(p==0){setsid();ioctl(m,TIOCSCTTY,0);dup2(m,0);dup2(m,1);dup2(m,2);if(m>2)close(m);int n=(*e)->GetArrayLength(e,argv);char**a=calloc((size_t)n+2,sizeof(char*));if(!a)_exit(127);a[0]=(char*)cmd;for(int i=0;i<n;i++){jstring s=(jstring)(*e)->GetObjectArrayElement(e,argv,i);const char*v=(*e)->GetStringUTFChars(e,s,0);a[i+1]=strdup(v?v:"");if(v)(*e)->ReleaseStringUTFChars(e,s,v);(*e)->DeleteLocalRef(e,s);}a[n+1]=0;setenv("TERM","xterm-256color",1);setenv("COLORTERM","truecolor",1);execvp(cmd,a);_exit(127);}(*e)->ReleaseStringUTFChars(e,command,cmd);return m;}
JNIEXPORT jint JNICALL Java_com_infidelrahul_antigravitymobile_terminal_NativePty_nativeResize(JNIEnv*e,jclass c,jint fd,jint rows,jint cols){(void)e;(void)c;struct winsize w={(unsigned short)rows,(unsigned short)cols,0,0};return ioctl(fd,TIOCSWINSZ,&w);}
JNIEXPORT jint JNICALL Java_com_infidelrahul_antigravitymobile_terminal_NativePty_nativeWrite(JNIEnv*e,jclass c,jint fd,jbyteArray d){(void)c;jsize n=(*e)->GetArrayLength(e,d);jbyte*b=(*e)->GetByteArrayElements(e,d,0);if(!b)return -1;ssize_t r=write(fd,b,n);(*e)->ReleaseByteArrayElements(e,d,b,JNI_ABORT);return r<0?-errno:(jint)r;}
JNIEXPORT jint JNICALL Java_com_infidelrahul_antigravitymobile_terminal_NativePty_nativeRead(JNIEnv*e,jclass c,jint fd,jbyteArray d){(void)c;jsize n=(*e)->GetArrayLength(e,d);jbyte*b=(*e)->GetByteArrayElements(e,d,0);if(!b)return -1;ssize_t r=read(fd,b,n);(*e)->ReleaseByteArrayElements(e,d,b,r>0?0:JNI_ABORT);return r<0?-errno:(jint)r;}
JNIEXPORT void JNICALL Java_com_infidelrahul_antigravitymobile_terminal_NativePty_nativeClose(JNIEnv*e,jclass c,jint fd){(void)e;(void)c;close(fd);}
