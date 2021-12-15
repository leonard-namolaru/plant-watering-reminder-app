package mobiles.projet.arrosage
import android.icu.util.Calendar
import androidx.room.*

@Entity
data class TablePlante (
    @PrimaryKey(autoGenerate = true)
    var idPlante : Int=0, // NOT NULL
    var nomCommun: String, // NOT NULL
    var nomLatin: String?,
    var dateProchainArosage: String
    //var lienPhotoDeLaPlante: String?
   //var dateDernierArrosage: Calendar,
)


