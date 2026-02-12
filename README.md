# Велопътешественик - Android Приложение

## Концепция
Приложение за велоергометър, което превръща тренировките в виртуално пътуване из България.

## Версия 1.1.0

### Нови функции:
- ✅ 50+ маршрута из цяла България (кратки 4-15км, средни, дълги)
- ✅ 4-таб навигация (Начало, Текущ, Маршрути, Постижения)
- ✅ Визуална карта на България с региони
- ✅ Автоматична проверка за актуализации от GitHub
- ✅ Изтегляне и инсталиране на нови версии директно от приложението

## Архитектура

### Слоеве:
1. **UI Layer** (Jetpack Compose)
   - Екрани: Home, Current Route, Route Selection, History, Achievements
   - ViewModels за всеки екран
   - Bottom Navigation Bar

2. **Domain Layer**
   - Use Cases: AddDailyKm, GetProgress, CheckAchievements
   - Models: Route, DailyEntry, Achievement, UserProgress

3. **Data Layer**
   - Repository pattern
   - Room Database за локално съхранение
   - UpdateChecker за GitHub интеграция

### Технологии:
- Kotlin
- Jetpack Compose
- Room Database
- Kotlin Coroutines & Flow
- Material 3 (Dark Theme)
- GitHub API за актуализации

## Основни екрани:

1. **Home Screen** - Текущ напредък, бърз вход на км
2. **Current Route Screen** - Визуална карта с велосипедист и напредък
3. **Route Selection** - Избор от 50+ маршрута
4. **History Screen** - Дневна история
5. **Achievements Screen** - Постижения

## Как да компилираш:

### Gradle от командна линия
```bash
# Windows
gradlew.bat assembleDebug

# Linux/Mac
./gradlew assembleDebug
```

APK файлът ще е в: `app/build/outputs/apk/debug/app-debug.apk`

### Инсталация на устройство:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## GitHub Actions Release

За да създадеш нова версия:

```bash
# Промени версията в UpdateChecker.kt
# Commit промените
git add .
git commit -m "Version 1.2.0"

# Създай tag
git tag v1.2.0

# Push tag
git push origin v1.2.0
```

GitHub Actions автоматично ще:
1. Компилира APK
2. Създаде Release
3. Качи APK файла

Всички потребители ще получат известие за актуализация при стартиране на приложението.

## Маршрути

Приложението включва 50+ маршрута:
- **Кратки (4-15 км)**: Перфектни за начинаещи
- **Средни (20-50 км)**: Средно ниво
- **Дълги (60-150 км)**: Напреднали
- **Много дълги (200+ км)**: Експертно ниво

Всички маршрути покриват реални градове и забележителности в България.

## Лиценз
MIT License
