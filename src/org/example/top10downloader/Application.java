package org.example.top10downloader;

public class Application {
	
	private String name;
	private String artist;
	private String releaseDate;
	
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
		return "name: " + this.name + "\n" +
			"artist: " + this.artist + "\n" +
				"Release Date: " +  this.releaseDate + "\n";
	}
}
