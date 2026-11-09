# Alise×Kutty Android app

Android Studio project with GitHub Actions APK building configured.

## Build on GitHub using your phone

1. Download and extract this ZIP.
2. Create a **private** repository named `Alise-Kutty` at https://github.com/new.
3. Upload the *contents* of the extracted `alise_kutty_app` folder to the repository root. Make sure `.github/workflows/android.yml` is included. On GitHub mobile, enable showing hidden files if needed; alternatively create `.github/workflows/android.yml` using **Add file → Create new file** and paste the workflow from this project.
4. Open the repository's **Actions** tab. The workflow runs after a push. You can also choose **Build Alise×Kutty APK → Run workflow**.
5. When the run finishes with a green check, open it and download the `Alise-Kutty-debug-apk` artifact. Extract the downloaded artifact ZIP to get `app-debug.apk`.

## Local build

Requires JDK 17 and Gradle 8.9:

```bash
gradle assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`.

## Notes

- This is a standalone Android app project, not the original Discord APK source and not a modified Discord client.
- The debug APK is suitable for testing. A distributable release APK should be signed with your own private signing key; never commit that key or its passwords to GitHub.
- Discord community button/invite configuration is part of the app; Discord OAuth sign-in is not configured in this starter project.
