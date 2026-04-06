# 📖 APPBIBLE v2.0 — Plan de Reestructuración Completo

## Información General

- **Proyecto**: AppBible — App Android gamificada para aprender Biblia jugando
- **Stack**: Kotlin, Jetpack Compose, MVVM, Room SQLite, Firebase Auth
- **Min SDK**: 26 | **Target SDK**: 36
- **Versiones Biblia**: RVR1960 (default) → NTV (2da) → NVI (3ra)
- **UI**: Material 3, colores pergamino dorado, dark theme
- **Diferenciador**: NO compite con YouVersion → APRENDIZAJE JUEGOS + PRÁCTICO

## Principios Fundamentales

- Fidelidad bíblica absoluta (NO modificar versículos)
- No se modifica la Biblia
- Edificación espiritual
- Accesibilidad (todas las edades)
- Gamificación educativa (NO competitiva)

---

## 📊 ESTADO ACTUAL DEL PROYECTO

### LO QUE EXISTE y SE CONSERVA ✅ (6 archivos)

| Archivo | Ubicación | Descripción |
|---|---|---|
| VerseEntity.kt | data/local/entity/ | Tabla verses con 10 campos + índices |
| ReadingProgressEntity.kt | data/local/entity/ | Tabla reading_progress con 7 campos |
| VerseDao.kt | data/local/dao/ | 6 métodos (queries + inserts batch) |
| ReadingProgressDao.kt | data/local/dao/ | 4 métodos (progreso + racha) |
| AppDatabase.kt | data/local/ | Room DB + 46 versículos semilla RVR1960 |
| DailyReadingRepository.kt | data/repository/ | Repository con lógica cronológico/temático + racha |

### BUILD SYSTEM ✅ (configurado y funcional)

- AGP 8.9.1, Kotlin 2.1.0, KSP 2.1.0-1.0.29
- Compose BOM 2025.06.01, Room 2.7.2, Hilt 2.57
- Navigation Compose, WorkManager, Coroutines
- BUILD SUCCESSFUL con 38 tasks

### LO QUE FALTA ❌ (se crea en FASE 2)

- UI completa (tema, navegación, pantallas, ViewModels)
- Domain layer (models puros, use cases)
- DI modules (Hilt)
- Application class (@HiltAndroidApp)
- Game engines (trivia, fill verse, memorize)
- Game content (100 preguntas, 50 flashcards)
- Entidades de gamificación (puntos, logros, scores)

---

## 🏗️ ORDEN DE EJECUCIÓN

### PASO 0: Documento Plan (ACTUAL)
Crear este documento con todas las FASES detalladas.

### PASO 1: Entidades de Gamificación a Room
Agregar GameProgressEntity, AchievementEntity, ScoreEntity a la base de datos existente.

### PASO 2: FASE 2 — Lectura Diaria + 3 Juegos Jugables
Crear toda la UI, domain, DI, game engines y content.

### PASO 3: FASE 3 — Juegos 4-6 + Retos + Aprende
Agregar más juegos, retos espirituales, contexto histórico.

### PASO 4: FASE 4 — Comunidad + Notificaciones + Deploy
Comunidad, compartir, notificaciones push, APK Play Store.

---

# PASO 1: Entidades de Gamificación a Room

## Objetivo

Expandir la base de datos existente con 3 nuevas entidades para soportar el sistema de gamificación: puntos, logros y progreso de juegos.

## Archivos a Crear (3 nuevos)

### 1. `data/local/entity/GameProgressEntity.kt`

**Tabla**: `game_progress` — Registra cada partida de juego del usuario.

| Campo | Tipo | Descripción |
|---|---|---|
| id | Long (PK auto) | Identificador único |
| gameType | String | Tipo de juego ("trivia", "fillverse", "memorize", "character", "map", "comprehension") |
| difficulty | String | Dificultad ("facil", "medio", "dificil") |
| score | Int | Puntuación obtenida en la partida |
| totalQuestions | Int | Total de preguntas en la partida |
| correctAnswers | Int | Respuestas correctas |
| wrongAnswers | Int | Respuestas incorrectas |
| completedAt | Long | Timestamp de finalización |
| durationSeconds | Long | Duración de la partida en segundos |
| livesUsed | Int | Vidas gastadas (para juegos con vidas) |

**Índices**:
- `idx_game_type`: (gameType) — filtrar por tipo de juego
- `idx_game_date`: (completedAt DESC) — historial ordenado

### 2. `data/local/entity/AchievementEntity.kt`

**Tabla**: `achievements` — Logros desbloqueables del usuario.

