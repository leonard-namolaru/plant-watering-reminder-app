package mobiles.projet.arrosage

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mobiles.projet.arrosage.databinding.ActivityAffichePlanteBinding
import java.io.File
import java.util.*
import androidx.activity.*

class AffichePlante : AppCompatActivity() {
    private lateinit var binding: ActivityAffichePlanteBinding
    var localUri: Uri?=null

    val getContent= registerForActivityResult(ActivityResultContracts.GetContent())
    {
            uri:Uri?->/*siurinullrienàfaire*/
        if(uri==null)
            return@registerForActivityResult
        /*inputStreamavecl’imagedelaplante*/
        val inputStream=getContentResolver().openInputStream(uri)
        /*fabriquerlenomdefichierlocalpourstockerl’image*/
        val fileNamePrefix="plante"
        val preferences=getSharedPreferences("numImage", Context.MODE_PRIVATE)
        val numImage=preferences.getInt("numImage",1)
        val fileName="$fileNamePrefix$numImage"
        /*ouvriroutputStreamversunfichierlocal*/
        val file= File(this.filesDir,fileName)
        val outputStream=file.outputStream()
        /* sauvegarder le nouveau compteur d’image */
        preferences.edit().putInt("numImage",numImage+1).commit()
        /* copier inputStream qui pointe sur l’image de la galerie * vers le fichier local */
        inputStream?.copyTo(outputStream)
        /* mémoriser Uri de fichier local dans la propriété localUri */
        localUri = file.toUri()
        outputStream.close()
        inputStream?.close()
        //éventuellement afficher l’image dans ImageView
        val image=findViewById<ImageView>(R.id.photoPlante)
        // utilisation de view model pour changer l'uri de l'image
        var modelPlant = ViewModelProvider(this).get(PlantViewModel::class.java)
        modelPlant.plante.value?.lienPhotoDeLaPlante=localUri.toString()
        // observer pour changer l'image à afficher
        modelPlant.plante.observe(this) {
            image.setImageURI(modelPlant.plante.value?.lienPhotoDeLaPlante?.toUri())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiche_plante)
        val actionBar = supportActionBar
        actionBar!!.title= "Affichage Plante"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        binding = ActivityAffichePlanteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent() //retourne Intent qui a fait démarrer l'activité
        val nomCommun : String?  = intent.getExtras()?.getString( "nomCommun" )
        val nomLatin : String?  = intent.getExtras()?.getString( "nomLatin" )
        val dateProchainArosage :Long ?= intent.getExtras()?.getLong("ProchainArosage")
        val planteId : Int?  = intent.getExtras()?.getInt("id")
        if (nomCommun != null) binding.leNomCommun.text = nomCommun  // afichage
        if (nomLatin != null) binding.leNomLatin.text = nomLatin

        if (dateProchainArosage!= null) {
            var prochainArosage : Calendar = Calendar.getInstance()
            prochainArosage.setTimeInMillis(dateProchainArosage)
            val jour : String = prochainArosage.get(Calendar.DAY_OF_MONTH).toString()
            var moins : String = (prochainArosage.get(Calendar.MONTH).toInt() + 1).toString()
            val annee : String = prochainArosage.get(Calendar.YEAR).toString()
            binding.dateArosage.text = jour + " " + moins + " " + annee
        }

        // récupérer la référence vers RecyclerView
        val recyclerView = binding.recycler as RecyclerView
        recyclerView.hasFixedSize() /* pour améliorer les pérformances*/
        recyclerView.layoutManager = LinearLayoutManager(this)

        if(planteId != null) {
            // BDD
            val bd = PlantesBD.getDatabase(application)
            var listeDesFrequences : Array<Frequence> = arrayOf() // Le Array de frequences à afficher dans RecyclerView
            val t = Thread{
                listeDesFrequences = bd.myDao().loadFrequencesById(planteId)

            }
            t.start()
            t.join()

            val couleurPaire: Int = ResourcesCompat.getColor(resources, R.color.couleurPaire, null)
            val couleurImpaire: Int = ResourcesCompat.getColor(resources, R.color.couleurImpaire, null)

            var adapter: ListeFrequencesRecycleAdapter = ListeFrequencesRecycleAdapter( this , listeDesFrequences, couleurPaire, couleurImpaire)
            recyclerView.adapter = adapter

        } else {
            Toast.makeText(this, "Les fréquences pour cette plante n'ont pas pu être trouvées dans la base de données. \n", Toast.LENGTH_LONG).show()
        }

    }

    fun getListePlantes(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}