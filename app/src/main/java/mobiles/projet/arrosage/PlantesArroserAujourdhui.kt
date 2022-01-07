package mobiles.projet.arrosage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mobiles.projet.arrosage.databinding.ActivityPlantesArroserAujourdhuiBinding
import java.util.*

class PlantesArroserAujourdhui : AppCompatActivity() {
    private lateinit var binding: ActivityPlantesArroserAujourdhuiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plantes_arroser_aujourdhui)

        binding = ActivityPlantesArroserAujourdhuiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent() // Retourne l'Intent qui a fait démarrer l'activité

        // récupérer la référence vers RecyclerView
        val recyclerView = binding.recycler as RecyclerView
        recyclerView.hasFixedSize() /* pour améliorer les pérformances*/
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Récupéerer l'instance vers la base de données
        val bd = PlantesBD.getDatabase(application)

        // Le Array des plantes  à afficher dans RecyclerView
        var listeDesPlantes : Array<TablePlante> = arrayOf() // on a declaré une liste de plantes qu'on va recupéerer pour l'afficher dans recycler view
        var listePourAffichage : MutableList<TablePlante> = mutableListOf()
        // on remplit le Array des plantes a affciher a partir de la bd avec le loadAll()
        val t= Thread{ listeDesPlantes = bd.myDao().loadAll() }
        t.start()  // loadall  fait  :    "SELECT * FROM TablePlante" et renvoie un Array<TablePlante>
        t.join()

        for(plante in listeDesPlantes) {
            val ajd : Calendar = Calendar.getInstance()
            val jour = ajd.get(Calendar.DAY_OF_MONTH) == plante.dateProchainArosage.get(java.util.Calendar.DAY_OF_MONTH)
            val mois = ajd.get(Calendar.MONTH) == plante.dateProchainArosage.get(java.util.Calendar.MONTH)
            val annee = ajd.get(Calendar.YEAR) == plante.dateProchainArosage.get(java.util.Calendar.YEAR)

            if (jour && mois && annee)
                listePourAffichage.add(plante)
        }

        val couleurPaire: Int = ResourcesCompat.getColor(resources, R.color.couleurPaire, null)
        val couleurImpaire: Int = ResourcesCompat.getColor(resources, R.color.couleurImpaire, null)


        var adapter: ListePlantesArroserAujourdhuiRecycleAdapter = ListePlantesArroserAujourdhuiRecycleAdapter( this , listePourAffichage, couleurPaire, couleurImpaire)
        recyclerView.adapter = adapter

    }
}