| Campo | Tipo | Descripción |
|---|---|---|
| id | Long (PK) | Identificador único del logro |
| name | String | Nombre del logro ("Primer Paso", "Sabio de Israel") |
| description | String | Descripción del logro |
| icon | String | Icono/emoji del logro ("📖", "🏆", "⭐") |
| category | String | Categoría ("lectura", "juegos", "rachas", "comunidad") |
| requirement | Int | Requisito numérico para desbloquear |
| xpReward | Int | XP otorgado al desbloquear |
| isUnlocked | Boolean | true = ya desbloqueado |
| unlockedAt | Long? | Timestamp de desbloqueo |

**Índices**:
- `idx_achievement_category`: (category) — filtrar por categoría
- `idx_achievement_unlocked`: (isUnlocked) — ver desbloqueados

### 3. `data/local/entity/ScoreEntity.kt`

**Tabla**: `user_scores` — Puntuación acumulada del usuario.

| Campo | Tipo | Descripción |
|---|---|---|
| id | Long (PK auto) | Identificador único |
| totalPoints | Int | Puntos totales acumulados |
| totalXP | Int | XP total acumulado |
| gamesPlayed | Int | Total de partidas jugadas |
| gamesWon | Int | Partidas ganadas |
| streakDays | Int | Racha actual de días activos |
| bestStreakDays | Int | Mejor racha histórica |
| versesMemorized | Int | Versículos memorizados |
| readingsCompleted | Int | Lecturas completadas |
| level | Int | Nivel actual del usuario |
| lastActiveDate | String | Última fecha de actividad ("2026-04-03") |

**Índices**:
- Único: Solo 1 registro activo (siempre hay un solo score del usuario)

## DAOs a Crear (2 nuevos)

### 4. `data/local/dao/GameProgressDao.kt`

| Método | Query | Retorna |
|---|---|---|
| insertGameProgress(progress) | INSERT | Unit |
| getGameProgressByType(type, limit) | WHERE gameType = :type ORDER BY completedAt DESC LIMIT :limit | Flow<List<GameProgressEntity>> |
| getBestScoreByType(type) | WHERE gameType = :type ORDER BY score DESC LIMIT 1 | GameProgressEntity? |
| getTotalGamesPlayed() | COUNT(*) | Int |
| getGamesByDifficulty(type, difficulty) | WHERE gameType = :type AND difficulty = :difficulty | Flow<List<GameProgressEntity>> |

### 5. `data/local/dao/AchievementDao.kt`

| Método | Query | Retorna |
|---|---|---|
| insertAchievements(achievements) | INSERT OR IGNORE | Unit |
| getAllAchievements() | SELECT * ORDER BY category, id | Flow<List<AchievementEntity>> |
| getUnlockedAchievements() | WHERE isUnlocked = 1 | Flow<List<AchievementEntity>> |
| unlockAchievement(id, timestamp) | UPDATE SET isUnlocked = 1, unlockedAt = :timestamp | Unit |
| getAchievementsByCategory(category) | WHERE category = :category | Flow<List<AchievementEntity>> |

### 6. `data/local/dao/ScoreDao.kt`

| Método | Query | Retorna |
|---|---|---|
| getUserScore() | SELECT * LIMIT 1 | Flow<ScoreEntity?> |
| initializeScore(score) | INSERT | Unit |
| updateScore(score) | UPDATE (REPLACE) | Unit |
| addPoints(points) | UPDATE SET totalPoints = totalPoints + :points | Unit |
| addXP(xp) | UPDATE SET totalXP = totalXP + :xp | Unit |
| incrementGamesPlayed() | UPDATE SET gamesPlayed = gamesPlayed + 1 | Unit |
| incrementGamesWon() | UPDATE SET gamesWon = gamesWon + 1 | Unit |
| updateStreak(days) | UPDATE SET streakDays = :days | Unit |
| updateBestStreak(days) | UPDATE SET bestStreakDays = :days WHERE :days > bestStreakDays | Unit |

## Modificaciones a Archivos Existentes

### 7. `data/local/AppDatabase.kt` — Actualizar

**Cambios**:
- Agregar entities: GameProgressEntity, AchievementEntity, ScoreEntity
- Subir version a 2
- Agregar abstract methods: gameProgressDao(), achievementDao(), scoreDao()
- DatabaseCallback: pre-poblar 24 logros iniciales + inicializar ScoreEntity

**Logros semilla (24 logros)**:

