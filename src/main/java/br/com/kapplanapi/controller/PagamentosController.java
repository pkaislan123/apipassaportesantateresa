package br.com.kapplanapi.controller;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodsRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import br.com.kapplanapi.models.Cliente;
import br.com.kapplanapi.models.Fatura;
import br.com.kapplanapi.models.PaymentResponse;
import br.com.kapplanapi.models.PaymentResponse.results;
import br.com.kapplanapi.repository.ClienteRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.kapplanapi.models.PaymentUpdate;



@CrossOrigin
@RestController
@RequestMapping("v1/")
public class PagamentosController {

	@Autowired
	ClienteRepository clienteRepository;

	public Fatura criarUrlPagamento(Fatura fatura) {

		MercadoPagoConfig.setAccessToken("APP_USR-5774823959891304-092616-eaabb8a2d36713d0d79177830bdec3da-12656440");
		// Cria um objeto de preferência
		PreferenceClient client = new PreferenceClient();

		// Cria um item na preferência
		List<PreferenceItemRequest> items = new ArrayList<>();
		PreferenceItemRequest item = PreferenceItemRequest.builder()
				.title("PassaporteSantaTeresa")
				.currencyId("BRL")
				.description("CUPOM DE DESCONTO")
				.categoryId("art")
				.quantity(1)
				.unitPrice(new BigDecimal(fatura.getValor()))
				.build();
		items.add(item);

		Cliente pagador = fatura.getCliente();

		String identificador = "CPF";
		String identificacao = pagador.getCpf();

		IdentificationRequest identification = IdentificationRequest.builder().type(identificador).number(identificacao)
				.build();

		// pagador
		PreferencePayerRequest payer = null;
		payer = PreferencePayerRequest.builder()
				.name(pagador.getNome_completo())
				.surname("")
				.email(pagador.getEmail())
				.identification(identification)
				.build();

		// url que o usuario sera redireciado ao conclir o pagamento
		PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
				.success("https://localhost:3000/faturamento")
				.pending("https://www.seu-site/pending")
				.failure("https://www.seu-site/failure")
				
				.build();

		// referenciaExterna;
		String referencia = "reference0310_" + fatura.getCliente().getId_cliente();

		

		PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
				.excludedPaymentTypes(new ArrayList<>()) // Não excluir nenhum tipo de pagamento
				.excludedPaymentMethods(new ArrayList<>()) // Não excluir nenhum método de pagamento
				.build();

		PreferenceRequest request = PreferenceRequest.builder().items(items).payer(payer)
				.statementDescriptor("Passaporte Santa Teresa")
				.paymentMethods(paymentMethods)
				.externalReference(referencia)
				.expires(false)
				.build();

		try {
			Preference preferencias = client.create(request);

			fatura.setLink_pagamento(preferencias.getInitPoint());

			fatura.setId_preferencia_pagamento(preferencias.getId());

			fatura.setExternal_reference(referencia);

			return fatura;

		} catch (MPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (MPApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@CrossOrigin
	@PostMapping("protected/pagamentos/atualizar")
	public void atualizarInfoPagamento(@RequestParam(value = "topic", required = true) String topic,
			@RequestParam(value = "id", required = true) String id) {

		System.out.println("Topico: " + topic);
		System.out.println("id: " + id);

		if (topic != null && topic.equals("payment")) {

			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url("https://api.mercadopago.com/v1/payments/" + id)
					.get()
					.addHeader("Authorization",
							"Bearer APP_USR-4447456175872936-092519-f6048e61979a7687174e8fbc10e0f94f-12656440")
					.build();

			try {
				Response response = client.newCall(request).execute();
				String result = response.body().string();
				Gson gson = new Gson(); // Or use new GsonBuilder().create();
				PaymentResponse target2 = gson.fromJson(result, PaymentResponse.class);

				System.out.println(target2.toString());

			} catch (SocketTimeoutException f) {
				// TODO Auto-generated catch block
				System.out.println("Sem Conexão com a internet!");

				f.printStackTrace();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

	}

	public results buscarInfoPagamento(String reference, String token) {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://api.mercadopago.com/v1/payments/search?sort=date_created&criteria=desc&external_reference="
						+ reference)
				.get()
				.addHeader("Authorization", "Bearer " + token)
				.build();

		try {
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			Gson gson = new Gson(); // Or use new GsonBuilder().create();
			PaymentResponse target2 = gson.fromJson(result, PaymentResponse.class);

			return target2.getResults().get(0);

		} catch (SocketTimeoutException f) {
			// TODO Auto-generated catch block
			System.out.println("Sem Conexão com a internet!");

			return null;

		} catch (Exception e) {

			return null;

		}

	}


	
	@CrossOrigin
	@PostMapping({ "protected/mp/webhock" })
	public void webhockPagamentos(@RequestBody String dados) {
	 
        System.out.println(dados);


		 ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Desserializa o JSON na classe PaymentUpdate
            PaymentUpdate paymentUpdate = objectMapper.readValue(dados, PaymentUpdate.class);

            // Agora você pode acessar os valores em variáveis separadas
            String action = paymentUpdate.getAction();
            String apiVersion = paymentUpdate.getApiVersion();
            String dataId = paymentUpdate.getData().getId();
            String dateCreated = paymentUpdate.getDateCreated();
            long id = paymentUpdate.getId();
            boolean liveMode = paymentUpdate.isLiveMode();
            String type = paymentUpdate.getType();
            String userId = paymentUpdate.getUserId();

            // Exemplo de saída
            System.out.println("Action: " + action);
            System.out.println("API Version: " + apiVersion);
            System.out.println("Data ID: " + dataId);
            System.out.println("Date Created: " + dateCreated);
            System.out.println("ID: " + id);
            System.out.println("Live Mode: " + liveMode);
            System.out.println("Type: " + type);
            System.out.println("User ID: " + userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}


}
