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
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // récupérer la référence vers RecyclerView qui se trouve dans actvitymain.xml
        val recyclerView = findViewById(R.id.recycler) as RecyclerView
        recyclerView.hasFixedSize() /* pour améliorer les pérformances*/
        recyclerView.layoutManager = LinearLayoutManager(this)

        val couleurPaire: Int = ResourcesCompat.getColor(resources, R.color.couleurPaire, null)
        val couleurImpaire: Int = ResourcesCompat.getColor(resources, R.color.couleurImpaire, null)


        var adapter: ListePlantesRecycleAdapter = ListePlantesRecycleAdapter( this@MainActivity, couleurPaire, couleurImpaire)
        recyclerView.adapter = adapter

        val bd = PlantesBD.getDatabase(application) // Récupéerer l'instance vers la base de données
        var listeDesPlantes : LiveData<List<TablePlante>> = bd.myDao().loadAllLive()  // La liste des plantes  à afficher dans RecyclerView

        /* quand model.authors change mettre à jour la liste d'auteurs dans adapter */
        listeDesPlantes.observe(this) {listePlantes: List<TablePlante> ->
            adapter.setPlantes(listePlantes)
        }

        /* **** Une alarme qui se déclenche périodiquement chaque 24h. **** */

        /* intent à mettre dans pendingIntent. AlarmManager va ensuite l'envoyer vers le service MyService */
        val intent = Intent(this, MyService::class.java)
        // intent.putExtra(MyService.MEDIA_STATE, MyService.START )

        /* création de pendingIntent avec le intent dedans */
        // PendingIntent.getService( context : context, reqCode : Int, intent : , flags: Int ) : PendingIntent
        val pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)

        /* récupérer la référence vers AlarmManager */
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.AM_PM, Calendar.AM)
            set(Calendar.MINUTE, 30)
        }

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    fun ajouterPlante(view: View) {
        val intent = Intent(this, AjouterNouvellePlante::class.java)
        startActivity(intent)
    }

    fun plantesArroserAujourdhui(item: MenuItem) {
        val intent = Intent(this, PlantesArroserAujourdhui::class.java)
        startActivity(intent)
    }
}