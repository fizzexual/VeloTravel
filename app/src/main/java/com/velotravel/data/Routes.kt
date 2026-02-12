package com.velotravel.data

import com.velotravel.data.model.Milestone
import com.velotravel.data.model.Route

object Routes {
    // SHORT ROUTES (4-15 km) - Perfect for beginners
    val SOFIA_BOYANA = Route(
        id = "sofia-boyana",
        name = "София → Бояна",
        startCity = "София",
        endCity = "Бояна",
        totalKm = 8.0,
        description = "Кратка разходка до Боянската църква",
        milestones = listOf(
            Milestone(0.0, "София Център"),
            Milestone(8.0, "Бояна", "Боянска църква")
        )
    )
    
    val SOFIA_DRAGALEVTSI = Route(
        id = "sofia-dragalevtsi",
        name = "София → Драгалевци",
        startCity = "София",
        endCity = "Драгалевци",
        totalKm = 6.5,
        description = "Бърза обиколка до Витоша",
        milestones = listOf(
            Milestone(0.0, "София Център"),
            Milestone(6.5, "Драгалевци", "Подножието на Витоша")
        )
    )
    
    val PLOVDIV_ASENOVGRAD = Route(
        id = "plovdiv-asenovgrad",
        name = "Пловдив → Асеновград",
        startCity = "Пловдив",
        endCity = "Асеновград",
        totalKm = 14.0,
        description = "Приятна разходка до Асеновата крепост",
        milestones = listOf(
            Milestone(0.0, "Пловдив"),
            Milestone(14.0, "Асеновград", "Асенова крепост")
        )
    )
    
    val VARNA_GOLDEN_SANDS = Route(
        id = "varna-golden-sands",
        name = "Варна → Златни пясъци",
        startCity = "Варна",
        endCity = "Златни пясъци",
        totalKm = 12.0,
        description = "Крайбрежна алея до курорта",
        milestones = listOf(
            Milestone(0.0, "Варна Център"),
            Milestone(12.0, "Златни пясъци", "Морски курорт")
        )
    )
    
    val BURGAS_SOZOPOL_SHORT = Route(
        id = "burgas-sozopol-short",
        name = "Бургас → Созопол (кратък)",
        startCity = "Бургас",
        endCity = "Созопол",
        totalKm = 15.0,
        description = "Южно крайбрежие",
        milestones = listOf(
            Milestone(0.0, "Бургас"),
            Milestone(15.0, "Созопол", "Старинен град")
        )
    )
    
    val RUSE_IVANOVO = Route(
        id = "ruse-ivanovo",
        name = "Русе → Иваново",
        startCity = "Русе",
        endCity = "Иваново",
        totalKm = 13.0,
        description = "До скалните манастири",
        milestones = listOf(
            Milestone(0.0, "Русе"),
            Milestone(13.0, "Иваново", "Скални манастири")
        )
    )
    
    val VELIKO_TARNOVO_ARBANASI = Route(
        id = "veliko-tarnovo-arbanasi",
        name = "В. Търново → Арбанаси",
        startCity = "Велико Търново",
        endCity = "Арбанаси",
        totalKm = 4.5,
        description = "Най-кратък маршрут до село Арбанаси",
        milestones = listOf(
            Milestone(0.0, "Велико Търново"),
            Milestone(4.5, "Арбанаси", "Архитектурен резерват")
        )
    )
    
    val BANSKO_DOBRINISHTE = Route(
        id = "bansko-dobrinishte",
        name = "Банско → Добринище",
        startCity = "Банско",
        endCity = "Добринище",
        totalKm = 6.0,
        description = "Планински маршрут в Пирин",
        milestones = listOf(
            Milestone(0.0, "Банско"),
            Milestone(6.0, "Добринище", "Минерални бани")
        )
    )
    
    val KAZANLAK_SHIPKA = Route(
        id = "kazanlak-shipka",
        name = "Казанлък → Шипка",
        startCity = "Казанлък",
        endCity = "Шипка",
        totalKm = 12.0,
        description = "Долината на розите до паметника",
        milestones = listOf(
            Milestone(0.0, "Казанлък"),
            Milestone(12.0, "Шипка", "Паметник Шипка")
        )
    )
    
