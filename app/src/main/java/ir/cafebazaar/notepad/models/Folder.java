package ir.cafebazaar.notepad.models;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import ir.cafebazaar.notepad.database.AppDatabase;

/**
 * Created by MohMah on 8/17/2016.
 */
@Table(database = AppDatabase.class, allFields = true)
public class Folder extends BaseModel{

	@PrimaryKey(autoincrement = true)
	private int id;
	private String name;
	private Long createdAt;

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public Long getCreatedAt(){
		return createdAt;
	}

	public void setCreatedAt(Long createdAt){
		this.createdAt = createdAt;
	}
}
