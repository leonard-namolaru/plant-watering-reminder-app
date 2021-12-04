package mobiles.projet.arrosage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // récupérer la référence vers RecyclerView
        val recyclerView = findViewById(R.id.recycler) as RecyclerView
        recyclerView.hasFixedSize() /* pour améliorer les pérformances*/
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*  créer une liste de Strings à partir de fichier ressources
            //var listeDesPlantes : MutableList<Plante> = mutableListOf<Plante>()
           // créer une liste de Strings à partir de fichier ressources (resources : une propriété d'Activity)
            val nomsDesPlantes: Array<String> =  resources.getStringArray(R.array.plantes)

            for(i in nomsDesPlantes.indices)
                listeDesPlantes.add( Plante(nomsDesPlantes[i]) )
         */

        // BDD
        val bd = PlantesBD.getDatabase(application)
        var listeDesPlantes : Array<TablePlante> = arrayOf() // Le Array de pays à afficher dans RecyclerView
        val t= Thread{ listeDesPlantes = bd.myDao().loadAll() }
        t.start()
        t.join()

        val couleurPaire: Int = ResourcesCompat.getColor(resources, R.color.couleurPaire, null)
        val couleurImpaire: Int = ResourcesCompat.getColor(resources, R.color.couleurImpaire, null)

        var adapter: ListePlantesRecycleAdapter = ListePlantesRecycleAdapter(listeDesPlantes, couleurPaire, couleurImpaire)
        recyclerView.adapter = adapter
    }

    fun ajouterPlante(view: View) {
        val intent = Intent(this, AjouterNouvellePlante::class.java)
        startActivity(intent)
    }
}
