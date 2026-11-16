# Liquid Glass Control Center — starter 0.1.0

Starter Android project for POCO F6 / HyperOS 3. It uses Jetpack Compose and Kyant0's
Backdrop library (`io.github.kyant0:backdrop:2.0.1`) for the glass rendering.

## What works in this first UI prototype
- Liquid Glass panel design using Backdrop: blur, vibrancy and lens.
- Demo toggles for Wi-Fi, Bluetooth, mobile, airplane and torch.
- Demo brightness and volume sliders.
- A small in-app drag handle to hide/show the panel.

## Important limitations
This is **not yet a system-wide Quick Settings replacement**. Android reserves the actual
status-bar pull-down for the system notification shade. A normal third-party app cannot
replace that shade. The controls in this first version are visual/demo state only; they
do not yet change radios, torch, system brightness or media volume.

The first prototype uses a swipe handle **inside the app window**. Later we can add an
overlay/launcher entry point, then implement only controls allowed by Android permissions
and device policy. Avoid granting Accessibility or device-admin access to untrusted builds.

## Build
Open this folder as a project in Android Studio, allow Gradle sync, then run `app`.
The project uses Android Gradle Plugin 8.13.0, Kotlin 2.2.20, compileSdk 36, and
Backdrop 2.0.1 from Maven Central.

A GitHub Actions workflow is included to build a debug APK after you upload this project
to a GitHub repository. The APK is a debug build and is not Play Store ready.
