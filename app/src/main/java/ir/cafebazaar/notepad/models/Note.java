package ir.cafebazaar.notepad.models;

import android.text.Spannable;
import android.text.SpannableString;

import com.commonsware.cwac.richtextutils.SpannableStringGenerator;
import com.commonsware.cwac.richtextutils.SpannedXhtmlGenerator;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import ir.cafebazaar.notepad.database.AppDatabase;

/**
 * Created by MohMah on 8/17/2016.
 */
@Table(database = AppDatabase.class, allFields = true)
public class Note extends BaseModel {

	@PrimaryKey(autoincrement = true)
	private int id;
	private String title;
	private String body;
	private Blob drawing;
	private Blob drawingTrimmed;
	private Date createdAt;

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

	public Blob getDrawingTrimmed(){
		return drawingTrimmed;
	}

	public void setDrawingTrimmed(Blob drawingTrimmed){
		this.drawingTrimmed = drawingTrimmed;
	}

	public Date getCreatedAt(){
		return createdAt;
	}

	public void setCreatedAt(Date createdAt){
		this.createdAt = createdAt;
	}

	public Spannable getSpannedBody(){
		SpannableStringGenerator spannableStringGenerator = new SpannableStringGenerator();
		try{
			return spannableStringGenerator.fromXhtml(body);
		}catch (ParserConfigurationException e){
			e.printStackTrace();
		}catch (SAXException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return new SpannableString("!ERROR!");
	}

	public void setSpannedBody(Spannable spannedBody){
		SpannedXhtmlGenerator spannedXhtmlGenerator = new SpannedXhtmlGenerator();
		body = spannedXhtmlGenerator.toXhtml(spannedBody);
		body = body.replaceAll("(?m)(^ *| +(?= |$))", "")
		           .replaceAll("(?m)^$([\r\n]+?)(^$[\r\n]+?^)+", "$1")
		           .replace("<br/>", "\n");
	}

	@Override public boolean equals(Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Note note = (Note) o;

		return id == note.id;
	}

	@Override public int hashCode(){
		return id;
	}

	@Override public String toString(){
		return "Note{" +
				"id=" + id +
				", title='" + title + '\'' +
				"} " + super.toString();
	}
}
