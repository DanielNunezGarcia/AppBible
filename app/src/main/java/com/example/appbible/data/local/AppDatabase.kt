package com.example.appbible.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appbible.data.local.dao.AchievementDao
import com.example.appbible.data.local.dao.GameProgressDao
import com.example.appbible.data.local.dao.ReadingProgressDao
import com.example.appbible.data.local.dao.ScoreDao
import com.example.appbible.data.local.dao.VerseDao
import com.example.appbible.data.local.entity.AchievementEntity
import com.example.appbible.data.local.entity.GameProgressEntity
import com.example.appbible.data.local.entity.ReadingProgressEntity
import com.example.appbible.data.local.entity.ScoreEntity
import com.example.appbible.data.local.entity.VerseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Database(
    entities = [
        VerseEntity::class,
        ReadingProgressEntity::class,
        GameProgressEntity::class,
        AchievementEntity::class,
        ScoreEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun verseDao(): VerseDao
    abstract fun readingProgressDao(): ReadingProgressDao
    abstract fun gameProgressDao(): GameProgressDao
    abstract fun achievementDao(): AchievementDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        const val DATABASE_NAME = "appbible_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateSeedData(
                        database.verseDao(),
                        database.achievementDao(),
                        database.scoreDao()
                    )
                }
            }
        }

        private suspend fun populateSeedData(
            verseDao: VerseDao,
            achievementDao: AchievementDao,
            scoreDao: ScoreDao
        ) {
            if (verseDao.getTotalVerses() == 0) {
                verseDao.insertVerses(seedVerses)
            }

            if (achievementDao.getAllAchievements().firstOrNull()?.isEmpty() != false) {
                achievementDao.insertAchievements(seedAchievements)
            }

            if (scoreDao.getUserScore().firstOrNull() == null) {
                scoreDao.initializeScore(initialScore)
            }
        }

        private val seedVerses = listOf(
            VerseEntity(1, "Génesis", 1, 1, "En el principio creó Dios los cielos y la tierra.", "Génesis 1:1", "RVR1960", 1, false),
            VerseEntity(2, "Génesis", 1, 3, "Y dijo Dios: Sea la luz; y fue la luz.", "Génesis 1:3", "RVR1960", 2, false),
            VerseEntity(3, "Génesis", 1, 27, "Y creó Dios al hombre a su imagen, a imagen de Dios lo creó; varón y hembra los creó.", "Génesis 1:27", "RVR1960", 3, false),
            VerseEntity(4, "Génesis", 2, 7, "Entonces Jehová Dios formó al hombre del polvo de la tierra, y sopló en su nariz aliento de vida, y fue el hombre un ser viviente.", "Génesis 2:7", "RVR1960", 4, false),
            VerseEntity(5, "Génesis", 3, 15, "Y pondré enemistad entre ti y la mujer, y entre tu simiente y la simiente suya; ésta te herirá en la cabeza, y tú le herirás en el calcañar.", "Génesis 3:15", "RVR1960", 5, false),
            VerseEntity(6, "Génesis", 6, 22, "E hizo Noé conforme a todo lo que le mandó Jehová; así lo hizo.", "Génesis 6:22", "RVR1960", 6, false),
            VerseEntity(7, "Génesis", 9, 13, "Mi arco he puesto en las nubes, el cual será por señal del pacto entre mí y la tierra.", "Génesis 9:13", "RVR1960", 7, false),
            VerseEntity(8, "Génesis", 12, 1, "Pero Jehová había dicho a Abram: Vete de tu tierra y de tu parentela, y de la casa de tu padre, a la tierra que te mostraré.", "Génesis 12:1", "RVR1960", 8, false),
            VerseEntity(9, "Génesis", 15, 6, "Y creyó a Jehová, y le fue contado por justicia.", "Génesis 15:6", "RVR1960", 9, false),
            VerseEntity(10, "Génesis", 22, 8, "Y dijo Abraham: Dios se proveerá de cordero para el holocausto, hijo mío.", "Génesis 22:8", "RVR1960", 10, false),
            VerseEntity(11, "Éxodo", 3, 14, "Y respondió Dios a Moisés: YO SOY EL QUE SOY.", "Éxodo 3:14", "RVR1960", 11, false),
            VerseEntity(12, "Éxodo", 14, 14, "Jehová peleará por vosotros, y vosotros estaréis tranquilos.", "Éxodo 14:14", "RVR1960", 12, false),
            VerseEntity(13, "Éxodo", 15, 2, "Jehová es mi fortaleza y mi cántico, y ha sido mi salvación.", "Éxodo 15:2", "RVR1960", 13, false),
            VerseEntity(14, "Éxodo", 20, 2, "Yo soy Jehová tu Dios, que te saqué de la tierra de Egipto, de casa de servidumbre.", "Éxodo 20:2", "RVR1960", 14, false),
            VerseEntity(15, "Levítico", 19, 2, "Hablad a toda la congregación de los hijos de Israel, y decidles: Santos seréis, porque santo soy yo Jehová vuestro Dios.", "Levítico 19:2", "RVR1960", 15, false),
            VerseEntity(16, "Números", 6, 24, "Jehová te bendiga, y te guarde.", "Números 6:24", "RVR1960", 16, false),
            VerseEntity(17, "Deuteronomio", 6, 5, "Y amarás a Jehová tu Dios de todo tu corazón, y de toda tu alma, y de todas tus fuerzas.", "Deuteronomio 6:5", "RVR1960", 17, false),
            VerseEntity(18, "Deuteronomio", 31, 8, "Y Jehová es el que va delante de ti; él estará contigo, no te dejará, ni te desamparará; no temas ni te intimides.", "Deuteronomio 31:8", "RVR1960", 18, false),
            VerseEntity(19, "Josué", 1, 9, "Mira que te mando que te esfuerces y seas valiente; no temas ni desmayes, porque Jehová tu Dios estará contigo dondequiera que vayas.", "Josué 1:9", "RVR1960", 19, false),
            VerseEntity(20, "Jueces", 6, 14, "Ve con esta tu fuerza, y salvarás a Israel de la mano de los madianitas; ¿no te envío yo?", "Jueces 6:14", "RVR1960", 20, false),
            VerseEntity(21, "Rut", 1, 16, "Tu pueblo será mi pueblo, y tu Dios mi Dios.", "Rut 1:16", "RVR1960", 21, false),
            VerseEntity(22, "1 Samuel", 17, 47, "Jehová no salva con espada y con lanza; porque de Jehová es la batalla.", "1 Samuel 17:47", "RVR1960", 22, false),
            VerseEntity(23, "2 Samuel", 22, 3, "Dios mío, fortaleza mía, en él confiaré.", "2 Samuel 22:3", "RVR1960", 23, false),
            VerseEntity(24, "1 Reyes", 3, 9, "Da, pues, a tu siervo corazón entendido para juzgar a tu pueblo, y para discernir entre lo bueno y lo malo.", "1 Reyes 3:9", "RVR1960", 24, false),
            VerseEntity(25, "2 Reyes", 19, 19, "Ahora pues, Jehová Dios nuestro, sálvanos de su mano, y sepan todos los reinos de la tierra que sólo tú eres Jehová.", "2 Reyes 19:19", "RVR1960", 25, false),
            VerseEntity(26, "Salmos", 1, 1, "Bienaventurado el varón que no anduvo en consejo de malos, ni estuvo en camino de pecadores, ni en silla de escarnecedores se ha sentado.", "Salmos 1:1", "RVR1960", 26, false),
            VerseEntity(27, "Salmos", 23, 1, "Jehová es mi pastor; nada me faltará.", "Salmos 23:1", "RVR1960", 27, false),
            VerseEntity(28, "Salmos", 27, 1, "Jehová es mi luz y mi salvación; ¿de quién temeré?", "Salmos 27:1", "RVR1960", 28, false),
            VerseEntity(29, "Salmos", 46, 1, "Dios es nuestro amparo y fortaleza, nuestro pronto auxilio en las tribulaciones.", "Salmos 46:1", "RVR1960", 29, false),
            VerseEntity(30, "Salmos", 91, 1, "El que habita al abrigo del Altísimo morará bajo la sombra del Omnipotente.", "Salmos 91:1", "RVR1960", 30, false),
            VerseEntity(31, "Hebreos", 11, 1, "Es, pues, la fe la certeza de lo que se espera, la convicción de lo que no se ve.", "Hebreos 11:1", "RVR1960", 1, true, "fe"),
            VerseEntity(32, "Romanos", 10, 17, "Así que la fe es por el oír, y el oír, por la palabra de Dios.", "Romanos 10:17", "RVR1960", 1, true, "fe"),
            VerseEntity(33, "1 Corintios", 13, 4, "El amor es sufrido, es benigno; el amor no tiene envidia, el amor no es jactancioso, no se envanece.", "1 Corintios 13:4", "RVR1960", 2, true, "amor"),
            VerseEntity(34, "Juan", 3, 16, "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna.", "Juan 3:16", "RVR1960", 2, true, "amor"),
            VerseEntity(35, "Romanos", 8, 28, "Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien.", "Romanos 8:28", "RVR1960", 3, true, "esperanza"),
            VerseEntity(36, "Jeremías", 29, 11, "Porque yo sé los pensamientos que tengo acerca de vosotros, dice Jehová, pensamientos de paz, y no de mal, para daros el fin que esperáis.", "Jeremías 29:11", "RVR1960", 3, true, "esperanza"),
            VerseEntity(37, "Proverbios", 3, 5, "Fíate de Jehová de todo tu corazón, y no te apoyes en tu propia prudencia.", "Proverbios 3:5", "RVR1960", 4, true, "sabiduría"),
            VerseEntity(38, "Santiago", 1, 5, "Y si alguno de vosotros tiene falta de sabiduría, pídala a Dios, el cual da a todos abundantemente y sin reproche, y le será dada.", "Santiago 1:5", "RVR1960", 4, true, "sabiduría"),
            VerseEntity(39, "Filipenses", 4, 6, "Por nada estéis afanosos, sino sean conocidas vuestras peticiones delante de Dios en toda oración y ruego, con acción de gracias.", "Filipenses 4:6", "RVR1960", 5, true, "oración"),
            VerseEntity(40, "1 Tesalonicenses", 5, 17, "Orad sin cesar.", "1 Tesalonicenses 5:17", "RVR1960", 5, true, "oración"),
            VerseEntity(41, "Efesios", 4, 32, "Antes sed benignos unos con otros, misericordiosos, perdonándoos unos a otros, como Dios también os perdonó a vosotros en Cristo.", "Efesios 4:32", "RVR1960", 6, true, "perdón"),
            VerseEntity(42, "Mateo", 6, 14, "Porque si perdonáis a los hombres sus ofensas, os perdonará también a vosotros vuestro Padre celestial.", "Mateo 6:14", "RVR1960", 6, true, "perdón"),
            VerseEntity(43, "Isaías", 40, 31, "Pero los que esperan a Jehová tendrán nuevas fuerzas; levantarán alas como las águilas; correrán, y no se cansarán; caminarán, y no se fatigarán.", "Isaías 40:31", "RVR1960", 7, true, "fortaleza"),
            VerseEntity(44, "Filipenses", 4, 13, "Todo lo puedo en Cristo que me fortalece.", "Filipenses 4:13", "RVR1960", 7, true, "fortaleza"),
            VerseEntity(45, "1 Tesalonicenses", 5, 18, "Dad gracias en todo, porque esta es la voluntad de Dios para con vosotros en Cristo Jesús.", "1 Tesalonicenses 5:18", "RVR1960", 8, true, "gratitud"),
            VerseEntity(46, "Salmos", 107, 1, "Alabad a Jehová, porque él es bueno; porque para siempre es su misericordia.", "Salmos 107:1", "RVR1960", 8, true, "gratitud"),
            // Versículos adicionales cronológicos (días 31-60)
            VerseEntity(47, "Génesis", 28, 15, "He aquí, yo estoy contigo, y te guardaré por dondequiera que fueres.", "Génesis 28:15", "RVR1960", 31, false),
            VerseEntity(48, "Génesis", 39, 2, "Y Jehová estaba con José, y fue varón próspero.", "Génesis 39:2", "RVR1960", 32, false),
            VerseEntity(49, "Génesis", 45, 5, "Dios me envió delante de vosotros para preservar vida.", "Génesis 45:5", "RVR1960", 33, false),
            VerseEntity(50, "Génesis", 50, 20, "Vosotros pensasteis mal contra mí, mas Dios lo encaminó a bien.", "Génesis 50:20", "RVR1960", 34, false),
            VerseEntity(51, "Éxodo", 23, 25, "Serviréis a Jehová vuestro Dios, y él bendecirá tu pan y tus aguas.", "Éxodo 23:25", "RVR1960", 35, false),
            VerseEntity(52, "Éxodo", 33, 14, "Mi presencia irá contigo, y te daré descanso.", "Éxodo 33:14", "RVR1960", 36, false),
            VerseEntity(53, "Levítico", 20, 7, "Santificaos y sed santos, porque yo Jehová soy vuestro Dios.", "Levítico 20:7", "RVR1960", 37, false),
            VerseEntity(54, "Números", 14, 24, "Por cuanto hubo en él otro espíritu, y decidió seguir en pos de mí.", "Números 14:24", "RVR1960", 38, false),
            VerseEntity(55, "Deuteronomio", 4, 29, "Y desde allí buscarás a Jehová tu Dios, y lo encontrarás.", "Deuteronomio 4:29", "RVR1960", 39, false),
            VerseEntity(56, "Deuteronomio", 8, 3, "No solo de pan vivirá el hombre, sino de toda palabra que sale de la boca de Jehová.", "Deuteronomio 8:3", "RVR1960", 40, false),
            VerseEntity(57, "Deuteronomio", 28, 13, "Jehová te pondrá por cabeza y no por cola.", "Deuteronomio 28:13", "RVR1960", 41, false),
            VerseEntity(58, "Josué", 24, 15, "Yo y mi casa serviremos a Jehová.", "Josué 24:15", "RVR1960", 42, false),
            VerseEntity(59, "Jueces", 7, 2, "No sea que se alabe Israel contra mí.", "Jueces 7:2", "RVR1960", 43, false),
            VerseEntity(60, "1 Samuel", 2, 2, "No hay santo como Jehová; no hay ninguno fuera de ti.", "1 Samuel 2:2", "RVR1960", 44, false),
            VerseEntity(61, "1 Samuel", 12, 24, "Temed a Jehová y servidle de corazón con toda verdad.", "1 Samuel 12:24", "RVR1960", 45, false),
            VerseEntity(62, "2 Samuel", 7, 22, "Tú eres grande, oh Jehová Dios; no hay como tú.", "2 Samuel 7:22", "RVR1960", 46, false),
            VerseEntity(63, "1 Reyes", 8, 60, "Para que sepan todos los pueblos de la tierra que Jehová es Dios.", "1 Reyes 8:60", "RVR1960", 47, false),
            VerseEntity(64, "1 Reyes", 18, 39, "¡Jehová es el Dios! ¡Jehová es el Dios!", "1 Reyes 18:39", "RVR1960", 48, false),
            VerseEntity(65, "2 Reyes", 5, 15, "He aquí ahora conozco que no hay Dios en toda la tierra, sino en Israel.", "2 Reyes 5:15", "RVR1960", 49, false),
            VerseEntity(66, "1 Crónicas", 16, 11, "Buscad a Jehová y su poder; buscad su rostro continuamente.", "1 Crónicas 16:11", "RVR1960", 50, false),
            VerseEntity(67, "2 Crónicas", 7, 14, "Si se humillare mi pueblo, y oraren, y buscaren mi rostro, yo sanaré su tierra.", "2 Crónicas 7:14", "RVR1960", 51, false),
            VerseEntity(68, "Esdras", 8, 22, "La mano de nuestro Dios es sobre todos los que le buscan para bien.", "Esdras 8:22", "RVR1960", 52, false),
            VerseEntity(69, "Nehemías", 8, 10, "El gozo de Jehová es vuestra fortaleza.", "Nehemías 8:10", "RVR1960", 53, false),
            VerseEntity(70, "Ester", 4, 14, "¿Y quién sabe si para esta hora has llegado al reino?", "Ester 4:14", "RVR1960", 54, false),
            VerseEntity(71, "Job", 19, 25, "Yo sé que mi Redentor vive, y al fin se levantará sobre el polvo.", "Job 19:25", "RVR1960", 55, false),
            VerseEntity(72, "Job", 42, 2, "Yo conozco que todo lo puedes, y que no hay pensamiento que se esconda de ti.", "Job 42:2", "RVR1960", 56, false),
            VerseEntity(73, "Salmos", 16, 8, "A Jehová he puesto siempre delante de mí; porque está a mi diestra, no vacilaré.", "Salmos 16:8", "RVR1960", 57, false),
            VerseEntity(74, "Salmos", 18, 2, "Jehová, roca mía y castillo mío, y mi libertador; Dios mío, fortaleza mía.", "Salmos 18:2", "RVR1960", 58, false),
            VerseEntity(75, "Salmos", 32, 8, "Te haré entender, y te enseñaré el camino en que debes andar.", "Salmos 32:8", "RVR1960", 59, false),
            VerseEntity(76, "Salmos", 34, 8, "Gustad, y ved que es bueno Jehová; dichoso el varón que confía en él.", "Salmos 34:8", "RVR1960", 60, false),
            // Nuevo Testamento (días 61-85)
            VerseEntity(77, "Mateo", 5, 6, "Bienaventurados los que tienen hambre y sed de justicia.", "Mateo 5:6", "RVR1960", 61, false),
            VerseEntity(78, "Mateo", 5, 44, "Amad a vuestros enemigos, bendecid a los que os maldicen.", "Mateo 5:44", "RVR1960", 62, false),
            VerseEntity(79, "Mateo", 28, 19, "Id, y haced discípulos a todas las naciones, bautizándolos.", "Mateo 28:19", "RVR1960", 63, false),
            VerseEntity(80, "Marcos", 11, 24, "Todas las cosas que pidiereis orando, creed que las recibiréis.", "Marcos 11:24", "RVR1960", 64, false),
            VerseEntity(81, "Lucas", 1, 37, "Porque nada hay imposible para Dios.", "Lucas 1:37", "RVR1960", 65, false),
            VerseEntity(82, "Lucas", 19, 10, "El Hijo del Hombre vino a buscar y a salvar lo que se había perdido.", "Lucas 19:10", "RVR1960", 66, false),
            VerseEntity(83, "Juan", 1, 1, "En el principio era el Verbo, y el Verbo era con Dios, y el Verbo era Dios.", "Juan 1:1", "RVR1960", 67, false),
            VerseEntity(84, "Juan", 8, 32, "Y conoceréis la verdad, y la verdad os hará libres.", "Juan 8:32", "RVR1960", 68, false),
            VerseEntity(85, "Juan", 10, 10, "Yo he venido para que tengan vida, y para que la tengan en abundancia.", "Juan 10:10", "RVR1960", 69, false),
            VerseEntity(86, "Juan", 15, 5, "Yo soy la vid, vosotros los pámpanos; el que permanece en mí, este lleva mucho fruto.", "Juan 15:5", "RVR1960", 70, false),
            VerseEntity(87, "Hechos", 1, 8, "Recibiréis poder, cuando haya venido sobre vosotros el Espíritu Santo, y me seréis testigos.", "Hechos 1:8", "RVR1960", 71, false),
            VerseEntity(88, "Hechos", 4, 12, "En ningún otro hay salvación; no hay otro nombre bajo el cielo dado a los hombres.", "Hechos 4:12", "RVR1960", 72, false),
            VerseEntity(89, "Hechos", 16, 31, "Cree en el Señor Jesucristo, y serás salvo, tú y tu casa.", "Hechos 16:31", "RVR1960", 73, false),
            VerseEntity(90, "Romanos", 1, 16, "No me avergüenzo del evangelio, porque es poder de Dios para salvación.", "Romanos 1:16", "RVR1960", 74, false),
            VerseEntity(91, "Romanos", 5, 8, "Mas Dios muestra su amor para con nosotros, en que siendo aún pecadores, Cristo murió por nosotros.", "Romanos 5:8", "RVR1960", 75, false),
            VerseEntity(92, "Romanos", 12, 1, "Presentéis vuestros cuerpos en sacrificio vivo, santo, agradable a Dios.", "Romanos 12:1", "RVR1960", 76, false),
            VerseEntity(93, "1 Corintios", 3, 11, "Nadie puede poner otro fundamento que el que está puesto, el cual es Jesucristo.", "1 Corintios 3:11", "RVR1960", 77, false),
            VerseEntity(94, "1 Corintios", 10, 13, "Fiel es Dios, que no os dejará ser tentados más de lo que podéis resistir.", "1 Corintios 10:13", "RVR1960", 78, false),
            VerseEntity(95, "1 Corintios", 15, 58, "Estad firmes y constantes, creciendo en la obra del Señor siempre.", "1 Corintios 15:58", "RVR1960", 79, false),
            VerseEntity(96, "2 Corintios", 5, 17, "De modo que si alguno está en Cristo, nueva criatura es.", "2 Corintios 5:17", "RVR1960", 80, false),
            VerseEntity(97, "Gálatas", 2, 20, "Ya no vivo yo, mas vive Cristo en mí.", "Gálatas 2:20", "RVR1960", 81, false),
            VerseEntity(98, "Efesios", 2, 8, "Por gracia sois salvos por medio de la fe; y esto no de vosotros, pues es don de Dios.", "Efesios 2:8", "RVR1960", 82, false),
            VerseEntity(99, "Efesios", 6, 11, "Vestíos de toda la armadura de Dios, para estar firmes contra las asechanzas del diablo.", "Efesios 6:11", "RVR1960", 83, false),
            VerseEntity(100, "Filipenses", 1, 6, "El que comenzó en vosotros la buena obra, la perfeccionará hasta el día de Jesucristo.", "Filipenses 1:6", "RVR1960", 84, false),
            VerseEntity(101, "Colosenses", 3, 17, "Hacedlo todo en el nombre del Señor Jesús, dando gracias a Dios Padre.", "Colosenses 3:17", "RVR1960", 85, false),
            // Versículos temáticos adicionales
            VerseEntity(102, "Salmos", 37, 4, "Deléitate asimismo en Jehová, y él te concederá las peticiones de tu corazón.", "Salmos 37:4", "RVR1960", 1, true, "fe"),
            VerseEntity(103, "Marcos", 9, 23, "Todo es posible para el que cree.", "Marcos 9:23", "RVR1960", 1, true, "fe"),
            VerseEntity(104, "Juan", 13, 34, "Un mandamiento nuevo os doy: Que os améis unos a otros.", "Juan 13:34", "RVR1960", 2, true, "amor"),
            VerseEntity(105, "Romanos", 5, 5, "El amor de Dios ha sido derramado en nuestros corazones por el Espíritu Santo.", "Romanos 5:5", "RVR1960", 2, true, "amor"),
            VerseEntity(106, "Salmos", 42, 11, "Espera en Dios; porque aún he de alabarle.", "Salmos 42:11", "RVR1960", 3, true, "esperanza"),
            VerseEntity(107, "Lamentaciones", 3, 22-23, "Por la misericordia de Jehová no hemos sido consumidos. Nuevas son cada mañana.", "Lamentaciones 3:22-23", "RVR1960", 3, true, "esperanza"),
            VerseEntity(108, "Proverbios", 16, 3, "Encomienda a Jehová tus obras, y tus pensamientos serán afirmados.", "Proverbios 16:3", "RVR1960", 4, true, "sabiduría"),
            VerseEntity(109, "1 Corintios", 1, 25, "Lo insensato de Dios es más sabio que los hombres.", "1 Corintios 1:25", "RVR1960", 4, true, "sabiduría"),
            VerseEntity(110, "1 Juan", 5, 14, "Esta es la confianza que tenemos en él: que si pedimos alguna cosa conforme a su voluntad, él nos oye.", "1 Juan 5:14", "RVR1960", 5, true, "oración"),
            VerseEntity(111, "Salmos", 145, 18, "Cercano está Jehová a todos los que le invocan, a todos los que le invocan de veras.", "Salmos 145:18", "RVR1960", 5, true, "oración"),
            VerseEntity(112, "Colosenses", 3, 13, "Soportándoos unos a otros, y perdonándoos si alguno tuviere queja contra otro.", "Colosenses 3:13", "RVR1960", 6, true, "perdón"),
            VerseEntity(113, "1 Juan", 1, 9, "Si confesamos nuestros pecados, él es fiel y justo para perdonar.", "1 Juan 1:9", "RVR1960", 6, true, "perdón"),
            VerseEntity(114, "Salmos", 18, 32, "El Dios que me ciñó de fuerza, y quien hizo perfecto mi camino.", "Salmos 18:32", "RVR1960", 7, true, "fortaleza"),
            VerseEntity(115, "2 Timoteo", 1, 7, "No nos ha dado Dios espíritu de cobardía, sino de poder, de amor y de dominio propio.", "2 Timoteo 1:7", "RVR1960", 7, true, "fortaleza"),
            VerseEntity(116, "Salmos", 100, 4, "Entrad por sus puertas con acción de gracias, por sus atrios con alabanza.", "Salmos 100:4", "RVR1960", 8, true, "gratitud"),
            VerseEntity(117, "Hebreos", 12, 28, "Sirvamos a Dios agradándole con temor y reverencia.", "Hebreos 12:28", "RVR1960", 8, true, "gratitud")
        )

        private val seedAchievements = listOf(
            AchievementEntity(1, "Primer Paso", "Completa tu primera lectura", "📖", "lectura", 1, 50, false),
            AchievementEntity(2, "Lector Constante", "7 días seguidos leyendo", "🔥", "rachas", 7, 100, false),
            AchievementEntity(3, "Lector Dedicado", "30 días seguidos leyendo", "📚", "rachas", 30, 250, false),
            AchievementEntity(4, "Explorador", "Completa 50 lecturas", "🗺️", "lectura", 50, 200, false),
            AchievementEntity(5, "Sabio de Israel", "100 respuestas correctas en trivia", "🧠", "juegos", 100, 300, false),
            AchievementEntity(6, "Trivia Master", "Gana 10 partidas de trivia", "🏆", "juegos", 10, 150, false),
            AchievementEntity(7, "Memoria de Elefante", "Memoriza 10 versículos", "🐘", "juegos", 10, 200, false),
            AchievementEntity(8, "Completista", "Completa 25 versículos perdidos", "✍️", "juegos", 25, 150, false),
            AchievementEntity(9, "Primer Juego", "Juega tu primer juego", "🎮", "juegos", 1, 25, false),
            AchievementEntity(10, "Jugador Activo", "Juega 50 partidas", "🎯", "juegos", 50, 200, false),
            AchievementEntity(11, "Versículo de Oro", "Memoriza 25 versículos", "⭐", "juegos", 25, 300, false),
            AchievementEntity(12, "Conocedor", "Completa 5 temas de Aprende la Biblia", "📖", "lectura", 5, 150, false),
            AchievementEntity(13, "Perseverante", "Vuelve a jugar después de perder", "💪", "rachas", 1, 50, false),
            AchievementEntity(14, "Racha de Fuego", "14 días seguidos activo", "🔥🔥", "rachas", 14, 200, false),
            AchievementEntity(15, "Leyenda", "30 días seguidos activo", "👑", "rachas", 30, 500, false),
            AchievementEntity(16, "Primer Reto", "Completa tu primer reto espiritual", "🙏", "comunidad", 1, 75, false),
            AchievementEntity(17, "Siervo Fiel", "Completa 10 retos espirituales", "⛪", "comunidad", 10, 200, false),
            AchievementEntity(18, "Generoso", "Comparte 5 versículos", "💝", "comunidad", 5, 100, false),
            AchievementEntity(19, "Nivel 5", "Alcanza nivel 5", "📈", "juegos", 5, 100, false),
            AchievementEntity(20, "Nivel 10", "Alcanza nivel 10", "🌟", "juegos", 10, 250, false),
            AchievementEntity(21, "Nivel 25", "Alcanza nivel 25", "💎", "juegos", 25, 500, false),
            AchievementEntity(22, "Coleccionista", "Desbloquea 15 logros", "🎖️", "juegos", 15, 300, false),
            AchievementEntity(23, "Maestro Bíblico", "Completa todos los modos de juego en difícil", "🎓", "juegos", 6, 1000, false),
            AchievementEntity(24, "Discípulo", "Usa la app por 90 días", "✝️", "rachas", 90, 750, false)
        )

        private val initialScore = ScoreEntity(
            id = 1,
            totalPoints = 0,
            totalXP = 0,
            gamesPlayed = 0,
            gamesWon = 0,
            streakDays = 0,
            bestStreakDays = 0,
            versesMemorized = 0,
            readingsCompleted = 0,
            level = 1,
            lastActiveDate = ""
        )
    }
}
