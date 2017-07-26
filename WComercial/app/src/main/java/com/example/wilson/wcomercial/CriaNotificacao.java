package com.example.wilson.wcomercial;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by Wilson on 04/02/2017.
 */

public class CriaNotificacao {

    private Context context;
    private String mensagem;
    private String titulo;
    private Intent intent;
    private String ticker;

    public CriaNotificacao(Context context,String mensagem,String titulo,Intent intent,String ticker){
        this.context = context;
        this.mensagem = mensagem;
        this.titulo = titulo;
        this.intent = intent;
        this.ticker = ticker;
    }

    public void executaNotificacao(){
        PendingIntent pending = PendingIntent.getActivity(this.context,0,intent,0);
        Notification notification = new Notification.Builder(this.context)
                .setContentTitle(this.titulo)
                .setContentText(this.mensagem)
                .setSmallIcon(R.drawable.ic_launcher).setTicker(this.ticker)
                .setContentIntent(pending).getNotification();
        notification.vibrate = new long[]{100,1500};
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(R.string.app_name,notification);

        Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone toque = RingtoneManager.getRingtone(this.context,som);
        toque.play();
    }

}