| ID | Nombre | Descripción | Icono | Categoría | Requisito | XP |
|---|---|---|---|---|---|---|
| 1 | Primer Paso | Completa tu primera lectura | 📖 | lectura | 1 | 50 |
| 2 | Lector Constante | 7 días seguidos leyendo | 🔥 | rachas | 7 | 100 |
| 3 | Lector Dedicado | 30 días seguidos leyendo | 📚 | rachas | 30 | 250 |
| 4 | Explorador | Completa 50 lecturas | 🗺️ | lectura | 50 | 200 |
| 5 | Sabio de Israel | 100 respuestas correctas en trivia | 🧠 | juegos | 100 | 300 |
| 6 | Trivia Master | Gana 10 partidas de trivia | 🏆 | juegos | 10 | 150 |
| 7 | Memoria de Elefante | Memoriza 10 versículos | 🐘 | juegos | 10 | 200 |
| 8 | Completista | Completa 25 versículos perdidos | ✍️ | juegos | 25 | 150 |
| 9 | Primer Juego | Juega tu primer juego | 🎮 | juegos | 1 | 25 |
| 10 | Jugador Activo | Juega 50 partidas | 🎯 | juegos | 50 | 200 |
| 11 | Versículo de Oro | Memoriza 25 versículos | ⭐ | juegos | 25 | 300 |
| 12 | Conocedor | Completa 5 temas de Aprende la Biblia | 📖 | lectura | 5 | 150 |
| 13 | Perseverante | Vuelve a jugar después de perder | 💪 | rachas | 1 | 50 |
| 14 | Racha de Fuego | 14 días seguidos activo | 🔥🔥 | rachas | 14 | 200 |
| 15 | Leyenda | 30 días seguidos activo | 👑 | rachas | 30 | 500 |
| 16 | Primer Reto | Completa tu primer reto espiritual | 🙏 | comunidad | 1 | 75 |
| 17 | Siervo Fiel | Completa 10 retos espirituales | ⛪ | comunidad | 10 | 200 |
| 18 | Generoso | Comparte 5 versículos | 💝 | comunidad | 5 | 100 |
| 19 | Nivel 5 | Alcanza nivel 5 | 📈 | juegos | 5 | 100 |
| 20 | Nivel 10 | Alcanza nivel 10 | 🌟 | juegos | 10 | 250 |
| 21 | Nivel 25 | Alcanza nivel 25 | 💎 | juegos | 25 | 500 |
| 22 | Coleccionista | Desbloquea 15 logros | 🎖️ | juegos | 15 | 300 |
| 23 | Maestro Bíblico | Completa todos los modos de juego en difícil | 🎓 | juegos | 6 | 1000 |
| 24 | Discípulo | Usa la app por 90 días | ✝️ | rachas | 90 | 750 |

### 8. `gradle/libs.versions.toml` — Sin cambios

Ya tiene todas las dependencias necesarias.

## Resultado Esperido

✅ 3 nuevas entidades Room para gamificación
✅ 3 nuevos DAOs con queries para juegos, logros y puntuación
✅ AppDatabase actualizada a versión 2 con migration
✅ 24 logros semilla pre-poblados
✅ ScoreEntity inicializado al primer launch
✅ BUILD SUCCESSFUL

---

# PASO 2: FASE 2 — Lectura Diaria + 3 Juegos Jugables

## Objetivo

Crear la app completa y funcional con:
- Lectura diaria con devocional
- 3 juegos bíblicos jugables (Trivia, Versículo Perdido, Memorización)
- Sistema de puntos y logros
- Navegación completa con BottomNav
- Tema pergamino dorado con dark mode

## 2.1 — Tema UI (pergamino dorado)

### `presentation/theme/Color.kt`

**Light Mode**:
| Color | Hex | Uso |
|---|---|---|
| PergaminoFondo | #F5E6C8 | Fondo principal |
| PergaminoClaro | #FFF8E7 | Cards, superficies |
| DoradoPrimario | #8B6914 | Botones, acentos |
| DoradoOscuro | #6B4F10 | Botones pressed |
| DoradoClaro | #C4A35A | Iconos, bordes decorativos |
| MarronTexto | #5C4033 | Texto principal |
| MarronClaro | #8B7355 | Texto secundario |
| Carmesi | #8B0000 | Referencias bíblicas |
| VerdeEsperanza | #2E7D32 | Indicadores de progreso |
| VerdeClaro | #4CAF50 | Checkmarks, badges |

**Dark Mode**:
| Color | Hex | Uso |
|---|---|---|
| PergaminoOscuro | #2C1810 | Fondo principal dark |
| PergaminoMedio | #3D2B1F | Cards, superficies dark |
| DoradoSuave | #C4A35A | Acentos dark |
| TextoClaro | #F5E6C8 | Texto principal dark |
| TextoSecundario | #B8A88A | Texto secundario dark |

### `presentation/theme/Typography.kt`

| Estilo | Fuente | Peso | Tamaño | Uso |
|---|---|---|---|---|
| DisplayLarge | Roboto Slab | Bold | 32sp | Títulos principales |
| TitleLarge | Roboto Slab | SemiBold | 24sp | Headers de pantalla |
| TitleMedium | Roboto Slab | Medium | 20sp | Subtítulos |
| BodyLarge | Roboto | Regular | 16sp | Texto body |
| BodyMedium | Roboto | Regular | 14sp | Texto secundario |
| VerseText | Playfair Display | Italic | 20sp | Versículos bíblicos |
| Reference | Roboto | Medium | 14sp | Referencias bíblicas |
| LabelSmall | Roboto | Medium | 12sp | Labels, badges |

