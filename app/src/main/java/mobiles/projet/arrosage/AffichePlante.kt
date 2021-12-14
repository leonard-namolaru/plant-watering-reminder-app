package mobiles.projet.arrosage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mobiles.projet.arrosage.databinding.ActivityAffichePlanteBinding

class AffichePlante : AppCompatActivity() {
    private lateinit var binding: ActivityAffichePlanteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiche_plante)

        binding = ActivityAffichePlanteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent() //retourne Intent qui a fait démarrer l'activité
        val nomCommun : String?  = intent.getExtras()?.getString( "nomCommun" )
        val nomLatin : String?  = intent.getExtras()?.getString( "nomLatin" )
        val planteId : Int?  = intent.getExtras()?.getInt("id")
        if (nomCommun != null) binding.leNomCommun.text = nomCommun
        if (nomLatin != null) binding.leNomLatin.text = nomLatin

        // récupérer la référence vers RecyclerView
        val recyclerView = binding.recycler as RecyclerView
        recyclerView.hasFixedSize() /* pour améliorer les pérformances*/
        recyclerView.layoutManager = LinearLayoutManager(this)

        // BDD
        val bd = PlantesBD.getDatabase(application)
        var listeDesFrequences : Array<TablePlante> = arrayOf() // Le Array de pays à afficher dans RecyclerView
        val t= Thread{ listeDesFrequences = bd.myDao().loadAll() }
        t.start()
        t.join()

        val couleurPaire: Int = ResourcesCompat.getColor(resources, R.color.couleurPaire, null)
        val couleurImpaire: Int = ResourcesCompat.getColor(resources, R.color.couleurImpaire, null)

        var adapter: ListePlantesRecycleAdapter = ListePlantesRecycleAdapter( this , listeDesPlantes, couleurPaire, couleurImpaire)
        recyclerView.adapter = adapter
    }

    fun getListePlantes(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}