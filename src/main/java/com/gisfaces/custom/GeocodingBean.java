package com.gisfaces.custom;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.jboss.logging.Logger;

import com.gisfaces.model.graphic.GraphicsModel;
import com.gisfaces.model.graphic.MarkerGraphic;
import com.gisfaces.model.map.Coordinate;
import com.gisfaces.utilities.GISUtilities;

@Named
@ViewScoped
public class GeocodingBean implements Serializable {
	private static final long serialVersionUID = 9207600666292378232L;
	private static Logger log = Logger.getLogger(GeocodingBean.class.getName());
	private String address;
	private double latitude = 21.47;
	private double longitude = -157.97;
	private int zoom = 10;
	private GraphicsModel locations = new GraphicsModel();

	/**
	 * Sends address to geocoding service.
	 * 
	 * @param event
	 */
	public void geocodingActionListener(ActionEvent event) {
		address = address.replace(" ", "+");
		try {
			String jsonResponse = GISUtilities.getUrlConnectionOutput(
					"http://geodata.hawaii.gov/arcgis/rest/services/Geocoding/USA/GeocodeServer/findAddressCandidates?f=json&SingleLine="
							+ address);

			// JsonParser is faster than JsonObject but requires more code
			// use JsonParser when you don't want to keep the entire json object
			// in memory
			JsonParser parser = Json.createParser(new StringReader(jsonResponse));
			boolean updateCenter = false;
			while (parser.hasNext()) {
				Event e = parser.next();
				if (e.equals(Event.KEY_NAME) && "address".equals(parser.getString())) {
					MarkerGraphic marker = new MarkerGraphic();
					Map<String, Object> attrib = new HashMap<String, Object>();
					parser.next();
					attrib.put("address", parser.getString());
					while (parser.hasNext()) {
						e = parser.next();
						if (e.equals(Event.KEY_NAME) && "x".equals(parser.getString())) {
							parser.next();
							String x = parser.getString();
							attrib.put("x", x);
							parser.next();
							parser.next();
							String y = parser.getString();
							attrib.put("y", y);
							marker.setCoordinate(new Coordinate(Double.valueOf(y), Double.valueOf(x)));
							break;
						}
					}
					while (parser.hasNext()) {
						e = parser.next();
						if (e.equals(Event.KEY_NAME) && "score".equals(parser.getString())) {
							parser.next();
							attrib.put("score", parser.getString());
							break;
						}
					}

					if (!updateCenter) {
						latitude = marker.getCoordinate().getLatitude();
						longitude = marker.getCoordinate().getLongitude();
						zoom = 18;
						updateCenter = true;
					}

					marker.setAttributes(attrib);
					locations.getGraphics().add(marker);
				}
			}
			// Refresh the graphics model.
			locations.setRefresh(true);

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

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public int getZoom() {
		return zoom;
	}

	public GraphicsModel getLocations() {
		return locations;
	}

}
