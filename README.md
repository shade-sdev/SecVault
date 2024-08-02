### Architecture

- **di/**: Contains dependency injection setup using Koin.
    - `AppModule.kt`: Defines Koin modules for dependency injection.
- **model/**: Contains data models and repository classes.
    - `Person.kt`: Data class representing a person.
    - `PersonRepository.kt`: Repository class for data operations.
- **ui/**: Contains UI-related code.
    - **theme/**: Contains theme-related files for Jetpack Compose.
        - `Color.kt`, `Shape.kt`, `Theme.kt`, `Type.kt`: Define colors, shapes, themes, and typography.
    - **components/**: Contains reusable UI components.
        - `PersonItem.kt`: Composable function for displaying a person item.
    - **screens/**: Contains screen composables.
        - `PersonScreen.kt`: Composable function for the person screen.
    - `App.kt`: Main composable function for the application.
- **viewmodel/**: Contains ViewModel classes.
    - `PersonViewModel.kt`: ViewModel for managing person data.
- `Main.kt`: Entry point of the application.

### Architecture Pattern

The architecture pattern to follow is MVVM (Model-View-ViewModel), which is well-suited for Jetpack Compose
applications.

### MVVM Components

- **Model**: Represents the data layer, including data classes and repository.
    - `Person.kt`: Data class.
    - `PersonRepository.kt`: Repository for data operations.
- **View**: Represents the UI layer, including composable functions.
    - `PersonScreen.kt`: Screen composable.
    - `PersonItem.kt`: UI component.
- **ViewModel**: Manages UI-related data and business logic.
    - `PersonViewModel.kt`: ViewModel for person data.

<p class="has-line-data" data-line-start="1" data-line-end="27">src/<br>
├── main/<br>
│   ├── kotlin/<br>
│   │   ├── com/<br>
│   │   │   ├── yourapp/<br>
│   │   │   │   ├── di/<br>
│   │   │   │   │   └── AppModule.kt<br>
│   │   │   │   ├── model/<br>
│   │   │   │   │   ├── Person.kt<br>
│   │   │   │   │   └── PersonRepository.kt<br>
│   │   │   │   ├── ui/<br>
│   │   │   │   │   ├── theme/<br>
│   │   │   │   │   │   ├── Color.kt<br>
│   │   │   │   │   │   ├── Shape.kt<br>
│   │   │   │   │   │   ├── Theme.kt<br>
│   │   │   │   │   │   └── Type.kt<br>
│   │   │   │   │   ├── components/<br>
│   │   │   │   │   │   └── PersonItem.kt<br>
│   │   │   │   │   ├── screens/<br>
│   │   │   │   │   │   └── PersonScreen.kt<br>
│   │   │   │   │   └── App.kt<br>
│   │   │   │   ├── viewmodel/<br>
│   │   │   │   │   └── PersonViewModel.kt<br>
│   │   │   │   └── Main.kt<br>
│   ├── resources/<br>
│   │   └── …</p>
