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

               val bd = PlantesBD.getDatabase(application).myDao()  // Supprimer les espaces au début et à la fin de chaque valeur String

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

            Log.d("freq1", freq1)


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
                bd.insertPlante( TablePlante( nomCommun =nomCom, nomLatin = nomLat /*, lienPhotoDeLaPlante = lien*/))

            val lesPlantes: Array<TablePlante> = bd.loadAll() // Récupérer toutes les plantes
            idPlanteDansBDD = lesPlantes.last().idPlante;
            }
        t.start()
        t.join();

            Log.d("time1", nomCom)


            if(freq1.isNotBlank()) {
                Log.d("time2", nomCom)

                if(!freq1.isDigitsOnly()){ // c'est pas vide et ça contient des caractères
                    Log.d("time3", nomCom)

                    Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                    return
                }else{  // Ça contient que des digit on peut mettre dans la BD

                    Log.d("idPlanteDansBDD", idPlanteDansBDD.toString())
                    Log.d("freq1", freq1)

                    var t1 = Thread {
                        Log.d("time4", nomCom)
                        bd.insertFrequence( Frequence( IdPlante = idPlanteDansBDD, dateDebut = "janvier", dateFin = "septembre" , nbFois = freq1 ))
                    }
                    t1.start()
                    t1.join();

                }
            }
            Log.d("time5", nomCom)

            if(freq2.isNotBlank()) {
                if(!freq1.isDigitsOnly()){ // c'est pas vide et ça contient des caractères
                    Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                    return
                }else{  // Ça contient que des digit on peut mettre dans la BD

                    var t2 = Thread {
                        bd.insertFrequence( Frequence( IdPlante = idPlanteDansBDD, dateDebut = "octobre", dateFin = "novembre" , nbFois = freq2))
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
                        bd.insertFrequence( Frequence( IdPlante = idPlanteDansBDD, dateDebut = "novembre", dateFin = "decembre" , nbFois = freq3 ))

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