    val KOPRIVSHTITSA_LOOP = Route(
        id = "koprivshtitsa-loop",
        name = "Копривщица (обиколка)",
        startCity = "Копривщица",
        endCity = "Копривщица",
        totalKm = 8.0,
        description = "Кръгова обиколка на града",
        milestones = listOf(
            Milestone(0.0, "Копривщица Център"),
            Milestone(4.0, "Околности"),
            Milestone(8.0, "Копривщица Център", "Завършена обиколка")
        )
    )
    
    val MELNIK_ROZHEN = Route(
        id = "melnik-rozhen",
        name = "Мелник → Роженски манастир",
        startCity = "Мелник",
        endCity = "Роженски манастир",
        totalKm = 7.0,
        description = "Винен маршрут до манастира",
        milestones = listOf(
            Milestone(0.0, "Мелник"),
            Milestone(7.0, "Роженски манастир")
        )
    )
    
    val BELOGRADCHIK_LOOP = Route(
        id = "belogradchik-loop",
        name = "Белоградчик (обиколка)",
        startCity = "Белоградчик",
        endCity = "Белоградчик",
        totalKm = 10.0,
        description = "Обиколка на скалите",
        milestones = listOf(
            Milestone(0.0, "Белоградчик"),
            Milestone(5.0, "Скалите"),
            Milestone(10.0, "Белоградчик", "Завършена обиколка")
        )
    )
    
    val NESEBAR_SUNNY_BEACH = Route(
        id = "nesebar-sunny-beach",
        name = "Несебър → Слънчев бряг",
        startCity = "Несебър",
        endCity = "Слънчев бряг",
        totalKm = 5.0,
        description = "Най-кратък морски маршрут",
        milestones = listOf(
            Milestone(0.0, "Несебър"),
            Milestone(5.0, "Слънчев бряг")
        )
    )
    
    val BALCHIK_KAVARNA = Route(
        id = "balchik-kavarna",
        name = "Балчик → Каварна",
        startCity = "Балчик",
        endCity = "Каварна",
        totalKm = 14.0,
        description = "Северно черноморие",
        milestones = listOf(
            Milestone(0.0, "Балчик", "Двореца"),
            Milestone(14.0, "Каварна")
        )
    )
    
    val TRYAVNA_GABROVO = Route(
        id = "tryavna-gabrovo",
        name = "Трявна → Габрово",
        startCity = "Трявна",
        endCity = "Габрово",
        totalKm = 15.0,
        description = "Занаятчийски маршрут",
        milestones = listOf(
            Milestone(0.0, "Трявна"),
            Milestone(15.0, "Габрово", "Град на хумора")
        )
    )
    
    val SMOLYAN_PAMPOROVO = Route(
        id = "smolyan-pamporovo",
        name = "Смолян → Пампорово",
        startCity = "Смолян",
        endCity = "Пампорово",
        totalKm = 12.0,
        description = "Родопски маршрут",
        milestones = listOf(
            Milestone(0.0, "Смолян"),
            Milestone(12.0, "Пампорово", "Ски курорт")
        )
    )
    
    val SANDANSKI_MELNIK = Route(
        id = "sandanski-melnik",
        name = "Сандански → Мелник",
        startCity = "Сандански",
        endCity = "Мелник",
        totalKm = 13.0,
        description = "Винен маршрут",
        milestones = listOf(
            Milestone(0.0, "Сандански"),
            Milestone(13.0, "Мелник", "Най-малкият град")
        )
    )
    
    val HASKOVO_DIMITROVGRAD = Route(
        id = "haskovo-dimitrovgrad",
        name = "Хасково → Димитровград",
        startCity = "Хасково",
        endCity = "Димитровград",
        totalKm = 15.0,
        description = "Южна България",
        milestones = listOf(
            Milestone(0.0, "Хасково"),
            Milestone(15.0, "Димитровград")
        )
    )
    
    val YAMBOL_ELHOVO = Route(
        id = "yambol-elhovo",
        name = "Ямбол → Елхово",
        startCity = "Ямбол",
        endCity = "Елхово",
        totalKm = 14.5,
        description = "Източна България",
        milestones = listOf(
            Milestone(0.0, "Ямбол"),
            Milestone(14.5, "Елхово")
        )
    )
    
