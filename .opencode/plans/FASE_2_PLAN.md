# FASE 2: UI Completa + 3 Juegos Bíblicos Jugables

## Estado: EN PROGRESO

## Objetivo General

Crear la aplicación completa y funcional con:
- Lectura diaria con devocional
- 3 juegos bíblicos jugables (Trivia, Versículo Perdido, Memorización)
- Sistema de puntos y logros
- Navegación completa con BottomNav
- Tema pergamino dorado con dark mode
- 100+ versículos seed

## Configuración Aprobada

- **Juegos**: 3 completos (Trivia + Completa Versículo + Memorización Progresiva)
- **Versículos**: Expandir de 46 a 100+ versículos seed
- **UI**: Crear tema pergamino dorado desde cero
- **Orden**: Tema → DI → Domain → Screens → Games

---

## SECCION 1: Tema UI (Pergamino Dorado)

### Archivos a crear (3)

**1. `presentation/theme/Color.kt`**
- Paleta light mode: PergaminoFondo, PergaminoClaro, DoradoPrimario, DoradoOscuro, MarronTexto, Carmesí, VerdeEsperanza
- Paleta dark mode: PergaminoOscuro, PergaminoMedio, DoradoSuave, TextoClaro

**2. `presentation/theme/Typography.kt`**
- Roboto Slab para títulos (Bold, SemiBold, Medium)
- Roboto para body (Regular, Medium)
- Playfair Display Italic para versículos

**3. `presentation/theme/Theme.kt`**
- Material 3 ColorScheme personalizado
- Typography personalizado
- AppBibleTheme composable wrapper
- Soporte light/dark automático

---

## SECCION 2: DI Modules (Hilt)

### Archivos a crear (3)

**4. `di/DatabaseModule.kt`**
- provideDatabase(context) → AppDatabase (Singleton)
- provideVerseDao(database) → VerseDao
- provideReadingProgressDao(database) → ReadingProgressDao
- provideGameProgressDao(database) → GameProgressDao
- provideAchievementDao(database) → AchievementDao
- provideScoreDao(database) → ScoreDao

**5. `di/RepositoryModule.kt`**
- provideDailyReadingRepository(verseDao, progressDao) → DailyReadingRepository

**6. `di/UseCaseModule.kt`**
- provideGetDailyReadingUseCase(repository) → GetDailyReadingUseCase
- provideMarkAsReadUseCase(repository) → MarkAsReadUseCase
- provideGetReadingStreakUseCase(repository) → GetReadingStreakUseCase

---

## SECCION 3: Domain Models + UseCases

### Archivos a crear (7)

**7. `domain/model/Verse.kt`** - Modelo puro de versículo
**8. `domain/model/DailyReading.kt`** - Lectura diaria completa
**9. `domain/model/ReadingState.kt`** - Estado UI sellado (Loading/Success/Error)
**10. `domain/model/GameScore.kt`** - Puntuación y estadísticas del usuario
**11. `domain/model/Achievement.kt`** - Logro desbloqueable

**12. `domain/usecase/GetDailyReadingUseCase.kt`** - Obtener lectura del día (cronológico/temático)
**13. `domain/usecase/MarkAsReadUseCase.kt`** - Marcar lectura como completada (+50 XP)
**14. `domain/usecase/GetReadingStreakUseCase.kt`** - Calcular racha de días consecutivos

---

## SECCION 4: Game Models + Engines + Content

### Archivos a crear (8)

**15. `game/model/TriviaQuestion.kt`** - Pregunta de trivia con opciones, dificultad, referencia
**16. `game/model/FlashCard.kt`** - Flashcard para memorización con palabras ocultas
**17. `game/model/GameResult.kt`** - Resultado de partida (score, XP, logros desbloqueados)

**18. `game/engine/TriviaEngine.kt`** - Lógica de trivia (vidas, timer, puntuación, 100 preguntas)
**19. `game/engine/FillVerseEngine.kt`** - Lógica de completar versículo (palabras faltantes, validación exacta)
**20. `game/engine/MemorizeEngine.kt`** - Lógica de memorización progresiva (5 niveles)

**21. `game/content/TriviaQuestions.kt`** - 100 preguntas trivia (30 fáciles, 40 medias, 30 difíciles)
**22. `game/content/FlashCards.kt`** - 50 flashcards para memorización (20 AT, 20 NT, 10 temáticos)

---

## SECCION 5: Expandir Versículos Seed a 100+

### Archivos a modificar (1)

**23. `data/local/AppDatabase.kt`** - Actualizar
- Expandir seedVerses de 46 a 100+ versículos
- Agregar más versículos cronológicos (Génesis → Apocalipsis)
- Agregar más versículos temáticos (8 temas × 3-4 versículos cada uno)

---

## SECCION 6: Navigation + BottomNavBar

### Archivos a crear (2)

