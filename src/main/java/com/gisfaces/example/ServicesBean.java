package com.gisfaces.example;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.gisfaces.model.service.ServiceDirectoryMetadata;
import com.gisfaces.model.service.ServiceDirectoryMetadataBuilder;
import com.gisfaces.utilities.JSFUtilities;

@ManagedBean
@ViewScoped
public class ServicesBean
{
	private String url;
	private ServiceDirectoryMetadata serviceDirectory;

	public ServicesBean()
	{
		super();
		reset();
	}

	public void reset()
	{
		this.url = "http://sampleserver1.arcgisonline.com/ArcGIS/rest/services/PublicSafety";
		this.serviceDirectory = null;
	}

	public void doQueryButtonActionListener(ActionEvent event)
	{
		System.out.println("Begin query action listener.");

		try
		{
			this.serviceDirectory = null;

			ServiceDirectoryMetadataBuilder builder = new ServiceDirectoryMetadataBuilder();
			this.serviceDirectory = builder.build(this.url);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JSFUtilities.addErrorMessage(e.getMessage());
		}

		System.out.println("End query action listener.");
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public ServiceDirectoryMetadata getServiceDirectory()
	{
		return serviceDirectory;
	}

	public void setServiceDirectory(ServiceDirectoryMetadata serviceDirectory)
	{
		this.serviceDirectory = serviceDirectory;
	}
}
