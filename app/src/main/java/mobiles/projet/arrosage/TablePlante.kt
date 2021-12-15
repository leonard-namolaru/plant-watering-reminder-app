package mobiles.projet.arrosage
import androidx.room.*
import java.util.Calendar

@Entity
data class TablePlante (
    @PrimaryKey(autoGenerate = true)
    var idPlante : Int=0, // NOT NULL
    var nomCommun: String, // NOT NULL
    var nomLatin: String?,
    var dateProchainArosage: Calendar
    //var lienPhotoDeLaPlante: String?
   //var dateDernierArrosage: Calendar,
)


