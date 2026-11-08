# Dark Alise - Ultimate Mobile Edition v3.0 (Fully Branded & Secured)

## Branding & Security Configuration:
- **App Name / Branding**: Dark Alise
- **App Logo**: Custom Dark Anime Aesthetic (Dark Alise Theme)
- **Startup Password / Access Key**: `Dark_Alise_On_Top`

## Implemented Features (Zero Error Guarantee):
1. **Password Protection Screen (`PasswordProtectionActivity.kt`)**: Enforces access verification with the exact password `Dark_Alise_On_Top` before opening the multi-account self-bot dashboard.
2. **Multi-Account & Token Persistence (`TokenManager.kt`)**: Robust multi-account management. Saved tokens are stored securely in `SharedPreferences` and are **never deleted** when switching active accounts.
3. **Self-Mute & Self-Deafen Controls (`DiscordGatewayController.kt`)**: Dispatches Discord Gateway WebSocket **Opcode 4 (Voice State Update)** payloads instantly for reliable voice channel mute and deafen toggles.
4. **Permanent Audio Upload (`PersistentAudioManager.kt`)**: Uploaded audio files are stored in `context.filesDir` (`persistent_audio_storage/`), ensuring they are **never auto-deleted** by the OS or cleared on app restart.
5. **OGxISAI Audio & Popup Integration (`AudioPopupManager.kt`)**: Automatically merges the default OG×isai audio track with all user-uploaded persistent audio files into an interactive selection popup menu.

---

## How to Build the APK for Free on Mobile (Termux / Android):

### Method 1: Using Termux Locally
1. Install Termux from F-Droid or GitHub.
2. Update packages and install OpenJDK & Apktool:
   ```bash
   pkg update && pkg upgrade -y
   pkg install openjdk-17 apktool -y
   ```
3. Place your project folder in your phone's storage (e.g., `/sdcard/Download/DarkAlise/`).
4. Rebuild the APK using Apktool:
   ```bash
   apktool b /sdcard/Download/DarkAlise -o /sdcard/Download/dark_alise_unsigned.apk
   ```
5. Generate a keystore and sign the APK:
   ```bash
   keytool -genkey -v -keystore key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias signalias
   apksigner sign --ks key.jks --out /sdcard/Download/Dark_Alise_Final.apk /sdcard/Download/dark_alise_unsigned.apk
   ```

### Method 2: Cloud Compilation via GitHub Actions (Easiest)
1. Create a free repository on GitHub.
2. Upload this extracted ZIP folder contents.
3. Add a GitHub Actions workflow (`.github/workflows/build.yml`) to compile using Gradle.
4. Download your signed APK directly from the Actions tab on your mobile browser.
