package com.example.appbible.game.content

import com.example.appbible.game.model.FlashCard

fun getFlashCards(): List<FlashCard> {
    return listOf(
        // ANTIGUO TESTAMENTO (20 flashcards)
        FlashCard(1, "En el principio creó Dios los cielos y la tierra.", "Génesis 1:1", listOf(1, 2, 3), listOf("En", "el", "principio", "creó", "Dios", "los", "cielos", "y", "la", "tierra"), "facil"),
        FlashCard(2, "Y dijo Dios: Sea la luz; y fue la luz.", "Génesis 1:3", listOf(0, 1, 2), listOf("Y", "dijo", "Dios:", "Sea", "la", "luz;", "y", "fue", "la", "luz."), "facil"),
        FlashCard(3, "Jehová es mi pastor; nada me faltará.", "Salmos 23:1", listOf(0, 1, 2), listOf("Jehová", "es", "mi", "pastor;", "nada", "me", "faltará."), "facil"),
        FlashCard(4, "En ti confiarán los que conocen tu nombre.", "Salmos 9:10", listOf(0, 1, 2), listOf("En", "ti", "confiarán", "los", "que", "conocen", "tu", "nombre,"), "facil"),
        FlashCard(5, "Lámpara es a mis pies tu palabra.", "Salmos 119:105", listOf(0, 1, 2), listOf("Lámpara", "es", "a", "mis", "pies", "tu", "palabra,"), "facil"),
        FlashCard(6, "Fíate de Jehová de todo tu corazón.", "Proverbios 3:5", listOf(0, 1, 2), listOf("Fíate", "de", "Jehová", "de", "todo", "tu", "corazón,"), "facil"),
        FlashCard(7, "Mira que te mando que te esfuerces y seas valiente.", "Josué 1:9", listOf(0, 1, 2), listOf("Mira", "que", "te", "mando", "que", "te", "esfuerces", "y", "seas", "valiente;"), "facil"),
        FlashCard(8, "Jehová te bendiga, y te guarde.", "Números 6:24", listOf(0, 1, 2), listOf("Jehová", "te", "bendiga,", "y", "te", "guarde;"), "facil"),
        FlashCard(9, "Yo sé los pensamientos que tengo acerca de vosotros.", "Jeremías 29:11", listOf(0, 1, 2), listOf("Yo", "sé", "los", "pensamientos", "que", "tengo", "acerca", "de", "vosotros,"), "facil"),
        FlashCard(10, "Los que esperan a Jehová tendrán nuevas fuerzas.", "Isaías 40:31", listOf(0, 1, 2), listOf("Los", "que", "esperan", "a", "Jehová", "tendrán", "nuevas", "fuerzas;"), "facil"),
        FlashCard(11, "Creer es el fundamento de lo que se espera.", "Hebreos 11:1", listOf(0, 1, 2), listOf("Creer", "es", "el", "fundamento", "de", "lo", "que", "se", "espera,"), "medio"),
        FlashCard(12, "Todo lo puedo en Cristo que me fortalece.", "Filipenses 4:13", listOf(0, 1, 2), listOf("Todo", "lo", "puedo", "en", "Cristo", "que", "me", "fortalece."), "medio"),
        FlashCard(13, "Dios es nuestro amparo y fortaleza.", "Salmos 46:1", listOf(0, 1, 2), listOf("Dios", "es", "nuestro", "amparo", "y", "fortaleza,"), "medio"),
        FlashCard(14, "El amor es sufrido, es benigno.", "1 Corintios 13:4", listOf(0, 1, 2), listOf("El", "amor", "es", "sufrido,", "es", "benigno;"), "medio"),
        FlashCard(15, "Porque de tal manera amó Dios al mundo.", "Juan 3:16", listOf(0, 1, 2), listOf("Porque", "de", "tal", "manera", "amó", "Dios", "al", "mundo,"), "medio"),
        FlashCard(16, "Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien.", "Romanos 8:28", listOf(0, 1, 2, 3, 4), listOf("Y", "sabemos", "que", "a", "los", "que", "aman", "a", "Dios,", "todas", "las", "cosas", "les", "ayudan", "a", "bien."), "medio"),
        FlashCard(17, "Por nada estéis afanosos, sino sean conocidas vuestras peticiones delante de Dios.", "Filipenses 4:6", listOf(0, 1, 2, 3, 4), listOf("Por", "nada", "estéis", "afanosos,", "sino", "sean", "conocidas", "vuestras", "peticiones", "delante", "de", "Dios."), "medio"),
        FlashCard(18, "Orad sin cesar.", "1 Tesalonicenses 5:17", listOf(0, 1), listOf("Orad", "sin", "cesar."), "facil"),
        FlashCard(19, "Dad gracias en todo, porque esta es la voluntad de Dios.", "1 Tesalonicenses 5:18", listOf(0, 1, 2), listOf("Dad", "gracias", "en", "todo,", "porque", "esta", "es", "la", "voluntad", "de", "Dios."), "medio"),
        FlashCard(20, "Alabad a Jehová, porque él es bueno.", "Salmos 107:1", listOf(0, 1, 2), listOf("Alabad", "a", "Jehová,", "porque", "él", "es", "bueno;"), "facil"),
        
        // NUEVO TESTAMENTO (20 flashcards)
        FlashCard(21, "Porque donde están dos o tres congregados en mi nombre, allí estoy yo en medio de ellos.", "Mateo 18:20", listOf(0, 1, 2, 3, 4), listOf("Porque", "donde", "están", "dos", "o", "tres", "congregados", "en", "mi", "nombre,", "allí", "estoy", "yo", "en", "medio", "de", "ellos."), "dificil"),
        FlashCard(22, "Yo soy el camino, y la verdad, y la vida.", "Juan 14:6", listOf(0, 1, 2), listOf("Yo", "soy", "el", "camino,", "y", "la", "verdad,", "y", "la", "vida;"), "medio"),
        FlashCard(23, "La paga del pecado es muerte, mas el don de Dios es vida eterna.", "Romanos 6:23", listOf(0, 1, 2, 3), listOf("La", "paga", "del", "pecado", "es", "muerte,", "mas", "el", "don", "de", "Dios", "es", "vida", "eterna."), "dificil"),
        FlashCard(24, "Mas buscad primeramente el reino de Dios y su justicia.", "Mateo 6:33", listOf(0, 1, 2), listOf("Mas", "buscad", "primeramente", "el", "reino", "de", "Dios", "y", "su", "justicia,"), "medio"),
        FlashCard(25, "Venid a mí todos los que estáis trabajados y cargados.", "Mateo 11:28", listOf(0, 1, 2), listOf("Venid", "a", "mí", "todos", "los", "que", "estáis", "trabajados", "y", "cargados,"), "medio"),
        FlashCard(26, "El que cree en el Hijo tiene vida eterna.", "Juan 3:36", listOf(0, 1, 2), listOf("El", "que", "cree", "en", "el", "Hijo", "tiene", "vida", "eterna;"), "medio"),
        FlashCard(27, "Yo soy la luz del mundo; el que me sigue no andará en tinieblas.", "Juan 8:12", listOf(0, 1, 2, 3), listOf("Yo", "soy", "la", "luz", "del", "mundo;", "el", "que", "me", "sigue", "no", "andará", "en", "tinieblas,"), "dificil"),
        FlashCard(28, "Antes sed benignos unos con otros, misericordiosos.", "Efesios 4:32", listOf(0, 1, 2), listOf("Antes", "sed", "benignos", "unos", "con", "otros,", "misericordiosos,"), "medio"),
        FlashCard(29, "Sobre todo guardad vuestro corazón, porque de él mana la vida.", "Proverbios 4:23", listOf(0, 1, 2), listOf("Sobre", "todo", "guardad", "vuestro", "corazón,", "porque", "de", "él", "mana", "la", "vida."), "medio"),
        FlashCard(30, "Jesucristo es el mismo ayer, y hoy, y por los siglos.", "Hebreos 13:8", listOf(0, 1, 2), listOf("Jesucristo", "es", "el", "mismo", "ayer,", "y", "hoy,", "y", "por", "los", "siglos."), "medio"),
        FlashCard(31, "Y todo lo que hacéis, hacedlo de corazón.", "Colosenses 3:23", listOf(0, 1, 2), listOf("Y", "todo", "lo", "que", "hacéis,", "hacedlo", "de", "corazón,"), "medio"),
        FlashCard(32, "La fe es sinónimo de creer en lo que no se ve.", "Hebreos 11:1", listOf(0, 1, 2), listOf("La", "fe", "es", "sinónimo", "de", "creer", "en", "lo", "que", "no", "se", "ve."), "facil"),
        FlashCard(33, "Si confesares con tu boca que Jesús es el Señor, serás salvo.", "Romanos 10:9", listOf(0, 1, 2), listOf("Si", "confesares", "con", "tu", "boca", "que", "Jesús", "es", "el", "Señor,", "serás", "salvo."), "medio"),
        FlashCard(34, "El fruto del Espíritu es amor, gozo, paz, paciencia.", "Gálatas 5:22", listOf(0, 1, 2, 3), listOf("El", "fruto", "del", "Espíritu", "es", "amor,", "gozo,", "paz,", "paciencia,"), "medio"),
        FlashCard(35, "No os conforméis a este siglo, sino transformaos.", "Romanos 12:2", listOf(0, 1, 2), listOf("No", "os", "conforméis", "a", "este", "siglo,", "sino", "transformaos,"), "medio"),
        FlashCard(36, "Instruye al niño en su camino, y aun cuando fuere viejo no se apartará de él.", "Proverbios 22:6", listOf(0, 1, 2), listOf("Instruye", "al", "niño", "en", "su", "camino,", "y", "aun", "cuando", "fuere", "viejo", "no", "se", "apartará", "de", "él."), "dificil"),
        FlashCard(37, "Mirad que nadie pague a otro mal por mal.", "1 Tesalonicenses 5:15", listOf(0, 1, 2), listOf("Mirad", "que", "nadie", "pague", "a", "otro", "mal", "por", "mal,"), "medio"),
        FlashCard(38, "El nombre de Jehová es torre fuerte.", "Proverbios 18:10", listOf(0, 1, 2), listOf("El", "nombre", "de", "Jehová", "es", "torre", "fuerte;"), "medio"),
        FlashCard(39, "Aunque ande en valle de sombra de muerte, no temeré mal alguno.", "Salmos 23:4", listOf(0, 1, 2, 3), listOf("Aunque", "ande", "en", "valle", "de", "sombra", "de", "muerte,", "no", "temeré", "mal", "alguno."), "dificil"),
        FlashCard(40, "Jehová es mi luz y mi salvación; ¿de quién temeré?", "Salmos 27:1", listOf(0, 1, 2), listOf("Jehová", "es", "mi", "luz", "y", "mi", "salvación;", "¿de", "quién", "temeré?"), "medio"),
        
        // TEMATICOS (10 flashcards)
        FlashCard(41, "Es, pues, la fe la certeza de lo que se espera.", "Hebreos 11:1", listOf(0, 1, 2), listOf("Es,", "pues,", "la", "fe", "la", "certeza", "de", "lo", "que", "se", "espera,"), "tematico"),
        FlashCard(42, "Así que la fe es por el oír, y el oír, por la palabra de Dios.", "Romanos 10:17", listOf(0, 1, 2), listOf("Así", "que", "la", "fe", "es", "por", "el", "oír,", "y", "el", "oír,", "por", "la", "palabra", "de", "Dios."), "tematico"),
        FlashCard(43, "El amor nunca deja de ser.", "1 Corintios 13:8", listOf(0, 1, 2), listOf("El", "amor", "nunca", "deja", "de", "ser;"), "tematico"),
        FlashCard(44, "En esto conocemos el amor, que él puso su vida por nosotros.", "1 Juan 3:16", listOf(0, 1, 2), listOf("En", "esto", "conocemos", "el", "amor,", "que", "él", "puso", "su", "vida", "por", "nosotros."), "tematico"),
        FlashCard(45, "Bendito el Dios y Padre de nuestro Señor Jesucristo, Padre de misericordias y Dios de toda consolación.", "2 Corintios 1:3", listOf(0, 1, 2), listOf("Bendito", "el", "Dios", "y", "Padre", "de", "nuestro", "Señor", "Jesucristo,", "Padre", "de", "misericordias", "y", "Dios", "de", "toda", "consolación,"), "tematico"),
        FlashCard(46, "Y si alguno de vosotros tiene falta de sabiduría, pídala a Dios.", "Santiago 1:5", listOf(0, 1, 2), listOf("Y", "si", "alguno", "de", "vosotros", "tiene", "falta", "de", "sabiduría,", "pídala", "a", "Dios,"), "tematico"),
        FlashCard(47, "El temor de Jehová es el principio de la sabiduría.", "Proverbios 9:10", listOf(0, 1, 2), listOf("El", "temor", "de", "Jehová", "es", "el", "principio", "de", "la", "sabiduría;"), "tematico"),
        FlashCard(48, "Por nada estéis afanosos, sino sean conocidas vuestras peticiones delante de Dios en toda oración.", "Filipenses 4:6", listOf(0, 1, 2), listOf("Por", "nada", "estéis", "afanosos,", "sino", "sean", "conocidas", "vuestras", "peticiones", "delante", "de", "Dios", "en", "toda", "oración."), "tematico"),
        FlashCard(49, "Perdonándoos unos a otros, como Dios también os perdonó en Cristo.", "Efesios 4:32", listOf(0, 1, 2), listOf("Perdonándoos", "unos", "a", "otros,", "como", "Dios", "también", "os", "perdonó", "en", "Cristo."), "tematico"),
        FlashCard(50, "Pero los que esperan a Jehová tendrán nuevas fuerzas; levantarán alas como las águilas.", "Isaías 40:31", listOf(0, 1, 2, 3), listOf("Pero", "los", "que", "esperan", "a", "Jehová", "tendrán", "nuevas", "fuerzas;", "levantarán", "alas", "como", "las", "águilas;"), "tematico")
    )
}
