# Readify - Android Reading Application

<div align="center">
  
  ### Your Personal Digital Library
  
</div>

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Data Flow](#data-flow)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Building the Project](#building-the-project)
- [Testing](#testing)
- [Contributing](#contributing)
- [Troubleshooting](#troubleshooting)
- [License](#license)
- [Contact & Support](#contact--support)
- [Acknowledgments](#acknowledgments)

---

## Overview

**Readify** is a modern Android application designed to provide users with a seamless digital reading experience. Built with Java and powered by Firebase, Readify offers a comprehensive platform for managing, reading, and discovering PDF books. Whether you're a student, professional, or book enthusiast, Readify transforms your Android device into a portable library.

### Key Highlights

- **PDF Reading** - Advanced PDF viewer with smooth rendering
- **User Authentication** - Secure login and registration system
- **Personal Library** - Organize and manage your book collection
- **Favorites** - Quick access to your favorite books
- **Book Store** - Discover and download new books
- **Categories** - Organize books by custom categories
- **Modern UI** - Clean, intuitive Material Design interface
- **Cloud Sync** - Firebase-backed data synchronization

---

## Features

### Authentication & User Management
- **User Registration** - Create new accounts with email validation
- **Secure Login** - Firebase Authentication integration
- **Password Recovery** - Reset forgotten passwords via email
- **Profile Management** - Edit and update user information
- **Session Management** - Persistent login with secure token handling

### Reading Experience
- **PDF Viewer** - High-performance PDF rendering using Barteksc PDF Viewer
- **Smooth Navigation** - Page scrolling and zooming capabilities
- **Reading Progress** - Bookmark and resume functionality
- **Full-Screen Mode** - Distraction-free reading experience

### Library Management
- **Personal Library** - View and manage your book collection
- **Category Organization** - Create and manage custom categories
- **Add Books** - Upload PDF files to your library
- **Featured Books** - Showcase recommended or popular books
- **Search Functionality** - Quick search through your collection

### Favorites & Collections
- **Favorite Books** - Mark books as favorites for quick access
- **Collections** - Organize books into custom collections
- **Quick Access** - Dedicated favorites section

### Store & Discovery
- **Book Store** - Browse available books
- **Book Details** - View comprehensive book information
- **Download Books** - Save books to your library
- **Category Browsing** - Filter books by categories

### User Interface
- **Material Design** - Modern, consistent UI/UX
- **Dark Mode Support** - Eye-friendly reading in low light
- **Responsive Layouts** - Optimized for different screen sizes
- **Smooth Animations** - Polished transitions and effects
- **Custom Fonts** - Enhanced typography with Adlam Display & Alexandria

---

## Architecture

Readify follows a **layered architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│  (Activities, Adapters, UI Components)  │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│          Business Logic Layer           │
│   (Managers, Filters, Helper Classes)   │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│           Data Layer                    │
│  (Models, API Services, Firebase SDK)   │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│         Backend Services                │
│  (Firebase Auth, Database, Storage)     │
└─────────────────────────────────────────┘
```

### Architecture Components

#### 1. **Presentation Layer**
- **Activities**: Handle user interactions and lifecycle management
  - `MainActivity` - Entry point and splash screen
  - `Login` / `SignUp` - Authentication flows
  - `Dashboard` / `HomeActivity` - Main navigation hub
  - `LibraryActivity` - Personal library management
  - `PDFViewerActivity` - PDF reading interface
  - `ProfileActivity` - User profile management

- **Adapters**: Manage RecyclerView data binding
  - `AdapterCategory` - Category list display
  - `StoreAdapter` - Book store grid/list
  - `LibraryAdapter` - Library book display
  - `FeaturedBookAdapter` - Featured books carousel

#### 2. **Business Logic Layer**
- `FavoriteManager` - Manages favorite books state
- `FilterCategory` - Category filtering logic
- `HelperClass` - Utility functions and helpers

#### 3. **Data Layer**
- **Models**:
  - `BookModel` - Book data structure
  - `ModelCategory` - Category data structure
  - `BookResponse` - API response wrapper
  
- **API Services**:
  - `BookApi` - Retrofit interface for book APIs

#### 4. **Backend Services**
- Firebase Authentication - User management
- Firebase Realtime Database - Data storage
- Firebase Storage - PDF file storage

### Design Patterns

- **ViewBinding** - Type-safe view references
- **Adapter Pattern** - RecyclerView implementations
- **Observer Pattern** - Firebase realtime listeners
- **Singleton Pattern** - FavoriteManager, HelperClass
- **MVC Pattern** - Activity-based structure

---

## Data Flow

### Authentication Flow
```
User Input → Login Activity → Firebase Auth
                                    ↓
                              Validate Credentials
                                    ↓
                          Success → Dashboard Activity
                                    ↓
                          Failure → Error Message
```

### Book Reading Flow
```
Library/Store → Book Selection → BookReaderActivity
                                        ↓
                                  Load PDF URL
                                        ↓
                              PDFViewerActivity
                                        ↓
                          Render PDF using PDFView
                                        ↓
                              Track Reading Progress
```

### Data Synchronization Flow
```
Local Action → Firebase Realtime Database
                        ↓
                  Cloud Update
                        ↓
              Realtime Listener
                        ↓
                  UI Update
```

---

## Tech Stack

### Core Technologies
| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 11 | Primary programming language |
| **Android SDK** | API 24-34 | Android development framework |
| **Gradle** | 8.7.2 | Build automation |

### Android Components
- **AndroidX Libraries**
  - AppCompat `1.7.0`
  - ConstraintLayout `2.2.0`
  - RecyclerView `1.3.2`
  - Material Components `1.12.0`
  - Lifecycle Components `2.8.7`

### Backend & Cloud
- **Firebase**
  - Firebase Authentication `23.1.0` - User authentication
  - Firebase Realtime Database `21.0.0` - Data storage
  - Firebase Storage `21.0.1` - File storage
  - Google Services `4.4.2`

### Networking
- **Retrofit** `2.9.0` - REST API client
- **Gson Converter** `2.9.0` - JSON serialization
- **OkHttp Logging** `4.9.3` - Network debugging

### PDF Rendering
- **Barteksc Android PDF Viewer** `2.8.2` - PDF display library

### Development Tools
- **View Binding** - Type-safe view access
- **ProGuard** - Code obfuscation and optimization

### Testing
- **JUnit** `4.13.2` - Unit testing
- **Espresso** `3.6.1` - UI testing
- **AndroidX Test** `1.2.1` - Testing framework

---

## Project Structure

```
Readify-Android-App/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/readify/
│   │   │   │   ├── Activities/
│   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   ├── Dashboard.java
│   │   │   │   │   ├── HomeActivity.java
│   │   │   │   │   ├── Login.java
│   │   │   │   │   ├── SignUp.java
│   │   │   │   │   ├── ForgetPassword.java
│   │   │   │   │   ├── ProfileActivity.java
│   │   │   │   │   ├── EditProfile.java
│   │   │   │   │   ├── LibraryActivity.java
│   │   │   │   │   ├── CategoryDashboardActivity.java
│   │   │   │   │   ├── CatagoryAddActivity.java
│   │   │   │   │   ├── PdfAddActivity.java
│   │   │   │   │   ├── PDFViewerActivity.java
│   │   │   │   │   ├── BookReaderActivity.java
│   │   │   │   │   ├── FavouriteActivity.java
│   │   │   │   │   ├── FeatureBookActivity.java
│   │   │   │   │   ├── StoreActivity.java
│   │   │   │   │   └── TermCondition.java
│   │   │   │   ├── Adapters/
│   │   │   │   │   ├── AdapterCategory.java
│   │   │   │   │   ├── LibraryAdapter.java
│   │   │   │   │   ├── StoreAdapter.java
│   │   │   │   │   └── FeaturedBookAdapter.java
│   │   │   │   ├── Models/
│   │   │   │   │   ├── BookModel.java
│   │   │   │   │   ├── ModelCategory.java
│   │   │   │   │   └── BookResponse.java
│   │   │   │   ├── API/
│   │   │   │   │   └── BookApi.java
│   │   │   │   ├── Managers/
│   │   │   │   │   └── FavoriteManager.java
│   │   │   │   ├── Utils/
│   │   │   │   │   ├── HelperClass.java
│   │   │   │   │   ├── FilterCategory.java
│   │   │   │   │   └── DimActivity.java
│   │   │   ├── res/
│   │   │   │   ├── layout/          # XML layouts
│   │   │   │   ├── drawable/        # Vector drawables & images
│   │   │   │   ├── anim/            # Animations
│   │   │   │   ├── font/            # Custom fonts
│   │   │   │   ├── values/          # Strings, colors, styles
│   │   │   │   └── mipmap/          # App icons
│   │   │   ├── AndroidManifest.xml
│   │   │   └── assets/
│   │   ├── androidTest/             # Instrumented tests
│   │   └── test/                    # Unit tests
│   ├── build.gradle.kts
│   ├── google-services.json         # Firebase configuration
│   └── proguard-rules.pro
├── gradle/
│   ├── libs.versions.toml           # Dependency versions
│   └── wrapper/
├── build.gradle.kts                 # Project-level build file
├── settings.gradle.kts
├── gradle.properties
├── gradlew                          # Gradle wrapper (Unix)
├── gradlew.bat                      # Gradle wrapper (Windows)
└── README.md
```

### Key Directories Explained

- **`app/src/main/java/`** - Application source code
- **`app/src/main/res/`** - Application resources (layouts, drawables, strings)
- **`app/src/main/assets/`** - Raw asset files
- **`app/src/androidTest/`** - Instrumented tests (run on device)
- **`app/src/test/`** - Unit tests (run on JVM)
- **`gradle/`** - Gradle configuration and wrapper files

---

## Prerequisites

Before you begin, ensure you have the following installed:

### Required Software
- **Java Development Kit (JDK)** 11 or higher
  - Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
  
- **Android Studio** Hedgehog (2023.1.1) or later
  - Download: [Android Studio](https://developer.android.com/studio)
  
- **Android SDK** with the following components:
  - Android SDK Platform 34 (Android 14)
  - Android SDK Build-Tools 34.0.0
  - Android Emulator (for testing)

### Firebase Setup
- **Firebase Account** - [Create one here](https://firebase.google.com/)
- **Firebase Project** - Set up a new Firebase project
- **google-services.json** - Download from Firebase Console

### Recommended Tools
- **Git** - For version control
- **Postman** - For API testing (optional)

---

## Quick Start

### 1. Clone the Repository

```bash
# Clone the repository
git clone https://github.com/yourusername/Readify-Android-App.git

# Navigate to project directory
cd Readify-Android-App
```

### 2. Firebase Configuration

1. **Create Firebase Project**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Click "Add Project"
   - Follow the setup wizard

2. **Add Android App to Firebase**
   - Click "Add App" → Select Android
   - Package name: `com.example.readify`
   - Download `google-services.json`
   - Place it in `app/` directory

3. **Enable Firebase Services**
   - **Authentication**: Enable Email/Password sign-in
   - **Realtime Database**: Create database in test/production mode
   - **Storage**: Set up Firebase Storage with appropriate rules

4. **Firebase Security Rules** (Optional but Recommended)

   **Realtime Database Rules:**
   ```json
   {
     "rules": {
       "users": {
         "$uid": {
           ".read": "$uid === auth.uid",
           ".write": "$uid === auth.uid"
         }
       },
       "books": {
         ".read": "auth != null",
         ".write": "auth != null"
       },
       "categories": {
         ".read": "auth != null",
         ".write": "auth != null"
       }
     }
   }
   ```

   **Storage Rules:**
   ```
   rules_version = '2';
   service firebase.storage {
     match /b/{bucket}/o {
       match /pdfs/{allPaths=**} {
         allow read: if request.auth != null;
         allow write: if request.auth != null;
       }
     }
   }
   ```

### 3. Open Project in Android Studio

```bash
# Open Android Studio
# File → Open → Select Readify-Android-App folder
```

### 4. Sync Project

- Android Studio will automatically detect `build.gradle.kts`
- Click "Sync Now" when prompted
- Wait for Gradle sync to complete

### 5. Configure API Endpoint (If Using External API)

If you're using the Retrofit API service, update the base URL in `BookApi.java`:

```java
public interface BookApi {
    String BASE_URL = "https://your-api-endpoint.com/";
    // ... rest of the code
}
```

### 6. Run the Application

**Using Emulator:**
1. Tools → Device Manager
2. Create Virtual Device (Recommended: Pixel 6, API 34)
3. Click Run (▶️) button

**Using Physical Device:**
1. Enable Developer Options on your device
2. Enable USB Debugging
3. Connect device via USB
4. Select device in Android Studio
5. Click Run (▶️) button

---

## Configuration

### Application Configuration

Edit `app/build.gradle.kts` to customize:

```kotlin
android {
    namespace = "com.example.readify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.readify"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}
```

### ProGuard Configuration

For release builds, edit `proguard-rules.pro`:

```proguard
# Keep Firebase classes
-keep class com.google.firebase.** { *; }

# Keep PDF Viewer classes
-keep class com.github.barteksc.pdfviewer.** { *; }

# Keep Retrofit
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
```

### Gradle Properties

Edit `gradle.properties` for build optimization:

```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
org.gradle.parallel=true
org.gradle.caching=true
android.useAndroidX=true
```

---

## Building the Project

### Debug Build

```bash
# Using Gradle Wrapper (Recommended)
./gradlew assembleDebug

# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release Build

```bash
# Generate signed release APK
./gradlew assembleRelease

# Or create signed bundle
./gradlew bundleRelease

# Output: app/build/outputs/apk/release/app-release.apk
```

### Generate Signed APK via Android Studio

1. Build → Generate Signed Bundle / APK
2. Select APK or Android App Bundle
3. Create or choose existing keystore
4. Fill in keystore credentials
5. Select build variant (release)
6. Click Finish

### Build Variants

- **debug** - Development build with debugging enabled
- **release** - Production build with ProGuard/R8 optimization

---

## Testing

### Unit Tests

```bash
# Run all unit tests
./gradlew test

# Run specific test class
./gradlew test --tests LibraryActivityTest
```

### Instrumented Tests

```bash
# Run on connected device/emulator
./gradlew connectedAndroidTest

# Run specific test
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.readify.LoginTest
```

### Manual Testing Checklist

- [ ] User registration with valid/invalid data
- [ ] Login with correct/incorrect credentials
- [ ] Password reset functionality
- [ ] Profile update operations
- [ ] Book upload and viewing
- [ ] Category creation and management
- [ ] Favorite books functionality
- [ ] PDF viewing and navigation
- [ ] Search functionality
- [ ] Network connectivity handling
- [ ] Offline mode behavior

---

## Contributing

We welcome contributions from the community! Here's how you can help:

### Getting Started

1. **Fork the Repository**
   ```bash
   # Click 'Fork' button on GitHub
   ```

2. **Create a Feature Branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Make Your Changes**
   - Write clean, documented code
   - Follow existing code style
   - Add tests for new features

4. **Commit Changes**
   ```bash
   git commit -m "Add: amazing feature description"
   ```

5. **Push to Branch**
   ```bash
   git push origin feature/amazing-feature
   ```

6. **Open Pull Request**
   - Provide clear description
   - Reference related issues
   - Wait for review

### Commit Message Guidelines

Follow conventional commits:

- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation changes
- `style:` Code style changes (formatting)
- `refactor:` Code refactoring
- `test:` Test additions or changes
- `chore:` Build process or auxiliary tool changes

Example:
```
feat: add dark mode support to PDF viewer
fix: resolve crash on empty library
docs: update installation instructions
```

### Code Style Guidelines

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods small and focused
- Use proper indentation (4 spaces)

### Pull Request Checklist

- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Comments added to complex code
- [ ] Documentation updated
- [ ] No new warnings generated
- [ ] Tests added/updated
- [ ] All tests passing
- [ ] Branch is up to date with main

---

## Troubleshooting

### Common Issues and Solutions

#### 1. Gradle Sync Failed

**Problem**: Gradle sync fails with dependency resolution errors

**Solutions**:
```bash
# Clear Gradle cache
./gradlew clean

# Invalidate caches in Android Studio
File → Invalidate Caches → Invalidate and Restart
```

#### 2. Firebase Connection Issues

**Problem**: App can't connect to Firebase

**Solutions**:
- Verify `google-services.json` is in `app/` directory
- Check package name matches Firebase console
- Ensure internet permission in AndroidManifest.xml
- Verify Firebase services are enabled

#### 3. PDF Not Loading

**Problem**: PDF files won't display

**Solutions**:
- Check internet connectivity
- Verify Firebase Storage rules allow read access
- Ensure PDF URL is valid and accessible
- Check storage permissions in AndroidManifest.xml

#### 4. Build Errors

**Problem**: Build fails with compilation errors

**Solutions**:
```bash
# Clean and rebuild
./gradlew clean build

# Check Java version
java -version  # Should be 11 or higher

# Update Android Studio and SDK tools
```

#### 5. App Crashes on Startup

**Problem**: Application crashes immediately after launch

**Solutions**:
- Check logcat for stack trace:
  ```bash
  adb logcat | grep -i exception
  ```
- Verify all required permissions
- Check Firebase initialization
- Ensure minimum SDK version compatibility

#### 6. Authentication Errors

**Problem**: Login/signup not working

**Solutions**:
- Verify Firebase Authentication is enabled
- Check email/password provider is activated
- Ensure network connectivity
- Verify API key configuration

#### 7. Out of Memory Errors

**Problem**: App crashes with OutOfMemoryError

**Solutions**:
- Increase heap size in `gradle.properties`:
  ```properties
  org.gradle.jvmargs=-Xmx4096m
  ```
- Optimize image resources
- Implement pagination for large lists

### Getting Help

If you encounter issues not listed here:

1. **Check Logcat**: View detailed error logs
   ```bash
   adb logcat -v time > logcat.txt
   ```

2. **Search Issues**: Check existing GitHub issues

3. **Create New Issue**: Provide:
   - Android version
   - Device model
   - Steps to reproduce
   - Logcat output
   - Screenshots (if applicable)

4. **Community Support**: Join our Discord/Slack channel

---

## License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2025 Readify Development Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## Contact & Support

### Get in Touch

- **Email**: ttawhid401@gmail.com
- **GitHub Issues**: [Report a bug](https://github.com/yourusername/Readify-Android-App/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yourusername/Readify-Android-App/discussions)

### Reporting Bugs

Please use our [Issue Template](https://github.com/yourusername/Readify-Android-App/issues/new) and include:
- Detailed description
- Steps to reproduce
- Expected vs actual behavior
- Screenshots or screen recordings
- Device and Android version
- Logcat output (if applicable)

### Feature Requests

We love hearing your ideas! Submit feature requests via:
- [GitHub Discussions](https://github.com/yourusername/Readify-Android-App/discussions)
- Tag with `enhancement` label

### Security Vulnerabilities

For security issues, please email: **ttawhid401@gmail.com**

Do not create public issues for security vulnerabilities.

---

## Acknowledgments

### Third-Party Libraries

- [Barteksc Android PDF Viewer](https://github.com/barteksc/AndroidPdfViewer) - PDF rendering
- [Firebase](https://firebase.google.com/) - Backend services
- [Retrofit](https://square.github.io/retrofit/) - HTTP client
- [Gson](https://github.com/google/gson) - JSON processing
- [Material Components](https://material.io/develop/android) - UI components

### Contributors

Thanks to all the amazing people who have contributed to this project!

<!-- ALL-CONTRIBUTORS-LIST:START -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

### Inspiration

- Inspired by modern reading apps and digital library management systems
- Special thanks to the Android developer community

### Resources

- [Android Developer Documentation](https://developer.android.com/)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Material Design Guidelines](https://material.io/design)

---



<div align="center">

### Star this repository if you find it helpful!

Made with love by the Readify Team

**[Back to Top](#readify---android-reading-application)**

</div>
