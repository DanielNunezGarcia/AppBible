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
            question = "¿Por qué es importante leer la Biblia?",
            correctAnswer = "Es la Palabra de Dios y nos guía en la vida",
            options = listOf(
                "Es la Palabra de Dios y nos guía en la vida",
                "Es un libro de historia antigua",
                "Es solo para religiosos"
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
            question = "¿Qué significa que Jesús es el Hijo de Dios?",
            correctAnswer = "Que es Dios mismo encarnado en forma humana",
            options = listOf(
                "Que es Dios mismo encarnado en forma humana",
                "Que es un profeta especial",
                "Que es un buen maestro"
            ),
            explanation = "Jesús es Dios hecho carne, venido a la tierra para salvarnos de nuestros pecados.",
            relatedVerses = listOf(
                "Juan 1:1 - 'En el principio era el Verbo, y el Verbo era con Dios, y el Verbo era Dios.'",
                "Juan 3:16 - 'Porque tanto amó Dios al mundo, que dio a su Hijo unigénito, para que todo aquel que en él cree no se pierda, sino que tenga vida eterna.'",
                "Colosenses 1:15 - 'El cual es imagen del Dios invisible, primogénito de toda creación.'"
            )
        ),
        DailyQuestion(
            id = 3,
            question = "¿Cuál es el mandamiento más grande?",
            correctAnswer = "Amar a Dios y al prójimo como a uno mismo",
            options = listOf(
                "Amar a Dios y al prójimo como a uno mismo",
                "Guardar los diez mandamientos",
                "Ir a la iglesia cada semana"
            ),
            explanation = "El amor es la base de toda la ley y los profetas.",
            relatedVerses = listOf(
                "Mateo 22:37-39 - 'Amarás al Señor tu Dios con todo tu corazón, y con toda tu alma, y con toda tu mente. Este es el primero y grande mandamiento. Y el segundo es semejante: Amarás a tu prójimo como a ti mismo.'",
                "1 Juan 4:8 - 'El que no ama no ha conocido a Dios; porque Dios es amor.'",
                "1 Corintios 13:13 - 'Y ahora permanecen la fe, la esperanza y el amor, estos tres; pero el mayor de ellos es el amor.'"
            )
        ),
        DailyQuestion(
            id = 4,
            question = "¿Qué debemos hacer para ser salvos?",
            correctAnswer = "Creer en Jesús, arrepentirnos y seguirle",
            options = listOf(
                "Creer en Jesús, arrepentirnos y seguirle",
                "Ser buena persona",
                "Hacer muchas obras buenas"
            ),
            explanation = "La salvación es por gracia, mediante la fe en Jesucristo.",
            relatedVerses = listOf(
                "Hechos 4:12 - 'Y en ningún otro hay salvación; porque no hay otro nombre bajo el cielo, dado a los hombres, en que podamos ser salvos.'",
                "Romanos 10:9 - 'Que si confesares con tu boca que Jesús es el Señor, y creyeres en tu corazón que Dios le levantó de los muertos, serás salvo.'",
                "Juan 14:6 - 'Jesús le dijo: Yo soy el camino, la verdad, y la vida; nadie viene al Padre, sino por mí.'"
            )
        ),
        DailyQuestion(
            id = 5,
            question = "¿Cómo podemos conocer la voluntad de Dios?",
            correctAnswer = "A través de la oración, la Biblia y el Espíritu Santo",
            options = listOf(
                "A través de la oración, la Biblia y el Espíritu Santo",
                "Pidiendo señales milagrosas",
                "Consultando a otras personas"
            ),
            explanation = "Dios quiere revelarnos Su voluntad cuando le buscamos sinceramente.",
            relatedVerses = listOf(
                "Proverbios 3:5-6 - 'Fíate de Jehová con todo tu corazón, y no te apoyes en tu propia inteligencia. Reconócelo en todos tus caminos, y él enderezará tus veredas.'",
                "Santiago 1:5 - 'Y si alguno de vosotros carece de sabiduría, pídala a Dios, quien da a todos liberalmente y sin reproche, y le será dada.'",
                "Romanos 12:2 - 'No os conforméis a este siglo, sino transformaos por medio de la renovación de vuestro entendimiento, para que comprobéis cuál sea la buena voluntad de Dios, agradable y perfecta.'"
            )
        ),
        DailyQuestion(
            id = 6,
            question = "¿Qué es el Espíritu Santo?",
            correctAnswer = "Es Dios, la tercera persona de la Trinidad",
            options = listOf(
                "Es Dios, la tercera persona de la Trinidad",
                "Es una fuerza espiritual",
                "Es un ángel especial"
            ),
            explanation = "El Espíritu Santo nos guía, nos consuela y nos da poder para vivir como cristianos.",
            relatedVerses = listOf(
                "Juan 14:26 - 'Mas el Consolador, el Espíritu Santo, a quien el Padre enviará en mi nombre, él os enseñará todas las cosas, y os recordará todo lo que yo os he dicho.'",
                "Hechos 5:3-4 - '¿Por qué ha llenado Satanás tu corazón para que mintieses al Espíritu Santo?... No has mentido a los hombres, sino a Dios.'",
                "1 Corintios 6:19 - '¿O no sabéis que vuestro cuerpo es templo del Espíritu Santo, el cual está en vosotros?'"
            )
        ),
        DailyQuestion(
            id = 7,
            question = "¿Por qué murió Jesús en la cruz?",
            correctAnswer = "Para pagar el precio por nuestros pecados",
            options = listOf(
                "Para pagar el precio por nuestros pecados",
                "Porque fue injustamente condenado",
                "Para darnos un ejemplo de sacrificio"
            ),
            explanation = "La muerte de Jesús en la cruz fue un acto de amor supremo para nuestra redención.",
            relatedVerses = listOf(
                "Isaías 53:5 - 'Ciertamente él llevó nuestras enfermedades, y cargó con nuestros dolores... y por su llaga fuimos nosotros curados.'",
                "Romanos 5:8 - 'Mas Dios muestra su amor hacia nosotros, en que siendo aún pecadores, Cristo murió por nosotros.'",
                "1 Pedro 2:24 - 'El mismo llevó nuestros pecados en su cuerpo sobre el madero, para que nosotros, estando muertos a los pecados, vivamos para la justicia.'"
            )
        ),
        DailyQuestion(
            id = 8,
            question = "¿Qué significa la resurrección de Jesús?",
            correctAnswer = "Que venció la muerte y nos da vida eterna",
            options = listOf(
                "Que venció la muerte y nos da vida eterna",
                "Que su espíritu vive en nosotros",
                "Que sus enseñanzas continúan"
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
            question = "¿Cómo debemos orar?",
            correctAnswer = "Con fe, humildad y según la voluntad de Dios",
            options = listOf(
                "Con fe, humildad y según la voluntad de Dios",
                "Usando palabras elegantes",
                "Solo en la iglesia"
            ),
            explanation = "La oración es nuestra comunicación con Dios, quien escucha y responde nuestras peticiones.",
            relatedVerses = listOf(
                "Mateo 6:9-13 - 'Padre nuestro que estás en los cielos, santificado sea tu nombre...'",
                "Filipenses 4:6 - 'Por nada estéis afanosos, sino sean hechas saber vuestras peticiones ante Dios en toda oración y súplica, con acción de gracias.'",
                "Santiago 1:6 - 'Pero pida con fe, no dudando nada; porque el que duda es semejante a la onda del mar.'"
            )
        ),
        DailyQuestion(
            id = 10,
            question = "¿Qué significa ser hijo de Dios?",
            correctAnswer = "Tener relación personal con Dios y ser heredero",
            options = listOf(
                "Tener relación personal con Dios y ser heredero",
                "Ser bautizado",
                "Ir a la iglesia"
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
            question = "¿Qué nos enseña la creación?",
            correctAnswer = "Que Dios creó todo con propósito y a Su imagen",
            options = listOf(
                "Que Dios creó todo con propósito y a Su imagen",
                "Que el mundo es casualidad",
                "Que evolucionamos"
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
            question = "¿Qué es una alianza bíblica?",
            correctAnswer = "Un pacto entre Dios y Su pueblo",
            options = listOf(
                "Un pacto entre Dios y Su pueblo",
                "Un acuerdo entre personas",
                "Una promesa cualquiera"
            ),
            explanation = "Dios establece alianzas con nosotros por amor y fidelidad.",
            relatedVerses = listOf(
                "Génesis 9:9 - 'He aquí yo establezco mi alianza con vosotros... y con todo lo que vive con vosotros.'",
                "Jeremías 31:33 - 'Este es el pacto que haré con la casa de Israel después de aquellos días, dice Jehová: Daré mi ley en su mente, y la escribiré en su corazón.'",
                "Lucas 22:20 - 'Esta copa es el nuevo pacto en mi sangre, que por vosotros se derrama.'"
            )
        ),
        DailyQuestion(
            id = 13,
            question = "¿Por qué es importante la iglesia?",
            correctAnswer = "Es el cuerpo de Cristo donde crecemos juntos",
            options = listOf(
                "Es el cuerpo de Cristo donde crecemos juntos",
                "Es un edificio religioso",
                "Es una organización social"
            ),
            explanation = "La iglesia es comunidad de fe donde compartimos el amor de Cristo.",
            relatedVerses = listOf(
                "Hechos 2:47 - 'Y el Señor añadía cada día a la iglesia los que habían de ser salvos.'",
                "Efesios 4:12 - 'A fin de perfeccionar a los santos para la obra del ministerio, para la edificación del cuerpo de Cristo.'",
                "Hebreos 10:24-25 - 'Consideremos los unos a los otros para estimularnos al amor y a las buenas obras, no dejando de congregarnos...'"
            )
        ),
        DailyQuestion(
            id = 14,
            question = "¿Qué nos enseña el Salmo 23?",
            correctAnswer = "Que Dios es nuestro pastor que nos guía y protege",
            options = listOf(
                "Que Dios es nuestro pastor que nos guía y protege",
                "Que los pastores cuidan ovejas",
                "Que debemos ser humildes"
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
            question = "¿Qué significa 'arrepentirse'?",
            correctAnswer = "Cambiar de mentalidad y dejar el pecado",
            options = listOf(
                "Cambiar de mentalidad y dejar el pecado",
                "Sentir tristeza",
                "Pedir perdón"
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
            question = "¿Cuál es el propósito de los mandamientos?",
            correctAnswer = "Guiarnos para amar a Dios y al prójimo",
            options = listOf(
                "Guiarnos para amar a Dios y al prójimo",
                "Restringir nuestra libertad",
                "Mostrarnos lo que no podemos hacer"
            ),
            explanation = "Los mandamientos revelan la voluntad de Dios para una vida llena de propósito.",
            relatedVerses = listOf(
                "Éxodo 20:3-17 - 'No tendrás otros dioses delante de mí...'",
                "Deuteronomio 6:5 - 'Amarás a Jehová tu Dios con todo tu corazón, y con toda tu alma, y con toda tu fuerza.'",
                "Santiago 2:8 - 'Amarás a tu prójimo como a ti mismo, bien hacéis.'"
            )
        ),
        DailyQuestion(
            id = 17,
            question = "¿Qué nos enseña el hijo pródigo?",
            correctAnswer = "Que Dios nos recibe con amor al arrepentirnos",
            options = listOf(
                "Que Dios nos recibe con amor al arrepentirnos",
                "Que no debemos gastar dinero",
                "Que los padres son buenos"
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
            question = "¿Qué significa 'fe sin obras está muerta'?",
            correctAnswer = "Que la verdadera fe se demuestra con acciones",
            options = listOf(
                "Que la verdadera fe se demuestra con acciones",
                "Que las obras salvan",
                "Que la fe no importa"
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
            question = "¿Cómo resistir la tentación?",
            correctAnswer = "Con oración, la Palabra y el Espíritu Santo",
            options = listOf(
                "Con oración, la Palabra y el Espíritu Santo",
                "Evitando todo peligro",
                "Con fuerza de voluntad"
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
            question = "¿Qué promesa nos da Dios sobre el futuro?",
            correctAnswer = "Vida eterna con Él en Su reino",
            options = listOf(
                "Vida eterna con Él en Su reino",
                "Prosperidad económica",
                "Salud perfecta"
            ),
            explanation = "El futuro de un creyente es seguro en las manos de Dios.",
            relatedVerses = listOf(
                "Juan 14:2-3 - 'Voy, pues, a preparar lugar para vosotros.'",
                "Apocalipsis 21:4 - 'Enjugará Dios toda lágrima de sus ojos, y no habrá más muerte, ni habrá más llanto, ni clamor, ni dolor.'",
                "Romanos 8:28 - 'A los que aman a Dios, todas las cosas les ayudan a bien.'"
            )
        )
    )
}
