package mobiles.projet.arrosage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlantViewModel : ViewModel() {

val plante : MutableLiveData<Plante> by lazy { MutableLiveData<Plante>().apply { value=Plante(0,"","",null,"") } }
}