### `presentation/theme/Theme.kt`

- Material 3 con ColorScheme personalizado
- Typography personalizado
- Soporte light/dark automático
- AppBibleTheme composable wrapper

## 2.2 — Navegación

### `presentation/navigation/AppNavigation.kt`

**Rutas**:
| Ruta | Pantalla | Descripción |
|---|---|---|
| "lectura" | LecturaScreen | Lectura diaria devocional |
| "juegos" | JuegosScreen | Hub de juegos bíblicos |
| "juegos/trivia" | TriviaScreen | Juego de trivia bíblica |
| "juegos/fillverse" | FillVerseScreen | Completar versículo |
| "juegos/memorize" | MemorizeScreen | Memorización progresiva |
| "retos" | RetosScreen | Retos espirituales |
| "progreso" | ProgresoScreen | Puntos, logros, estadísticas |

### `presentation/navigation/BottomNavBar.kt`

**Items**:
| Icono | Label | Ruta |
|---|---|---|
| MenuBook | Lectura | lectura |
| Games | Juegos | juegos |
| LocalFireDepartment | Retos | retos |
| EmojiEvents | Progreso | progreso |

## 2.3 — Application + DI

### `AppBibleApplication.kt`

- @HiltAndroidApp
- onCreate(): NotificationScheduler.scheduleDailyReminder()

### `di/DatabaseModule.kt`

- provideDatabase(context) → AppDatabase (Singleton)
- provideVerseDao(database) → VerseDao
- provideReadingProgressDao(database) → ReadingProgressDao
- provideGameProgressDao(database) → GameProgressDao
- provideAchievementDao(database) → AchievementDao
- provideScoreDao(database) → ScoreDao

### `di/RepositoryModule.kt`

- provideDailyReadingRepository(verseDao, progressDao) → DailyReadingRepository

### `di/UseCaseModule.kt`

- provideGetDailyReadingUseCase(repository) → GetDailyReadingUseCase
- provideMarkAsReadUseCase(repository) → MarkAsReadUseCase
- provideGetReadingStreakUseCase(repository) → GetReadingStreakUseCase

## 2.4 — Domain Models

### `domain/model/Verse.kt`

```kotlin
data class Verse(
    val id: Long,
    val book: String,
    val chapter: Int,
    val verseNumber: Int,
    val text: String,
    val reference: String,
    val version: String,
    val theme: String?
)
```

### `domain/model/DailyReading.kt`

```kotlin
data class DailyReading(
    val verses: List<Verse>,
    val date: String,
    val dayNumber: Int,
    val isThematic: Boolean,
    val isCompleted: Boolean,
    val streak: Int,
    val reflection: String?
)
```

### `domain/model/ReadingState.kt`

```kotlin
sealed class ReadingState {
    object Loading : ReadingState()
    data class Success(val reading: DailyReading) : ReadingState()
    data class Error(val message: String) : ReadingState()
}
```

### `domain/model/GameScore.kt`

```kotlin
data class GameScore(
    val totalPoints: Int,
    val totalXP: Int,
    val gamesPlayed: Int,
    val gamesWon: Int,
    val streakDays: Int,
    val bestStreakDays: Int,
    val versesMemorized: Int,
    val readingsCompleted: Int,
    val level: Int
)
```

### `domain/model/Achievement.kt`

```kotlin
data class Achievement(
    val id: Long,
    val name: String,
    val description: String,
    val icon: String,
    val category: String,
    val requirement: Int,
    val xpReward: Int,
    val isUnlocked: Boolean,
    val unlockedAt: Long?
)
```

## 2.5 — Domain UseCases

### `domain/usecase/GetDailyReadingUseCase.kt`

- Calcular día del año (1-365)
- L-V → cronológico, S-D → temático
- Retornar Flow<DailyReading>

### `domain/usecase/MarkAsReadUseCase.kt`

- Guardar progreso
- Actualizar puntos (+50 XP lectura completada)
- Actualizar racha

### `domain/usecase/GetReadingStreakUseCase.kt`

- Calcular días consecutivos desde hoy
- Retornar Int

## 2.6 — Game Models

### `game/model/TriviaQuestion.kt`

```kotlin
data class TriviaQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val difficulty: String,
    val category: String,
    val reference: String,
    val explanation: String
)
```

### `game/model/FlashCard.kt`

```kotlin
data class FlashCard(
    val id: Int,
    val verseText: String,
    val reference: String,
    val hiddenWords: List<Int>,
    val allWords: List<String>,
    val difficulty: String
)
```

### `game/model/GameResult.kt`

```kotlin
data class GameResult(
    val gameType: String,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val xpEarned: Int,
    val pointsEarned: Int,
    val durationSeconds: Long,
    val newAchievements: List<Achievement>
)
```

## 2.7 — Game Engines

### `game/engine/TriviaEngine.kt`

