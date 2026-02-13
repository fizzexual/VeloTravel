package com.velotravel.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val unlockedDate: Long? = null,
    val icon: String = "🏆"
)

// Предефинирани постижения
object AchievementDefinitions {
    // Distance Milestones
    val FIRST_10KM = Achievement(
        id = "first_10km",
        title = "Първи 10 км",
        description = "Изминахте първите си 10 километра!",
        icon = "🚴"
    )
    
    val FIRST_50KM = Achievement(
        id = "first_50km",
        title = "Половин стотица",
        description = "Вече сте на 50 км!",
        icon = "💪"
    )
    
    val HUNDRED_KM = Achievement(
        id = "hundred_km",
        title = "Стотица!",
        description = "100 километра изминати!",
        icon = "⭐"
    )
    
    val TWO_HUNDRED_KM = Achievement(
        id = "two_hundred_km",
        title = "Двеста километра",
        description = "200 км - впечатляващо постижение!",
        icon = "🌟"
    )
    
    val FIVE_HUNDRED_KM = Achievement(
        id = "five_hundred_km",
        title = "Петстотин!",
        description = "500 км изминати - вие сте легенда!",
        icon = "👑"
    )
    
    val THOUSAND_KM = Achievement(
        id = "thousand_km",
        title = "Хилядник",
        description = "1000 км! Невероятно постижение!",
        icon = "🏆"
    )
    
    val TWO_THOUSAND_KM = Achievement(
        id = "two_thousand_km",
        title = "Две хиляди",
        description = "2000 км - вие сте истински шампион!",
        icon = "💎"
    )
    
    val FIVE_THOUSAND_KM = Achievement(
        id = "five_thousand_km",
        title = "Пет хиляди",
        description = "5000 км - майстор на педалите!",
        icon = "🔥"
    )
    
    // Streak Achievements
    val THREE_DAYS_STREAK = Achievement(
        id = "three_days",
        title = "Три дни подред",
        description = "3 дни последователно каране!",
        icon = "📅"
    )
    
    val SEVEN_DAYS_STREAK = Achievement(
        id = "seven_days",
        title = "Седмица подред",
        description = "7 дни последователно каране!",
        icon = "🔥"
    )
    
    val TWO_WEEKS_STREAK = Achievement(
        id = "two_weeks",
        title = "Две седмици",
        description = "14 дни без прекъсване!",
        icon = "💪"
    )
    
    val MONTH_STREAK = Achievement(
        id = "month_streak",
        title = "Месец подред",
        description = "30 дни последователно - невероятна дисциплина!",
        icon = "🎯"
    )
    
    val HUNDRED_DAYS_STREAK = Achievement(
        id = "hundred_days",
        title = "Сто дни",
        description = "100 дни без прекъсване - вие сте машина!",
        icon = "⚡"
    )
    
    // Route Completion Achievements
    val FIRST_ROUTE_COMPLETE = Achievement(
        id = "first_route",
        title = "Първи маршрут",
        description = "Завършихте първия си маршрут!",
        icon = "🎉"
    )
    
    val THREE_ROUTES = Achievement(
        id = "three_routes",
        title = "Три маршрута",
        description = "Завършихте 3 различни маршрута!",
        icon = "🗺️"
    )
    
    val FIVE_ROUTES = Achievement(
        id = "five_routes",
        title = "Пет маршрута",
        description = "5 маршрута завършени!",
        icon = "🌍"
    )
    
    val TEN_ROUTES = Achievement(
        id = "ten_routes",
        title = "Десет маршрута",
        description = "10 маршрута - истински пътешественик!",
        icon = "🌏"
    )
    
    val ALL_ROUTES = Achievement(
        id = "all_routes",
        title = "Всички маршрути",
        description = "Завършихте всички налични маршрути!",
        icon = "🏅"
    )
    
    // Daily Distance Achievements
    val TWENTY_KM_DAY = Achievement(
        id = "twenty_km_day",
        title = "20 км за ден",
        description = "Изминахте 20 км за един ден!",
        icon = "🚵"
    )
    
