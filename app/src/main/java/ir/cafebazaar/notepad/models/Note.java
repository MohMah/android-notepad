package ir.cafebazaar.notepad.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.structure.BaseModel;
import ir.cafebazaar.notepad.database.AppDatabase;
import java.util.Date;

/**
 * Created by MohMah on 8/17/2016.
 */
@ModelContainer
@Table(database = AppDatabase.class, allFields = true)
public class Note extends BaseModel implements Parcelable{

	public static final Creator<Note> CREATOR = new Creator<Note>(){
		@Override
		public Note createFromParcel(Parcel in){
			return new Note(in);
		}

		@Override
		public Note[] newArray(int size){
			return new Note[size];
		}
	};
	@PrimaryKey(autoincrement = true)
	private int id;
	private String title;
	private String body;
	private Blob drawing;
	private Date createdAt;
	private Date lastModified;

	public Note(){}

	protected Note(Parcel in){
		id = in.readInt();
		title = in.readString();
		body = in.readString();
	}

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

	@Override public int describeContents(){
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags){
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(body);
	}
}
