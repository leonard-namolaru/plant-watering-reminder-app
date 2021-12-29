package mobiles.projet.arrosage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import mobiles.projet.arrosage.MainActivity
import java.util.Calendar

class ListePlantesRecycleAdapter ( val context : Context , val listeDesPlantes: Array<TablePlante>, val couleurPaire: Int, val couleurImpaire: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // Parametres du constructeur : la liste mutable detudiants, les couleurs couleurPaire, couleurImpair (en tant que Int)

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    // RecyclerView apelle onCreateViewHolder quand il a besoin d'un nouveau couple (View, Holder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //A partir de la liste , on crée les couple view holder
        // Crer View dun element de la liste a partir de fichier layout xml
        val v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.element_liste_plantes, parent, false)

        val holder: RecyclerView.ViewHolder = VH(v)

        // Installer le meme listener sur chaqaue View
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

        return holder // retourner le ViewHolder
    }

    // La fonction onBindViewHolder est appelee par RecyclerView
    // quand litem a la position "position" devient visible.
    // Le holder a recycler est donne comme le premier parametre.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /* Recuperer la View :
         * holder.itemView cest la View associee a ce holder
         */
        val textViewPourNom: TextView = holder.itemView.findViewById(R.id.nom) as TextView
        val imageViewPourPlante: ImageView = holder.itemView.findViewById(R.id.photoPlante) as ImageView

        textViewPourNom.text = listeDesPlantes[position].nomCommun + " "
            if (position % 2 == 0)
                holder.itemView.setBackgroundColor(couleurPaire)
            else
                holder.itemView.setBackgroundColor(couleurImpaire)
    }

    override fun getItemCount(): Int = listeDesPlantes.size
}