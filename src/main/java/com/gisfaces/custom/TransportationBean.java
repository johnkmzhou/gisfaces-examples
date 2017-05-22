package com.gisfaces.custom;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.gisfaces.model.map.Background;
import com.gisfaces.model.service.ServiceLayerMetadata;
import com.gisfaces.model.service.ServiceMetadataBuilder;
import com.gisfaces.utilities.json.JSONException;

@Named
@ViewScoped
public class TransportationBean implements Serializable {
	private static final long serialVersionUID = 1862019119442479815L;
	private static Logger log = Logger.getLogger(TransportationBean.class.getName());
	private String url = "http://geodata.hawaii.gov/arcgis/rest/services/Transportation/MapServer";
	private String background = "streets";
	private String layer = "0";
	private List<SelectItem> layers;

	public TransportationBean() {
		try {
			// creates a list of layers for the drop down menu
			List<ServiceLayerMetadata> services = new ServiceMetadataBuilder().build(url, "Transportation", "MapServer")
					.getLayers();
			layers = new ArrayList<SelectItem>(services.size());
			for (ServiceLayerMetadata l : services) {
				layers.add(new SelectItem(l.getId(), l.getName()));
			}
		} catch (IOException | JSONException e) {
			log.error("Exception processing service layer metadata.", e);
		}
	}

	public String getUrl() {
		return url;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public List<SelectItem> getBackgrounds() {
		return Background.getSelectItems();
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public List<SelectItem> getLayers() {
		return layers;
	}

}