**Lógica**:
- Cargar preguntas por categoría y dificultad
- Sistema de vidas (3 por partida)
- Puntuación: +100 correcta, +150 sin errores, +50% bonus racha
- Timer por pregunta (30s fácil, 20s medio, 15s difícil)
- Feedback inmediato con explicación bíblica
- Guardar resultado al terminar

**Contenido**: 100 preguntas trivia distribuidas:
- 30 fáciles (personajes básicos, eventos conocidos)
- 40 medias (contexto histórico, detalles)
- 30 difíciles (profecías, genealogías, números)

### `game/engine/FillVerseEngine.kt`

**Lógica**:
- Mostrar versículo con palabras faltantes
- Usuario escribe las palabras exactas
- Dificultad: fácil (2 palabras), medio (5 palabras), difícil (10 palabras)
- Validación exacta (sin modificar texto bíblico)
- Puntuación: +200 por palabra correcta, bonus por completar todo
- Versículos de los 46 seed + adicionales

**Contenido**: 50 versículos preparados para completar

### `game/engine/MemorizeEngine.kt`

**Lógica**:
- Memorización progresiva:
  1. Mostrar versículo completo
  2. Ocultar 2 palabras aleatorias
  3. Ocultar 5 palabras
  4. Ocultar 10 palabras
  5. Solo留下 pistas (primera letra)
- Flashcards rápidas: elegir referencia correcta
- Puntuación: +300 versículo dominado
- Tracking de versículos memorizados

**Contenido**: 50 flashcards con versículos

## 2.8 — Content Data

### `game/content/TriviaQuestions.kt`

**100 preguntas trivia** organizadas por categoría y dificultad:

**Fáciles (30)**:
- ¿Quién construyó el arca? (Noé)
- ¿Cuántos días tardó Dios en crear el mundo? (6)
- ¿Cuántos discípulos tenía Jesús? (12)
- ¿Qué mar abrió Moisés? (Mar Rojo)
- ¿Quién mató a Goliat? (David)
- ...25 más

**Medias (40)**:
- ¿Qué rey construyó el primer templo? (Salomón)
- ¿Cuántos años vivieron los israelitas en el desierto? (40)
- ¿En qué monte recibió Moisés los mandamientos? (Sinaí)
- ¿Quién traicionó a Jesús? (Judas Iscariote)
- ...36 más

**Difíciles (30)**:
- ¿Cuántos libros tiene la Biblia? (66)
- ¿Cuál es el capítulo más largo de la Biblia? (Salmo 119)
- ¿Cuántas plagas hubo en Egipto? (10)
- ¿Quién fue el primer mártir cristiano? (Esteban)
- ...26 más

### `game/content/FlashCards.kt`

**50 flashcards** para memorización:
- 20 del AT (Génesis, Éxodo, Salmos, Proverbios, Isaías)
- 20 del NT (Evangelios, Hechos, Epístolas, Apocalipsis)
- 10 temáticos (fe, amor, esperanza, sabiduría, oración)

## 2.9 — Pantallas Compose

### `MainActivity.kt`

- @AndroidEntryPoint
- setContent { AppBibleTheme { AppNavigation() } }
- Request POST_NOTIFICATIONS permission (Android 13+)

### `presentation/home/HomeScreen.kt`

**Estructura**:
```
┌──────────────────────────────────────────┐
│  AppBible ✨              Puntos: 1,250  │ ← TopAppBar
├──────────────────────────────────────────┤
│                                          │
│  👋 ¡Buenos días, Hermano!               │ ← Saludo
│  🔥 Racha: 7 días                        │
│                                          │
│  ┌────────────────────────────────────┐  │
│  │  📖 Lectura de Hoy                │  │ ← Card destacada
│  │  Génesis 1:1                      │  │
│  │  [Leer Ahora]                     │  │
│  └────────────────────────────────────┘  │
│                                          │
│  🎮 Juegos Bíblicos                      │ ← Sección juegos
│  ┌────────┐ ┌────────┐ ┌────────┐       │
│  │ Trivia │ │Completa│ │Memo-   │       │ ← Carousel
│  │ Bíblica│ │Versículo│ │riza    │       │
│  │ ⭐4.5  │ │ ✍️     │ │ 🧠    │       │
│  └────────┘ └────────┘ └────────┘       │
│                                          │
│  🏆 Logros Recientes                     │ ← Sección logros
│  📖 Primer Paso  🔥 Lector Constante    │
│                                          │
├──────────────────────────────────────────┤
│  📖      🎮      🔥      🏆             │ ← BottomNav
│ Lectura  Juegos  Retos  Progreso        │
└──────────────────────────────────────────┘
```

### `presentation/lectura/LecturaScreen.kt`

