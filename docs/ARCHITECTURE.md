# Architecture

Android -> local bridge -> LinuxDroid-style runtime/process/session layer -> PRoot -> existing Linux userspace -> official agy CLI/Agent Runtime.

Linux is authoritative. Android holds only transient UI/connection state. Use real PTY for shell. Use official agy stream-json rather than scraping TUI output.
