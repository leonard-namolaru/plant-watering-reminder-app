package mobiles.projet.arrosage
import android.icu.util.Calendar
import androidx.room.*

@Entity
data class TablePlante (
    @PrimaryKey
    var idPlante : Int, // NOT NULL
    var nomCommun: String, // NOT NULL
    var nomLatin: String?,
    var dateDernierArrosage: Calendar,
    var lienPhotoDeLaPlante: String?
)

