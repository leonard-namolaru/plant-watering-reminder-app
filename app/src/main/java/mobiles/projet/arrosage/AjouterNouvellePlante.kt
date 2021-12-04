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
        setContentView(binding.root)
    }



        // Cette fonction me permet d'ajouter des plantes dans la base de données
        // C'est à dire on récupère les champs et on les mets dans la bd les champs
    fun ajouter(button: View){
        val bd = PlantesBD.getDatabase(application).myDao()
         // Supprimer les espaces au début et à la fin de chaque valeur String

        var nomCom: String = binding.nomCommun.text.toString().trim()
        var nomLat: String = binding.nomLatin.text.toString().trim()


        // Vérifier que les Strings obtenus ne soient pas vide
        if(nomCom == "" && nomLat == "" ){
            Toast.makeText(this, "Vous devez mentioner un nom ", Toast.LENGTH_LONG).show()
            return
        }

        var nomC: String?
        var nomL: String?
        if(!binding.nomCommun.text.isNullOrBlank())
            nomC= binding.nomCommun.text.toString()
        else
            nomC= null // Si le TextView de nomCommun est vide alors insérer null comme la NomLatin.

        if(!binding.nomLatin.text.isNullOrBlank())
                nomL = binding.nomLatin.text.toString()
        else
                nomL = null // Si le TextView de nomLatin est vide alors insérer null comme NomLatin.

        // Insertion
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



        Thread {
            bd.insertPlante( TablePlante( nomCommun =nomCom, nomLatin = nomLat))

            // Pour tester : récupérer toutes les plantes
            val lesPlantes: Array<TablePlante> = bd.loadAll()
            for(plante in lesPlantes)
                Log.d("liste des pays", "${plante.nomCommun}")

        }.start()

        // Nettoyer tous les EditText
        binding.nomCommun.text.clear()
        binding.nomLatin.text.clear()
    }

}





