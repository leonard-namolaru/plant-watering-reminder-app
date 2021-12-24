package mobiles.projet.arrosage

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service.START_REDELIVER_INTENT
import android.content.Context
import android.content.Intent
import android.media.MediaParser.SeekPoint.START
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
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

        var adapter: ListePlantesRecycleAdapter = ListePlantesRecycleAdapter( this@MainActivity , listeDesPlantes, couleurPaire, couleurImpaire)
        recyclerView.adapter = adapter

        /* **** Une alarme qui se déclenche périodiquement chaque 24h. **** */

        /* intent à mettre dans pendingIntent. AlarmManager va ensuite l'envoyer vers le service MyService */
        val intent = Intent(this, MyService::class.java)
        // intent.putExtra(MyService.MEDIA_STATE, MyService.START )

        /* création de pendingIntent avec le intent dedans */
        // PendingIntent.getService( context : context, reqCode : Int, intent : , flags: Int ) : PendingIntent
        val pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)

        /* récupérer la référence vers AlarmManager */
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

         /* alarm sera déclenché dans sec secondes (à peu près, ce n'est pas une alarme exacte */
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10 * 1000, pendingIntent) // 10 sec
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 10)
        }

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    fun ajouterPlante(view: View) {
        val intent = Intent(this, AjouterNouvellePlante::class.java)
        startActivity(intent)
    }
}