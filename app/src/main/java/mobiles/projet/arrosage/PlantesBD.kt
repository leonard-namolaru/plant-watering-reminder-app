package mobiles.projet.arrosage

import android.content.Context
import androidx.room.*

/**
 * Une classe PlantesBD pour définir la base de données.
 */
@Database(entities = [TablePlante::class, Frequence::class], version = 1) // version : The database version.
@TypeConverters(Converters::class)
abstract class PlantesBD : RoomDatabase() { // Une classe pour representer la base de données
    abstract fun myDao(): MyDao //  la fonction abstraite qui retourne le Dao que nous avons défini

    // Classe en singleton :
    // une classe avec une seule instance qu’on obtient en appelant getDatabase
    companion object { //  compagnon object est l'objet unique attaché à une classe (un peu comme les static en java)
        @Volatile // Volatile : meaning that writes to this field are immediately made visible to other threads.
        private var instance: PlantesBD? = null // Dans la variable privée instance on stocke une référence vers la BD


        fun getDatabase( context : Context): PlantesBD{
            if( instance != null )
                return instance!! // the not-null assertion operator (!!) converts any value to a non-null type
            val db = Room.databaseBuilder( context.applicationContext, PlantesBD::class.java, "TablePlante" ).build()
            instance = db
            return instance!!
        }
    }
}