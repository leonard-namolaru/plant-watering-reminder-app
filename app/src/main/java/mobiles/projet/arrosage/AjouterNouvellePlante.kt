package mobiles.projet.arrosage

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mobiles.projet.arrosage.databinding.ActivityAjouterNouvellePlanteBinding
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import java.util.Calendar

//import android.app.Activity
//import androidx.room.PrimaryKey
//import android.content.Intent
//import org.jcp.xml.dsig.internal.dom.DOMKeyInfo.getContent
//import java.io.File

class AjouterNouvellePlante : AppCompatActivity() {
    private lateinit var binding: ActivityAjouterNouvellePlanteBinding

    /* propriété : Uri de fichier local */
    var localUri : Uri? = null
    /* la propriété getContent pour enregistrer une méthode callback
    * appelée au retour de la nouvelle activité.
    * Ici cette méthode callback est implementée par une lambda qui
    * qui obtient l’Uri de l’image comme paramètre. */
    // val getContent = registerForActivityResult(ActivityResultContracts
    //     .GetContent())

    /* { uri: Uri? ->
        if( uri == null ) return@registerForActivityResult
            /* à faire :
            * si uri de l’image différent de null alors copier l’image dans
            la
            * mémoire interne de l’appareil et mémoriser l’emplacement de l
            ’image
            * copiée dans la base de données.
            */
       }
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAjouterNouvellePlanteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title= "AJouter Plante"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Cette fonction permet d'ajouter des plantes dans la base de données
    // C'est à dire : on récupère les champs et on les mets dans la bd
    fun valider(button: View){
        val bd = PlantesBD.getDatabase(application).myDao()

        var dateAujourdhui : Calendar = Calendar.getInstance()
        val moisEnCours : Int = dateAujourdhui.get(Calendar.MONTH)
        var frequenceActuelle : Int = 0


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
        var spinner6 :Spinner = findViewById(R.id.dateFinFreq3);
        var dateFinFreq3 : String = spinner6.selectedItem.toString().trim();

        // Les fréquences des trois spinner
        var freq1: String = binding.freq1.text.toString().trim()
        var freq2: String = binding.freq2.text.toString().trim()
        var freq3 : String = binding.freq3.text.toString().trim()

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

        var insertFrequence1 : Boolean = false
        var insertFrequence2 : Boolean = false
        var insertFrequence3 : Boolean = false

        if(freq1.isNotBlank()) {

            if(!freq1.isDigitsOnly()){ // c'est pas vide et ça contient des caractères
                Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                return
            }else{  // Ça contient que des digit
                if(freq1.toInt() < 1) {
                    Toast.makeText(this, "Fréquence 1 : La fréquence doit être supérieure à zéro", Toast.LENGTH_LONG).show()
                    return
                }

                if (spinner1.selectedItemPosition >= spinner2.selectedItemPosition) {
                    Toast.makeText(this, "Fréquence 1 : Le mois de fin doit être supérieur au mois de début", Toast.LENGTH_LONG).show()
                    return
                }

                if (moisEnCours >= spinner1.selectedItemPosition && moisEnCours <= spinner2.selectedItemPosition)
                    frequenceActuelle = freq1.toInt()

                insertFrequence1 = true
            } // else
        } // if

        if(freq2.isNotBlank()) {
            if(!freq1.isDigitsOnly()){ // c'est pas vide et ça contient des caractères
                Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                return
            }else{  // Ça contient que des digit
                if(freq2.toInt() < 1) {
                    Toast.makeText(this, "Fréquence 2 : La fréquence doit être supérieure à zéro", Toast.LENGTH_LONG).show()
                    return
                }

                if ((spinner3.selectedItemPosition < spinner2.selectedItemPosition) || (spinner3.selectedItemPosition >= spinner4.selectedItemPosition)) {
                    Toast.makeText(this, "Fréquence 2 : Le mois de fin doit être supérieur au mois de début et les deux doivent toujours être supérieurs aux mois de fréquence 1", Toast.LENGTH_LONG).show()
                    return
                }

                if (moisEnCours >= spinner3.selectedItemPosition && moisEnCours <= spinner4.selectedItemPosition)
                    frequenceActuelle = freq2.toInt()

                insertFrequence2 = true
            } // else
        } // if

        if(freq3.isNotBlank()) {
            if(!freq1.isDigitsOnly()){ // c'est pas vide et ça contient des caractères
                Toast.makeText(this, "Les frequence doit etre un chiffre uniquement ", Toast.LENGTH_LONG).show()
                return
            }else{  // Ça contient que des digit
                if(freq3.toInt() < 1) {
                    Toast.makeText(this, "Fréquence 3 : La fréquence doit être supérieure à zéro", Toast.LENGTH_LONG).show()
                    return
                }

                if ((spinner5.selectedItemPosition < spinner4.selectedItemPosition) || (spinner6.selectedItemPosition >= spinner5.selectedItemPosition)) {
                    Toast.makeText(this, "Fréquence 3 : Le mois de fin doit être supérieur au mois de début et les deux doivent toujours être supérieurs aux mois de fréquence 2", Toast.LENGTH_LONG).show()
                    return
                }

                if (moisEnCours >= spinner5.selectedItemPosition && moisEnCours <= spinner6.selectedItemPosition)
                    frequenceActuelle = freq3.toInt()

                insertFrequence3 = true
            } // else
        } // if

        var idPlanteDansBDD : Int = 0

        if (!insertFrequence1 && !insertFrequence2 && !insertFrequence3) {
            Toast.makeText(this, "Veuillez remplir au moins une fréquence", Toast.LENGTH_LONG).show()
            return
        }

        val prochainArosage : Calendar = Calendar.getInstance()
        prochainArosage.set(Calendar.DAY_OF_YEAR, prochainArosage.get(Calendar.DAY_OF_YEAR) + frequenceActuelle)

        if(frequenceActuelle == 0) {
            if(insertFrequence1) {
                val annee : Int = ( if (moisEnCours < spinner1.selectedItemPosition) dateAujourdhui.get(Calendar.YEAR) else (dateAujourdhui.get(Calendar.YEAR) + 1))
                prochainArosage.set(annee  , spinner1.selectedItemPosition, 1) // set(int year, int month, int date)

            } else if(insertFrequence2) {
                val annee : Int = ( if (moisEnCours < spinner3.selectedItemPosition) dateAujourdhui.get(Calendar.YEAR) else (dateAujourdhui.get(Calendar.YEAR) + 1))
                prochainArosage.set(annee  , spinner3.selectedItemPosition, 1) // set(int year, int month, int date)

            } else if(insertFrequence3) {
                val annee : Int = ( if (moisEnCours < spinner5.selectedItemPosition) dateAujourdhui.get(Calendar.YEAR) else (dateAujourdhui.get(Calendar.YEAR) + 1))
                prochainArosage.set(annee  , spinner5.selectedItemPosition, 1) // set(int year, int month, int date)

            }
        }

        // Insertion
        var t = Thread {
            bd.insertPlante( TablePlante( nomCommun =nomCom, nomLatin = nomLat /*, lienPhotoDeLaPlante = lien*/, dateProchainArosage = prochainArosage))

            val lesPlantes : Array<TablePlante> = bd.loadAll() // Récupérer toutes les plantes
            idPlanteDansBDD = lesPlantes.last().idPlante;
        }
        t.start()
        t.join();

        if(insertFrequence1) {
            var t1 = Thread {
                bd.insertFrequence( Frequence( IdPlante = idPlanteDansBDD, dateDebut = dateDebutFreq1, dateFin = dateFinFreq1 , nbFois = freq1 ))
            }
            t1.start()
            t1.join();
        }

        if(insertFrequence2) {
            var t2 = Thread {
                bd.insertFrequence( Frequence( IdPlante = idPlanteDansBDD, dateDebut = dateDebutFreq2, dateFin = dateFinFreq2, nbFois = freq2))
            }
            t2.start()
            t2.join();
        }

        if(insertFrequence3) {
            var t3 = Thread {
                bd.insertFrequence( Frequence( IdPlante = idPlanteDansBDD, dateDebut = dateDebutFreq3, dateFin = dateFinFreq3 , nbFois = freq3 ))
            }
            t3.start()
            t3.join();
        }

        // Nettoyer tous les EditText
        binding.nomCommun.text.clear()
        binding.nomLatin.text.clear()
        binding.freq1.text.clear()
        binding.freq2.text.clear()
        binding.freq3.text.clear()
    }

}