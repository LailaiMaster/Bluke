<div align="center">

<img src="Logo.svg" width="128" height="128" alt="Bluke Logo" />

# Bluke

Bluke turns your Android phone into a Bluetooth keyboard, touchpad, gamepad, and multimedia remote without needing any host companion software.

[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![License: AGPL v3](https://img.shields.io/badge/License-AGPL%20v3-blue.svg?style=for-the-badge)](LICENSE)
[![Latest Release](https://img.shields.io/github/v/release/arnav-kr/Bluke?style=for-the-badge&color=orange)](https://github.com/arnav-kr/Bluke/releases)

</div>

---

## 本分支的摇杆触感

基于官方 v1.1.0，增加两项可独立设置的触感反馈：

- **首次触摸轻震**：手指首次按到左右摇杆时轻震一下，可单独关闭。
- **摇动梯度震感**：跨越摇杆幅度档位时轻震，推得越远震感越明显；同一档内移动不会反复震动。
- 摇动强度提供 **关闭 → 很轻 → 轻柔 → 适中 → 较强** 五档，默认轻柔，设置会自动保存。

进入 **Settings → Controller**，或长按手柄顶部的 **Mode**，即可调整。

启动时不再自动弹出普通版本更新提示。

## Features

- Direct Bluetooth HID connection with no companion apps or server software running on the host
- Four input modes: Keyboard, Touchpad, Gamepad, and Multimedia remote
- 12 mechanical switch sound profiles, plus support for importing custom Mechvibes sound packs
- Multiple typing layouts (QWERTY, AZERTY, QWERTZ, Dvorak, Colemak, Russian ЙЦУКЕН) with `Shift + Space` cycling
- Multiple keyboard geometries (60%, 65%, 75%) and custom themes with case, plate, and keycap styling
- Gamepad controller with live switching between Native, Android, and Web mappings
- Multimedia remote with presentation controls, touchpad, and phone volume button forwarding
- Built for peak UX with haptic feedback, OLED black mode, and Material You dynamic theming


[Usage & troubleshooting guide](docs/USAGE.md) · [Changelog](CHANGELOG.md)

## Requirements

- **Android 9 (API level 28) or higher** with firmware supporting the Bluetooth HID Device profile. Profile availability depends on device chipset and OEM ROM.
- A compatible Bluetooth host (Windows, Linux, Android, ChromeOS). Apple and macOS devices are currently unsupported / future scope.

## Build and Installation

### Prerequisites

- Android Studio / JDK compatible with the project's Android Gradle Plugin.
- Android SDK 36.1, as configured in `app/build.gradle.kts`.
- The included Gradle Wrapper.

### Setup and Compilation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/LailaiMaster/Bluke.git
   cd Bluke
   ```

2. **Build with Gradle**:
   ```bash
   ./gradlew assembleDebug      # Linux / macOS
   .\gradlew.bat assembleDebug  # Windows PowerShell
   ```

3. **Install on device**:
   Enable USB Debugging on your Android phone and run:
   ```bash
   ./gradlew installDebug
   ```

The debug APK is output to `app/build/outputs/apk/debug/app-debug.apk`.

## Star History

<a href="https://www.star-history.com/?repos=arnav-kr%2FBluke&type=date&legend=top-left">
 <picture>
   <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/chart?repos=arnav-kr/Bluke&type=date&theme=dark&legend=top-left" />
   <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/chart?repos=arnav-kr/Bluke&type=date&legend=top-left" />
   <img alt="Star History Chart" src="https://api.star-history.com/chart?repos=arnav-kr/Bluke&type=date&legend=top-left" />
 </picture>
</a>

## License

This project is licensed under the [AGPL-3.0](LICENSE).

## Credits

* **[kbsim](https://github.com/tplai/kbsim)**: Part of mechanical switch audio assets are sourced from [kbs.im](https://kbs.im).

## Contributors

<a href="https://github.com/arnav-kr/Bluke/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=arnav-kr/Bluke" alt="Bluke Contributors" />
</a>

 See the [full contributor graph](https://github.com/arnav-kr/Bluke/graphs/contributors).