**Estructura**:
```
┌──────────────────────────────────────────┐
│  📖 Lectura Diaria                🔥 7   │
│  Viernes, 3 de Abril 2026                │
├──────────────────────────────────────────┤
│                                          │
│  ┌────────────────────────────────────┐  │
│  │  "En el principio creó Dios        │  │ ← Versículo
│  │   los cielos y la tierra."         │  │   (Playfair Italic)
│  │              — Génesis 1:1 (RVR60) │  │
│  └────────────────────────────────────┘  │
│                                          │
│  ┌────────────────────────────────────┐  │
│  │  📝 Reflexión del día             │  │
│  │  Este versículo nos recuerda...   │  │
│  └────────────────────────────────────┘  │
│                                          │
│  ┌────────────────────────────────────┐  │
│  │   ✅ Marcar como leído (+50 XP)   │  │
│  └────────────────────────────────────┘  │
│                                          │
│  Versión: [RVR60 ▼]    [Siguiente →]     │
└──────────────────────────────────────────┘
```

### `presentation/juegos/JuegosScreen.kt`

**Estructura**:
```
┌──────────────────────────────────────────┐
│  🎮 Juegos Bíblicos         Puntos: 1250 │
├──────────────────────────────────────────┤
│                                          │
│  ┌────────────────────────────────────┐  │
│  │  🧠 Trivia Bíblica                │  │
│  │  100 preguntas · 3 dificultades   │  │
│  │  [Jugar]  Mejor: 850 pts          │  │
│  └────────────────────────────────────┘  │
│                                          │
│  ┌────────────────────────────────────┐  │
│  │  ✍️ Completa el Versículo          │  │
│  │  50 versículos · 3 dificultades   │  │
│  │  [Jugar]  Mejor: 600 pts          │  │
│  └────────────────────────────────────┘  │
│                                          │
│  ┌────────────────────────────────────┐  │
│  │  🃏 Memorización Progresiva       │  │
│  │  50 flashcards · 5 niveles        │  │
│  │  [Jugar]  Memorizados: 5          │  │
│  └────────────────────────────────────┘  │
│                                          │
│  🔒 Próximamente:                        │
│  🎭 Adivinar Personaje · 🗺️ Mapa Bíblico│
└──────────────────────────────────────────┘
```

### `presentation/juegos/trivia/TriviaScreen.kt`

**Estructura**:
```
┌──────────────────────────────────────────┐
│  ← Trivia Bíblica    ❤️❤️❤️   ⏱️ 25s    │
│  Pregunta 3/10 · Fácil                    │
├──────────────────────────────────────────┤
│                                          │
│  ¿Quién construyó el arca según          │
│  la Biblia?                              │ ← Pregunta
│                                          │
│  ┌────────────────────────────────────┐  │
│  │  A) Moisés                         │  │ ← Opción A
│  └────────────────────────────────────┘  │
│  ┌────────────────────────────────────┐  │
│  │  B) Noé ✅                         │  │ ← Correcta (verde)
│  └────────────────────────────────────┘  │
│  ┌────────────────────────────────────┐  │
│  │  C) Abraham                        │  │
│  └────────────────────────────────────┘  │
│  ┌────────────────────────────────────┐  │
│  │  D) David                          │  │
│  └────────────────────────────────────┘  │
│                                          │
│  📖 Génesis 6:14 - "Hazte un arca..."   │ ← Explicación
└──────────────────────────────────────────┘
```

### `presentation/juegos/fillverse/FillVerseScreen.kt`

**Estructura**:
```
┌──────────────────────────────────────────┐
│  ← Completa el Versículo   ❤️❤️❤️        │
│  Dificultad: Medio                        │
├──────────────────────────────────────────┤
│                                          │
│  "En el principio _______ Dios           │
│   los _______ y la tierra."              │ ← Versículo con huecos
│                                          │
│  ┌──────┐ ┌──────┐ ┌──────┐             │
│  │ creó │ │cielos│ │hizo  │             │ ← Opciones
│  └──────┘ └──────┘ └──────┘             │   (drag & drop)
│                                          │
│  [Verificar]                             │ ← Botón
│                                          │
│  📖 Génesis 1:1                          │ ← Referencia
└──────────────────────────────────────────┘
```

### `presentation/juegos/memorize/MemorizeScreen.kt`

**Estructura**:
```
┌──────────────────────────────────────────┐
│  ← Memorización           Nivel: 3/5     │
│  Versículos memorizados: 5               │
├──────────────────────────────────────────┤
│                                          │
│  "En el principio [___] Dios             │
│   los [___] y la [___]."                 │ ← Palabras ocultas
│                                          │
│  ┌────────────────────────────────────┐  │
│  │  Escribe las palabras faltantes:   │  │
│  │  [_____________]                    │  │ ← Input
│  └────────────────────────────────────┘  │
│                                          │
│  [Siguiente palabra]  [Mostrar todo]     │
│                                          │
│  Progreso: ████░░░░░░ 40%                │
│  📖 Génesis 1:1                          │
└──────────────────────────────────────────┘
```

