# Antigravity Mobile

Native Android controller for Google Antigravity running inside an existing ARM64 Linux userspace under Android PRoot. Android is UI/controller; Linux owns projects, files, Git, credentials, sessions and runtime state.

References: LinuxDroid https://github.com/LinuxDroidapp/LinuxDroid ; PRoot https://github.com/LinuxDroidapp/proot ; Antigravity https://www.antigravity.google/docs/

The PRoot fork is tracked as a Git submodule. The project intentionally assumes an existing Linux rootfs and does not provision a distro.

This is a production-oriented source baseline; final release certification requires device testing against the exact LinuxDroid rootfs and Android device.
