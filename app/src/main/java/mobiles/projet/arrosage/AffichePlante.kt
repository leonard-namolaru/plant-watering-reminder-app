package mobiles.projet.arrosage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        if (nomCommun != null) binding.leNomCommun.text = nomCommun
        if (nomLatin != null) binding.leNomLatin.text = nomLatin

    }


}