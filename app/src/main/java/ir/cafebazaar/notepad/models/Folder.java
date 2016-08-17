package ir.cafebazaar.notepad.models;

/**
 * Created by MohMah on 8/17/2016.
 */
public class Folder{

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
