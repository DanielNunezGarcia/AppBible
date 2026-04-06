{
  "name": "AppBiblica_MCP",
  "version": "1.0.0",
  "description": "Sistema multi-agente para el desarrollo de una aplicación bíblica cristiana",

  "orchestrator": {
    "agent": "supervisor_agent",
    "strategy": "sequential_pipeline",
    "validation_required": true
  },

  "execution_flow": [
    {
      "step": 1,
      "name": "analysis",
      "agent": "supervisor_agent",
      "description": "Analiza la solicitud del usuario"
    },
    {
      "step": 2,
      "name": "planning",
      "agent": "supervisor_agent",
      "description": "Divide la tarea en subtareas"
    },
    {
      "step": 3,
      "name": "assignment",
      "agent": "supervisor_agent",
      "description": "Asigna tareas a agentes especializados"
    },
    {
      "step": 4,
      "name": "execution",
      "agent": "dynamic",
      "description": "Ejecutan los agentes correspondientes"
    },
    {
      "step": 5,
      "name": "validation",
      "agent": "supervisor_agent",
      "description": "Valida resultados técnicos y espirituales"
    },
    {
      "step": 6,
      "name": "delivery",
      "agent": "supervisor_agent",
      "description": "Entrega resultado final estructurado"
    }
  ],

  "agents": {
    "supervisor_agent": {
      "role": "Coordinator",
      "type": "orchestrator",
      "can_generate_code": false,
      "responsibilities": [
        "Analizar objetivos",
        "Dividir tareas",
        "Asignar agentes",
        "Validar resultados",
        "Garantizar fidelidad bíblica",
        "Asegurar calidad técnica"
      ],
      "validation_rules": [
        "Coherencia espiritual",
        "Escalabilidad",
        "Buenas prácticas",
        "Consistencia entre módulos"
      ]
    },

    "product_manager_agent": {
      "role": "Product Manager",
      "type": "planner",
      "responsibilities": [
        "Definir funcionalidades",
        "Crear roadmap",
        "Generar user stories",
        "Priorizar features"
      ]
    },

    "tech_lead_agent": {
      "role": "Tech Lead",
      "type": "architect",
      "responsibilities": [
        "Definir arquitectura",
        "Seleccionar tecnologías",
        "Diseñar estructura del proyecto",
        "Validar decisiones técnicas"
      ]
    },

    "backend_agent": {
      "role": "Backend Developer",
      "type": "developer",
      "responsibilities": [
        "Diseñar base de datos",
        "Crear APIs",
        "Implementar lógica de negocio",
        "Gestionar usuarios y progreso"
      ]
    },

    "mobile_agent": {
      "role": "Mobile Developer",
      "type": "developer",
      "platform": "Android/iOS",
      "responsibilities": [
        "Crear UI",
        "Implementar ViewModels",
        "Integrar backend",
        "Gestionar estados"
      ]
    },

    "ui_ux_agent": {
      "role": "UI/UX Designer",
      "type": "designer",
      "responsibilities": [
        "Definir diseño visual",
        "Crear layouts",
        "Seleccionar colores y tipografía",
								"Mejorar la optimización del diseño de cada pantalla",
        "Mejorar experiencia de usuario"
      ]
    },

    "content_agent": {
      "role": "Bible Content Specialist",
      "type": "specialist",
      "rules": [
        "No modificar texto bíblico",
        "No añadir interpretaciones doctrinales"
      ],
      "responsibilities": [
        "Seleccionar versículos",
								"Todo basado en la Biblia",
        "Generar preguntas",
        "Explicar contexto histórico",
								"Poner versículos como respuesta de cada pregunta"
      ]
    },

    "game_design_agent": {
      "role": "Game Designer",
      "type": "designer",
      "responsibilities": [
        "Diseñar mecánicas de juego",
        "Crear sistemas de puntos",
        "Definir progresión",
								"Crear Trivial de la Biblia, con preguntas fáciles, medias y difíciles",
								"Crear juego Adivina el personaje, ciudad, libro por pistas bíblicas e imágenes",
								"Hacer un estilo de juego de ordenador libros bíblicos",
								"Memorizar el versículo a través de retos",
								"Hacer Mapa bíblico donde hay que ubicar los lugares de historias bíblicas",
        "Diseñar experiencias educativas"
      ]
    },

    "qa_agent": {
      "role": "QA Tester",
      "type": "tester",
      "responsibilities": [
        "Detectar errores",
        "Crear casos de prueba",
        "Validar experiencia de usuario",
								"Testear todos los juegos que funcionen bien y estén encaminados con lo que dice la Biblia"
        "Asegurar calidad final"
      ]
    },

    "devops_agent": {
      "role": "DevOps Engineer",
      "type": "engineer",
      "responsibilities": [
        "Configurar CI/CD",
        "Gestionar despliegue",
        "Configurar infraestructura",
        "Publicar en stores"
      ]
    }
  },

  "global_rules": [
    "Nunca modificar el texto bíblico",
    "Priorizar crecimiento espiritual sobre entretenimiento",
    "Mantener código limpio y escalable",
    "Diseñar para multiplataforma",
    "Asegurar experiencia de usuario clara"
  ],

  "tech_stack": {
    "mobile": [
      "Kotlin (Jetpack Compose)",
      "Flutter (opcional)"
    ],
    "architecture": [
      "Clean Architecture",
      "MVVM"
    ],
    "local_database": [
      "Room",
      "SQLite"
    ],
    "backend": [
      "Firebase",
      "Node.js"
    ]
  }
}