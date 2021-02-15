package com.example.demo;

import java.util.Map;

public class DataCache {
	
	private String namesUri;
	private String phonesUri;
	private WebClient webClient;


	private Map<String, String> namesMap;
	private Map<String, String> phonesMap;
	public DataCache() {
		
	}
	public DataCache( String dataSourcseUrl, String namesAddress, String phonesAddress) {
		this.namesUri = dataSourcseUrl + namesAddress;
		this.phonesUri = dataSourcseUrl + phonesAddress;
	

		this.webClient = new WebClient();
	}
	
	public Map<String, String> getNamesMap() {
		if (namesMap == null) {
			refreshNamesMapFromSource();
		}
		return namesMap;
	}
	public void setNamesMap( Map<String, String> namesMap) {
		this.namesMap = namesMap;
	}
	public Map<String, String> getPhonesMap() {
		if (phonesMap == null)
			refreshPhonesMapFromSource();
		return phonesMap;
	}
	public void setPhonesMap( Map<String, String> phonesMap) {
		this.phonesMap = phonesMap;
	}
	private void refreshNamesMapFromSource() {
		LogObject.info("DataCache", "refreshNamesMapFromSource", "update names map from " + namesUri);
		
		var names = webClient.get(namesUri);
		try {
			namesMap = JsonUtils.jsonToMap(names);
		} catch (Exception ex) {
			LogObject.info("DataCache", "refreshNamesMapFromSource", "names map updating error");
		
		}
	}

	private void refreshPhonesMapFromSource() {
		
		LogObject.info("DataCache", "refreshPhonesMapFromSource","update phones map from " + phonesUri);
		var phones = webClient.get(phonesUri);
		try {
			phonesMap = JsonUtils.jsonToMap(phones);
		} catch (Exception ex) {
			
			LogObject.info("DataCache", "refreshPhonesMapFromSource","names map updating error");
		}
	}
	public void refreshDataCache() {
		refreshPhonesMapFromSource();
		refreshNamesMapFromSource();
	}
}
