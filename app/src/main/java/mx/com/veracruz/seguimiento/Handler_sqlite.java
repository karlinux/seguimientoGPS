package mx.com.veracruz.seguimiento;
//2836075738
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class Handler_sqlite extends SQLiteOpenHelper {


	private final String FECHA = "fecha";
	private final String ESTADO = "estado";
	private final String GPS = "GPS";	//TABLA

	private final String LATITUD = "LATITUD";
	private final String IMEI = "IMEI";
	private final String LONGITUD = "LONGITUD";

	public Handler_sqlite(Context ctx) {
		super(ctx, "mibase", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

		String query3 = "CREATE TABLE "+ GPS +"("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				LATITUD + " text, " +
				LONGITUD + " text, " +
				IMEI + " text, " +
				FECHA + " TIMESTAMP default (datetime('now', 'localtime')) unique, " +
				ESTADO + " INTEGER DEFAULT 0);";
		db.execSQL(query3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int version_ant, int version_nue)
	{
		//db.execSQL("DROP TABLE IF EXISTS " + GPS);
		//onCreate(db);

	}

	public void insertarGps(String latitud, String longitud)
	{
		ContentValues valores = new ContentValues();
		valores.put(LATITUD, latitud);
		valores.put(LONGITUD, longitud);

		if ( (latitud != null) && (!latitud.equals("")) ) {
			this.getWritableDatabase().insert(GPS , null, valores);
		}
	}

	public Cursor getGps(){
		String columnas[] = {LATITUD, LONGITUD, FECHA, ESTADO};
		String[] ident= {"1"};
		Cursor c = this.getReadableDatabase().query(GPS, columnas, null, null, null, null, null);
		return c;
	}

	public Cursor enviarGps( String estado ){
		String columnas[] = {_ID, LATITUD, LONGITUD, FECHA};
		String[] ident= { estado };
		Cursor c = this.getReadableDatabase().query(GPS, columnas, ESTADO + "=?", ident, null, null, null);
		return c;
	}

	public void modificaGPS(String id, String estado) {
		ContentValues valores = new ContentValues();
		valores.put(ESTADO, estado);
		this.getWritableDatabase().update(GPS, valores, _ID+"=?", new String[] {id});
	}

	public void abrir() {
		// abrir base
		this.getWritableDatabase();
	}

	public void cerrar() {
		this.close();
	}
}
