package mobiles.projet.arrosage
import android.icu.util.Calendar
import androidx.room.*
data class Frequence(
 @PrimaryKey
 var IdPlante : Int,
 var IdFrequence : Int,
 var dateDebut : Calendar,
 var dateFin : Calendar,
 var nbFois : Int,
 var Frequence : String


)
