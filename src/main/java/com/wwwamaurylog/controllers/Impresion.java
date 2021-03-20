package com.wwwamaurylog.controllers;

import java.net.Socket;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.ZebraPrintException;
import fr.w3blog.zpl.model.ZebraUtils;
import fr.w3blog.zpl.model.element.ZebraBarCode39;
import fr.w3blog.zpl.model.element.ZebraBarCode;
import fr.w3blog.zpl.model.element.ZebraNativeZpl;
import fr.w3blog.zpl.model.element.ZebraText;

@RestController
public class Impresion {

	@GetMapping("/imprimir")
	public ResponseEntity<PrinterStatus> Impresora()
	{
		PrinterStatus returnValue = new PrinterStatus();
		String res = "";
		
		try
		{
			Socket clientSocket = new Socket("192.168.1.64", 9100);
			
			if (clientSocket.isConnected()) {
				returnValue.setMessage("Impresora Conectada Correctamente");
				returnValue.setStatusCode(HttpStatus.OK);
			}
		}
		catch(Exception ex)
		{
			returnValue.setMessage(ex.getMessage());
			returnValue.setStatusCode(HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<PrinterStatus>(returnValue, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/sendTicket")
	public String sendTicket() {
		String resultado;
		
		ZebraLabel zebraLabel = new ZebraLabel(400, 500);
		zebraLabel.setDefaultZebraFont(ZebraFont.ZEBRA_ZERO);

		zebraLabel.addElement(new ZebraText(10, 84, "Product:", 14));
		zebraLabel.addElement(new ZebraText(395, 85, "Camera", 14));

		zebraLabel.addElement(new ZebraText(10, 161, "CA201212AA", 14));
		
		//Add Code Bar 39
		zebraLabel.addElement(new ZebraBarCode39(10, 297, "CA201212AA", 118, 2, 2));

		zebraLabel.addElement(new ZebraText(10, 365, "Qté:", 11));
		zebraLabel.addElement(new ZebraText(180, 365, "3", 11));
		zebraLabel.addElement(new ZebraText(317, 365, "QA", 11));

		zebraLabel.addElement(new ZebraText(10, 520, "Ref log:", 11));
		zebraLabel.addElement(new ZebraText(180, 520, "0035", 11));
		zebraLabel.addElement(new ZebraText(10, 596, "Ref client:", 11));
		zebraLabel.addElement(new ZebraText(180, 599, "1234", 11));
		
		zebraLabel.getZplCode();

		try {
			ZebraUtils.printZpl(zebraLabel, "192.168.1.64", 9100);
			resultado = "{ 'message': 'Etiqueta Impresa' }";
		} catch (ZebraPrintException e) {
			resultado = "{ 'message': '" + e.getMessage() + "' }";
		}
		
		return resultado;
	}
	
}
@JsonPropertyOrder({"statusCode", "message"})
class PrinterStatus {
	
	private HttpStatus statusCode;
	private String message;
		
	public PrinterStatus() {}
	public PrinterStatus(HttpStatus statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
