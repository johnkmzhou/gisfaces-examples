package com.gisfaces.custom;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.gisfaces.utilities.GISUtilities;

@Named
@ViewScoped
public class GeocodingBean implements Serializable {
	private static final long serialVersionUID = 6809054747251918793L;
	private static Logger log = Logger.getLogger(GeocodingBean.class.getName());
	private String address;
	private String jsonResponse;

	/**
	 * Sends address to geocoding service.
	 * 
	 * @param event
	 */
	public void geocodingActionListener(ActionEvent event) {
		String url = "http://geodata.hawaii.gov/arcgis/rest/services/Geocoding/USA/GeocodeServer/findAddressCandidates?f=pjson&SingleLine="
				+ address;
		url = url.replace(" ", "+");
		try {
			jsonResponse = GISUtilities.getUrlConnectionOutput(url);
		} catch (IOException e) {
			log.error("Exception accessing geocoding service.", e);
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}
}