    val FIFTY_KM_DAY = Achievement(
        id = "fifty_km_day",
        title = "50 км за ден",
        description = "50 км за един ден - страхотно!",
        icon = "💨"
    )
    
    val HUNDRED_KM_DAY = Achievement(
        id = "hundred_km_day",
        title = "Стотица за ден",
        description = "100 км за един ден - невероятно!",
        icon = "🦸"
    )
    
    // Special Achievements
    val EARLY_BIRD = Achievement(
        id = "early_bird",
        title = "Ранна птичка",
        description = "Карахте преди 7 сутринта!",
        icon = "🌅"
    )
    
    val NIGHT_RIDER = Achievement(
        id = "night_rider",
        title = "Нощен ездач",
        description = "Карахте след 22 часа!",
        icon = "🌙"
    )
    
    val WEEKEND_WARRIOR = Achievement(
        id = "weekend_warrior",
        title = "Уикенд воин",
        description = "Карахте и двата дни от уикенда!",
        icon = "🎊"
    )
    
    val SPEED_DEMON = Achievement(
        id = "speed_demon",
        title = "Бърз като вятър",
        description = "Завършихте маршрут за рекордно време!",
        icon = "⚡"
    )
    
    val EXPLORER = Achievement(
        id = "explorer",
        title = "Изследовател",
        description = "Започнахте 5 различни маршрута!",
        icon = "🧭"
    )
    
    val DEDICATED = Achievement(
        id = "dedicated",
        title = "Отдаден",
        description = "Използвате приложението 30 дни!",
        icon = "❤️"
    )
    
    val CENTURY_CLUB = Achievement(
        id = "century_club",
        title = "Клуб Стотица",
        description = "Изминахте 100+ км за седмица!",
        icon = "🎖️"
    )
    
    val CONSISTENT = Achievement(
        id = "consistent",
        title = "Постоянен",
        description = "Карахте поне веднъж седмично за месец!",
        icon = "📊"
    )
    
    val MOUNTAIN_CLIMBER = Achievement(
        id = "mountain_climber",
        title = "Планинар",
        description = "Завършихте маршрут с планински участък!",
        icon = "⛰️"
    )
    
    val COASTAL_RIDER = Achievement(
        id = "coastal_rider",
        title = "Крайбрежен ездач",
        description = "Завършихте маршрут покрай морето!",
        icon = "🌊"
    )
    
    val CITY_EXPLORER = Achievement(
        id = "city_explorer",
        title = "Градски изследовател",
        description = "Завършихте градски маршрут!",
        icon = "🏙️"
    )
    
    val NATURE_LOVER = Achievement(
        id = "nature_lover",
        title = "Любител на природата",
        description = "Завършихте маршрут през природа!",
        icon = "🌲"
    )
    
    val SOCIAL_RIDER = Achievement(
        id = "social_rider",
        title = "Социален ездач",
        description = "Добавихте бележки към 10 записа!",
        icon = "💬"
    )
    
    val PHOTO_FINISH = Achievement(
        id = "photo_finish",
        title = "Фото финиш",
        description = "Завършихте маршрут точно на целта!",
        icon = "📸"
    )
    
    val OVERACHIEVER = Achievement(
        id = "overachiever",
        title = "Свръхпостижение",
        description = "Изминахте повече от целта на маршрута!",
        icon = "🎯"
    )
    
    val COMEBACK = Achievement(
        id = "comeback",
        title = "Завръщане",
        description = "Продължихте да карате след 30 дни пауза!",
        icon = "🔄"
    )
    
    val MARATHON = Achievement(
        id = "marathon",
        title = "Маратон",
        description = "Изминахте 42 км за един ден!",
        icon = "🏃"
    )
    
    val IRON_LEGS = Achievement(
        id = "iron_legs",
        title = "Железни крака",
        description = "Карахте 7 дни подред, всеки ден над 20 км!",
        icon = "🦿"
    )
    
