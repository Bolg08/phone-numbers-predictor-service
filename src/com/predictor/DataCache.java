package com.predictor;

import java.util.Map;

public class DataCache {
	
	private String namesUri;
	private String phonesUri;
	private WebClient webClient;
	private ILogger logger;

	private Map<String, String> namesMap;
	private Map<String, String> phonesMap;

	public DataCache( String dataSourcseUrl, String namesAddress, String phonesAddress,ILogger logger) {
		this.namesUri = dataSourcseUrl + namesAddress;
		this.phonesUri = dataSourcseUrl + phonesAddress;
		this.logger = logger;

		this.webClient = new WebClient(logger);
	}

	public Map<String, String> getNamesMap() {
		if (namesMap == null) {
			refreshNamesMapFromSource();
		}
		return namesMap;
	}

	public Map<String, String> getPhonesMap() {
		if (phonesMap == null)
			refreshPhonesMapFromSource();
		return phonesMap;
	}

	private void refreshNamesMapFromSource() {
		logger.log("update names map from " + namesUri);
		var names = webClient.get(namesUri);
		try {
			namesMap = JsonUtils.jsonToMap(names);
		} catch (Exception ex) {
			logger.log("names map updating error");
		}
	}

	private void refreshPhonesMapFromSource() {
		logger.log("update phones map from " + phonesUri);
		var phones = webClient.get(phonesUri);
		try {
			phonesMap = JsonUtils.jsonToMap(phones);
		} catch (Exception ex) {
			logger.log("names map updating error");
		}
	}
	public void refreshDataCache() {
		refreshPhonesMapFromSource();
		refreshNamesMapFromSource();
	}
}
