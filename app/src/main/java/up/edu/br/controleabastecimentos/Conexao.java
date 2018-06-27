package up.edu.br.controleabastecimentos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static Conexao conexao;

    public static Conexao getInstance(){ return conexao; }

    public Conexao (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        conexao = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String statement = " create table abastecimento (" +
                " id integer primary key autoincrement," +
                " data date," +
                " custoTotal double," +
                " precoLitro double," +
                " litros double," +
                " odometro integer," +
                " taqueCheio boolean," +
                " media double," +
                " posto varchar (255)" +
                ")";

        db.execSQL(statement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
