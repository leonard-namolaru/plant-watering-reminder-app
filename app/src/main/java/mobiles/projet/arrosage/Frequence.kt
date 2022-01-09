package mobiles.projet.arrosage
import android.icu.util.Calendar
import androidx.room.*
import java.time.LocalDate

@Entity
data class Frequence(
 @PrimaryKey(autoGenerate = true)
 var IdFrequence : Int=0,
 var IdPlante : Int,
 var dateDebut : String,
 var dateFin : String,
 var nbFois :  String
)
