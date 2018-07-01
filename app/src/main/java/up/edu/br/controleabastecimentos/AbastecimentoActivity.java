package up.edu.br.controleabastecimentos;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AbastecimentoActivity extends AppCompatActivity {

    Abastecimento abastecimento;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abastecimento);

        NotificationCompat.Builder notificao = new NotificationCompat.Builder(this);
        notificao.setAutoCancel(true);

        EditText txtData = (EditText) findViewById(R.id.txtData);
        EditText txtOdometro = (EditText) findViewById(R.id.txtOdometro);
        EditText txtCustoTotal = (EditText) findViewById(R.id.txtCustoTotal);
        EditText txtPrecoLitro = (EditText) findViewById(R.id.txtPrecoLitro);
        EditText txtPosto = (EditText) findViewById(R.id.txtPosto);
        EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        CheckBox chkTanqueCheio = (CheckBox) findViewById(R.id.chkTanqueCheio);

        Intent it = getIntent();
        if (it != null && it.hasExtra("abastecimento")) {

            abastecimento = (Abastecimento) it.getSerializableExtra("abastecimento");

            txtData.setText(abastecimento.getData());
            txtOdometro.setText(String.valueOf(abastecimento.getOdometro()));
            txtCustoTotal.setText(Double.toString(abastecimento.getCustoTotal()));
            txtPrecoLitro.setText(Double.toString(abastecimento.getPrecoLitro()));
            if (abastecimento.getTanqueCheio() == 0) {
                chkTanqueCheio.setChecked(true);
            } else {
                chkTanqueCheio.setChecked(false);
            }
            txtPosto.setText(abastecimento.getPosto());
            txtTelefone.setText(abastecimento.getTelefone());

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_abastecimento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save){

            EditText txtData = (EditText) findViewById(R.id.txtData);
            EditText txtOdometro = (EditText) findViewById(R.id.txtOdometro);
            EditText txtCustoTotal = (EditText) findViewById(R.id.txtCustoTotal);
            EditText txtPrecoLitro = (EditText) findViewById(R.id.txtPrecoLitro);
            EditText txtPosto = (EditText) findViewById(R.id.txtPosto);
            EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
            CheckBox chkTanqueCheio = (CheckBox) findViewById(R.id.chkTanqueCheio);

            if (abastecimento == null){
                abastecimento = new Abastecimento();
            }

            abastecimento.setData(txtData.getText().toString());
            abastecimento.setOdometro(Integer.parseInt(txtOdometro.getText().toString()));
            abastecimento.setCustoTotal(Double.parseDouble(txtCustoTotal.getText().toString()));
            abastecimento.setPrecoLitro(Double.parseDouble(txtPrecoLitro.getText().toString()));
            if (chkTanqueCheio.isChecked()){
                abastecimento.setTanqueCheio(0);
            } else {
                abastecimento.setTanqueCheio(1);
            }
            abastecimento.setPosto(txtPosto.getText().toString());
            abastecimento.setTelefone(txtTelefone.getText().toString());


            new AbastecimentoDao().salvar(abastecimento);
            notificao(abastecimento);
            abastecimento = null;

            Toast.makeText(getApplicationContext(), "Salvo com sucesso!", Toast.LENGTH_LONG).show();

            Intent it = new Intent(AbastecimentoActivity.this, MainActivity.class);
            startActivity(it);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ligar (View view){
        EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + txtTelefone.getText()));

        ActivityCompat.requestPermissions(AbastecimentoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        startActivity(callIntent);
    }


    public void notificao(Abastecimento abastecimento) {



        if (abastecimento.getMedia() != 0.0 && abastecimento.getUltimaMedia() != 0.00){
            int id = 1;
//            int icone = android.R.drawable.ic_dialog_info;
            String titulo;//titulo da notificação
            String texto;// texto da notificação
            Double diferencaMedia = ((abastecimento.getMedia() / abastecimento.getUltimaMedia())-1)*100;
            if(abastecimento.getUltimaMedia() > abastecimento.getMedia()){
                titulo = "Sua condução piorou!";
                texto = "A média de seu veículo caiu " + String.format("%.2f",diferencaMedia) + "% desde o último abastecimento com tanque cheio.";
            } else{
                titulo = "Parabéns, sua condução melhorou!";
                texto = "A média de seu veículo melhorou " + String.format("%.2f",diferencaMedia) + "% desde o último abastecimento com tanque cheio, a natureza e seu bolso agradecem..";
            }

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent p = getPendingIntent(id, intent, this);

            NotificationCompat.Builder notificao = new NotificationCompat.Builder(this);
            notificao.setSmallIcon(R.drawable.folha);
            notificao.setContentTitle(titulo);
            notificao.setContentText(texto);
            notificao.setContentIntent(p);
            notificao.setStyle( new NotificationCompat.BigTextStyle().bigText(texto));
            notificao.setAutoCancel(true);

            NotificationManagerCompat nm = NotificationManagerCompat.from(this);
            nm.notify(id, notificao.build());
        }

    }

    public PendingIntent getPendingIntent(int id, Intent intent, Context context){
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(intent.getComponent());
        stackBuilder.addNextIntent(intent);

        PendingIntent p = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
        return p;
    }

}
