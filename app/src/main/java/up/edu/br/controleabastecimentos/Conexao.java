package up.edu.br.controleabastecimentos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static Conexao conexao;

    public static Conexao getInstance(){
        return conexao;
    }

    public Conexao (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        conexao = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String statement = " create table abastecimento (" +
                " id integer primary key autoincrement," +
                " data varchar(10)," +
                " custoTotal double," +
                " precoLitro double," +
                " litros double," +
                " odometro integer," +
                " tanqueCheio integer," + //zero se verdadeiro
                " media double," +
                " posto varchar (255)," +
                " telefone varchar (20)," +
                " litrosAcumulados double," +
                " odometroUltimoTanqueCheio integer," +
                " ultimaMedia double" +
                ")";

        db.execSQL(statement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion == 1){

        }

    }
}