    val KYUSTENDIL_DUPNITSA = Route(
        id = "kyustendil-dupnitsa",
        name = "Кюстендил → Дупница",
        startCity = "Кюстендил",
        endCity = "Дупница",
        totalKm = 15.0,
        description = "Западна България",
        milestones = listOf(
            Milestone(0.0, "Кюстендил"),
            Milestone(15.0, "Дупница")
        )
    )
    
    // MEDIUM ROUTES (20-50 km)
    val SOFIA_SAMOKOV = Route(
        id = "sofia-samokov",
        name = "София → Самоков",
        startCity = "София",
        endCity = "Самоков",
        totalKm = 45.0,
        description = "Път към Рила",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(22.0, "Нови Искър"),
            Milestone(45.0, "Самоков", "Врата към Рила")
        )
    )
    
    val PLOVDIV_BACHKOVO = Route(
        id = "plovdiv-bachkovo",
        name = "Пловдив → Бачково",
        startCity = "Пловдив",
        endCity = "Бачково",
        totalKm = 28.0,
        description = "До Бачковския манастир",
        milestones = listOf(
            Milestone(0.0, "Пловдiv"),
            Milestone(14.0, "Асеновград"),
            Milestone(28.0, "Бачково", "Бачковски манастир")
        )
    )
    
    val VARNA_BALCHIK = Route(
        id = "varna-balchik",
        name = "Варна → Балчик",
        startCity = "Варна",
        endCity = "Балчик",
        totalKm = 42.0,
        description = "Северно крайбрежие",
        milestones = listOf(
            Milestone(0.0, "Варна"),
            Milestone(18.0, "Златни пясъци"),
            Milestone(30.0, "Албена"),
            Milestone(42.0, "Балчик", "Двореца")
        )
    )
    
    val BURGAS_PRIMORSKO = Route(
        id = "burgas-primorsko",
        name = "Бургас → Приморско",
        startCity = "Бургас",
        endCity = "Приморско",
        totalKm = 48.0,
        description = "Южно черноморие",
        milestones = listOf(
            Milestone(0.0, "Бургас"),
            Milestone(15.0, "Созопол"),
            Milestone(32.0, "Китен"),
            Milestone(48.0, "Приморско")
        )
    )
    
    val VELIKO_TARNOVO_GABROVO = Route(
        id = "veliko-tarnovo-gabrovo",
        name = "В. Търново → Габрово",
        startCity = "Велико Търново",
        endCity = "Габрово",
        totalKm = 38.0,
        description = "Централна Стара планина",
        milestones = listOf(
            Milestone(0.0, "Велико Търново"),
            Milestone(19.0, "Дряново"),
            Milestone(38.0, "Габрово")
        )
    )
    
    val SOFIA_KYUSTENDIL = Route(
        id = "sofia-kyustendil",
        name = "София → Кюстендил",
        startCity = "София",
        endCity = "Кюстендил",
        totalKm = 50.0,
        description = "Западна България",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(25.0, "Боснек"),
            Milestone(50.0, "Кюстендил", "Град на черешите")
        )
    )
    
    val PLOVDIV_HASKOVO = Route(
        id = "plovdiv-haskovo",
        name = "Пловдив → Хасково",
        startCity = "Пловдив",
        endCity = "Хасково",
        totalKm = 45.0,
        description = "Южна България",
        milestones = listOf(
            Milestone(0.0, "Пловдив"),
            Milestone(22.0, "Димитровград"),
            Milestone(45.0, "Хасково")
        )
    )
    
    val RUSE_SILISTRA = Route(
        id = "ruse-silistra",
        name = "Русе → Силистра",
        startCity = "Русе",
        endCity = "Силистра",
        totalKm = 50.0,
        description = "По Дунава",
        milestones = listOf(
            Milestone(0.0, "Русе"),
            Milestone(25.0, "Тутракан"),
            Milestone(50.0, "Силистра")
        )
    )
    
    val STARA_ZAGORA_KAZANLAK = Route(
        id = "stara-zagora-kazanlak",
        name = "Стара Загора → Казанлък",
        startCity = "Стара Загора",
        endCity = "Казанлък",
        totalKm = 35.0,
        description = "Долината на розите",
        milestones = listOf(
            Milestone(0.0, "Стара Загора"),
            Milestone(18.0, "Гурково"),
            Milestone(35.0, "Казанлък")
        )
    )
    
    val BLAGOEVGRAD_BANSKO = Route(
        id = "blagoevgrad-bansko",
        name = "Благоевград → Банско",
        startCity = "Благоевград",
        endCity = "Банско",
        totalKm = 48.0,
        description = "Път към Пирин",
        milestones = listOf(
            Milestone(0.0, "Благоевград"),
            Milestone(24.0, "Разлог"),
            Milestone(48.0, "Банско")
        )
    )
    
    val MONTANA_VIDIN = Route(
        id = "montana-vidin",
        name = "Монтана → Видин",
        startCity = "Монтана",
        endCity = "Видин",
        totalKm = 50.0,
        description = "Северозападна България",
        milestones = listOf(
            Milestone(0.0, "Монтана"),
            Milestone(25.0, "Лом"),
            Milestone(50.0, "Видин")
        )
    )
    
    // LONGER ROUTES (60-150 km)
    val SOFIA_PLOVDIV = Route(
        id = "sofia-plovdiv",
        name = "София → Пловдив",
        startCity = "София",
        endCity = "Пловдив",
        totalKm = 145.0,
        description = "Класически маршрут",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(48.0, "Ихтиман"),
            Milestone(96.0, "Пазарджик"),
            Milestone(145.0, "Пловдив", "Древният град")
        )
    )
    
    val SOFIA_VIDIN = Route(
        id = "sofia-vidin",
        name = "София → Видин",
        startCity = "София",
        endCity = "Видин",
        totalKm = 120.0,
        description = "Северозападна България",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(40.0, "Враца"),
            Milestone(80.0, "Монтана"),
            Milestone(120.0, "Видин")
        )
    )
    
    val SOFIA_BANSKO = Route(
        id = "sofia-bansko",
        name = "София → Банско",
        startCity = "София",
        endCity = "Банско",
        totalKm = 135.0,
        description = "Към Пирин планина",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(45.0, "Дупница"),
            Milestone(90.0, "Благоевград"),
            Milestone(135.0, "Банско")
        )
    )
    
    val PLOVDIV_SMOLYAN = Route(
        id = "plovdiv-smolyan",
        name = "Пловдив → Смолян",
        startCity = "Пловдив",
        endCity = "Смолян",
        totalKm = 85.0,
        description = "Родопски маршрут",
        milestones = listOf(
            Milestone(0.0, "Пловдив"),
            Milestone(28.0, "Асеновград"),
            Milestone(56.0, "Чепеларе"),
            Milestone(85.0, "Смолян")
        )
    )
    
    val VARNA_BURGAS = Route(
        id = "varna-burgas",
        name = "Варна → Бургас",
        startCity = "Варна",
        endCity = "Бургас",
        totalKm = 125.0,
        description = "Черноморско крайбрежие",
        milestones = listOf(
            Milestone(0.0, "Варна"),
            Milestone(42.0, "Обзор"),
            Milestone(85.0, "Несебър"),
            Milestone(125.0, "Бургас")
        )
    )
    
    val RUSE_VARNA = Route(
        id = "ruse-varna",
        name = "Русе → Варна",
        startCity = "Русе",
        endCity = "Варна",
        totalKm = 145.0,
        description = "От Дунава до морето",
        milestones = listOf(
            Milestone(0.0, "Русе"),
            Milestone(48.0, "Разград"),
            Milestone(96.0, "Шумен"),
            Milestone(145.0, "Варна")
        )
    )
    
    val PLOVDIV_BURGAS = Route(
        id = "plovdiv-burgas",
        name = "Пловдив → Бургас",
        startCity = "Пловдив",
        endCity = "Бургас",
        totalKm = 145.0,
        description = "Към южното море",
        milestones = listOf(
            Milestone(0.0, "Пловдив"),
            Milestone(48.0, "Стара Загора"),
            Milestone(96.0, "Сливен"),
            Milestone(145.0, "Бургас")
        )
    )
    
    val SOFIA_KOPRIVSHTITSA = Route(
        id = "sofia-koprivshtitsa",
        name = "София → Копривщица",
        startCity = "София",
        endCity = "Копривщица",
        totalKm = 95.0,
        description = "Възрожденски град",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(48.0, "Ихтиман"),
            Milestone(72.0, "Антон"),
            Milestone(95.0, "Копривщица")
        )
    )
    
    val SOFIA_VELIKO_TARNOVO = Route(
        id = "sofia-veliko-tarnovo",
        name = "София → Велико Търново",
        startCity = "София",
        endCity = "Велико Търново",
        totalKm = 145.0,
        description = "Към старата столица",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(48.0, "Ябланица"),
            Milestone(96.0, "Севлиево"),
            Milestone(145.0, "Велико Търново")
        )
    )
    
    val SOFIA_KAZANLAK = Route(
        id = "sofia-kazanlak",
        name = "София → Казанлък",
        startCity = "София",
        endCity = "Казанлък",
        totalKm = 135.0,
        description = "Долината на розите",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(68.0, "Карлово"),
            Milestone(102.0, "Калофер"),
            Milestone(135.0, "Казанлък")
        )
    )
    
    // LONG ROUTES (200+ km)
    val SOFIA_VARNA = Route(
        id = "sofia-varna",
        name = "София → Варна",
        startCity = "София",
        endCity = "Варна",
        totalKm = 450.0,
        description = "Пътуване от столицата до морето",
        milestones = listOf(
            Milestone(0.0, "София", "Начало на пътешествието"),
            Milestone(90.0, "Велико Търново", "Старата столица"),
            Milestone(180.0, "Търговище"),
            Milestone(270.0, "Шумен", "Близо до морето"),
            Milestone(360.0, "Девня"),
            Milestone(450.0, "Варна", "Морската столица!")
        )
    )
    
    val SOFIA_BURGAS = Route(
        id = "sofia-burgas",
        name = "София → Бургас",
        startCity = "София",
        endCity = "Бургас",
        totalKm = 380.0,
        description = "Южен път към морето",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(95.0, "Пловдив", "Вторият по големина град"),
            Milestone(190.0, "Стара Загора"),
            Milestone(285.0, "Сливен"),
            Milestone(380.0, "Бургас", "Южното море!")
        )
    )
    
    val SOFIA_RUSE = Route(
        id = "sofia-ruse",
        name = "София → Русе",
        startCity = "София",
        endCity = "Русе",
        totalKm = 320.0,
        description = "Път към Дунава",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(80.0, "Плевен"),
            Milestone(160.0, "Бяла"),
            Milestone(240.0, "Бяла Черква"),
            Milestone(320.0, "Русе", "Град на Дунава!")
        )
    )
    
    val VIDIN_BURGAS = Route(
        id = "vidin-burgas",
        name = "Видин → Бургас",
        startCity = "Видин",
        endCity = "Бургас",
        totalKm = 420.0,
        description = "Пресичане на България",
        milestones = listOf(
            Milestone(0.0, "Видин"),
            Milestone(105.0, "Враца"),
            Milestone(210.0, "София"),
            Milestone(315.0, "Пловдив"),
            Milestone(420.0, "Бургас")
        )
    )
    
    val RUSE_BURGAS = Route(
        id = "ruse-burgas",
        name = "Русе → Бургас",
        startCity = "Русе",
        endCity = "Бургас",
        totalKm = 280.0,
        description = "От север на юг",
        milestones = listOf(
            Milestone(0.0, "Русе"),
            Milestone(70.0, "Търговище"),
            Milestone(140.0, "Сливен"),
            Milestone(210.0, "Карнобат"),
            Milestone(280.0, "Бургас")
        )
    )
    
    val PLOVDIV_VARNA = Route(
        id = "plovdiv-varna",
        name = "Пловдив → Варна",
        startCity = "Пловдив",
        endCity = "Варна",
        totalKm = 320.0,
        description = "Източна България",
        milestones = listOf(
            Milestone(0.0, "Пловдив"),
            Milestone(80.0, "Стара Загора"),
            Milestone(160.0, "Сливен"),
            Milestone(240.0, "Шумен"),
            Milestone(320.0, "Варна")
        )
    )
    
    val SOFIA_SANDANSKI = Route(
        id = "sofia-sandanski",
        name = "София → Сандански",
        startCity = "София",
        endCity = "Сандански",
        totalKm = 165.0,
        description = "Югозападна България",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(55.0, "Дупница"),
            Milestone(110.0, "Благоевград"),
            Milestone(165.0, "Сандански")
        )
    )
    
    val BURGAS_MALKO_TARNOVO = Route(
        id = "burgas-malko-tarnovo",
        name = "Бургас → Малко Търново",
        startCity = "Бургас",
        endCity = "Малко Търново",
        totalKm = 85.0,
        description = "Странджа планина",
        milestones = listOf(
            Milestone(0.0, "Бургас"),
            Milestone(28.0, "Приморско"),
            Milestone(56.0, "Царево"),
            Milestone(85.0, "Малко Търново")
        )
    )
    
    val SOFIA_BELOGRADCHIK = Route(
        id = "sofia-belogradchik",
        name = "София → Белоградчик",
        startCity = "София",
        endCity = "Белоградчик",
        totalKm = 145.0,
        description = "Северозападна България",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(48.0, "Мездра"),
            Milestone(96.0, "Враца"),
            Milestone(145.0, "Белоградчик")
        )
    )
    
    val VELIKO_TARNOVO_BURGAS = Route(
        id = "veliko-tarnovo-burgas",
        name = "В. Търново → Бургас",
        startCity = "Велико Търново",
        endCity = "Бургас",
        totalKm = 220.0,
        description = "От планината до морето",
        milestones = listOf(
            Milestone(0.0, "Велико Търново"),
            Milestone(55.0, "Сливен"),
            Milestone(110.0, "Ямбол"),
            Milestone(165.0, "Карнобат"),
            Milestone(220.0, "Бургас")
        )
    )
    
    fun getAll() = listOf(
        // Short routes first (4-15 km)
        VELIKO_TARNOVO_ARBANASI,
        NESEBAR_SUNNY_BEACH,
        BANSKO_DOBRINISHTE,
        SOFIA_DRAGALEVTSI,
        MELNIK_ROZHEN,
        SOFIA_BOYANA,
        KOPRIVSHTITSA_LOOP,
        BELOGRADCHIK_LOOP,
        VARNA_GOLDEN_SANDS,
        SMOLYAN_PAMPOROVO,
        KAZANLAK_SHIPKA,
        RUSE_IVANOVO,
        SANDANSKI_MELNIK,
        PLOVDIV_ASENOVGRAD,
        BALCHIK_KAVARNA,
        YAMBOL_ELHOVO,
        HASKOVO_DIMITROVGRAD,
        BURGAS_SOZOPOL_SHORT,
        TRYAVNA_GABROVO,
        KYUSTENDIL_DUPNITSA,
        
        // Medium routes (20-50 km)
        PLOVDIV_BACHKOVO,
        STARA_ZAGORA_KAZANLAK,
        VELIKO_TARNOVO_GABROVO,
        VARNA_BALCHIK,
        SOFIA_SAMOKOV,
        PLOVDIV_HASKOVO,
        BLAGOEVGRAD_BANSKO,
        BURGAS_PRIMORSKO,
        RUSE_SILISTRA,
        SOFIA_KYUSTENDIL,
        MONTANA_VIDIN,
        
        // Longer routes (60-150 km)
        BURGAS_MALKO_TARNOVO,
        SOFIA_KOPRIVSHTITSA,
        SOFIA_VIDIN,
        VARNA_BURGAS,
        SOFIA_BANSKO,
        SOFIA_KAZANLAK,
        PLOVDIV_SMOLYAN,
        SOFIA_PLOVDIV,
        RUSE_VARNA,
        PLOVDIV_BURGAS,
        SOFIA_VELIKO_TARNOVO,
        SOFIA_BELOGRADCHIK,
        SOFIA_SANDANSKI,
        
        // Long routes (200+ km)
        VELIKO_TARNOVO_BURGAS,
        RUSE_BURGAS,
        PLOVDIV_VARNA,
        SOFIA_RUSE,
        SOFIA_BURGAS,
        VIDIN_BURGAS,
        SOFIA_VARNA
    )
    
    fun getById(id: String) = getAll().find { it.id == id }
}
