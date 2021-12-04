package mobiles.projet.arrosage
import androidx.room.*

@Dao
interface MyDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertPlante(vararg plante :TablePlante): List<Long> // la liste des id des éléments insérés


}