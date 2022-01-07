package mobiles.projet.arrosage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListePlantesArroserAujourdhuiRecycleAdapter ( val context : Context , val listeDesPlantes: MutableList<TablePlante>, val couleurPaire: Int, val couleurImpaire: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // Parametres du constructeur : la liste mutable de plantes, les couleurs couleurPaire, couleurImpair (en tant que Int)
    val checked = ArrayList<String>()

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    /* un lister à associer à chaque View dans RecyclerView,
 * activé quand l'utilisateur
 * sélectionne un élément dans RecyclerView */
    var listener = View.OnClickListener { view ->
        val w = view as CheckedTextView
        w.toggle()
        if (w.isChecked) {
            checked.add(w.text.toString())
        } else {
            checked.remove(w.text.toString())
        }
    }

    // RecyclerView apelle onCreateViewHolder quand il a besoin d'un nouveau couple (View, Holder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //A partir de la liste , on crée les couple view holder
        // Crer View dun element de la liste a partir de fichier layout xml
        val v = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.element_liste_plantes_arroser_aujourdhui, parent, false) as CheckedTextView

        val holder: RecyclerView.ViewHolder = VH(v)

        /* Installer le meme listener sur chaqaue View
        v.setOnClickListener( View.OnClickListener {
            val plantePosition: Int = holder.absoluteAdapterPosition // récuperer la position sur la quelle on a cliqué
            val plante: TablePlante = listeDesPlantes[plantePosition]

            val intent = Intent(context , AffichePlante::class.java)
            intent.putExtra("id", plante.idPlante)
            intent.putExtra("nomCommun", plante.nomCommun)
            intent.putExtra("nomLatin", plante.nomLatin)
            intent.putExtra("ProchainArosage", plante.dateProchainArosage.getTimeInMillis())

            context.startActivity(intent) // on va lancer une autre actuvté en passant intent en parametre qui contient afficheplante (le fichier kt a lancer
        } )
        */

        /*installer le même listener sur chaque View */
        v.setOnClickListener(listener)
        return holder // retourner le ViewHolder
    }

    // La fonction onBindViewHolder est appelee par RecyclerView
    // quand litem a la position "position" devient visible.
    // Le holder a recycler est donne comme le premier parametre.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /* Recuperer la View :
         * holder.itemView cest la View associee a ce holder
         */
        val checkedTextViewPourNom: CheckedTextView = holder.itemView.findViewById(R.id.nom) as CheckedTextView
       // val imageViewPourPlante: ImageView = holder.itemView.findViewById(R.id.photoPlante) as ImageView

        checkedTextViewPourNom.text = listeDesPlantes[position].nomCommun + " "
        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(couleurPaire)
        else
            holder.itemView.setBackgroundColor(couleurImpaire)
    }

    override fun getItemCount(): Int = listeDesPlantes.size
}