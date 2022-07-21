# 👥 Image Finder

Sample app for searching images using Pixabay Api.

This application is using the latest tools and libraries with Multi-Module Architecture + MVVM pattern.

## ⚒️ Tools

### 🧑🏻‍💻 Development
- Application entirely written in [Kotlin](https://kotlinlang.org)
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for the ui
- Asynchronous processing using [Coroutines](https://kotlin.github.io/kotlinx.coroutines/)
- Dependency injection with [Hilt](https://dagger.dev/hilt/)
- [Retrofit](https://square.github.io/retrofit/) for the HTTP client
- Image loading with [Coil](https://coil-kt.github.io/coil/), it's written in Kotlin, uses coroutines, and supports Jetpack Compose.

### 🧪 Test
- Unit Tests

All used dependencies can be accessed in the [BuildSrc Package](https://github.com/rezafaraji93/Image-Finder/tree/main/buildSrc/src/main/java)

## 🏛 Architecture & Patterns
This project is based on the Layered-Feature Modularization for better separation of concerns and encapsulation.

### 🧩 Application modules

* **app** - The Application module. It contains all the initialization logic for the Android
  environment and starts the _Jetpack Navigation Compose Graph_.
* **buildSrc** - The module/folder containing all dependencies
* **core** - The module/folder containing all files and utils that are commonly used among other modules.
* **core-ui** - The modules contains composables and resources used commonly in UI sections.
* **image-finder** - The modules contains layered modules for image finder application.
  * **image-finder-data** * - this module contains the part of the app which is responsible to fetch data from remote server or database by using remote mediator.
  * **image-finder-domain** * -  The modules containing the most important part of the application: the business logic. This module depends only on itself and all interaction it does is via dependency inversion.
  * **image-finder-presentation** * -  The modules contains Search screen and Image detail screen to show detailed information about the image
