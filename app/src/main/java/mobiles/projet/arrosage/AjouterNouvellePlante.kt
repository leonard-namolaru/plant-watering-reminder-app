package mobiles.projet.arrosage

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.PrimaryKey
import mobiles.projet.arrosage.databinding.ActivityAjouterNouvellePlanteBinding
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class AjouterNouvellePlante : AppCompatActivity() {
    private lateinit var binding: ActivityAjouterNouvellePlanteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAjouterNouvellePlanteBinding.inflate(layoutInflater)
         setContentView(binding.root)
    }
















        // Cette fonction me permet d'ajouter des plantes dans la base de données
        // C'est à dire on récupère les champs et on les mets dans la bd les champs
    fun valider(button: View){
        val bd = PlantesBD.getDatabase(application).myDao()
         // Supprimer les espaces au début et à la fin de chaque valeur String

        var nomCom: String = binding.nomCommun.text.toString().trim()
        var nomLat: String = binding.nomLatin.text.toString().trim()
        var dateDebutFreq1 :String = binding.dateDebutFreq1.toString().trim()
        var dateFinFreq1 = binding.dateFinFreq1.toString().trim()

        var dateDebutFreq2 :String = binding.dateDebutFreq2.toString().trim()
        var dateFinFreq2 = binding.dateFinFreq2.toString().trim()

        var dateDebutFreq3 :String = binding.dateDebutFreq3.toString().trim()
        var dateFinFreq3 = binding.dateFinFreq3.toString().trim()

        var freq1: String = binding.freq1.text.toString().trim()
        var freq2: String = binding.freq2.text.toString().trim()
        var freq3 : String = binding.freq3.text.toString().trim()



        if(!binding.freq1.text.isNullOrBlank()){
            if(!freq1.isDigitsOnly()){
                Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                return
            }

        }
        if(!binding.freq2.text.isNullOrBlank()){
            if(!freq1.isDigitsOnly()){
                    Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                    return
                }
            }
        if(!binding.freq3.text.isNullOrBlank()){
            if(!freq1.isDigitsOnly()){
                    Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                    return
                }
            }



        // Vérifier que les Strings obtenus ne soient pas vide
        if(nomCom == "" && nomLat == "" ){
            Toast.makeText(this, "Vous devez mentioner un nom ", Toast.LENGTH_LONG).show()
            return
        }

        var nomC: String?
        var nomL: String?
        var lien : String?
        if(!binding.nomCommun.text.isNullOrBlank())
            nomC= binding.nomCommun.text.toString()
        else
            nomC= null // Si le TextView de nomCommun est vide alors insérer null comme la NomLatin.

        if(!binding.nomLatin.text.isNullOrBlank())
                nomL = binding.nomLatin.text.toString()
        else
                nomL = null // Si le TextView de nomLatin est vide alors insérer null comme NomLatin.

        if(!binding.lienPic.text.isNullOrBlank())
            lien = binding.lienPic.text.toString()
        else
            lien = null // Si le TextView de nomLatin est vide alors insérer null comme NomLatin.


            // Insertion
        Thread {
            bd.insertPlante( TablePlante( nomCommun =nomCom, nomLatin = nomLat /*, lienPhotoDeLaPlante = lien*/))

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





