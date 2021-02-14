package com.predictor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CodesMatcher {

	private ILogger logger;
	private DataCache dataCache;

	public CodesMatcher(DataCache dataCache, ILogger logger)
			throws NullPointerException {

		this.logger = logger;
		if (dataCache == null ) {
			logger.log("maps is null");
			throw new NullPointerException();
		}
		this.dataCache = dataCache;
	}

		public List<PhoneInfo> getSuitableCodes(String namePart) {
		try {
			var  namePartStd = namePart.toUpperCase();
			List<PhoneInfo> countries = dataCache.getNamesMap()
					.entrySet()
					.stream()
					.filter(e -> e.getValue().toUpperCase().startsWith(namePartStd))
					.map(i -> new PhoneInfo(i.getKey(), i.getValue(), dataCache.getPhonesMap().get(i.getKey())))
					.collect(Collectors.toList());
			return countries;
		} catch (Exception ex) {
			logger.log(ex.getMessage());
			return null;
		}

	}
}