### `presentation/retos/RetosScreen.kt`

**Estructura** (placeholder para FASE 2):
```
┌──────────────────────────────────────────┐
│  🔥 Retos Espirituales                    │
├──────────────────────────────────────────┤
│                                          │
│  🚧 Próximamente                         │
│                                          │
│  • Reto de 7 días de lectura             │
│  • Oración guiada diaria                 │
│  • Agradecimiento diario                 │
│                                          │
│  ¡Vuelve pronto!                         │
└──────────────────────────────────────────┘
```

### `presentation/progreso/ProgresoScreen.kt`

**Estructura**:
```
┌──────────────────────────────────────────┐
│  🏆 Mi Progreso                          │
├──────────────────────────────────────────┤
│                                          │
│  ┌────────────────────────────────────┐  │
│  │  ⭐ Nivel 5 · 1,250 puntos        │  │
│  │  ████████░░░░░░ 1,250/2,000 XP    │  │ ← Barra XP
│  └────────────────────────────────────┘  │
│                                          │
│  📊 Estadísticas                         │
│  📖 Lecturas: 25    🎮 Juegos: 50        │
│  🔥 Racha: 7 días   🧠 Memorizados: 5    │
│                                          │
│  🏅 Logros (8/24)                        │
│  ✅ Primer Paso    ✅ Lector Constante   │
│  ✅ Trivia Master  ✅ Primer Juego       │
│  🔒 Sabio de Israel  🔒 Leyenda          │
│                                          │
│  📈 Historial de Juegos                  │
│  Trivia: 850 pts · FillVerse: 600 pts    │
└──────────────────────────────────────────┘
```

## 2.10 — Resources

### `res/values/colors.xml`

Reemplazar colores default (púrpura/teal) con paleta pergamino dorado.

### `res/values/strings.xml`

Agregar TODOS los strings de la app:
- Nombres de pantallas
- Textos de botones
- Mensajes de logros
- Textos de juegos
- Mensajes de error
- Notificaciones

### `res/values/themes.xml`

Cambiar a tema NoActionBar para Compose.

---

# PASO 3: FASE 3 — Juegos 4-6 + Retos + Aprende

## Objetivo

Expandir con más juegos, retos espirituales funcionales y módulo de aprendizaje.

## 3.1 — Nuevos Juegos

### Adivinar el Personaje
- Pistas progresivas (características, historia, época)
- Sistema de pistas (AT/NT, época, personajes relacionados)
- 50+ personajes bíblicos

### Mapa Bíblico
- Ubicación de lugares históricos
- Contexto geográfico
- Pistas de ubicación
- Eventos por lugar

### Comprensión Bíblica
- Ordenar eventos cronológicamente
- Relacionar causa → consecuencia
- Historia de personajes

## 3.2 — Retos Espirituales

### Reto 7 Días
- Lectura diaria durante 7 días
- Versículo especial al completar
- Badge exclusivo

### Oración Guiada
- Texto de oración diaria
- Audio (futuro)
- Historial de oraciones

### Agradecimiento Diario
- Escribir 3 cosas por las que agradeces
- Historial de gratitud
- Reflexión semanal

## 3.3 — Aprende la Biblia

### Contexto Histórico
- Infografías por libro bíblico
- Autor, fecha, contexto
- Mapa del libro

### Líneas de Tiempo
- Timeline interactivo del AT y NT
- Eventos principales
- Personajes clave

### Comparar Versiones
- Ver mismo versículo en RVR1960, NTV, NVI
- Comparación lado a lado
- Notas de traducción

---

# PASO 4: FASE 4 — Comunidad + Notificaciones + Deploy

## 4.1 — Comunidad

- Compartir versículos (imagen con diseño pergamino)
- Chat moderado opcional (Firebase)
- Grupos de estudio

## 4.2 — Notificaciones

### DailyReminderWorker
- Notificación diaria a las 7:00 AM
- Mensajes rotativos motivadores
- Abre directamente LecturaScreen

### NotificationScheduler
- Configurar hora de notificación
- Activar/desactivar
- Tipos: recordatorio, palabra del día, frase motivacional

## 4.3 — Deploy

- Build APK release
- ProGuard rules
- Firmar APK
- Play Store listing

---

# ESTRUCTURA FINAL DE ARCHIVOS (FASE 2 COMPLETA)

