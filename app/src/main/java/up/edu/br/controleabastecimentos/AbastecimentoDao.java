package up.edu.br.controleabastecimentos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AbastecimentoDao {

    static ArrayList<Abastecimento> abastecimentos = new ArrayList<Abastecimento>();
    static Integer id = 0;
    static Integer nenhumAbastecimentoTanqueCheio = 0;
    static int ultimoKmTanqueCheio = 0;
    static Double totalLitros = 0.0;


    public void salvar(Abastecimento abastecimento) {

        abastecimento.setLitros(abastecimento.getCustoTotal() / abastecimento.getPrecoLitro());

        if (nenhumAbastecimentoTanqueCheio != 0 && abastecimento.getTanqueCheio() == 0) {
            int totalRodado = abastecimento.getOdometro() - ultimoKmTanqueCheio;
            totalLitros += abastecimento.getLitros();

            abastecimento.setMedia(totalRodado / totalLitros);

            ultimoKmTanqueCheio = abastecimento.getOdometro();
            totalLitros = 0.0;
        } else {
            if (abastecimento.getTanqueCheio() == 0) {
                nenhumAbastecimentoTanqueCheio = 1;
                ultimoKmTanqueCheio = abastecimento.getOdometro();
                totalLitros = 0.0;

                abastecimento.setMedia(0.0);
            } else {
                totalLitros += abastecimento.getLitros();

                abastecimento.setMedia(0.0);
            }
        }

        SQLiteDatabase conn = Conexao.getInstance().getReadableDatabase();

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

        if (abastecimento.getId() == null) {
            conn.insert("abastecimento", null, values);
        } else {
            conn.update("abastecimento", values, "id = ?", new String[]{abastecimento.getId().toString()});
        }
    }

    public List<Abastecimento> listar() {
        SQLiteDatabase conn = Conexao.getInstance().getReadableDatabase();

        Cursor c = conn.query("abastecimento", new String[]{"id", "data", "custoTotal", "precoLitro", "litros", "odometro", "tanqueCheio", "media", "posto", "telefone"},
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
                abastecimento.setTanqueCheio(c.getInt(6));
                abastecimento.setMedia(c.getDouble(7));
                abastecimento.setPosto(c.getString(8));
                abastecimento.setTelefone(c.getString(9));

                abastecimentos.add(abastecimento);
            } while (c.moveToNext());
        }

        return abastecimentos;
    }

    public void excluir(Abastecimento abastecimento) {

        SQLiteDatabase conn = Conexao.getInstance().getWritableDatabase();

        conn.delete("abastecimento", "id = ?", new String[]{abastecimento.getId().toString()});
    }
}
