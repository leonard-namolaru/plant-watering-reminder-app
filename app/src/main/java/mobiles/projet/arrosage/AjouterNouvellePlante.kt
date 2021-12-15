package mobiles.projet.arrosage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.PrimaryKey
import mobiles.projet.arrosage.databinding.ActivityAjouterNouvellePlanteBinding
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import java.util.Calendar

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

            val bd = PlantesBD.getDatabase(application).myDao()  // Supprimer les espaces au début et à la fin de chaque valeur String
            var nomCom: String = binding.nomCommun.text.toString().trim()
            var nomLat: String = binding.nomLatin.text.toString().trim()



            // Spinner 1 : date début et date de fin
            var spinner1 :Spinner = findViewById(R.id.dateDebutFreq1);
            var dateDebutFreq1 : String = spinner1.selectedItem.toString().trim();
            var spinner2 :Spinner = findViewById(R.id.dateFinFreq1);
            var dateFinFreq1 : String = spinner2.selectedItem.toString().trim();



            // Spinner 2 : date début et date de fin
            var spinner3 :Spinner = findViewById(R.id.dateDebutFreq2);
            var dateDebutFreq2 : String = spinner3.selectedItem.toString().trim();
            var spinner4 :Spinner = findViewById(R.id.dateFinFreq2);
            var dateFinFreq2: String = spinner4.selectedItem.toString().trim();


            // Spinner 3 : date début et date de fin
            var spinner5 :Spinner = findViewById(R.id.dateDebutFreq3);
            var dateDebutFreq3: String = spinner5.selectedItem.toString().trim();
            var spinner6 :Spinner = findViewById(R.id.dateFinFreq1);
            var dateFinFreq3 : String = spinner6.selectedItem.toString().trim();


            // Les fréquences des trois spinner
            var freq1: String = binding.freq1.text.toString().trim()
            var freq2: String = binding.freq2.text.toString().trim()
            var freq3 : String = binding.freq3.text.toString().trim()


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

            var idPlanteDansBDD : Int = 0

            // Insertion
            var t = Thread {
                var prochainArosage : Calendar = Calendar.getInstance()
                prochainArosage.set(2022, 10, 3)
                bd.insertPlante( TablePlante( nomCommun =nomCom, nomLatin = nomLat /*, lienPhotoDeLaPlante = lien*/, dateProchainArosage = prochainArosage))

            val lesPlantes : Array<TablePlante> = bd.loadAll() // Récupérer toutes les plantes
            idPlanteDansBDD = lesPlantes.last().idPlante;
            }
            t.start()
            t.join();

            if(freq1.isNotBlank()) {

                if(!freq1.isDigitsOnly()){ // c'est pas vide et ça contient des caractères
                    Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                    return
                }else{  // Ça contient que des digit on peut mettre dans la BD

                    var t1 = Thread {
                        bd.insertFrequence( Frequence( IdPlante = idPlanteDansBDD, dateDebut = dateDebutFreq1, dateFin = dateFinFreq1 , nbFois = freq1 ))
                    }
                    t1.start()
                    t1.join();

                }
            }

            if(freq2.isNotBlank()) {
                if(!freq1.isDigitsOnly()){ // c'est pas vide et ça contient des caractères
                    Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                    return
                }else{  // Ça contient que des digit on peut mettre dans la BD

                    var t2 = Thread {
                    bd.insertFrequence( Frequence( IdPlante = idPlanteDansBDD, dateDebut = dateDebutFreq2, dateFin = dateFinFreq2, nbFois = freq2))
                    }
                    t2.start()
                    t2.join();
                }
            }

            if(freq3.isNotBlank()) {
                if(!freq1.isDigitsOnly()){ // c'est pas vide et ça contient des caractères
                    Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                    return
                }else{  // Ça contient que des digit on peut mettre dans la BD

                    var t3 = Thread {
                    bd.insertFrequence( Frequence( IdPlante = idPlanteDansBDD, dateDebut = dateDebutFreq3, dateFin = dateFinFreq3 , nbFois = freq3 ))
                    }
                    t3.start()
                    t3.join();

                }
            }

            // Nettoyer tous les EditText
            binding.nomCommun.text.clear()
            binding.nomLatin.text.clear()
            binding.freq1.text.clear()
            binding.freq2.text.clear()
            binding.freq3.text.clear()

    }

}