```
app/src/main/java/com/example/appbible/
│
├── AppBibleApplication.kt
├── MainActivity.kt
│
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt                    ← MODIFICADO (v2, 5 entities)
│   │   ├── dao/
│   │   │   ├── VerseDao.kt                   ← EXISTENTE
│   │   │   ├── ReadingProgressDao.kt         ← EXISTENTE
│   │   │   ├── GameProgressDao.kt            ← NUEVO
│   │   │   ├── AchievementDao.kt             ← NUEVO
│   │   │   └── ScoreDao.kt                   ← NUEVO
│   │   └── entity/
│   │       ├── VerseEntity.kt                ← EXISTENTE
│   │       ├── ReadingProgressEntity.kt      ← EXISTENTE
│   │       ├── GameProgressEntity.kt         ← NUEVO
│   │       ├── AchievementEntity.kt          ← NUEVO
│   │       └── ScoreEntity.kt                ← NUEVO
│   └── repository/
│       └── DailyReadingRepository.kt         ← EXISTENTE
│
├── domain/
│   ├── model/
│   │   ├── Verse.kt
│   │   ├── DailyReading.kt
│   │   ├── ReadingState.kt
│   │   ├── GameScore.kt
│   │   └── Achievement.kt
│   └── usecase/
│       ├── GetDailyReadingUseCase.kt
│       ├── MarkAsReadUseCase.kt
│       └── GetReadingStreakUseCase.kt
│
├── presentation/
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Typography.kt
│   │   └── Theme.kt
│   ├── navigation/
│   │   ├── AppNavigation.kt
│   │   └── BottomNavBar.kt
│   ├── home/
│   │   └── HomeScreen.kt
│   ├── lectura/
│   │   ├── LecturaScreen.kt
│   │   └── LecturaViewModel.kt
│   ├── juegos/
│   │   ├── JuegosScreen.kt
│   │   ├── trivia/
│   │   │   ├── TriviaScreen.kt
│   │   │   └── TriviaViewModel.kt
│   │   ├── fillverse/
│   │   │   ├── FillVerseScreen.kt
│   │   │   └── FillVerseViewModel.kt
│   │   └── memorize/
│   │       ├── MemorizeScreen.kt
│   │       └── MemorizeViewModel.kt
│   ├── retos/
│   │   └── RetosScreen.kt
│   └── progreso/
│       ├── ProgresoScreen.kt
│       └── ProgresoViewModel.kt
│
├── di/
│   ├── DatabaseModule.kt
│   ├── RepositoryModule.kt
│   └── UseCaseModule.kt
│
├── game/
│   ├── engine/
│   │   ├── TriviaEngine.kt
│   │   ├── FillVerseEngine.kt
│   │   └── MemorizeEngine.kt
│   ├── model/
│   │   ├── TriviaQuestion.kt
│   │   ├── FlashCard.kt
│   │   └── GameResult.kt
│   └── content/
│       ├── TriviaQuestions.kt
│       └── FlashCards.kt
│
└── notification/
    ├── DailyReminderWorker.kt
    └── NotificationScheduler.kt
```

---

# RESUMEN DE FASES

| Fase | Objetivo | Archivos Nuevos | Modificados | Agentes |
|---|---|---|---|---|
| **Paso 0** | Documento plan | 1 | 0 | supervisor |
| **Paso 1** | Entidades gamificación Room | 6 | 1 | backend, bible-builder |
| **FASE 2** | Lectura + 3 Juegos | ~35 | 3 | mobile, ui-ux, game-design, content, bible-builder |
| **FASE 3** | Juegos 4-6 + Retos + Aprende | ~25 | 2 | mobile, game-design, content |
| **FASE 4** | Comunidad + Notif + Deploy | ~10 | 3 | mobile, backend, devops |

**Total estimado**: ~77 archivos nuevos + 9 modificados

---

# AGENTES ASIGNADOS POR FASE

| Fase | Agente Principal | Apoyo |
|---|---|---|
| Paso 0 (Plan) | supervisor-agent | product-manager |
| Paso 1 (Room Games) | backend-agent | bible-builder |
| FASE 2 UI | mobile-agent | ui-ux-agent |
| FASE 2 Games | game-design-agent | content-agent |
| FASE 2 Build | bible-builder-agent | tech-lead-agent |
| FASE 3 | mobile-agent | game-design-agent |
| FASE 4 | devops-agent | backend-agent |
| QA (todas) | qa-agent | - |
| Orquestación | supervisor-agent | master-bible |

---

# RESULTADO FINAL FASE 2

**Commit**: `feat: Fase2 LecturaDiaria + 3 JuegosBíblicos jugables`

**App funcional con**:
- ✅ BottomNav: Lectura | Juegos | Retos | Progreso
- ✅ TopAppBar: "AppBible ✨ Puntos: 1,250"
- ✅ Lectura diaria con versículo + reflexión + marcar leído
- ✅ Trivia Bíblica (100 preguntas, 3 dificultades, vidas, timer)
- ✅ Completa el Versículo (50 versículos, 3 dificultades)
- ✅ Memorización Progresiva (50 flashcards, 5 niveles)
- ✅ Sistema de puntos + XP + niveles
- ✅ 24 logros desbloqueables
- ✅ Pantalla de progreso con estadísticas
- ✅ Tema pergamino dorado + dark mode
- ✅ Carousel de juegos en HomeScreen
- ✅ FAB "Reto del Día"
