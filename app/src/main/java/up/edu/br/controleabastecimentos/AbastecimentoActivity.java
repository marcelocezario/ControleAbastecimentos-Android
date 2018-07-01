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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abastecimento);

        TextView txtData = (EditText) findViewById(R.id.txtData);
        TextView txtOdometro = (EditText) findViewById(R.id.txtOdometro);
        TextView txtCustoTotal = (EditText) findViewById(R.id.txtCustoTotal);
        TextView txtPrecoLitro = (EditText) findViewById(R.id.txtPrecoLitro);
        TextView txtPosto = (EditText) findViewById(R.id.txtPosto);
        CheckBox chkTanqueCheio = (CheckBox) findViewById(R.id.checkBoxTanqueCheio);

        Intent it = getIntent();
        if (it != null && it.hasExtra("abastecimento")) {

            abastecimento = (Abastecimento) it.getSerializableExtra("abastecimento");

            txtData.setText(abastecimento.getData());
            txtOdometro.setText(abastecimento.getOdometro());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save){

            TextView txtData = (EditText) findViewById(R.id.txtData);
            TextView txtOdometro = (EditText) findViewById(R.id.txtOdometro);
            TextView txtCustoTotal = (EditText) findViewById(R.id.txtCustoTotal);
            TextView txtPrecoLitro = (EditText) findViewById(R.id.txtPrecoLitro);
            TextView txtPosto = (EditText) findViewById(R.id.txtPosto);
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


            new AbastecimentoDao().salvar(abastecimento);
            abastecimento = null;

            Toast.makeText(getApplicationContext(), "Salvo com sucesso!", Toast.LENGTH_LONG).show();

            Intent it = new Intent(AbastecimentoActivity.this, MainActivity.class);
            startActivity(it);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
