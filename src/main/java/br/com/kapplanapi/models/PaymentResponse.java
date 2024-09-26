package br.com.kapplanapi.models;

import java.util.List;

import lombok.Data;

@Data
public class PaymentResponse {

	List<results> results;
	paging paging;
	
	@Data
	public class results{
		private String status;
		private String status_detail;
		private String date_approved;
		private String payment_method_id;
		
		
		
	}
	
	@Data
	public class paging{
		private String total;
		private String limit;
	}
	
}

