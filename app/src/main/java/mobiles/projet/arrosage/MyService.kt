package mobiles.projet.arrosage

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.app.Service
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.parseIntent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MyService : Service() {
    companion object { /* définir les constantes */
        const val notId = 9
        const val CHANNEL_ID = "message urgent"
    }

    //non utilisé par un service foreground mais nécessaire à définir
    // onBind() - tjrs à implémenter mais retourner tout de suite null pour le
    //service de type "started". Pour le service de type "bound" implémenter
    //l'interface de communication avec le client et retourner l'objet IBinder.
    override fun onBind(intent: Intent): IBinder? {
        return null;
    }

    // exécutée une seule fois à la création du service (avant
    //onStartCommand() ou onBind())
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        Log.d("onCreate()","onCreate()")
        Toast.makeText(this, "ALARM !!!!!", Toast.LENGTH_LONG).show();
        createNotificationChannel()
        val intent : Intent = Intent(); //retourne Intent qui a fait démarrer l'activité
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE )

        /*création de notification */
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("lancer l'activité C")
            .setContentText("ici tu peut lancer l'activité C immédiatement")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // la notification disparaît quand utilisateur...
            .setSmallIcon(R.drawable.ic_add) // SmallIcon est obligatoire, ic_add ; TEST
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // priority concerne les version android <= 7.1, à partir de la version 8 c'est le channel qui est utilisé
            .setLargeIcon( BitmapFactory.decodeResource( resources, R.drawable.ic_add ) ) // prend un bitmap en paramètre, ic_add ; TEST
            .build()

        /* envoi de notification */
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager) .notify(notId, notification)
        /* notId : ID de la notification, utilisé pour faire cancel et update */
    }

    // C’est dans startCommand() que nous implémentons les actions de service.
    // le service peut s'exécuter à l'infini, il doit
    //s'arrêter lui-même en exécutant selfStop() ou être arrêter par un
    //autre composant avec stopService(). Pas besoin d'implémenter
    //pour le service de type bound
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("onStartCommand()","onStartCommand()")
        //le service devient foreground
        // startForeground( id : Int, notification : Notification)
        stopSelf()

        return Service.START_STICKY
    }

    // onDestroy() - appelée quand le service est détruit ou terminé.
    // Libérer les ressources utilisées
    override fun onDestroy() {
        Log.d("onDestroy()","onDestroy()")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            // récupérer NotificationManager et enregistrer le channel
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}