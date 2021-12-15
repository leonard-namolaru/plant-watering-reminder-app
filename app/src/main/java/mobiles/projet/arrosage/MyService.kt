package mobiles.projet.arrosage

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class MyService : Service() {

    //non utilisé par un service foreground mais nécessaire à définir
    // onBind() - tjrs à implémenter mais retourner tout de suite null pour le
    //service de type "started". Pour le service de type "bound" implémenter
    //l'interface de communication avec le client et retourner l'objet IBinder.
    override fun onBind(intent: Intent): IBinder? {
        return null;
    }

    // exécutée une seule fois à la création du service (avant
    //onStartCommand() ou onBind())
    override fun onCreate() {
        Log.d("onCreate()","onCreate()")
        Toast.makeText(this, "ALARM !!!!!", Toast.LENGTH_LONG).show();
    }//facultatif

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
    override fun onDestroy() { //stopper MediaPlayer
        Log.d("onDestroy()","onDestroy()")
    }

    }