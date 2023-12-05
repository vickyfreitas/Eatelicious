# Eatelicious: Mobile Development with Android Course

Eatelicious is an Android application, constructed using Kotlin, as a component of an extensive course on Android data persistence. The app incorporates a straightforward authentication mechanism via Firebase, enabling user registration, login, and logout functionalities. Additionally, it functions as a recipe storage platform, employing shared preferences for personalized user features and managing a local SQLite database.

## Course Description

- Duration: 27 hours 52 minutes 58 seconds
- Prerequisites: None
- Expertise Level: Intermediate
- Code: IT_MDDPCIDJ_03_ENUS

## Objectives

- Understand key concepts of Android data storage
- Learn to choose appropriate storage methods for different scenarios
- Create an HTTP endpoint with app-specific storage
- Implement shared preferences for key-value data storage
- Utilize shared preferences for tracking recent items and autofilling forms
- Use SQLite for structured data storage
- Integrate SQLite with Android apps
- Implement a toggle switch in an Android app
- Set up CRUD operations in a restaurant app
- Configure Kotlin Symbol Processing (KSP) plugin and Room API libraries
- Utilize Room API to load data from SQLite
- Summarize key course concepts

## Instructor

Janani Ravi, Co-founder of Loonycorn

## Features

- User Authentication: The application uses Firebase Authentication for user sign up and login.
- MVVM Architecture: The application follows the Model-View-ViewModel (MVVM) design pattern.
- Dependency Injection: The application uses Hilt for dependency injection.

## Technologies Used

- Kotlin: The primary programming language used for development.
- Firebase: Used for user authentication.
- Hilt: A dependency injection library for Android, used to inject dependencies into different parts of the app.
- Jetpack Compose: The modern way to build native Android UI. It simplifies and accelerates UI development on Android.

## Project Structure

The project is divided into several packages:

- `auth`: Contains classes related to authentication, including the `AuthViewModel`, `AuthRepository`, and `AuthRepositoryImpl`.
- `ui`: Contains classes related to the user interface, including the `LoginScreen`.
- `utils`: Contains utility classes.

## Setup

To get started with the Eatelicious project:

1. Clone this repository.
2. Open the project in your Android development environment.
3. Set up Firebase in the Firebase console and download the `google-services.json` file into the `app` directory.
4. Build and run the project on an emulator or real device.
5. Follow the course modules to implement each feature step-by-step.

## Contributing

Feel free to fork this repository and submit pull requests with improvements or fixes.


Enjoy your programming journey and have fun learning Android data persistence!
