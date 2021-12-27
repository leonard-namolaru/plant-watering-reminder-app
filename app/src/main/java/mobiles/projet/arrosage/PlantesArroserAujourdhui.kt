package mobiles.projet.arrosage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mobiles.projet.arrosage.databinding.ActivityPlantesArroserAujourdhuiBinding

class PlantesArroserAujourdhui : AppCompatActivity() {
    private lateinit var binding: ActivityPlantesArroserAujourdhuiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plantes_arroser_aujourdhui)

        binding = ActivityPlantesArroserAujourdhuiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent() //retourne l'Intent qui a fait démarrer l'activité

    }
}