**24. `presentation/navigation/AppNavigation.kt`**
- NavHost con rutas: lectura, juegos, retos, progreso
- Rutas anidadas: juegos/trivia, juegos/fillverse, juegos/memorize
- Composables de navegación

**25. `presentation/navigation/BottomNavBar.kt`**
- BottomNavigationBar con 4 items: Lectura (MenuBook), Juegos (Games), Retos (LocalFireDepartment), Progreso (EmojiEvents)
- Estado seleccionado sincronizado con NavHost

---

## SECCION 7: AppBibleApplication + MainActivity

### Archivos a crear (2)

**26. `AppBibleApplication.kt`**
- @HiltAndroidApp
- onCreate(): NotificationScheduler.scheduleDailyReminder()

**27. `MainActivity.kt`**
- @AndroidEntryPoint
- setContent { AppBibleTheme { AppNavigation() } }
- Request POST_NOTIFICATIONS permission (Android 13+)
- Sin ActionBar (tema NoActionBar)

---

## SECCION 8: HomeScreen + LecturaScreen + LecturaViewModel

### Archivos a crear (3)

**28. `presentation/home/HomeScreen.kt`**
- TopAppBar: "AppBible ✨ Puntos: 1,250"
- Saludo personalizado + racha actual
- Card destacada: Lectura de hoy
- Carousel de juegos (Trivia, Completa, Memoriza)
- Sección logros recientes
- FAB: Reto del Día

**29. `presentation/lectura/LecturaScreen.kt`**
- Header con fecha y racha
- Card de versículo (Playfair Italic, referencia en carmesí)
- Card de reflexión del día
- Botón "Marcar como leído" (+50 XP)
- Selector de versión (RVR60/NTV/NVI)
- Botón "Siguiente"

**30. `presentation/lectura/LecturaViewModel.kt`**
- StateFlow con ReadingState
- Métodos: loadDailyReading(), markAsRead(), changeVersion(), nextReading()
- Inyección de use cases con Hilt

---

## SECCION 9: JuegosScreen + TriviaScreen + TriviaViewModel

### Archivos a crear (4)

**31. `presentation/juegos/JuegosScreen.kt`**
- Hub de juegos con carousel
- Cards para cada juego: Trivia, Completa Versículo, Memorización
- Mostrar mejor puntuación por juego
- Sección "Próximamente" para juegos de FASE 3

**32. `presentation/juegos/trivia/TriviaScreen.kt`**
- TopBar: vidas (❤️❤️❤️), timer (⏱️ 25s)
- Pregunta con 4 opciones
- Feedback inmediato (verde/rojo) con explicación bíblica
- Navegación entre preguntas
- Pantalla de resultados con score y XP ganado

**33. `presentation/juegos/trivia/TriviaViewModel.kt`**
- StateFlow con TriviaState (pregunta actual, vidas, score, timer)
- Métodos: loadQuestion(), selectAnswer(), checkAnswer(), nextQuestion(), finishGame()
- Inyección de TriviaEngine

**34. `presentation/juegos/trivia/TriviaEngine.kt`** (ya listado en SECCION 4)

---

## SECCION 10: FillVerseScreen + FillVerseViewModel

### Archivos a crear (3)

**35. `presentation/juegos/fillverse/FillVerseScreen.kt`**
- Versículo con palabras faltantes (huecos)
- Opciones de palabras (drag & drop o tap)
- Botón "Verificar"
- Feedback con referencia bíblica
- Pantalla de resultados

**36. `presentation/juegos/fillverse/FillVerseViewModel.kt`**
- StateFlow con FillVerseState (versículo actual, palabras faltantes, input usuario, score)
- Métodos: loadVerse(), checkAnswer(), nextVerse(), finishGame()
- Inyección de FillVerseEngine

**37. `presentation/juegos/fillverse/FillVerseEngine.kt`** (ya listado en SECCION 4)

---

## SECCION 11: MemorizeScreen + MemorizeViewModel

### Archivos a crear (3)

**38. `presentation/juegos/memorize/MemorizeScreen.kt`**
- Versículo con palabras progresivamente ocultas
- Niveles: 2 palabras → 5 palabras → 10 palabras → solo pistas
- Input de palabras faltantes
- Barra de progreso
- Botones: "Siguiente palabra", "Mostrar todo"

**39. `presentation/juegos/memorize/MemorizeViewModel.kt`**
- StateFlow con MemorizeState (nivel actual, palabras ocultas, progreso)
- Métodos: loadFlashCard(), revealLevel(), checkAnswer(), nextCard(), finishGame()
- Inyección de MemorizeEngine

**40. `presentation/juegos/memorize/MemorizeEngine.kt`** (ya listado en SECCION 4)

---

## SECCION 12: RetosScreen + ProgresoScreen + ProgresoViewModel

### Archivos a crear (3)

