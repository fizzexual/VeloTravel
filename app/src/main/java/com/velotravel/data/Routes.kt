package com.velotravel.data

import com.velotravel.data.model.Milestone
import com.velotravel.data.model.Route

object Routes {
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
    
    val SOFIA_PLOVDIV = Route(
        id = "sofia-plovdiv",
        name = "София → Пловдив",
        startCity = "София",
        endCity = "Пловдив",
        totalKm = 145.0,
        description = "Кратък маршрут за начинаещи",
        milestones = listOf(
            Milestone(0.0, "София"),
            Milestone(48.0, "Ихтиман"),
            Milestone(96.0, "Пазарджик"),
            Milestone(145.0, "Пловдив", "Древният град!")
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
    
    fun getAll() = listOf(
        SOFIA_PLOVDIV,  // Най-кратък първи
        SOFIA_BURGAS,
        SOFIA_RUSE,
        SOFIA_VARNA
    )
    
    fun getById(id: String) = getAll().find { it.id == id }
}
