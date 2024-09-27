package br.com.kapplanapi.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kapplanapi.models.Cliente;

import br.com.kapplanapi.models.Fatura;
import br.com.kapplanapi.models.Fornecedor;
import br.com.kapplanapi.models.PaymentResponse;
import br.com.kapplanapi.models.VendaCupom;

import br.com.kapplanapi.repository.ClienteRepository;
import br.com.kapplanapi.repository.CupomRepository;
import br.com.kapplanapi.repository.FaturaRepository;
import br.com.kapplanapi.repository.VendaCupomRepository;

import freemarker.template.Configuration;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.repo.InputStreamResource;

@CrossOrigin
@RestController
@RequestMapping("v1/")
public class VendaCupomController {


	@Autowired
	VendaCupomRepository vendaCupomRepository;

	

	@CrossOrigin
	@GetMapping("protected/vendacupom/listarpodcliente/{id_cliente}")
	public List<VendaCupom>  listarVendaCuponsPorCliente(@PathVariable int id_cliente) {
        return vendaCupomRepository.buscarPorCliente(id_cliente);
	}

	
}
