# PLAN IMPLEMENTACIÓN JUEGOS - FASE 2.1

**Objetivo:** Implementar y probar los 3 juegos bíblicos uno por uno

---

## JUEGO 1: TRIVIA BÍBLICA ✅ (Ya implementado)

### Estado Actual
- **TriviaScreen.kt** ✅ Implementado
- **TriviaViewModel.kt** ✅ Implementado
- **TriviaEngine.kt** ✅ Implementado
- **TriviaQuestions.kt** ✅ 100 preguntas

### Funcionalidades
- 10 preguntas por partida (seleccionadas aleatoriamente)
- 3 vidas (❤️❤️❤️)
- Timer de 25 segundos por pregunta
- 4 opciones por pregunta
- Feedback verde/rojo con explicación bíblica
- Puntuación: 100 puntos base + bonus por tiempo
- XP ganado: score / 10

### Lo que falta verificar
- [ ] Timer funciona correctamente
- [ ] Vidas se restan al fallar
- [ ] Al perder todas las vidas termina el juego
- [ ] Al responder correctamente suma puntos
- [ ] Se guarda el progreso en ScoreDao
- [ ] Pantalla de resultados muestra correctamente

---

## JUEGO 2: COMPLETA EL VERSÍCULO

### Estado Actual
- **FillVerseScreen.kt** ✅ Implementado
- **FillVerseViewModel.kt** ✅ Implementado
- **FillVerseEngine.kt** ✅ Implementado con datos de versículos

### Funcionalidades requeridas
- Mostrar versículo con ___ para palabras faltantes
- 4 opciones de palabras para seleccionar
- Al seleccionar, verificar si es correcta
- Feedback visual (verde/rojo)
- 3 vidas
- 5 versículos por partida
- Puntuación: 100 × número de palabras faltantes

### Lo que falta verificar/implementar
- [ ] Engine con datos de versículos completos
- [ ] Pantalla muestra versículos correctamente
- [ ] Validación de respuestas funciona
- [ ] Transición entre versículos
- [ ] Guardado de puntuación

---

## JUEGO 3: MEMORIZACIÓN

### Estado Actual
- **MemorizeScreen.kt** ✅ Implementado
- **MemorizeViewModel.kt** ✅ Implementado
- **MemorizeEngine.kt** ✅ Implementado con flashcards
- 10 flashcards de versículos bíblicos

### Funcionalidades requeridas
- 5 niveles de dificultad progresiva:
  - Nivel 1: Sin pistas (500 pts)
  - Nivel 2: 2 palabras reveladas (400 pts)
  - Nivel 3: 5 palabras reveladas (300 pts)
  - Nivel 4: 10 palabras reveladas (200 pts)
  - Nivel 5: Todas menos 1 (100 pts)
- Input de texto para escribir versículo
- Botón "Revelar más pistas"
- Botón "Verificar"
- 5 flashcards por partida

### Lo que falta verificar/implementar
- [ ] Flashcards con datos completos
- [ ] Progresión de niveles funciona
- [ ] Revelación de palabras funciona
- [ ] Validación de respuesta completa
- [ ] Cálculo de puntuación por nivel

---

## ORDEN DE IMPLEMENTACIÓN

### Paso 1: Commit estado actual (antes de mejoras)
```
feat: estado inicial FASE 2 - screens, navigation, games base
```

### Paso 2: Verificar y mejorar TRIVIA
- Revisar lógica del timer
- Verificar scoring
- Probar flujo completo

### Paso 3: Mejorar FILLVERSE
- Asegurar datos de versículos completos
- Verificar validación de respuestas
- Mejorar UI si necesario

### Paso 4: Mejorar MEMORIZE
- Asegurar datos de flashcards completos
- Verificar progresión de niveles
- Mejorar feedback visual

### Paso 5: Commit final
```
feat: juegos biblicos completos y probados
```

---

## TESTING Checklist

### Trivia
- [ ] Puedo iniciar el juego
- [ ] Las preguntas se muestran correctamente
- [ ] El timer cuenta hacia atrás
- [ ] Al responder mal pierdo una vida
- [ ] Al responder bien sumo puntos
- [ ] Al terminar veo la pantalla de resultados
- [ ] Al perder todas las vidas termina el juego

### FillVerse
- [ ] Puedo ver el versículo con huecos
- [ ] Las opciones de palabras se muestran
- [ ] Al seleccionar una opción me da feedback
- [ ] Puedo pasar al siguiente versículo
- [ ] Al terminar veo la pantalla de resultados

### Memorize
- [ ] Puedo ver el versículo
- [ ] Puedo ocultar palabras progresivamente
- [ ] Puedo escribir el versículo completo
- [ ] Al verificar me da feedback
- [ ] Puedo pasar a la siguiente flashcard

---

## ARCHIVOS A MODIFICAR POR JUEGO

### Trivia
- `TriviaScreen.kt` - UI
- `TriviaViewModel.kt` - Lógica
- `TriviaEngine.kt` - Engine
- `TriviaQuestions.kt` - Contenido

### FillVerse
- `FillVerseScreen.kt` - UI
- `FillVerseViewModel.kt` - Lógica
- `FillVerseEngine.kt` - Engine + Datos

### Memorize
- `MemorizeScreen.kt` - UI
- `MemorizeViewModel.kt` - Lógica
- `MemorizeEngine.kt` - Engine + Flashcards
