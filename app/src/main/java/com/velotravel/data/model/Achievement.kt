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
    
    fun getAll() = listOf(
        // Distance
        FIRST_10KM, FIRST_50KM, HUNDRED_KM, TWO_HUNDRED_KM, 
        FIVE_HUNDRED_KM, THOUSAND_KM, TWO_THOUSAND_KM, FIVE_THOUSAND_KM,
        
        // Streaks
        THREE_DAYS_STREAK, SEVEN_DAYS_STREAK, TWO_WEEKS_STREAK, 
        MONTH_STREAK, HUNDRED_DAYS_STREAK,
        
        // Routes
        FIRST_ROUTE_COMPLETE, THREE_ROUTES, FIVE_ROUTES, TEN_ROUTES, ALL_ROUTES,
        
        // Daily
        TWENTY_KM_DAY, FIFTY_KM_DAY, HUNDRED_KM_DAY,
        
        // Special
        EARLY_BIRD, NIGHT_RIDER, WEEKEND_WARRIOR, SPEED_DEMON,
        EXPLORER, DEDICATED, CENTURY_CLUB, CONSISTENT,
        MOUNTAIN_CLIMBER, COASTAL_RIDER, CITY_EXPLORER, NATURE_LOVER,
        SOCIAL_RIDER, PHOTO_FINISH, OVERACHIEVER, COMEBACK,
        MARATHON, IRON_LEGS, TOURIST, VETERAN, LEGEND
    )
}