    val TOURIST = Achievement(
        id = "tourist",
        title = "Турист",
        description = "Започнахте маршрути в 3 различни региона!",
        icon = "🎒"
    )
    
    val VETERAN = Achievement(
        id = "veteran",
        title = "Ветеран",
        description = "Използвате приложението 100 дни!",
        icon = "🎖️"
    )
    
    val LEGEND = Achievement(
        id = "legend",
        title = "Легенда",
        description = "Отключихте 30 постижения!",
        icon = "🌟"
    )
    
    // Steps Achievements
    val FIRST_1K_STEPS = Achievement(
        id = "first_1k_steps",
        title = "Първи 1000 стъпки",
        description = "Направихте 1000 стъпки за ден!",
        icon = "👣"
    )
    
    val FIVE_K_STEPS = Achievement(
        id = "five_k_steps",
        title = "5000 стъпки",
        description = "5000 стъпки за ден!",
        icon = "🚶"
    )
    
    val TEN_K_STEPS = Achievement(
        id = "ten_k_steps",
        title = "10000 стъпки",
        description = "10000 стъпки за ден - отлично!",
        icon = "🏃"
    )
    
    val TWENTY_K_STEPS = Achievement(
        id = "twenty_k_steps",
        title = "20000 стъпки",
        description = "20000 стъпки за ден - невероятно!",
        icon = "⚡"
    )
    
    val FIFTY_K_STEPS = Achievement(
        id = "fifty_k_steps",
        title = "50000 стъпки",
        description = "50000 стъпки за ден - супер постижение!",
        icon = "💪"
    )
    
    val HUNDRED_K_STEPS = Achievement(
        id = "hundred_k_steps",
        title = "100К стъпки",
        description = "100000 стъпки общо!",
        icon = "🏆"
    )
    
    val MILLION_STEPS = Achievement(
        id = "million_steps",
        title = "Милион стъпки",
        description = "1 милион стъпки общо - легенда!",
        icon = "👑"
    )
    
    // Calories Achievements
    val HUNDRED_CALS = Achievement(
        id = "hundred_cals",
        title = "100 калории",
        description = "Изгорихте 100 калории за ден!",
        icon = "🔥"
    )
    
    val FIVE_HUNDRED_CALS = Achievement(
        id = "five_hundred_cals",
        title = "500 калории",
        description = "500 калории изгорени за ден!",
        icon = "💥"
    )
    
    val THOUSAND_CALS = Achievement(
        id = "thousand_cals",
        title = "1000 калории",
        description = "1000 калории за ден - страхотно!",
        icon = "⚡"
    )
    
    val TWO_THOUSAND_CALS = Achievement(
        id = "two_thousand_cals",
        title = "2000 калории",
        description = "2000 калории за ден!",
        icon = "🌟"
    )
    
    val TEN_K_CALS = Achievement(
        id = "ten_k_cals",
        title = "10К калории",
        description = "10000 калории общо!",
        icon = "💎"
    )
    
    val FIFTY_K_CALS = Achievement(
        id = "fifty_k_cals",
        title = "50К калории",
        description = "50000 калории общо!",
        icon = "🏅"
    )
    
    val HUNDRED_K_CALS = Achievement(
        id = "hundred_k_cals",
        title = "100К калории",
        description = "100000 калории изгорени!",
        icon = "👑"
    )
    
    // Activity Type Achievements
    val FIRST_WALK = Achievement(
        id = "first_walk",
        title = "Първа разходка",
        description = "Добавихте първата си разходка!",
        icon = "🚶"
    )
    
    val FIRST_RUN = Achievement(
        id = "first_run",
        title = "Първо бягане",
        description = "Добавихте първото си бягане!",
        icon = "🏃"
    )
    
    val FIRST_CYCLE = Achievement(
        id = "first_cycle",
        title = "Първо колоездене",
        description = "Добавихте първото си колоездене!",
        icon = "🚴"
    )
    
    val FIRST_HIKE = Achievement(
        id = "first_hike",
        title = "Първи поход",
        description = "Добавихте първия си поход!",
        icon = "🥾"
    )
    
