package up.edu.br.controleabastecimentos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AbastecimentoDao {

    public void salvar(Abastecimento abastecimento) {

        abastecimento.setLitros(abastecimento.getCustoTotal() / abastecimento.getPrecoLitro());

        SQLiteDatabase conn = Conexao.getInstance().getReadableDatabase();
        Cursor c = conn.query("abastecimento", new String[]{"id", "litros", "odometro", "tanqueCheio",
                        "media", "litrosAcumulados", "odometroUltimoTanqueCheio", "ultimaMedia"},
                null, null, null, null, "id");


        if (abastecimento.getId() == null){

            if (c.moveToLast()) {
                Double litrosAcumulados = 0.0;
                do {
                    if (c.getInt(3) == 0) {
                        litrosAcumulados += abastecimento.getLitros();
                        abastecimento.setOdometroUltimoTanqueCheio(c.getInt(2));
                        abastecimento.setUltimaMedia(c.getDouble(4));
                    } else {
                        litrosAcumulados = c.getDouble(5) + abastecimento.getLitros();
                        abastecimento.setOdometroUltimoTanqueCheio(c.getInt(6));
                        abastecimento.setUltimaMedia(c.getDouble(7));
                    }
                    abastecimento.setLitrosAcumulados(litrosAcumulados);
                } while (c.moveToNext());
            }

            try {
                if (abastecimento.getTanqueCheio() == 0 && abastecimento.getOdometroUltimoTanqueCheio() != 0) {
                    int totalRodado = abastecimento.getOdometro() - abastecimento.getOdometroUltimoTanqueCheio();
                    abastecimento.setMedia(totalRodado / abastecimento.getLitrosAcumulados());

                } else {
                    abastecimento.setMedia(0.0);

                }
            } catch (NullPointerException e) {
                abastecimento.setMedia(0.0);
            }
        }

//        SQLiteDatabase conn = Conexao.getInstance().getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("data", abastecimento.getData());
        values.put("custoTotal", abastecimento.getCustoTotal());
        values.put("precoLitro", abastecimento.getPrecoLitro());
        values.put("litros", abastecimento.getLitros());
        values.put("odometro", abastecimento.getOdometro());
        values.put("tanqueCheio", abastecimento.getTanqueCheio());
        values.put("media", abastecimento.getMedia());
        values.put("posto", abastecimento.getPosto());
        values.put("telefone", abastecimento.getTelefone());
        values.put("litrosAcumulados", abastecimento.getLitrosAcumulados());
        values.put("odometroUltimoTanqueCheio", abastecimento.getOdometroUltimoTanqueCheio());
        values.put("ultimaMedia", abastecimento.getUltimaMedia());


        if (abastecimento.getId() == null) {
            conn.insert("abastecimento", null, values);
        } else {
            conn.update("abastecimento", values, "id = ?", new String[]{abastecimento.getId().toString()});
        }
    }

    public List<Abastecimento> listar() {
        SQLiteDatabase conn = Conexao.getInstance().getReadableDatabase();

        Cursor c = conn.query("abastecimento", new String[]{"id", "data", "custoTotal", "precoLitro", "litros", "odometro", "tanqueCheio", "media", "posto", "telefone", "litrosAcumulados", "odometroUltimoTanqueCheio", "ultimaMedia"},
                null, null, null, null, "id");

        ArrayList<Abastecimento> abastecimentos = new ArrayList<Abastecimento>();

        if (c.moveToLast()) {
            do {
                Abastecimento abastecimento = new Abastecimento();
                abastecimento.setId(c.getInt(0));
                abastecimento.setData(c.getString(1));
                abastecimento.setCustoTotal(c.getDouble(2));
                abastecimento.setPrecoLitro(c.getDouble(3));
                abastecimento.setLitros(c.getDouble(4));
                abastecimento.setOdometro(c.getInt(5));
                abastecimento.setTanqueCheio(c.getInt(6));
                abastecimento.setMedia(c.getDouble(7));
                abastecimento.setPosto(c.getString(8));
                abastecimento.setTelefone(c.getString(9));
                abastecimento.setLitrosAcumulados(c.getDouble(10));
                abastecimento.setOdometroUltimoTanqueCheio(c.getInt(11));
                abastecimento.setUltimaMedia(c.getDouble(12));

                abastecimentos.add(abastecimento);
            } while (c.moveToPrevious());
        }

        return abastecimentos;
    }

    public void excluir(Abastecimento abastecimento) {

        SQLiteDatabase conn = Conexao.getInstance().getWritableDatabase();

        conn.delete("abastecimento", "id = ?", new String[]{abastecimento.getId().toString()});
    }

}
