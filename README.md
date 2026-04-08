# 📱 CoursesApp

Android-приложение для просмотра и управления онлайн-курсами, реализованное по техническому заданию.
Проект выполнен с использованием **многомодульной архитектуры, Clean Architecture и MVVM**, с полной синхронизацией данных между экранами.

---

# 📸 Скриншоты

```
/screenshots/
```
---

## 🖼️ Отображение в README

## 🔐 Вход и регистрация

| Вход | Регистрация | Регистрация (состояние) |
|------|------------|------------------------|
| ![](screenshots/01_enter.png) | ![](screenshots/08_registration.png) | ![](screenshots/08_1_registration.png) |

---

## 🏠 Главный экран

![](screenshots/02_main.png)

---

## ❤️ Избранное

| Состояние 1 | Состояние 2 |
|------------|------------|
| ![](screenshots/04_favorite.png) | ![](screenshots/04_1_favorite.png) |

---

## 📄 Экран курса 🔃 Сортировка

| Состояние 1 | Состояние 2 |
|------------|------------|
| ![](screenshots/05_course.png) | ![](screenshots/05_1_course.png) |

---

## 👤 Профиль

| Состояние 1 | Состояние 2 |
|------------|------------|
| ![](screenshots/06_profile.png) | ![](screenshots/06_1_profile.png) |

---

## 🚀 Онбординг

![](screenshots/07_onboarding.png)

---

# 📄 Реализованный функционал

## 🔐 Экран входа (Enter)

✔ Валидация email (`text@text.text`)
✔ Запрет кириллицы
✔ Кнопка активна только при валидных данных
✔ Переход на главный экран
✔ VK → [https://vk.com/](https://vk.com/)
✔ OK → [https://ok.ru/](https://ok.ru/)

---

## 🏠 Главный экран (Main)

✔ Загрузка курсов из JSON API
✔ Отображение списка курсов
✔ Обрезка описания до 2 строк
✔ Сортировка по `publishDate`
✔ Работа с избранным (bookmark)
✔ Переход на экран курса

---

## ❤️ Избранное (Favorite)

✔ Показывает только избранные курсы
✔ Удаление курса сразу из списка
✔ Полная синхронизация состояния

---

## 📄 Экран курса (Course)

✔ Открывается по `courseId`
✔ Данные соответствуют выбранному курсу
✔ Bookmark синхронизирован с системой

---

## 👤 Профиль (Profile)

✔ Экран аккаунта
✔ Список курсов с прогрессом
✔ Работа с избранным

---

## ℹ️ Дополнительно

✔ Реализованы все экраны, включая те, которые не требовались по ТЗ (onboarding, profile расширенный)
✔ UI полностью соответствует макету

---

# 🧠 Архитектура

Проект реализован как **многомодульное приложение**:

```id="project_structure"
CoursesApp/
├── app/
├── data/
├── domain/
├── feature_auth/
├── feature_course/
├── feature_favorite/
├── feature_main/
├── feature_profile/
├── feature_onboarding/
├── settings.gradle.kts
```

---

## 📦 Domain слой

```id="domain_structure"
domain/
├── model/
│   └── Course.kt
├── repository/
│   └── CoursesRepository.kt
└── usecase/
    ├── GetCoursesUseCase.kt
    ├── GetFavoriteCoursesUseCase.kt
    └── ToggleFavoriteUseCase.kt
```

✔ Бизнес-логика
✔ Интерфейсы
✔ UseCases

---

## 📦 Data слой

```id="data_structure"
data/
├── remote/
│   ├── api/
│   │   └── CoursesApi.kt
│   ├── dto/
│   │   ├── CourseDto.kt
│   │   └── CoursesResponseDto.kt
│   └── mapper/
│       └── CourseMapper.kt
├── repository/
│   └── CoursesRepositoryImpl.kt
└── di/
    └── DataModule.kt
```

✔ Retrofit API
✔ DTO + Mapper
✔ Repository
✔ DI

---

## 📦 Feature модули

Каждый экран вынесен в отдельный модуль:

```id="feature_modules"
feature_auth       → Вход  
feature_main       → Главный экран  
feature_favorite   → Избранное  
feature_course     → Курс  
feature_profile    → Профиль  
feature_onboarding → Онбординг  
```

---

## 📦 App модуль

```id="app_structure"
app/
├── App.kt
├── MainActivity.kt
└── di/
    └── AppModule.kt
```

✔ Навигация
✔ DI
✔ запуск приложения

---

# 🛠️ Технологии

* Kotlin
* XML
* MVVM
* Clean Architecture
* Koin
* Retrofit
* OkHttp
* Room
* Coroutines + Flow
* Navigation Component

---

# 🌐 Работа с данными (ВАЖНО для защиты)

Вот как правильно объяснять 👇

> Сетевой слой реализован через Retrofit + OkHttp + CoursesApi.
> Для стабильной демонстрации добавлен fallback на локальный mock JSON (`assets/courses.json`), полностью повторяющий структуру ответа mock API из ТЗ.
> Избранное хранится локально через Room.
> Архитектура: многомодульная, MVVM, Clean Architecture.
> UI реализован на XML.

---

# ⭐ Избранное (hasLike)

В API используется поле:

```id="haslike_example"
hasLike: Boolean
```

В UI оно реализовано как **bookmark (флажок)**:

* `true` → зелёный (в избранном)
* `false` → пустой

📌 Это **не лайк**, а именно **избранное**

---

# 🔄 Синхронизация

Состояние избранного единое для всех экранов:

* Main
* Favorite
* Profile
* Course

✔ Изменение в одном месте обновляет всё приложение

---

# 💾 Хранение

* Курсы → API / mock JSON
* Избранное → Room
* Состояние сохраняется после перезапуска

---

# 🧪 Проверенные сценарии

✔ Добавление в избранное
✔ Удаление
✔ Синхронизация
✔ Сортировка
✔ Открытие конкретного курса
✔ Перезапуск приложения

---

# 📌 Соответствие ТЗ

✔ Все обязательные экраны реализованы
✔ Стек технологий соблюдён
✔ Данные из API
✔ Локальное хранение избранного
✔ UI соответствует макету
✔ Многомодульность

---

# 💬 Комментарий разработчика

Проект реализован с упором на:

* чистую архитектуру
* разделение ответственности
* масштабируемость
* синхронизацию состояния
* соответствие production-подходам

---

# 📬 Итог

Приложение демонстрирует:

✔ работу с API
✔ локальное хранение
✔ архитектуру
✔ UI по макету
✔ синхронизацию данных

---

## ✨ Автор

**Amanzhol Aimov**

---

## 🚀 Спасибо за просмотр, увидел ТЗ подумал 2 часа работы -> по факту вышло 2 дня, зато столько кайфа, спасибо Андроиду!

Если проект был полезен или интересен — поставь ⭐ репозиторию 😊