    val TEN_WALKS = Achievement(
        id = "ten_walks",
        title = "10 разходки",
        description = "Направихте 10 разходки!",
        icon = "🚶"
    )
    
    val TEN_RUNS = Achievement(
        id = "ten_runs",
        title = "10 бягания",
        description = "Направихте 10 бягания!",
        icon = "🏃"
    )
    
    val TEN_CYCLES = Achievement(
        id = "ten_cycles",
        title = "10 колоездения",
        description = "Направихте 10 колоездения!",
        icon = "🚴"
    )
    
    val TEN_HIKES = Achievement(
        id = "ten_hikes",
        title = "10 похода",
        description = "Направихте 10 похода!",
        icon = "🥾"
    )
    
    val FIFTY_WALKS = Achievement(
        id = "fifty_walks",
        title = "50 разходки",
        description = "50 разходки завършени!",
        icon = "🚶"
    )
    
    val FIFTY_RUNS = Achievement(
        id = "fifty_runs",
        title = "50 бягания",
        description = "50 бягания завършени!",
        icon = "🏃"
    )
    
    val FIFTY_CYCLES = Achievement(
        id = "fifty_cycles",
        title = "50 колоездения",
        description = "50 колоездения завършени!",
        icon = "🚴"
    )
    
    val FIFTY_HIKES = Achievement(
        id = "fifty_hikes",
        title = "50 похода",
        description = "50 похода завършени!",
        icon = "🥾"
    )
    
    val MULTI_SPORT = Achievement(
        id = "multi_sport",
        title = "Мултиспорт",
        description = "Пробвахте всички видове активности!",
        icon = "🎯"
    )
    
    // Weekly Achievements
    val ACTIVE_WEEK = Achievement(
        id = "active_week",
        title = "Активна седмица",
        description = "Активност всеки ден от седмицата!",
        icon = "📅"
    )
    
    val FIFTY_KM_WEEK = Achievement(
        id = "fifty_km_week",
        title = "50 км седмично",
        description = "50 км за една седмица!",
        icon = "🎖️"
    )
    
    val HUNDRED_KM_WEEK = Achievement(
        id = "hundred_km_week",
        title = "100 км седмично",
        description = "100 км за една седмица!",
        icon = "🏆"
    )
    
    val FIFTY_K_STEPS_WEEK = Achievement(
        id = "fifty_k_steps_week",
        title = "50К стъпки седмично",
        description = "50000 стъпки за седмица!",
        icon = "👟"
    )
    
    val FIVE_K_CALS_WEEK = Achievement(
        id = "five_k_cals_week",
        title = "5К калории седмично",
        description = "5000 калории за седмица!",
        icon = "🔥"
    )
    
    // Monthly Achievements
    val HUNDRED_KM_MONTH = Achievement(
        id = "hundred_km_month",
        title = "100 км месечно",
        description = "100 км за един месец!",
        icon = "📊"
    )
    
    val TWO_HUNDRED_KM_MONTH = Achievement(
        id = "two_hundred_km_month",
        title = "200 км месечно",
        description = "200 км за един месец!",
        icon = "🎯"
    )
    
    val FIVE_HUNDRED_KM_MONTH = Achievement(
        id = "five_hundred_km_month",
        title = "500 км месечно",
        description = "500 км за един месец - невероятно!",
        icon = "👑"
    )
    
    val HUNDRED_K_STEPS_MONTH = Achievement(
        id = "hundred_k_steps_month",
        title = "100К стъпки месечно",
        description = "100000 стъпки за месец!",
        icon = "🏅"
    )
    
    val TEN_K_CALS_MONTH = Achievement(
        id = "ten_k_cals_month",
        title = "10К калории месечно",
        description = "10000 калории за месец!",
        icon = "💥"
    )
    
    // Total Activities
    val TEN_ACTIVITIES = Achievement(
        id = "ten_activities",
        title = "10 активности",
        description = "Добавихте 10 активности!",
        icon = "🎉"
    )
    
