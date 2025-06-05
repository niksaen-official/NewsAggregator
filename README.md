# 📰 NewsAggregator — Android-приложение для агрегации новостей  

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen)](https://android-arsenal.com/api?level=21)

Приложение для удобного чтения новостей.  

<p align="center">
  <img src="previews/screenshot_1.png" width="200">
  <img src="previews/screenshot_2.png" width="200">
  <img src="previews/screenshot_2.png" width="200">
  <img src="previews/screenshot_3.png" width="200">
</p>

## 🔥 Возможности  
- 📡 Загрузка новостей из [The Guardian](https://www.theguardian.com/).  
- 🔍 Поиск и фильтрация по категориям.  
- 💾 Кэширование для оффлайн-доступа.  
- 🌙 Поддержка тёмной темы.  

## 🛠 Технологии  
- **Язык**: Kotlin  
- **Архитектура**: MVVM + Clean Architecture  
- **Библиотеки**:  
  - Retrofit + Gson — работа с API  
  - Room — локальная база данных  
  - Jetpack Compose — UI  
  - Coroutines & Flow — асинхронность  
  - Dagger Hilt — dependency injection  

## 🚀 Roadmap NewsAggregator (Статус + Планы)

```mermaid
gantt
    title Прогресс разработки
    dateFormat  YYYY-MM-DD
    todayMarker stroke=#000, thickness=4px
    section Завершено
    Базовая лента новостей       :done,    task1, 2024-06-01, 15d
    API News.org интеграция      :done,    task2, after task1, 10d
    section В процессе
    Pull-to-Refresh             :active,  task3, 2024-07-01, 7d
    section Очередь
    Пагинация                   :         task4, after task3, 10d
    Подкатегории фильтров       :         task5, after task4, 12d
    Firebase Auth               :         task6, after task5, 14d
    Комментарии/лайки           :crit,    task7, after task6, 21d
```

### 📊 Статус по задачам

| Функция               | Готовность | Технологии                          |
|-----------------------|------------|-------------------------------------|
| ✅ Лента новостей      | 100%       | Retrofit, Jetpack Compose           |
| ✅ API интеграция      | 100%       | Moshi, OkHttp Interceptors          |
| 🟡 Pull-to-Refresh    | 40%        | `swipeRefreshLayout` (XML) / `pullRefresh` (Compose) |
| ⚪️ Пагинация          | 0%         | Paging 3 Library + RemoteMediator   |
| ⚪️ Подкатегории       | 0%         | Nested `FlowRow` + StateFlow        |
| ⚪️ Firebase Auth      | 0%         | Firebase Auth, Hilt DI              |
| 🔴 Комментарии        | 0%         | WebSocket, Room DB                  |

## 🚀 Запуск проекта  
1. Склонируйте репозиторий:  
   ```bash
   git clone https://github.com/niksaen-official/NewsAggregator.git
2. Откройте проект в Android Studio (версия Arctic Fox+).
3. Запустите приложение через эмулятор или устройство.
