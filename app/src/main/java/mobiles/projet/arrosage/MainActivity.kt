package mobiles.projet.arrosage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var listeDesPlantes : MutableList<Plante> = mutableListOf<Plante>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // récupérer la référence vers RecyclerView
        val recyclerView = findViewById(R.id.recycler) as RecyclerView
        recyclerView.hasFixedSize() /* pour améliorer les pérformances*/
        recyclerView.layoutManager = LinearLayoutManager(this)

        // créer une liste de Strings à partir de fichier ressources (resources : une propriété d'Activity)
        val nomsDesPlantes: Array<String> =  resources.getStringArray(R.array.plantes)

        for(i in nomsDesPlantes.indices)
            listeDesPlantes.add( Plante(nomsDesPlantes[i]) )

        val couleurPaire: Int = ResourcesCompat.getColor(resources, R.color.couleurPaire, null)
        val couleurImpaire: Int = ResourcesCompat.getColor(resources, R.color.couleurImpaire, null)

        var adapter: ListePlantesRecycleAdapter = ListePlantesRecycleAdapter(listeDesPlantes, couleurPaire, couleurImpaire)
        recyclerView.adapter = adapter
    }
}