    val FIFTY_ACTIVITIES = Achievement(
        id = "fifty_activities",
        title = "50 активности",
        description = "50 активности записани!",
        icon = "🌟"
    )
    
    val HUNDRED_ACTIVITIES = Achievement(
        id = "hundred_activities",
        title = "100 активности",
        description = "100 активности - отлично!",
        icon = "💎"
    )
    
    val TWO_HUNDRED_ACTIVITIES = Achievement(
        id = "two_hundred_activities",
        title = "200 активности",
        description = "200 активности записани!",
        icon = "🏆"
    )
    
    val FIVE_HUNDRED_ACTIVITIES = Achievement(
        id = "five_hundred_activities",
        title = "500 активности",
        description = "500 активности - легенда!",
        icon = "👑"
    )
    
    // Special Milestones
    val PERFECT_MONTH = Achievement(
        id = "perfect_month",
        title = "Перфектен месец",
        description = "Активност всеки ден от месеца!",
        icon = "🌕"
    )
    
    val EARLY_STARTER = Achievement(
        id = "early_starter",
        title = "Ранен старт",
        description = "10 активности преди 7 сутринта!",
        icon = "🌅"
    )
    
    val NIGHT_OWL = Achievement(
        id = "night_owl",
        title = "Нощна птица",
        description = "10 активности след 22 часа!",
        icon = "🦉"
    )
    
    val WEEKEND_CHAMPION = Achievement(
        id = "weekend_champion",
        title = "Уикенд шампион",
        description = "Активност всеки уикенд за месец!",
        icon = "🎊"
    )
    
    val DISTANCE_MASTER = Achievement(
        id = "distance_master",
        title = "Майстор на дистанцията",
        description = "Изминахте 10000 км общо!",
        icon = "🌍"
    )
    
    val CALORIE_CRUSHER = Achievement(
        id = "calorie_crusher",
        title = "Разрушител на калории",
        description = "Изгорихте 500000 калории!",
        icon = "💪"
    )
    
    val STEP_MASTER = Achievement(
        id = "step_master",
        title = "Майстор на стъпките",
        description = "Направихте 10 милиона стъпки!",
        icon = "👣"
    )
    
    val ULTRA_RUNNER = Achievement(
        id = "ultra_runner",
        title = "Ултра бегач",
        description = "Изминахте 50 км бягане за ден!",
        icon = "🏃"
    )
    
    val ULTRA_CYCLIST = Achievement(
        id = "ultra_cyclist",
        title = "Ултра колоездач",
        description = "Изминахте 200 км колоездене за ден!",
        icon = "🚴"
    )
    
    val MOUNTAIN_GOAT = Achievement(
        id = "mountain_goat",
        title = "Планинска коза",
        description = "Направихте 50 похода!",
        icon = "🐐"
    )
    
    val WALKER_EXTRAORDINAIRE = Achievement(
        id = "walker_extraordinaire",
        title = "Изключителен ходач",
        description = "Направихте 100 разходки!",
        icon = "🚶"
    )
    
    val TRIPLE_THREAT = Achievement(
        id = "triple_threat",
        title = "Тройна заплаха",
        description = "3 различни активности за един ден!",
        icon = "🎯"
    )
    
    val CONSISTENCY_KING = Achievement(
        id = "consistency_king",
        title = "Крал на постоянството",
        description = "Активност всеки ден за 100 дни!",
        icon = "👑"
    )
    
    val YEAR_WARRIOR = Achievement(
        id = "year_warrior",
        title = "Годишен воин",
        description = "Активност всеки месец за година!",
        icon = "🗓️"
    )
    
    val SUPER_LEGEND = Achievement(
        id = "super_legend",
        title = "Супер легенда",
        description = "Отключихте 50 постижения!",
        icon = "⭐"
    )
    
    val ULTIMATE_CHAMPION = Achievement(
        id = "ultimate_champion",
        title = "Върховен шампион",
        description = "Отключихте 75 постижения!",
        icon = "🏆"
    )
    
