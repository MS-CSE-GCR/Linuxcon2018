package com.linuxcon.aks;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.linuxcon.aks")
public class ConfigBean {
	private String azureaiback;

	public String getAzureaiback() {
		return azureaiback;
	}

	public void setAzureaiback(String azureaiback) {
		this.azureaiback = azureaiback;
	}
	
	

}
