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
            VerseEntity(46, "Salmos", 107, 1, "Alabad a Jehová, porque él es bueno; porque para siempre es su misericordia.", "Salmos 107:1", "RVR1960", 8, true, "gratitud")
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