    val MASTER_OF_ALL = Achievement(
        id = "master_of_all",
        title = "Майстор на всичко",
        description = "Отключихте 90 постижения!",
        icon = "💎"
    )
    
    val PERFECTIONIST = Achievement(
        id = "perfectionist",
        title = "Перфекционист",
        description = "Отключихте всички постижения!",
        icon = "🌟"
    )
    
    fun getAll() = listOf(
        // Distance
        FIRST_10KM, FIRST_50KM, HUNDRED_KM, TWO_HUNDRED_KM, 
        FIVE_HUNDRED_KM, THOUSAND_KM, TWO_THOUSAND_KM, FIVE_THOUSAND_KM,
        DISTANCE_MASTER,
        
        // Streaks
        THREE_DAYS_STREAK, SEVEN_DAYS_STREAK, TWO_WEEKS_STREAK, 
        MONTH_STREAK, HUNDRED_DAYS_STREAK, CONSISTENCY_KING,
        
        // Routes (legacy - kept for compatibility)
        FIRST_ROUTE_COMPLETE, THREE_ROUTES, FIVE_ROUTES, TEN_ROUTES, ALL_ROUTES,
        
        // Daily
        TWENTY_KM_DAY, FIFTY_KM_DAY, HUNDRED_KM_DAY,
        
        // Steps
        FIRST_1K_STEPS, FIVE_K_STEPS, TEN_K_STEPS, TWENTY_K_STEPS,
        FIFTY_K_STEPS, HUNDRED_K_STEPS, MILLION_STEPS, STEP_MASTER,
        
        // Calories
        HUNDRED_CALS, FIVE_HUNDRED_CALS, THOUSAND_CALS, TWO_THOUSAND_CALS,
        TEN_K_CALS, FIFTY_K_CALS, HUNDRED_K_CALS, CALORIE_CRUSHER,
        
        // Activity Types
        FIRST_WALK, FIRST_RUN, FIRST_CYCLE, FIRST_HIKE,
        TEN_WALKS, TEN_RUNS, TEN_CYCLES, TEN_HIKES,
        FIFTY_WALKS, FIFTY_RUNS, FIFTY_CYCLES, FIFTY_HIKES,
        WALKER_EXTRAORDINAIRE, ULTRA_RUNNER, ULTRA_CYCLIST, MOUNTAIN_GOAT,
        MULTI_SPORT, TRIPLE_THREAT,
        
        // Weekly
        ACTIVE_WEEK, FIFTY_KM_WEEK, HUNDRED_KM_WEEK,
        FIFTY_K_STEPS_WEEK, FIVE_K_CALS_WEEK,
        
        // Monthly
        HUNDRED_KM_MONTH, TWO_HUNDRED_KM_MONTH, FIVE_HUNDRED_KM_MONTH,
        HUNDRED_K_STEPS_MONTH, TEN_K_CALS_MONTH, PERFECT_MONTH,
        
        // Total Activities
        TEN_ACTIVITIES, FIFTY_ACTIVITIES, HUNDRED_ACTIVITIES,
        TWO_HUNDRED_ACTIVITIES, FIVE_HUNDRED_ACTIVITIES,
        
        // Special
        EARLY_BIRD, NIGHT_RIDER, WEEKEND_WARRIOR, SPEED_DEMON,
        EXPLORER, DEDICATED, CENTURY_CLUB, CONSISTENT,
        MOUNTAIN_CLIMBER, COASTAL_RIDER, CITY_EXPLORER, NATURE_LOVER,
        SOCIAL_RIDER, PHOTO_FINISH, OVERACHIEVER, COMEBACK,
        MARATHON, IRON_LEGS, TOURIST, VETERAN,
        EARLY_STARTER, NIGHT_OWL, WEEKEND_CHAMPION, YEAR_WARRIOR,
        
        // Meta Achievements
        LEGEND, SUPER_LEGEND, ULTIMATE_CHAMPION, MASTER_OF_ALL, PERFECTIONIST
    )
}
