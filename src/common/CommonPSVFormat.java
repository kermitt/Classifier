package common;

import java.util.HashMap;
import java.util.Map;

public class CommonPSVFormat {
	public Map<String, Map<String, Seen>> router = new HashMap<>();

	public Map < String, Seen > person_id =  new HashMap<>();
	public Map < String, Seen > gender_code =  new HashMap<>();
	public Map < String, Seen > ccs_category_id =  new HashMap<>();
	public Map < String, Seen > ndc_code =  new HashMap<>();
	public Map < String, Seen > drug_label_name =  new HashMap<>();
	public Map < String, Seen > drug_group_description =  new HashMap<>();
	public Map < String, Seen > days_supply_count =  new HashMap<>();
	public Map < String, Seen > filled_date = new HashMap<>();
	public Map < String, Seen > patient_paid_amount = new HashMap<>();
	public Map < String, Seen > ingredient_cost_paid_amount = new HashMap<>();
	public Map < String, Seen > after_cure = new HashMap<>();
	public Map < String, Seen > during_treatment = new HashMap<>();
	public Map < String, Seen > before_diagnosis = new HashMap<>();
	
	public CommonPSVFormat() {
		router.put("person_id",person_id);
		router.put("gender_code",gender_code);
		router.put("ccs_category_id",ccs_category_id);
		router.put("ndc_code",ndc_code);
		router.put("drug_label_name",drug_label_name);
		router.put("drug_group_description",drug_group_description);
		router.put("days_supply_count",days_supply_count);
		router.put("filled_date",filled_date);
		router.put("patient_paid_amount",patient_paid_amount);
		router.put("ingredient_cost_paid_amount",ingredient_cost_paid_amount);
		router.put("after_cure",after_cure);
		router.put("during_treatment",during_treatment);
		router.put("before_diagnosis",before_diagnosis);
	}
	
	public void route(String key, String value) {
		value = value.trim();
		value = value.toLowerCase();
		if ( router.get(key).containsKey(value)) {
			router.get(key).get(value).seen++;
		} else {
			router.get(key).put(value, new Seen());
		}
	}
}
