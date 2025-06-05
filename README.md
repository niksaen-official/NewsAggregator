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
    classDef done fill:#2ecc71,stroke:#27ae60,color:white
    classDef active fill:#3498db,stroke:#2980b9,color:white
    title Прогресс разработки
    dateFormat  YYYY-MM-DD
    todayMarker stroke=#000, thickness=4px
    section Завершено
    Базовый функционал          :done,    task1, 2025-05-13, 7d
    Поиск                       :done,    task2, after task1, 2d
    Кэширование                 :done,    task3, after task2, 3d
    Обновление данных в фоне    :done,    task4, after task3, 4d
    Фильтрация по категориям    :done,    task5, after task4, 3d
    section В процессе
    Pull-to-Refresh             :active,  task6, after task5, 14d
    section Очередь
    Пагинация                   :         task7, after task6, 14d
    Подкатегории фильтров       :         task8, after task7, 14d
    Firebase Auth               :         task9, after task8, 14d
    Комментарии/лайки           :         task10, after task9, 21d
```

### 📊 Статус по задачам

| Функция                   | Готовность |
|---------------------------|------------|
| ✅ Базовый функционал     | 100%        |
| ✅ Поиск                  | 100%        |
| ✅ Кэширование            | 100%        |
| ✅ Обновление данных в фоне            | 100%        |
| ✅ Фильтрация по категориям            | 100%        |
| 🟡 Pull-to-Refresh    | 10%        |
| ⚪️ Пагинация          | 0%         |
| ⚪️ Подкатегории       | 0%         |
| ⚪️ Firebase Auth      | 0%         |
| ⚪️ Комментарии        | 0%         |

## 🚀 Запуск проекта  
1. Склонируйте репозиторий:  
   ```bash
   git clone https://github.com/niksaen-official/NewsAggregator.git
2. Откройте проект в Android Studio (версия Arctic Fox+).
3. Запустите приложение через эмулятор или устройство.
