# Архитектура на приложението

## Обща структура

Приложението следва Clean Architecture с три основни слоя:

```
┌─────────────────────────────────────┐
│         UI Layer (Compose)          │
│  - Screens                          │
│  - ViewModels                       │
│  - Theme                            │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│       Domain Layer (Business)       │
│  - Models                           │
│  - Repository Interface             │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│        Data Layer (Storage)         │
│  - Room Database                    │
│  - DAOs                             │
│  - Repository Implementation        │
└─────────────────────────────────────┘
```

## Структура на файловете

```
app/src/main/java/com/velotravel/
│
├── MainActivity.kt                 # Главна Activity
├── VeloTravelApp.kt               # Application клас
│
├── data/
│   ├── model/                     # Data models
│   │   ├── Route.kt              # Маршрут
│   │   ├── Milestone.kt          # Етап
│   │   ├── DailyEntry.kt         # Дневен запис
│   │   ├── UserProgress.kt       # Напредък
│   │   └── Achievement.kt        # Постижение
│   │
│   ├── local/                     # Локално съхранение
│   │   ├── VeloDatabase.kt       # Room Database
│   │   ├── DailyEntryDao.kt      # DAO за записи
│   │   ├── UserProgressDao.kt    # DAO за напредък
│   │   └── AchievementDao.kt     # DAO за постижения
│   │
│   ├── repository/
│   │   └── VeloRepository.kt     # Repository pattern
│   │
│   └── Routes.kt                  # Предефинирани маршрути
│
└── ui/
    ├── theme/
    │   └── Theme.kt              # Material 3 тема
    │
    ├── home/
    │   ├── HomeScreen.kt         # Главен екран
    │   └── HomeViewModel.kt      # ViewModel
    │
    ├── routes/
    │   └── RouteSelectionScreen.kt  # Избор на маршрут
    │
    ├── history/
    │   └── HistoryScreen.kt      # История
    │
    └── achievements/
        └── AchievementsScreen.kt # Постижения
```

## Слоеве в детайли

### 1. UI Layer (Jetpack Compose)

Отговаря за визуализацията и взаимодействието с потребителя.

**Компоненти:**
- `@Composable` функции за UI
- `ViewModel` за управление на състоянието
- `StateFlow` за реактивни данни
- Material 3 Design System

**Екрани:**
1. **HomeScreen** - Главен екран с текущ напредък и въвеждане на км
2. **RouteSelectionScreen** - Избор на маршрут
3. **HistoryScreen** - История на дневните записи
4. **AchievementsScreen** - Постижения

### 2. Domain Layer

Съдържа бизнес логиката и моделите.

**Models:**
- `Route` - Маршрут с етапи
- `Milestone` - Етап по маршрута
- `DailyEntry` - Дневен запис на километри
- `UserProgress` - Напредък по маршрут
- `Achievement` - Постижение

### 3. Data Layer

Управлява съхранението на данни.

**Room Database:**
- Локална SQLite база данни
- Три таблици: daily_entries, user_progress, achievements
- DAO (Data Access Object) за всяка таблица

**Repository Pattern:**
- Единна точка за достъп до данни
- Абстракция над базата данни
- Бизнес логика за постижения

## Поток на данни

### Добавяне на километри:

```
User Input (HomeScreen)
    ↓
HomeViewModel.addKilometers()
    ↓
VeloRepository.addDailyEntry()
    ↓
┌─────────────────────────────────┐
│ 1. Запис в DailyEntry таблица   │
│ 2. Обновяване на UserProgress   │
│ 3. Проверка за постижения       │
└─────────────────────────────────┘
    ↓
Flow емитира нови данни
    ↓
UI се обновява автоматично
```

### Реактивен поток с Flow:

```kotlin
// Repository
fun getProgress(routeId: String): Flow<UserProgress?>

// ViewModel
val progress: StateFlow<UserProgress?> = 
    repository.getProgress(routeId)
        .stateIn(viewModelScope, ...)

// UI
val progress by viewModel.progress.collectAsState()
```

## Ключови технологии

### Jetpack Compose
- Декларативен UI
- Реактивно обновяване
- Material 3 компоненти

### Room Database
- Локално съхранение
- Type-safe SQL queries
- Coroutines подкрепа

### Kotlin Coroutines & Flow
- Асинхронни операции
- Реактивни потоци от данни
- Structured concurrency

### Navigation Compose
- Навигация между екрани
- Type-safe аргументи

## Разширяване на приложението

### Добавяне на нов екран:

1. Създайте `@Composable` функция
2. Създайте `ViewModel` (ако е нужно)
3. Добавете route в `NavHost`
4. Добавете навигация от други екрани

### Добавяне на ново постижение:

Редактирайте `Achievement.kt`:

```kotlin
val NEW_ACHIEVEMENT = Achievement(
    id = "unique_id",
    title = "Заглавие",
    description = "Описание",
    icon = "🎯"
)
```

Добавете логика в `VeloRepository.checkAndUnlockAchievements()`

### Добавяне на нова функция:

1. Добавете метод в `VeloRepository`
2. Създайте/обновете ViewModel
3. Обновете UI компонента

## Тестване

### Unit Tests (препоръчително):
```kotlin
// ViewModel тест
@Test
fun `adding kilometers updates progress`() {
    // Given
    val viewModel = HomeViewModel(mockRepository)
    
    // When
    viewModel.onKmInputChange("10.5")
    viewModel.addKilometers()
    
    // Then
    verify(mockRepository).addDailyEntry(any(), eq(10.5))
}
```

### UI Tests (препоръчително):
```kotlin
@Test
fun homeScreen_displaysProgress() {
    composeTestRule.setContent {
        HomeScreen(viewModel, ...)
    }
    
    composeTestRule
        .onNodeWithText("София → Варна")
        .assertIsDisplayed()
}
```

## Performance съображения

1. **Database операции** - Винаги в coroutine (suspend функции)
2. **Flow вместо LiveData** - По-модерен и гъвкав
3. **StateFlow в ViewModel** - Кеширане на последната стойност
4. **Lazy initialization** - Database и Repository се създават при нужда

## Сигурност

- Локално съхранение (без мрежа)
- Няма лични данни
- Няма нужда от permissions
- Данните остават на устройството
