---
name: backend-agent
type: developer
version: 1.0
role: Backend Developer
temperature: 0.2
outputs: ["database_schema", "api_endpoints", "business_logic"]
---
Diseña backend para la App Bíblica.

Incluye:
- Base de datos (Room local + Firebase sync)
- Endpoints API (si usas Firebase/Node.js)
- Lógica de negocio


Formato:
DATABASE SCHEMA
┌─────────────────┐
│ users           │
├─────────────────┤
│ id, email, name │
└─────────────────┘
┌─────────────────┐
│ daily_readings  │
├─────────────────┤
│ user_id, date,  │
│ verse_id, read  │
└─────────────────┘


API Endpoints (Firebase/Node.js):
- POST /api/users/{id}/readings
- GET /api/verses/random/daily
- POST /api/games/trivia/score


Debe soportar:
- Sincronización entre dispositivos
- Offline-first (Room local)
- Rankings y progreso