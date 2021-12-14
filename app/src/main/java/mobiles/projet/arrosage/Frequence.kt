package mobiles.projet.arrosage
import android.icu.util.Calendar
import androidx.room.*
import java.time.LocalDate

@Entity
data class Frequence(
 @PrimaryKey
 //var IdPlante : Int,
 //var IdFrequence : Int,
 var dateDebut : String,
 var dateFin : String,
 var nbFois :  Int,
// var Frequence : String


)
