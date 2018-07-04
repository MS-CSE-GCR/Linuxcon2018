package com.linuxcon.aks;

import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ImageProcessor {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	ConfigBean configBean;

	// Process image provided by URL trough online webservice
	// Sample URL: https://www.backpacker.com/.image/c_fit%2Ccs_srgb%2Cfl_progressive%2Ch_300%2Cq_auto:good%2Cw_1022/MTQ0OTE0MDQ2MDg5ODMyMTY1/rei-rhyolite-hardshell.jpg

	public String processImageWebservice(ImageFile imageFile) {
		System.out.println("processing..." + imageFile.getFileURL());

		String url = "https://southcentralus.api.cognitive.microsoft.com/customvision/v2.0/Prediction/0a8f3cb9-1703-48c2-9a72-847ebf3d6919/url?iterationId=55de9c98-03a8-4836-aef8-aea86447de52";
		String predictionKey = "e61b2043b5c44671a9e0a2ad93fe162a";
		String contentType = "application/json";
		String image = "{\"Url\": \"" + imageFile.getFileURL() + "\"}";
		System.out.println(image);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Prediction-Key", predictionKey);
		requestHeaders.add("Content-Type", contentType);

		HttpEntity<String> requestEntity = new HttpEntity<String>(image, requestHeaders);

		String result = restTemplate.postForObject(url, requestEntity, String.class);

		// For print JSON format
		ObjectMapper mapper = new ObjectMapper();
		try {
			Object json = mapper.readValue(result, Object.class);
			result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// Access process image microservice: azureaiback
	public String processImage(ImageFile imageFile) {
		System.out.println("processing..." + imageFile.getFileURL());

		String url = "http://" + configBean.getAzureaiback() + "/url";
		String image = "{\"url\": \"" + imageFile.getFileURL() + "\"}";
		//System.out.println(url + image);

		HttpHeaders requestHeaders = new HttpHeaders();
		HttpEntity<String> requestEntity = new HttpEntity<String>(image, requestHeaders);

		String result = restTemplate.postForObject(url, requestEntity, String.class);

		// For print JSON format
		ObjectMapper mapper = new ObjectMapper();
		try {
			Object json = mapper.readValue(result, Object.class);
			result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
