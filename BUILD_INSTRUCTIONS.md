# Инструкции за компилиране без Android Studio

## Предварителни изисквания

1. **Java Development Kit (JDK) 17**
   - Изтеглете от: https://adoptium.net/
   - Инсталирайте и добавете в PATH

2. **Android SDK Command Line Tools**
   - Изтеглете от: https://developer.android.com/studio#command-tools
   - Разархивирайте в папка (например `C:\Android\cmdline-tools`)
   - Добавете в PATH: `C:\Android\cmdline-tools\latest\bin`

3. **Инсталирайте необходимите SDK компоненти:**
   ```cmd
   sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
   ```

## Компилиране на приложението

### Windows:

1. Отворете Command Prompt в папката на проекта
2. Изпълнете:
   ```cmd
   gradlew.bat assembleDebug
   ```

3. APK файлът ще бъде в:
   ```
   app\build\outputs\apk\debug\app-debug.apk
   ```

### Инсталиране на устройство:

1. Активирайте Developer Options и USB Debugging на телефона
2. Свържете телефона с USB кабел
3. Изпълнете:
   ```cmd
   adb install app\build\outputs\apk\debug\app-debug.apk
   ```

## Алтернативни методи

### 1. GitHub Actions (Автоматичен build в облака)

Създайте файл `.github/workflows/build.yml`:

```yaml
name: Android Build

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
          
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
        
      - name: Build with Gradle
        run: ./gradlew assembleDebug
        
      - name: Upload APK
        uses: actions/upload-artifact@v3
        with:
          name: app-debug
          path: app/build/outputs/apk/debug/app-debug.apk
```

След push в GitHub, APK файлът ще е достъпен в раздел "Actions" → "Artifacts"

### 2. Online Build Services

- **AppCenter**: https://appcenter.ms/
- **Bitrise**: https://www.bitrise.io/
- **Codemagic**: https://codemagic.io/

## Проблеми и решения

### Грешка: "ANDROID_HOME not set"
```cmd
set ANDROID_HOME=C:\Android\sdk
```

### Грешка: "Java version"
Уверете се, че използвате JDK 17:
```cmd
java -version
```

### Грешка при компилация
Изчистете build кеша:
```cmd
gradlew.bat clean
gradlew.bat assembleDebug
```

## Подписване на APK (за публикуване)

1. Генерирайте keystore:
   ```cmd
   keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias
   ```

2. Добавете в `app/build.gradle.kts`:
   ```kotlin
   signingConfigs {
       create("release") {
           storeFile = file("my-release-key.jks")
           storePassword = "your-password"
           keyAlias = "my-key-alias"
           keyPassword = "your-password"
       }
   }
   ```

3. Компилирайте release версия:
   ```cmd
   gradlew.bat assembleRelease
   ```
