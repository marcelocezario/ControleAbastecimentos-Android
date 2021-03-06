package up.edu.br.controleabastecimentos;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class AbastecimentoAdapter extends BaseAdapter{

    private List<Abastecimento> abastecimentos;
    Activity act;

    public AbastecimentoAdapter(List<Abastecimento> abastecimentos, Activity act){
        this.abastecimentos = abastecimentos;
        this.act = act;
    }

    @Override
    public int getCount() { return this.abastecimentos.size(); }

    @Override
    public Object getItem(int position) { return this.abastecimentos.get(position); }

    @Override
    public long getItemId(int position) { return 0; }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View v = act.getLayoutInflater().inflate(R.layout.abastecimento_adapter, parent, false);

        TextView txtData = v.findViewById(R.id.txtData);
        TextView txtOdometro = v.findViewById(R.id.txtOdometro);
        TextView txtMedia = v.findViewById(R.id.txtMedia);
        CheckBox chkTanqueCheio = v.findViewById(R.id.chkTanqueCheio);
        TextView txtLitros = v.findViewById(R.id.txtLitros);

        Abastecimento a = abastecimentos.get(position);

        txtData.setText("Data: " + a.getData().toString());
        txtOdometro.setText("Odometro: " + a.getOdometro());
        txtMedia.setText("Km/l: " + String.format("%,3f",a.getMedia()));
        txtLitros.setText("Litros: " + String.format("%,3f",a.getLitros()));


        return v;
    }

    public void remove (Abastecimento abastecimento) { this.abastecimentos.remove(abastecimento);}
}
