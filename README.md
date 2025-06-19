# 📰 NewsAggregator — Android-приложение для агрегации новостей  

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen)](https://android-arsenal.com/api?level=21)

Приложение для удобного чтения новостей.  

<p align="center">
  <img src="previews/screenshot_1.png" width="200" alt="Главный экран">
  <img src="previews/screenshot_2.png" width="200" alt="Экран поиска">
  <img src="previews/screenshot_3.png" width="200" alt="Тёмная тема">
  <img src="previews/screenshot_4.png" width="200" alt="Фильтрация">
</p>

## 🔥 Возможности  
- 📡 Загрузка новостей из [The Guardian](https://www.theguardian.com/)
- 🔍 Поиск
- 🗂 Фильтрация по категориям
- 💾 Оффлайн-режим
- 🌙 Адаптивный UI (светлая/тёмная тема)
- 🔄 Pull to refresh


## 🛠 Технологии  
| Компонент       | Реализация                          |
|-----------------|-------------------------------------|
| **Язык**        | Kotlin                     |
| **Архитектура** | MVVM + Clean Architecture           |
| **UI**          | Jetpack Compose + Material 3        |
| **Сеть**        | Retrofit 2 + OkHttp         |
| **Локальная БД**| Room                 |
| **DI**          | Dagger Hilt                         |
| **Асинхронность**| Coroutines + Flow + StateFlow      |

## 🚀 Roadmap NewsAggregator

```mermaid
gantt
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
    Pull-to-Refresh             :done,  task6, after task5, 18d
    section Очередь
    Подкатегории фильтров       :active,         task7, after task6, 14d
    Firebase Auth               :         task8, after task7, 14d
    Комментарии/лайки           :         task9, after task8, 21d
```


## 📊 Статус по задачам

| Функция                   | Готовность |
|---------------------------|------------|
| ✅ Базовый функционал     | 100%        |
| ✅ Поиск                  | 100%        |
| ✅ Кэширование            | 100%        |
| ✅ Обновление данных в фоне            | 100%        |
| ✅ Фильтрация по категориям            | 100%        |
| ✅ Pull-to-Refresh    | 100%        |
| 🟡 Подкатегории фльтров       | 0%         |
| ⚪️ Авторизация через Firebase      | 0%         |
| ⚪️ Лайки и комментарии через Firebase        | 0%         |

## 🚀 Запуск проекта  
1. Склонируйте репозиторий:  
   ```bash
   git clone https://github.com/niksaen-official/NewsAggregator.git
2. Откройте проект в Android Studio (версия Arctic Fox+).
3. Запустите приложение через эмулятор или устройство.
