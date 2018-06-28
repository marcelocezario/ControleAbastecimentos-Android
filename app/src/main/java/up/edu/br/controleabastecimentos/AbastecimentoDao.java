package up.edu.br.controleabastecimentos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AbastecimentoDao {

    static ArrayList<Abastecimento> abastecimentos = new ArrayList<Abastecimento>();
    static Integer id = 0;

    public void salvar (Abastecimento abastecimento){
        SQLiteDatabase conn = Conexao.getInstance().getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("data", abastecimento.getData().getDate());
        values.put("custoTotal", abastecimento.getCustoTotal());
        values.put("precoLitro", abastecimento.getPrecoLitro());
        values.put("litros", abastecimento.getLitros());
        values.put("odometro", abastecimento.getOdometro());
        values.put("tanqueCheio", abastecimento.isTanqueCheio());
        values.put("media", abastecimento.getMedia());
        values.put("posto", abastecimento.getPosto());



        if (abastecimento.getId() == null){
            conn.insert("abastecimento", null, values);
        } else {
            conn.update("abastecimento", values, "id = ?", new String[] {abastecimento.getId().toString()});
        }
    }

    public List<Abastecimento> listar() {
        SQLiteDatabase conn = Conexao.getInstance().getReadableDatabase();

        Cursor c = conn.query("abastecimento", new String[]{"id", "data", "custoTotal", "precoLitro", "litros", "odometro", "tanqueCheio", "media", "posto"},
                null, null, null, null, "id");

        ArrayList<Abastecimento> abastecimentos = new ArrayList<Abastecimento>();

        if (c.moveToFirst()) {
            do {
                Abastecimento abastecimento = new Abastecimento();
                abastecimento.setId(c.getInt(0));
                abastecimento.setData(c.getString(1));
                abastecimento.setCustoTotal(c.getDouble(2));
                abastecimento.setPrecoLitro(c.getDouble(3));
                abastecimento.setLitros(c.getDouble(4));
                abastecimento.setOdometro(c.getInt(5));
                abastecimento.setTanqueCheio(c.i);
                abastecimento.setMedia(c.getDouble(7));
                abastecimento.setPosto(c.getString(8));

                abastecimentos.add(abastecimento);
            } while (c.moveToNext());
        }

        return abastecimentos;
    }

    public void excluir (Abastecimento abastecimento){

        SQLiteDatabase conn = Conexao.getInstance().getWritableDatabase();

        conn.delete("abastecimento", "id = ?", new String [] {abastecimento.getId().toString()});
    }
}
