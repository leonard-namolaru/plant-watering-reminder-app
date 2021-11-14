package mobiles.projet.arrosage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListePlantesRecycleAdapter (val listeDesPlantes: MutableList<Plante>, val couleurPaire: Int, val couleurImpaire: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // Parametres du constructeur : la liste mutable detudiants, les couleurs couleurPaire, couleurImpair (en tant que Int)

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    // RecyclerView apelle onCreateViewHolder quand il a besoin d'un nouveau couple (View, Holder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Crer View dun element de la liste a partir de fichier layout xml
        val v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.element_liste_plantes, parent, false)

        val holder: RecyclerView.ViewHolder = VH(v)
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

        textViewPourNom.text = listeDesPlantes[position].nomCommun

            if (position % 2 == 0)
                holder.itemView.setBackgroundColor(couleurPaire)
            else
                holder.itemView.setBackgroundColor(couleurImpaire)
    }

    override fun getItemCount(): Int = listeDesPlantes.size
}