# Bridge integration

The source establishes the Android-side protocol/domain boundary. The next integration step is to bind the local IPC transport to the LinuxDroid process/session runtime and feed the official `agy --input-format stream-json --output-format stream-json` process.

This is intentionally isolated from UI code so the runtime can use the exact LinuxDroid session/process implementation and the existing rootfs.