**41. `presentation/retos/RetosScreen.kt`**
- Placeholder "Próximamente"
- Lista de retos futuros: 7 días, oración guiada, agradecimiento diario
- Mensaje motivacional

**42. `presentation/progreso/ProgresoScreen.kt`**
- Card de nivel y XP (barra de progreso)
- Estadísticas: lecturas, juegos, racha, versículos memorizados
- Sección de logros (8/24 desbloqueados)
- Historial de mejores puntuaciones por juego

**43. `presentation/progreso/ProgresoViewModel.kt`**
- StateFlow con ProgresoState (score, achievements, estadísticas)
- Métodos: loadScore(), loadAchievements(), loadStats()
- Inyección de ScoreDao, AchievementDao, GameProgressDao

---

## SECCION 13: Resources

### Archivos a modificar (3)

**44. `res/values/colors.xml`**
- Reemplazar colores default (púrpura/teal) con paleta pergamino dorado
- Definir todos los colores light y dark

**45. `res/values/strings.xml`**
- Agregar TODOS los strings de la app:
  - Nombres de pantallas
  - Textos de botones
  - Mensajes de logros
  - Textos de juegos (100 preguntas trivia)
  - Mensajes de error
  - Notificaciones

**46. `res/values/themes.xml`**
- Cambiar a Theme.AppBible (NoActionBar) para Compose
- Definir colores primarios/secundarios

---

## Resumen de Archivos

| Sección | Archivos Nuevos | Archivos Modificados |
|---|---|---|
| 1. Tema UI | 3 | 0 |
| 2. DI Modules | 3 | 0 |
| 3. Domain | 7 | 0 |
| 4. Game | 8 | 0 |
| 5. Seed Data | 0 | 1 |
| 6. Navigation | 2 | 0 |
| 7. App + Main | 2 | 0 |
| 8. Lectura | 3 | 0 |
| 9. Trivia | 4 | 0 |
| 10. FillVerse | 3 | 0 |
| 11. Memorize | 3 | 0 |
| 12. Retos/Progreso | 3 | 0 |
| 13. Resources | 0 | 3 |
| **TOTAL** | **43** | **4** |

---

## Resultado Esperido

✅ AppBible v2.0 completamente funcional
✅ BottomNav: Lectura | Juegos | Retos | Progreso
✅ TopAppBar con puntos y XP
✅ Lectura diaria con versículo + reflexión + marcar leído
✅ Trivia Bíblica (100 preguntas, 3 dificultades, vidas, timer)
✅ Completa el Versículo (50 versículos, 3 dificultades)
✅ Memorización Progresiva (50 flashcards, 5 niveles)
✅ Sistema de puntos + XP + niveles + 24 logros
✅ 100+ versículos seed RVR1960
✅ Tema pergamino dorado + dark mode
✅ BUILD SUCCESSFUL
✅ Commits descriptivos + push a GitHub

---

## Orden de Ejecución

1. ✅ SECCION 1: Tema UI
2. ⬜ SECCION 2: DI Modules
3. ⬜ SECCION 3: Domain Models + UseCases
4. ⬜ SECCION 4: Game Models + Engines + Content
5. ⬜ SECCION 5: Expandir versículos a 100+
6. ⬜ SECCION 6: Navigation
7. ⬜ SECCION 7: Application + MainActivity
8. ⬜ SECCION 8: HomeScreen + LecturaScreen
9. ⬜ SECCION 9: Trivia Game
10. ⬜ SECCION 10: FillVerse Game
11. ⬜ SECCION 11: Memorize Game
12. ⬜ SECCION 12: Retos + Progreso
13. ⬜ SECCION 13: Resources
14. ⬜ Validación + Commits + Push

---

## Commits Planificados

Cada sección generará commits descriptivos:
- `feat(ui): tema pergamino dorado Material3`
- `feat(di): Hilt modules para Database, Repository, UseCase`
- `feat(domain): models puros + use cases de lectura`
- `feat(game): engines Trivia, FillVerse, Memorize + 100 preguntas`
- `feat(data): expandir versículos seed a 100+`
- `feat(nav): Navigation Compose + BottomNavBar`
- `feat(app): AppBibleApplication + MainActivity`
- `feat(ui): HomeScreen + LecturaScreen con ViewModel`
- `feat(game-ui): TriviaScreen jugable con vidas y timer`
- `feat(game-ui): FillVerseScreen con validación exacta`
- `feat(game-ui): MemorizeScreen con progresión 5 niveles`
- `feat(ui): ProgresoScreen con estadísticas y logros`
- `feat(resources): colors, strings, themes pergamino`
- `chore: BUILD SUCCESSFUL FASE 2 completa`

---

## Siguiente Paso Inmediato

Comenzar con **SECCION 1: Tema UI** - Crear Color.kt, Typography.kt, Theme.kt con paleta pergamino dorado.
