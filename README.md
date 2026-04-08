# 📱 CoursesApp

Android-приложение для просмотра и управления онлайн-курсами, реализованное по техническому заданию.  
Проект выполнен с использованием **многомодульной архитектуры, Clean Architecture и MVVM**, с полной синхронизацией данных между экранами.

---

# 📸 Скриншоты

`screenshots/`

<table>
  <tr>
    <td width="50%" valign="top">

## 🔐 Вход и регистрация

| Вход | Регистрация |
|------|-------------|
| ![](screenshots/01_enter.png) | ![](screenshots/08_registration.png) |

| Регистрация (состояние) |
|-------------------------|
| ![](screenshots/08_1_registration.png) |

  </td>
    <td width="50%" valign="top">

## 🏠 Главный экран

| Главный экран |
|---------------|
| ![](screenshots/02_main.png) |

## 🚀 Онбординг

| Онбординг |
|-----------|
| ![](screenshots/07_onboarding.png) |

  </td>
  </tr>

  <tr>
    <td width="50%" valign="top">

## ❤️ Избранное

| Состояние 1 | Состояние 2 |
|------------|-------------|
| ![](screenshots/04_favorite.png) | ![](screenshots/04_1_favorite.png) |

  </td>
    <td width="50%" valign="top">

## 📄 Экран курса

| Состояние 1 | Состояние 2 |
|------------|-------------|
| ![](screenshots/05_course.png) | ![](screenshots/05_1_course.png) |

  </td>
  </tr>

  <tr>
    <td width="50%" valign="top">

## 👤 Профиль

| Состояние 1 | Состояние 2 |
|------------|-------------|
| ![](screenshots/06_profile.png) | ![](screenshots/06_1_profile.png) |

  </td>
    <td width="50%" valign="top">

## 📌 Примечание

Скриншоты демонстрируют разные состояния экранов:
- авторизация
- регистрация
- список курсов
- избранное
- экран курса
- профиль
- онбординг

  </td>
  </tr>
</table>

---

# 📄 Реализованный функционал

<table>
  <tr>
    <td width="50%" valign="top">

## 🔐 Экран входа (Enter)

✔ Валидация email (`text@text.text`)  
✔ Запрет кириллицы  
✔ Кнопка активна только при валидных данных  
✔ Переход на главный экран  
✔ VK → [vk.com](https://vk.com/)  
✔ OK → [ok.ru](https://ok.ru/)  

  </td>
    <td width="50%" valign="top">

## 🏠 Главный экран (Main)

✔ Загрузка курсов из JSON API  
✔ Отображение списка курсов  
✔ Обрезка описания до 2 строк  
✔ Сортировка по `publishDate`  
✔ Работа с избранным (bookmark)  
✔ Переход на экран курса  

  </td>
  </tr>

  <tr>
    <td width="50%" valign="top">

## ❤️ Избранное (Favorite)

✔ Показывает только избранные курсы  
✔ Удаление курса сразу из списка  
✔ Полная синхронизация состояния  

  </td>
    <td width="50%" valign="top">

## 📄 Экран курса (Course)

✔ Открывается по `courseId`  
✔ Данные соответствуют выбранному курсу  
✔ Bookmark синхронизирован с системой  

  </td>
  </tr>

  <tr>
    <td width="50%" valign="top">

## 👤 Профиль (Profile)

✔ Экран аккаунта  
✔ Список курсов с прогрессом  
✔ Работа с избранным  

  </td>
    <td width="50%" valign="top">

## ℹ️ Дополнительно

✔ Реализованы все экраны, включая те, которые не требовались по ТЗ  
✔ Добавлены onboarding и расширенный profile  
✔ UI соответствует макету  

  </td>
  </tr>
</table>

---

# 🧠 Архитектура

Проект реализован как **многомодульное приложение**:

```text
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
└── settings.gradle.kts
````

<table>
  <tr>
    <td width="50%" valign="top">

## 📦 Domain слой

```text
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

  </td>
    <td width="50%" valign="top">

## 📦 Data слой

```text
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

  </td>
  </tr>
</table>

---

## 📦 Feature модули

```text
feature_auth       → Вход
feature_main       → Главный экран
feature_favorite   → Избранное
feature_course     → Курс
feature_profile    → Профиль
feature_onboarding → Онбординг
```

---

## 📦 App модуль

```text
app/
├── App.kt
├── MainActivity.kt
└── di/
    └── AppModule.kt
```

✔ Навигация
✔ DI
✔ Запуск приложения

---

# 🛠️ Технологии

<table>
  <tr>
    <td width="50%" valign="top">

* Kotlin

* XML

* MVVM

* Clean Architecture

* Koin

  </td>
    <td width="50%" valign="top">

* Retrofit

* OkHttp

* Room

* Coroutines + Flow

* Navigation Component

  </td>
  </tr>

</table>

---

# 🌐 Работа с данными

> Сетевой слой реализован через Retrofit + OkHttp + CoursesApi.
> Для стабильной демонстрации добавлен fallback на локальный mock JSON (`assets/courses.json`), полностью повторяющий структуру ответа mock API из ТЗ.
> Избранное хранится локально через Room.
> Архитектура: многомодульная, MVVM, Clean Architecture.
> UI реализован на XML.

---

# ⭐ Избранное (hasLike)

В API используется поле:

```kotlin
hasLike: Boolean
```

В UI оно реализовано как **bookmark (флажок)**:

* `true` → зелёный, курс в избранном
* `false` → пустой bookmark

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

<table>
  <tr>
    <td width="50%" valign="top">

✔ Добавление в избранное
✔ Удаление
✔ Синхронизация

  </td>
    <td width="50%" valign="top">

✔ Сортировка
✔ Открытие конкретного курса
✔ Перезапуск приложения

  </td>
  </tr>
</table>

---

# 📌 Соответствие ТЗ

✔ Все обязательные экраны реализованы
✔ Стек технологий соблюдён
✔ Данные загружаются из API
✔ Локальное хранение избранного реализовано
✔ UI соответствует макету
✔ Многомодульность соблюдена

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

* работу с API
* локальное хранение
* архитектуру
* UI по макету
* синхронизацию данных

---

## 👨‍💻 Автор

**Amanzhol Aimov**

---

## 🚀 Спасибо за просмотр

Увидел ТЗ, подумал: *“2 часа работы”* 😄
По факту вышло 2 дня, зато было много кайфа. Спасибо Андроиду 💚

Если проект был полезен или интересен — поставь ⭐ репозиторию 😊
