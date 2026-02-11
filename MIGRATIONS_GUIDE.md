# Database Migrations Guide

## Защо са нужни migrations?

Когато промениш структурата на базата данни (добавиш поле, таблица и т.н.), Room трябва да знае как да обнови старата база без да загуби данните.

## Как да добавиш migration:

### Стъпка 1: Увеличи версията на базата

В `VeloDatabase.kt`:
```kotlin
@Database(
    entities = [DailyEntry::class, UserProgress::class, Achievement::class],
    version = 2,  // Беше 1, сега е 2
    exportSchema = false
)
```

### Стъпка 2: Създай migration

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Пример: Добавяне на ново поле
        database.execSQL(
            "ALTER TABLE daily_entries ADD COLUMN mood TEXT NOT NULL DEFAULT ''"
        )
    }
}
```

### Стъпка 3: Добави migration към базата

```kotlin
fun getDatabase(context: Context): VeloDatabase {
    return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
            context.applicationContext,
            VeloDatabase::class.java,
            "velo_database"
        )
        .addMigrations(MIGRATION_1_2)  // Добави тук
        .build()
        INSTANCE = instance
        instance
    }
}
```

## Примери за migrations:

### Добавяне на ново поле:
```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE daily_entries ADD COLUMN notes TEXT NOT NULL DEFAULT ''")
    }
}
```

### Създаване на нова таблица:
```kotlin
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS challenges (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                targetKm REAL NOT NULL,
                isCompleted INTEGER NOT NULL DEFAULT 0
            )
        """)
    }
}
```

### Преименуване на колона:
```kotlin
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // SQLite не поддържа RENAME COLUMN директно, трябва да:
        // 1. Създадеш нова таблица
        // 2. Копираш данните
        // 3. Изтриеш старата
        // 4. Преименуваш новата
        
        db.execSQL("""
            CREATE TABLE daily_entries_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                routeId TEXT NOT NULL,
                date INTEGER NOT NULL,
                kilometers REAL NOT NULL,  -- Беше kmRidden
                notes TEXT NOT NULL DEFAULT ''
            )
        """)
        
        db.execSQL("""
            INSERT INTO daily_entries_new (id, routeId, date, kilometers, notes)
            SELECT id, routeId, date, kmRidden, notes FROM daily_entries
        """)
        
        db.execSQL("DROP TABLE daily_entries")
        db.execSQL("ALTER TABLE daily_entries_new RENAME TO daily_entries")
    }
}
```

## Тестване на migrations:

```kotlin
@Test
fun migrate1To2() {
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        VeloDatabase::class.java
    )
    
    // Създай база с версия 1
    val db = helper.createDatabase(TEST_DB, 1)
    
    // Добави тестови данни
    db.execSQL("INSERT INTO daily_entries VALUES (...)")
    db.close()
    
    // Мигрирай към версия 2
    helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
    
    // Провери че данните са запазени
}
```

## Best Practices:

1. **Винаги тествай migrations** преди release
2. **Никога не променяй стари migrations** - създай нови
3. **Документирай промените** във версията
4. **Backup данните** преди големи промени
5. **Използвай exportSchema = true** в development за да генерираш schema файлове

## Ако нещо се обърка:

### Fallback strategy:
```kotlin
Room.databaseBuilder(...)
    .fallbackToDestructiveMigration()  // ВНИМАНИЕ: Изтрива всички данни!
    .build()
```

Използвай само в development или ако наистина няма друг начин!

## Текуща версия:

- **Database version**: 1
- **Entities**: DailyEntry, UserProgress, Achievement
- **No migrations yet** - първата версия

Когато добавиш нови функции, следвай този guide за да запазиш данните на потребителите! 🎯
