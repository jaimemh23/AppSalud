package AccesoDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDOpenHelper extends SQLiteOpenHelper {
    String tabla_Persona = "CREATE TABLE Persona(IdPersona Integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                            " nombres VARCHAR(40) NOT NULL, apellidos VARCHAR(40) NOT NULL," +
                            " sexo VARCHAR(10) NOT NULL, ciudad VARCHAR(30) NOT NULL, edad Integer NOT NULL," +
                            " dni Varchar(8) NOT NULL, peso DOUBLE NOT NULL, altura DOUBLE NOT NULL, foto BLOB)";
    public BDOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla_Persona);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Persona");
        db.execSQL(tabla_Persona);
    }
}
