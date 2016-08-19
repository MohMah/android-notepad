package ir.cafebazaar.notepad.models;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.structure.BaseModel;
import ir.cafebazaar.notepad.database.AppDatabase;
import java.util.Date;

/**
 * Created by MohMah on 8/17/2016.
 */
@Table(database = AppDatabase.class, allFields = true)
public class Note extends BaseModel{

	@PrimaryKey
	private int id;
	private String title;
	private String body;
	private Blob drawing;
	private Date createdAt;
	private Date lastModified;
	public Note(){}

	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}

	public String getTitle(){
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getBody(){
		return body;
	}

	public void setBody(String body){
		this.body = body;
	}

	public Blob getDrawing(){
		return drawing;
	}

	public void setDrawing(Blob drawing){
		this.drawing = drawing;
	}

	public Date getCreatedAt(){
		return createdAt;
	}

	public void setCreatedAt(Date createdAt){
		this.createdAt = createdAt;
	}

	public Date getLastModified(){
		return lastModified;
	}

	public void setLastModified(Date lastModified){
		this.lastModified = lastModified;
	}
}
