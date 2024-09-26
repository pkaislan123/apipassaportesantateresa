package br.com.kapplanapi.utils;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

		
	@NotBlank
	@Size(max = 20)
	private String identificacao;
	
	
	@NotBlank
	@Size(max = 100)
	@Email
	private String email;
    
 
  
	
}
