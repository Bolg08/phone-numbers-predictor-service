package mavenpackage;

import java.util.List;
import java.util.stream.Collectors;


public class CodesMatcher {

	private DataCache dataCache;

	public CodesMatcher(DataCache dataCache)
			throws NullPointerException {

		if (dataCache == null ) {
		
			LogObject.info("CodesMatcher", "CodesMatcher", "maps is null");
			throw new NullPointerException();
		}
		this.dataCache = dataCache;
	}

		public List<PhoneInfo> getSuitableCodes(String namePart) {
		try {
			var namePartStd = namePart.toUpperCase();
			List<PhoneInfo> countries = dataCache.getNamesMap()
					.entrySet()
					.stream()
					.filter(e -> e.getValue().toUpperCase().startsWith(namePartStd))
					.map(i -> new PhoneInfo(i.getKey(), i.getValue(), dataCache.getPhonesMap().get(i.getKey())))
					.collect(Collectors.toList());
			return countries;
		} catch (Exception ex) {
		
			LogObject.info("CodesMatcher", "getSuitableCodes", ex.getMessage());
			return null;
		}

	}
}
