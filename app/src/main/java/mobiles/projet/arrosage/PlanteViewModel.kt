package mobiles.projet.arrosage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class PlanteViewModel(application: Application) : AndroidViewModel(application){
    // Attributs
    val dao = PlantesBD.getDatabase(application).myDao()
    // LiveData pour stocker les résultats des requêtes
    var plantes = MutableLiveData<List<Plante>>()

    fun partialNomPays(nom: String) {
        // On utilise postValue pour modifier le contenu de MutableLiveData à partir d’un autre Thread
        Thread { plantes.postValue(dao.loadPartialName(nom)) }.start()
    }


}