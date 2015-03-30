package org.example.top10downloader;

public class Application {
	
	private String image;
	private String name;
	private String artist;
	private String releaseDate;
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String toString() {
		return 	"image: " + this.image + "\n" +
				"name: " + this.name + "\n" +
			"artist: " + this.artist + "\n" +
				"Release Date: " +  this.releaseDate + "\n";
	}
}
