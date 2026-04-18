package com.example.appbible.game.content

data class DailyQuestion(
    val id: Int,
    val question: String,
    val correctAnswer: String,
    val options: List<String>,
    val explanation: String,
    val relatedVerses: List<String>
)

fun getDailyQuestions(): List<DailyQuestion> {
    return listOf(
        DailyQuestion(
            id = 1,
            question = "¿Por qué es importante leer la Biblia diariamente?",
            correctAnswer = "Porque es la Palabra de Dios que nos guía, corrige y enseña a vivir según Su voluntad",
            options = listOf(
                "Porque es la Palabra de Dios que nos guía, corrige y enseña a vivir según Su voluntad",
                "Es simplemente un libro de historia antigua que contiene escritos religiosos",
                "Es una obligación religiosa más para sentir piedad sin verdadera devoción"
            ),
            explanation = "La Biblia nos guía, nos corrige y nos enseña a vivir según la voluntad de Dios.",
            relatedVerses = listOf(
                "2 Timoteo 3:16 - 'Toda la Escritura es inspirada por Dios y útil para enseñar, para redargüir, para corregir, para instruir en justicia.'",
                "Salmo 119:105 - 'Lámpara es a mis pies tu palabra, y luz para mi camino.'",
                "Hebreos 4:12 - 'Porque la palabra de Dios es viva y eficaz, y más cortante que toda espada de dos filos.'"
            )
        ),
        DailyQuestion(
            id = 2,
            question = "¿Qué significa realmente que Jesús es el Hijo de Dios?",
            correctAnswer = "Que es Dios mismo encarnado en forma humana, venido a la tierra para salvarnos",
            options = listOf(
                "Que es Dios mismo encarnado en forma humana, venido a la tierra para salvarnos",
                "Que Jesús era solo un profeta especial con enseñanzas significativas",
                "Que era simplemente un buen maestro moral con enseigne"
            ),
            explanation = "Jesús es Dios hecho carne, venido a la tierra para salvarnos de nuestros pecados.",
            relatedVerses = listOf(
                "Juan 1:1 - 'En el principio era el Verbo, y el Verbo era con Dios, y el Verbo era Dios.'",
                "Juan 3:16 - 'Porque tanto amó Dios al mundo, que dio a su Hijo unigénito, para que todo aquel que en él cree no se pierda.'",
                "Colosenses 1:15 - 'El cual es imagen del Dios invisible, primogénito de toda creación.'"
            )
        ),
        DailyQuestion(
            id = 3,
            question = "¿Cuál es el mandamiento más importante de todos?",
            correctAnswer = "Amar a Dios con todo el corazón, alma y mente, y amar al prójimo como a uno mismo",
            options = listOf(
                "Amar a Dios con todo el corazón, alma y mente, y amar al prójimo como a uno mismo",
                "Guardar los diez mandamientos perfectamente sin fallar en ninguno",
                "Ir a la iglesia cada semana y asistir a todos los servicios"
            ),
            explanation = "El amor es la base de toda la ley y los profetas.",
            relatedVerses = listOf(
                "Mateo 22:37-39 - 'Amarás al Señor tu Dios con todo tu corazón, y con toda tu alma, y con toda tu mente.'",
                "1 Juan 4:8 - 'El que no ama no ha conocido a Dios; porque Dios es amor.'",
                "1 Corintios 13:13 - 'Y ahora permanecen la fe, la esperanza y el amor, estos tres; pero el mayor de ellos es el amor.'"
            )
        ),
        DailyQuestion(
            id = 4,
            question = "¿Qué debemos hacer exactamente para recibir la salvación eterna?",
            correctAnswer = "Creer en Jesús como Señor y Salvador, arrepentirnos de nuestros pecado y seguirle",
            options = listOf(
                "Creer en Jesús como Señor y Salvador, arrepentirnos de nuestros pecado y seguirle",
                "Simplemente ser una buena persona y hacer obras de altruismo",
                "Hacer muchas obras buenas y donaciones a la iglesia"
            ),
            explanation = "La salvación es por gracia, mediante la fe en Jesucristo.",
            relatedVerses = listOf(
                "Hechos 4:12 - 'Y en ningún otro hay salvación; porque no hay otro nombre bajo el cielo, dado a los hombres.'",
                "Romanos 10:9 - 'Que si confesares con tu boca que Jesús es el Señor, y creyeres en tu corazón que Dios le levantó de los muertos, serás salvo.'",
                "Juan 14:6 - 'Jesús le dijo: Yo soy el camino, la verdad, y la vida; nadie viene al Padre, sino por mí.'"
            )
        ),
        DailyQuestion(
            id = 5,
            question = "¿Cómo podemos realmente conocer la voluntad perfecta de Dios para nuestras vidas?",
            correctAnswer = "A través de la oración constante, estudiando la Biblia y escuchando al Espíritu Santo",
            options = listOf(
                "A través de la oración constante, estudiando la Biblia y escuchando al Espíritu Santo",
                "Pidiendo señales milagrosas y visiones especiales para confirmar",
                "Consultando a otros creyentes y siguiendo sus consejos"
            ),
            explanation = "Dios quiere revelarnos Su voluntad cuando le buscamos sinceramente.",
            relatedVerses = listOf(
                "Proverbios 3:5-6 - 'Fíate de Jehovah con todo tu corazón, y no te apoyes en tu propia inteligencia.'",
                "Santiago 1:5 - 'Y si alguno de vosotros carece de sabiduría, pídala a Dios, quien da a todos liberalmente.'",
                "Romanos 12:2 - 'No os conforméis a este siglo, sino transformaos por medio de la renovación de vuesntendimiento.'"
            )
        ),
        DailyQuestion(
            id = 6,
            question = "¿Qué es exactamente el Espíritu Santo y cuál es Su personalidad divina?",
            correctAnswer = "Es la tercera persona de la Trinidad, Dios mismo que mora en nosotros como Consolador",
            options = listOf(
                "Es la tercera persona de la Trinidad, Dios mismo que mora en nosotros como Consolador",
                "Es simplemente una fuerza espiritual sin personalidad real",
                "Es solo un ángel especial enviado por Dios para asistirnos"
            ),
            explanation = "El Espíritu Santo nos guía, nos consuela y nos da poder para vivir como cristianos.",
            relatedVerses = listOf(
                "Juan 14:26 - 'Mas el Consolador, el Espíritu Santo, a quien el Padre enviará en mi nombre, él os enseñará todas las cosas.'",
                "Hechos 5:3-4 - '¿Por qué ha llenado Satanás tu corazón para que mintieses al Espírito Santo?'",
                "1 Corintios 6:19 - '¿O no sabéis que vuestro cuerpo es templo del Espíritu Santo?'"
            )
        ),
        DailyQuestion(
            id = 7,
            question = "¿Por qué exactamente murió Jesús en la cruz por nosotros?",
            correctAnswer = "Para pagar el precio completo por nuestros pecado y ofrecernos redención eterna",
            options = listOf(
                "Para pagar el precio completo por nuestros pecado y ofrecernos redención eterna",
                "Porque fue injustamente condenado por las autoridades religiosas",
                "Para给我们 un ejemplo perfecto de sacrificio y servicio"
            ),
            explanation = "La muerte de Jesús en la cruz fue un acto de amor supremo para nuestra redención.",
            relatedVerses = listOf(
                "Isaías 53:5 - 'Ciertamente él llevó nuestras enfermedades, y cargó con nuestros dolores.'",
                "Romanos 5:8 - 'Mas Dios muestra su amor hacia nosotros, en que siendo aún pecadores, Cristo murió por nosotros.'",
                "1 Pedro 2:24 - 'El mismo llevó nuestros pecados en su cuerpo sobre el madero.'"
            )
        ),
        DailyQuestion(
            id = 8,
            question = "¿Qué significa realmente la resurrección de Jesús para nosotros los creyentes?",
            correctAnswer = "Que venció la muerte completamente y nos garantiza vida eterna con Dios",
            options = listOf(
                "Que venció la muerte completamente y nos garantiza vida eterna con Dios",
                "Que su espíritu simplemente vive dentro de nosotros los creyentes",
                "Que sus enseñanzasy moral continúan influyendo en la sociedad"
            ),
            explanation = "La resurrección es la prueba de que Jesús es el Hijo de Dios y de nuestra salvación.",
            relatedVerses = listOf(
                "1 Corintios 15:17 - 'Y si Cristo no resucitó, vuestra fe es vana; aun estáis en vuestros pecados.'",
                "Romanos 6:9 - 'Sabiendo que Cristo, habiendo resucitado de los muertos, ya no muere; la muerte ya no tiene dominio sobre él.'",
                "Juan 11:25 - 'Jesús le dijo: Yo soy la resurrección y la vida; el que cree en mí, aunque esté muerto, vivirá.'"
            )
        ),
        DailyQuestion(
            id = 9,
            question = "¿Cómo debemos orar efectivamente según las enseñanzas bíblicas?",
            correctAnswer = "Con fe verdadera, humildad profunda y siempre según la voluntad perfecta de Dios",
            options = listOf(
                "Con fe verdadera, humildad profunda y siempre según la voluntad perfecta de Dios",
                "Usando palabras elegantes y rituales elaboradas para impresionar",
                "Solo en la iglesia y en servicios formales especiales"
            ),
            explanation = "La oración es nuestra comunicación con Dios, quien escucha y responde nuestras peticiones.",
            relatedVerses = listOf(
                "Mateo 6:9-13 - 'Padre nuestro que estás en los cielos, santificado sea tu nombre...'",
                "Filipenses 4:6 - 'Por nada estéis afanosos, sino sean hechas saber vuestras peticiones ante Dios en toda oración y súplica.'",
                "Santiago 1:6 - 'Pero pida con fe, no dudando nada; porque el que duda es semelhante a la onda del mar.'"
            )
        ),
        DailyQuestion(
            id = 10,
            question = "¿Qué significa verdaderamente ser hijo de Dios según la Biblia?",
            correctAnswer = "Tener una relación personal íntima con Dios como nuestro Padre celestial y ser herederos",
            options = listOf(
                "Tener una relación personal íntima con Dios como nuestro Padre celestial y ser herederos",
                "Simplemente haber sido bautizado en la iglesia particular",
                "Asistir regularmente a la iglesia y participar en su actividad"
            ),
            explanation = "Cuando aceptamos a Jesús, recibimos el derecho de llamar a Dios 'Padre'.",
            relatedVerses = listOf(
                "Juan 1:12 - 'Mas a todos los que le recibieron, a los que creen en su nombre, les dio potestad de ser hechos hijos de Dios.'",
                "Romanos 8:16 - 'El mismo Espíritu da testimonio a nuestro espíritu, de que somos hijos de Dios.'",
                "Gálatas 4:7 - 'Así que ya no eres esclavo, sino hijo; y si hijo, también heredero por Dios.'"
            )
        ),
        DailyQuestion(
            id = 11,
            question = "¿Qué nos enseña la creación del universo sobre Dios y nosotros como humanos?",
            correctAnswer = "Que Dios creó todo intencionalmente con propósito y que estamos hechos a imagen divina",
            options = listOf(
                "Que Dios creó todo intencionalmente con propósito y que estamos hechos a imagen divina",
                "Que el universo es simplemente resultado del azar puro",
                "Que los seres humanos evolucionaron gradualmente de especies inferiores"
            ),
            explanation = "Somos creados con un propósito divino y tenemos valor incalculable ante Dios.",
            relatedVerses = listOf(
                "Génesis 1:1 - 'En el principio creó Dios los cielos y la tierra.'",
                "Génesis 1:27 - 'Y creó Dios al hombre a su imagen, a imagen de Dios lo creó; varón y hembra los creó.'",
                "Salmo 139:14 - 'Te alabaré; porque formidables y maravillosas son tus obras.'"
            )
        ),
        DailyQuestion(
            id = 12,
            question = "¿Qué es exactamente una alianza bíblicca entre Dios y los humanos?",
            correctAnswer = "Un pacto sagrado donde Dios promete bendiciones y nosotros nos comprometemos a obedecerle",
            options = listOf(
                "Un pacto sagrado donde Dios promete bendiciones y nosotros nos comprometemos a obedecerle",
                "Simplemente un acuerdo formal entre dos partes interesadas",
                "Es solo una promesa general sin consecuencias específicas"
            ),
            explanation = "Dios establece alianzas con nosotros por amor y fidelidad.",
            relatedVerses = listOf(
                "Génesis 9:9 - 'He aquí yo establezco mi alianza con vosotros y con toda vuestra descendencia.'",
                "Jeremías 31:33 - 'Este es el pacto que haré con la casa de Israel después de aquellos días, dice Jehovah.'",
                "Lucas 22:20 - 'Esta copa es el nuevo pacto en mi sangre, que por vosotros se derrama.'"
            )
        ),
        DailyQuestion(
            id = 13,
            question = "¿Por qué es importante la iglesia local en nuestra vida espiritual?",
            correctAnswer = "Es el cuerpo de Cristo donde crecemos juntos en fe, amor y propósito común",
            options = listOf(
                "Es el cuerpo de Cristo donde crecemos juntos en fe, amor y propósito común",
                "Es simplemente un edificio religioso donde nos reunimos",
                "Es solo una organización social para hacer contactos útiles"
            ),
            explanation = "La iglesia es comunidad de fe donde compartimos el amor de Cristo.",
            relatedVerses = listOf(
                "Hechos 2:47 - 'Y el Señor añadía cada día a la iglesia los que habían de ser salvos.'",
                "Efesios 4:12 - 'A fin de perfeccionar a los santos para la obra del ministerio, para la edificación del cuerpo de Cristo.'",
                "Hebreos 10:24-25 - 'Consideremos los unos a los otros para estimularnos al amor y a las buenas obras.'"
            )
        ),
        DailyQuestion(
            id = 14,
            question = "¿Qué nos enseña específicamente el Salmo 23 sobre Dios?",
            correctAnswer = "Que Dios es nuestro buen pastor que nos guía, protege y provee en todo momento",
            options = listOf(
                "Que Dios es nuestro buen pastor que nos guía, protege y provee en todo momento",
                "Que los pastores del campo tienen una vida especial con las ovejas",
                "Que debemos ser humildes y obedecer siempre a las autoridades"
            ),
            explanation = "En Dios encontramos refugio, guía y provisión en todo momento de la vida.",
            relatedVerses = listOf(
                "Salmo 23:1 - 'Jehová es mi pastor; nada me faltará.'",
                "Salmo 23:4 - 'Aunque ande en valle de sombra de muerte, no temeré mal alguno, porque tú estarás conmigo.'",
                "Salmo 23:6 - 'Ciertamente bondad y misericordia me seguirán todos los días de mi vida.'"
            )
        ),
        DailyQuestion(
            id = 15,
            question = "¿Qué significa verdaderamente arrepentirse según las enseñanza bíblicas?",
            correctAnswer = "Cambiar genuinamente de mentalidad, confesar nuestros pecado y abandonar el pecado",
            options = listOf(
                "Cambiar genuinamente de mentalidad, confesar nuestros pecado y abandonar el pecado",
                "Sentir tristeza temporal por nuestras acciones pasadas",
                "Simplemente pedir perdón sin cambiar nuestro comportamiento real"
            ),
            explanation = "El arrepentimiento es el primer paso para una vida nueva en Cristo.",
            relatedVerses = listOf(
                "Hechos 3:19 - 'Así que, arrepentíos y convertíos, para que sean borrados vuestros pecados.'",
                "Mateo 4:17 - 'Arrepentíos, porque el reino de los cielos se ha acercado.'",
                "2 Corintios 7:10 - 'La tristeza según Dios produce arrepentimiento para salvación.'"
            )
        ),
        DailyQuestion(
            id = 16,
            question = "¿Cuál es el verdadero propósito de los mandamientos divinos?",
            correctAnswer = "Guiarnos para amar genuinamente a Dios y mostrar amor al prójimo",
            options = listOf(
                "Guiarnos para amar genuinamente a Dios y mostrar amor al prójimo",
                "Simplemente restringir nuestra libertad y controlar nuestro comportamiento",
                "Mostranos exactamente lo que no podemos hacer bajo ninguna circunstancia"
            ),
            explanation = "Los mandamientos revelan la voluntad de Dios para una vida llena de propósito.",
            relatedVerses = listOf(
                "Éxodo 20:3-17 - 'No tendrás otros dioses delante de mí...'",
                "Deuteronomio 6:5 - 'Amarás a Jehovah tu Dios con todo tu corazón, y con toda tu alma, y con toda tu fuerza.'",
                "Santiago 2:8 - 'Amarás a tu prójimo como a ti mismo, bien hacéis.'"
            )
        ),
        DailyQuestion(
            id = 17,
            question = "¿Qué nos enseña específicamente la parábola del hijo pródigo?",
            correctAnswer = "Que Dios nos recibe siempre con amor incondicional cuando nos arrepentimos sinceramente",
            options = listOf(
                "Que Dios nos recibe siempre con amor incondicional cuando nos arrepentimos sinceramente",
                "Que no debemos malgastar nuestro dinero en cosas innecesarias",
                "Que los padres siempre deben ser comprensivos y tolerantes"
            ),
            explanation = "El amor del Padre siempre está esperando nuestro regreso con brazos abiertos.",
            relatedVerses = listOf(
                "Lucas 15:20 - 'Y cuando aún estaba lejos, su padre lo vio, y fue movido a compasión, y corrió, y abrazó, y besó.'",
                "Lucas 15:24 - 'Porque este mi hijo era muerto, y ha revivido; se había perdido, y halló.'",
                "1 Juan 1:9 - 'Si confesamos nuestros pecados, él es fiel y justo para perdonar nuestros pecados.'"
            )
        ),
        DailyQuestion(
            id = 18,
            question = "¿Qué significa exactamente que la fe sin obras está muerta?",
            correctAnswer = "Que la verdadera fe salvadora siempre se demuestra con acciones concretas de amor",
            options = listOf(
                "Que la verdadera fe salvadora siempre se demuestra con acciones concretas de amor",
                "Que las buenas obras son lo único que realmente nos salva",
                "Que tener fe no importa nada para la salvación personal"
            ),
            explanation = "Nuestra fe debe manifestarse en amor y servicio práctico a otros.",
            relatedVerses = listOf(
                "Santiago 2:17 - 'Así también la fe, si no tiene obras, es muerta en sí misma.'",
                "Santiago 2:26 - 'Como el cuerpo sin espíritu está muerto, así también la fe sin obras está muerta.'",
                "Efesios 2:10 - 'Somos hechura suya, creados en Cristo Jesús para buenas obras.'"
            )
        ),
        DailyQuestion(
            id = 19,
            question = "¿Cómo podemos verdaderamente resistir las tentaciones del diablo?",
            correctAnswer = "Con oración constante, la Palabra de Dios y el poder del Espíritu Santo",
            options = listOf(
                "Con oración constante, la Palabra de Dios y el poder del Espíritu Santo",
                "Evitando absolutamente todo peligro y situación riesgosa",
                "Con pura fuerza de voluntad y determinación personal"
            ),
            explanation = "Dios provee una salida de toda tentación cuando la buscamos.",
            relatedVerses = listOf(
                "1 Corintios 10:13 - 'Fiel es Dios, que no os dejará ser tentados más de lo que podéis resistir.'",
                "Mateo 4:4 - 'No con solo el pan vivirá el hombre, sino con toda palabra que sale de la boca de Dios.'",
                "Santiago 4:7 - 'Someteos, pues, a Dios; resistid al diablo, y huirá de vosotros.'"
            )
        ),
        DailyQuestion(
            id = 20,
            question = "¿Qué promete verdaderamente Dios sobre nuestro futuro eterno?",
            correctAnswer = "Vida eterna con Él en Su reino celestial donde no habrá más dolor ni lágrimas",
            options = listOf(
                "Vida eterna con Él en Su reino celestial donde no habrá más dolor ni lágrimas",
                "Prosperidad económica y riqueza material garantizada",
                "Salud física perfecta y bienestar total en esta tierra"
            ),
            explanation = "El futuro de un creyente es seguro en las manos de Dios.",
            relatedVerses = listOf(
                "Juan 14:2-3 - 'Voy, pues, a preparar lugar para vosotros.'",
                "Apocalipsis 21:4 - 'Enjugará Dios toda lágrima de sus ojos, y no habrá más muerte, ni habrá más llanto, ni clamor.'",
                "Romanos 8:28 - 'A los que aman a Dios, todas las cosas les ayudan a bien.'"
            )
        )
    )
}