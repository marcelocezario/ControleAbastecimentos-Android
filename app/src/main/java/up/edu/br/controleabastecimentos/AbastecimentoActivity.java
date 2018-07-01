package up.edu.br.controleabastecimentos;

import android.Manifest;
import android.app.Activity;
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

        EditText txtData = (EditText) findViewById(R.id.txtData);
        EditText txtOdometro = (EditText) findViewById(R.id.txtOdometro);
        EditText txtCustoTotal = (EditText) findViewById(R.id.txtCustoTotal);
        EditText txtPrecoLitro = (EditText) findViewById(R.id.txtPrecoLitro);
        EditText txtPosto = (EditText) findViewById(R.id.txtPosto);
        EditText txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        CheckBox chkTanqueCheio = (CheckBox) findViewById(R.id.checkBoxTanqueCheio);

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
            CheckBox chkTanqueCheio = (CheckBox) findViewById(R.id.checkBoxTanqueCheio);

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
}
