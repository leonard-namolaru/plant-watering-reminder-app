package mobiles.projet.arrosage
import androidx.room.*

@Dao
interface MyDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertPlante(vararg plante :TablePlante): List<Long> // la liste des id des éléments insérés

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertFrequence(vararg freq : Frequence): List<Long> // la liste des fréquences déja insérées

    /**
     * La méthode loadAll fait une requête SELECT
     * qui retourne un Array<Pays> avec tous les pays.
     */
    @Query("SELECT * FROM TablePlante") // L'annotation Query contient une requête select
    fun loadAll(): Array<TablePlante> // La fonction qui exécute select retourne un Array d'objets Pays
    @Query("SELECT * FROM Frequence")
    fun loadallFrequences(): Array<Frequence>

    /**
     * La méthode loadFrequencesById(id) retourne un Array<Frequence> avec tous les frequences d'une plante
     */
    @Query("SELECT * FROM Frequence WHERE IdFrequence == :id ")
    fun loadFrequencesById(id: Int): Array<Frequence> // Requête paramétrée : dans la requete le nom de paramètre de la fonction précédé par :
}