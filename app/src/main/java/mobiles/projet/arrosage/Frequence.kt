package mobiles.projet.arrosage
import android.icu.util.Calendar
import androidx.room.*
import java.time.LocalDate

@Entity
data class Frequence(
 @PrimaryKey
 var IdPlante : Int,
 var IdFrequence : Int,
 var dateDebut : Long,
 var dateFin : Long,
 var nbFois :  Int,
 var Frequence : String


)
