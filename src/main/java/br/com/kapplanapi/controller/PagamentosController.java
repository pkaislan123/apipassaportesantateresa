package br.com.kapplanapi.controller;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import br.com.kapplanapi.models.VendaCupom;
import br.com.kapplanapi.models.Fatura;
import br.com.kapplanapi.models.PaymentResponse;
import br.com.kapplanapi.repository.ClienteRepository;
import br.com.kapplanapi.repository.PagamentoRepository;
import br.com.kapplanapi.repository.FaturaRepository;
import br.com.kapplanapi.repository.VendaCupomRepository;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.kapplanapi.models.PaymentUpdate;
import br.com.kapplanapi.models.Pagamento;

@CrossOrigin
@RestController
@RequestMapping("v1/")
public class PagamentosController {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	PagamentoRepository pagamentoRepository;

	@Autowired
	FaturaRepository faturaRepository;

	
	@Autowired
	VendaCupomRepository vendaCupomRepository;

	

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
				.success("https://passaporte-chi.vercel.app/meus-cupons")
				.pending("https://passaporte-chi.vercel.app/meus-cupons")
				.failure("https://passaporte-chi.vercel.app/meus-cupons")

				.build();

		// referenciaExterna;
		String referencia = "reference" + fatura.getCliente().getId_cliente() + "" + LocalDateTime.now();

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
							"Bearer APP_USR-5774823959891304-092616-eaabb8a2d36713d0d79177830bdec3da-12656440")
					.build();

			try {
				Response response = client.newCall(request).execute();
				String result = response.body().string();
				Gson gson = new Gson(); // Or use new GsonBuilder().create();
				PaymentResponse target2 = gson.fromJson(result, PaymentResponse.class);

				System.out.println(target2.toString());

				if (topic.equals("payment") && target2.getStatus().equals("approved")) {
					// verificar se ja existe um pagamento com esse id
					Optional<Pagamento> possivel_pagamento = pagamentoRepository
							.buscarPorIdPagamentoMercadoPago(id);

					if (possivel_pagamento.isPresent()) {
						Pagamento pagamento = possivel_pagamento.get();
						pagamento.setPayment_status(target2.getStatus());
						pagamento.setPayment_method_id(target2.getPayment_method_id());
						pagamento.setPayment_status_detail(target2.getStatus_detail());
						pagamento.setExternal_reference(target2.getExternal_reference());
						pagamento.setPayment_type_id(target2.getPayment_type_id());

						pagamentoRepository.save(pagamento);

					} else {
						System.out.println("Novo pagamento a ser salvo!");
						Pagamento pagamento = new Pagamento();
						pagamento.setPayment_id(id);
						pagamento.setPayment_status(target2.getStatus());
						pagamento.setPayment_method_id(target2.getPayment_method_id());
						pagamento.setPayment_status_detail(target2.getStatus_detail());
						pagamento.setExternal_reference(target2.getExternal_reference());
						pagamento.setPayment_type_id(target2.getPayment_type_id());

						pagamento = pagamentoRepository.saveAndFlush(pagamento);

						// criar a venda do cupom
						// primeiro achar a fatura pelo external reference
						Optional<Fatura> possivel_fatura = faturaRepository
								.buscarPorExternalReference(target2.getExternal_reference());

						if (possivel_fatura.isPresent()) {
							System.out.println("A fatura existe");

							Fatura fatura = possivel_fatura.get();

							if(target2.getStatus().equals("approved")){
								//aprovado
								fatura.setStatus_fatura(1);
								fatura.setData_pagamento(LocalDateTime.now());

								fatura = faturaRepository.saveAndFlush(fatura);

								//criar a VendaCupom
								VendaCupom venda = new VendaCupom();
								venda.setData_cadastro(LocalDateTime.now());
								venda.setData_pagamento(LocalDateTime.now());
								venda.setId_cliente(fatura.getCliente().getId_cliente());
								venda.setData_validade( LocalDateTime.now().plusDays( fatura.getCupom().getValidade() ));
								venda.setStatus(1);
								venda.setCodigo_cupom(result);
								venda.setPagamento(pagamento);
								venda.setCodigo_cupom(UUID.randomUUID().toString());
								venda.setFatura(fatura);
								vendaCupomRepository.save(venda);

								System.out.println("Venda criada");


							}
						

						}

					}
				}

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
