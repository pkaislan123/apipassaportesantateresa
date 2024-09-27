package br.com.kapplanapi.models;


import lombok.Data;

@Data
public class PaymentResponse {

	
	
		
		private String external_reference;
		
		private String payment_method_id;
		private String payment_type_id;
		private String status;
		private String status_detail;

	
}

