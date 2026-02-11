# Как да добавите нови маршрути

## Стъпка 1: Отворете файла с маршрути

Редактирайте файла: `app/src/main/java/com/velotravel/data/Routes.kt`

## Стъпка 2: Създайте нов маршрут

Добавете нов маршрут в обекта `Routes`:

```kotlin
val SOFIA_BANSKO = Route(
    id = "sofia-bansko",                    // Уникален идентификатор
    name = "София → Банско",                // Име на маршрута
    startCity = "София",                    // Начален град
    endCity = "Банско",                     // Краен град
    totalKm = 160.0,                        // Обща дистанция в километри
    description = "Път към планината",     // Кратко описание
    milestones = listOf(                    // Етапи по пътя
        Milestone(0.0, "София", "Начало"),
        Milestone(40.0, "Самоков"),
        Milestone(80.0, "Белица"),
        Milestone(120.0, "Разлог"),
        Milestone(160.0, "Банско", "Планинският курорт!")
    )
)
```

## Стъпка 3: Добавете маршрута в списъка

В същия файл, намерете функцията `getAll()` и добавете новия маршрут:

```kotlin
fun getAll() = listOf(
    SOFIA_PLOVDIV,
    SOFIA_BURGAS,
    SOFIA_RUSE,
    SOFIA_VARNA,
    SOFIA_BANSKO  // Новият маршрут
)
```

## Пълен пример

```kotlin
object Routes {
    // ... съществуващи маршрути ...
    
    val SOFIA_BANSKO = Route(
        id = "sofia-bansko",
        name = "София → Банско",
        startCity = "София",
        endCity = "Банско",
        totalKm = 160.0,
        description = "Път към планината",
        milestones = listOf(
            Milestone(0.0, "София", "Начало на пътешествието"),
            Milestone(40.0, "Самоков", "Край Рила"),
            Milestone(80.0, "Белица"),
            Milestone(120.0, "Разлог", "Близо до Пирин"),
            Milestone(160.0, "Банско", "Планинският курорт!")
        )
    )
    
    val VARNA_BURGAS = Route(
        id = "varna-burgas",
        name = "Варна → Бургас",
        startCity = "Варна",
        endCity = "Бургас",
        totalKm = 130.0,
        description = "Крайбрежен маршрут",
        milestones = listOf(
            Milestone(0.0, "Варна"),
            Milestone(35.0, "Обзор"),
            Milestone(70.0, "Несебър", "Древният град"),
            Milestone(100.0, "Поморие"),
            Milestone(130.0, "Бургас")
        )
    )
    
    fun getAll() = listOf(
        SOFIA_PLOVDIV,
        SOFIA_BURGAS,
        SOFIA_RUSE,
        SOFIA_VARNA,
        SOFIA_BANSKO,
        VARNA_BURGAS
    )
    
    fun getById(id: String) = getAll().find { it.id == id }
}
```

## Параметри на Route

- `id`: Уникален идентификатор (използвайте lowercase с тире)
- `name`: Показваното име (може да съдържа български букви и символи)
- `startCity`: Начален град
- `endCity`: Краен град
- `totalKm`: Обща дистанция (трябва да съвпада с последния milestone)
- `description`: Кратко описание (опционално)
- `milestones`: Списък с етапи

## Параметри на Milestone

- `kmFromStart`: Километри от началото (започва от 0.0)
- `cityName`: Име на града/етапа
- `description`: Допълнително описание (опционално)

## Съвети

1. **Реалистични дистанции**: Използвайте реални километри между градовете
2. **Подходящи етапи**: Добавяйте етап на всеки 30-50 км за по-добро усещане за напредък
3. **Интересни описания**: Добавете кратки, мотивиращи описания към етапите
4. **Сортиране**: Подредете маршрутите в `getAll()` от най-кратки към най-дълги

## След промяната

1. Запазете файла
2. Компилирайте приложението отново:
   ```cmd
   gradlew.bat assembleDebug
   ```
3. Инсталирайте новата версия на телефона

Новият маршрут ще се появи автоматично в екрана за избор на маршрут!
