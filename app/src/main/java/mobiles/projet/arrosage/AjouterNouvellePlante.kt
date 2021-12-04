package mobiles.projet.arrosage

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.PrimaryKey
import mobiles.projet.arrosage.databinding.ActivityAjouterNouvellePlanteBinding
import android.util.Log
import android.view.View
import android.widget.Toast

class AjouterNouvellePlante : AppCompatActivity() {
    private lateinit var binding: ActivityAjouterNouvellePlanteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAjouterNouvellePlanteBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_ajouter_nouvelle_plante)
    }

    /**
     * Cette activité permet d’ajouter un nouveau Pays dans la table des pays
     */
    data class PlanteBD (
        @PrimaryKey
        var idPlante : Int, // NOT NULL
        var nomCommun: String, // NOT NULL
        var nomLatin: String?,
        var dateDernierArrosage: Calendar,
        var lienPhotoDeLaPlante: String?
    )

        // Cette fonction me permet d'ajouter des plantes dans la base de données
        // C'est à dire on récupère les champs et on les mets dans la bd les champs
    fun ajouter(button: View){
        val bd = PlanteBD.getDatabase(application).myDao()

        // Supprimer les espaces au début et à la fin de chaque valeur String
        var pays: String = binding.edPays.text.toString().trim()
        var continent: String = binding.edContinent.text.toString().trim()
        var capitale: String = binding.edCapitale.text.toString().trim()

        // Vérifier que les Strings obtenus ne soient pas vide
        if(pays == "" || continent == "" || capitale == ""){
            Toast.makeText(this, "Le nom de pays, continent ou capitale est vide", Toast.LENGTH_LONG).show()
            return
        }

        var superficie: Int?
        if(!binding.edSuperficie.text.isNullOrBlank())
            superficie = binding.edSuperficie.text.toString().toInt()
        else
            superficie = null // Si le TextView de superficie est vide alors insérer null comme la superficie.

        // Insertion
        Thread {
            bd.insertPays( Pays(pays = pays, continent = continent, capitale = capitale, superficie = superficie) )

            // Pour tester : récupérer tous les pays
            val lesPays: Array<Pays> = bd.loadAll()
            for(pays in lesPays)
                Log.d("liste des pays", "${pays.pays}")

        }.start()

        // Nettoyer tous les EditText
        binding.edPays.text.clear()
        binding.edContinent.text.clear()
        binding.edCapitale.text.clear()
        binding.edSuperficie.text.clear()
    }

}





