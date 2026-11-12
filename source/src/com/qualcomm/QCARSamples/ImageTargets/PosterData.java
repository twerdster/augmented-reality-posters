
package com.qualcomm.QCARSamples.ImageTargets;

import java.io.Serializable;

//PROJECT_TAG:
//This class is used for holding all the posters data parsed 
//from the downloaded index file
@SuppressWarnings("serial")
public class PosterData implements Serializable {
	private int id;
	private String title;
	private String names;
	private String webSite;

	public PosterData(int id, String title, String names, String webSite) {
		super();
		this.setId(id);
		this.setTitle(title);
		this.setNames(names);
		this.setWebSite(webSite);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	
}
