# Велопътешественик - Android Приложение

## Концепция
Приложение за велоергометър, което превръща тренировките в виртуално пътуване из България.

## Архитектура

### Слоеве:
1. **UI Layer** (Jetpack Compose)
   - Екрани: Home, Route Selection, Progress, History, Achievements
   - ViewModels за всеки екран

2. **Domain Layer**
   - Use Cases: AddDailyKm, GetProgress, CheckAchievements
   - Models: Route, DailyEntry, Achievement, UserProgress

3. **Data Layer**
   - Repository pattern
   - Room Database за локално съхранение
   - DataStore за настройки

### Технологии:
- Kotlin
- Jetpack Compose
- Room Database
- Kotlin Coroutines & Flow
- Material 3 (Dark Theme)

## Основни екрани:

1. **Home Screen** - Текущ напредък, бърз вход на км
2. **Route Selection** - Избор на маршрут
3. **Progress Screen** - Визуална карта с етапи
4. **History Screen** - Дневна история
5. **Achievements Screen** - Постижения

## Как да компилираш без Android Studio:

### Вариант 1: Gradle от командна линия
```bash
# Windows
gradlew.bat assembleDebug

# Linux/Mac
./gradlew assembleDebug
```

APK файлът ще е в: `app/build/outputs/apk/debug/app-debug.apk`

### Вариант 2: Online Build
- Качи проекта в GitHub
- Използвай GitHub Actions за автоматичен build
- Или използвай AppCenter / Bitrise

### Инсталация на устройство:
```bash
adb install app-debug.apk
```

## Добавяне на нови маршрути:

Редактирай `app/src/main/java/com/velotravel/data/Routes.kt`:

```kotlin
Route(
    id = "sofia-ruse",
    name = "София → Русе",
    startCity = "София",
    endCity = "Русе",
    totalKm = 320.0,
    milestones = listOf(
        Milestone(0.0, "София"),
        Milestone(80.0, "Плевен"),
        Milestone(160.0, "Бяла"),
        Milestone(320.0, "Русе")
    )
)
```
