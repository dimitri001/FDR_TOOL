package org.korso.apps.rsscursoandroid.data;

import org.korso.apps.rsscursoandroid.data.FeedsContract.FeedsTable;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DummyFeed {
	
	private DummyFeed(){		
	}
	
	public static void createFeeds(SQLiteDatabase db, ContentValues values){
		
		double random = (Math.random() * 3000 + 1);
		
		values.put(FeedsTable.TITLE, "El ejercicio que te prepara para todos los deportes: la zancada perfecta");
		values.put(FeedsTable.LINK_URL, "http://www.sportlife.es/deportes/articulo/ejercicio-prepara-todos-deportes-zancada-perfecta"+random);
		values.put(FeedsTable.IMAGE_URL, "http://estaticosv2.sportlife.es/rcs/articles/8882/thumb/zancada-perfecta-perfil-correcto_thumb_d.jpg"+random);
		values.put(FeedsTable.DATE, "Fri, 21 Feb 2014 11:17:00 +0100");
		values.put(FeedsTable.CONTENT, "La zancada es un movimiento con transferencia a cualquier deporte, pero hay que saber ejecutarla correctamente. Te explicamos c�mo evitar fallos y conseguir una zancada perfecta...");
		long id = db.insert(FeedsTable.TABLE_NAME,null, values);
		
		if (!db.getAttachedDbs().isEmpty()){
			for (int i=0; i<10; i++){
				random = (Math.random() * 3000 + 1);
				values.put(FeedsTable.TITLE, "El ejercicio que te prepara para todos los deportes: la zancada perfecta");
				values.put(FeedsTable.LINK_URL, "http://www.sportlife.es/deportes/articulo/ejercicio-prepara-todos-deportes-zancada-perfecta"+random);
				values.put(FeedsTable.IMAGE_URL, "http://estaticosv2.sportlife.es/rcs/articles/8882/thumb/zancada-perfecta-perfil-correcto_thumb_d.jpg"+random);
				values.put(FeedsTable.DATE, "Fri, 21 Feb 2014 11:17:00 +0100");
				values.put(FeedsTable.CONTENT, "La zancada es un movimiento con transferencia a cualquier deporte, pero hay que saber ejecutarla correctamente. Te explicamos c�mo evitar fallos y conseguir una zancada perfecta...");
				id = db.insert(FeedsTable.TABLE_NAME,null, values);
				
			}	
				
		}
		
		
	}

}
