package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class InvoiceController implements Initializable{
	//Text Fields
	@FXML
	private TextField txtCompanyName;
	@FXML
	private TextField txtInvoiceNumber;
	@FXML
	private DatePicker pickerShipDate;
	
	//Table and columns
	@FXML
	private TableView<InvoiceModel> tblNewInvoice;
	@FXML
	private TableColumn<InvoiceModel, String> colProductCode;
	@FXML
	private TableColumn<InvoiceModel, Number> colQuantityOrdered;
	@FXML
	private TableColumn<InvoiceModel, String> colDescription;
	@FXML
	private TableColumn<InvoiceModel, Number> colWeight;
	@FXML
	private TableColumn<InvoiceModel, Number> colPrice;
	@FXML
	private TableColumn<InvoiceModel, Number> colAmount;
	
	// Buttons
	@FXML
	private Button btnAddLine;
	@FXML
	private Button btnSubmit;
	@FXML
	private Button btnRemoveLine;
	
	ObservableList<InvoiceModel> ilist = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Make Table Editable
		tblNewInvoice.setEditable(true);
		
		// Make columns editable, and save edits when they are made
		colProductCode.setEditable(true);
		colProductCode.setCellFactory(TextFieldTableCell.forTableColumn());
		colProductCode.setOnEditCommit(new EventHandler<CellEditEvent<InvoiceModel, String>>(){
			
			@Override
			public void handle(CellEditEvent<InvoiceModel, String> event ) {
				InvoiceModel invoice = event.getRowValue();
				invoice.setInvItemId(event.getNewValue());
			}
		});
		
		colQuantityOrdered.setEditable(true);
		colQuantityOrdered.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		colQuantityOrdered.setOnEditCommit(new EventHandler<CellEditEvent<InvoiceModel, Number>>(){
			
			@Override
			public void handle(CellEditEvent<InvoiceModel, Number> event ) {
				InvoiceModel invoice = event.getRowValue();
				invoice.setInvQuantity(event.getNewValue().doubleValue());
			}
		});
		
		colDescription.setEditable(true);
		colDescription.setCellFactory(TextFieldTableCell.forTableColumn());
		colDescription.setOnEditCommit(new EventHandler<CellEditEvent<InvoiceModel, String>>(){
			
			@Override
			public void handle(CellEditEvent<InvoiceModel, String> event ) {
				InvoiceModel invoice = event.getRowValue();
				invoice.setInvDesc(event.getNewValue());
			}
		});
		
		colWeight.setEditable(true);
		colWeight.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		colWeight.setOnEditCommit(new EventHandler<CellEditEvent<InvoiceModel, Number>>(){
			
			@Override
			public void handle(CellEditEvent<InvoiceModel, Number> event ) {
				InvoiceModel invoice = event.getRowValue();
				invoice.setInvWeight(event.getNewValue().doubleValue());
			}
		});
		
		colPrice.setEditable(true);
		colPrice.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		colPrice.setOnEditCommit(new EventHandler<CellEditEvent<InvoiceModel, Number>>(){
			
			@Override
			public void handle(CellEditEvent<InvoiceModel, Number> event ) {
				InvoiceModel invoice = event.getRowValue();
				invoice.setInvPrice(event.getNewValue().doubleValue());
			}
		});
		
		colAmount.setEditable(true);
		colAmount.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		colAmount.setOnEditCommit(new EventHandler<CellEditEvent<InvoiceModel, Number>>(){
			
			@Override
			public void handle(CellEditEvent<InvoiceModel, Number> event ) {
				InvoiceModel invoice = event.getRowValue();
				invoice.setInvCost(event.getNewValue().doubleValue());
			}
		});
		
		String compName = "", productCode = "Change Me", Description = "Change Me", shipDate = "";
		Integer invoiceNumber = 11111;
		Double quantityOrdered = 1.0, weight = 1.0, price = 1.0, amount = 1.0;
		
		ilist = FXCollections.observableArrayList(new InvoiceModel(compName, shipDate, productCode, Description, invoiceNumber, quantityOrdered, weight, price, amount));
		colProductCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvItemId()));
		colQuantityOrdered.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvQuantity()));
		colDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvDesc()));
		colWeight.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvWeight()));
		colPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvPrice()));
		colAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvCost()));
		
		tblNewInvoice.setItems(ilist);
	}
	public void btnAddLineClick(ActionEvent event) {
		InvoiceModel newline = new InvoiceModel(null, null, "Change Me", "Change Me", null, 1.0, 1.0, 1.0, 1.0);
		tblNewInvoice.getItems().add(newline);
	}
	
	public void btnRemoveLineClick(ActionEvent event) {
		tblNewInvoice.getItems().removeAll(tblNewInvoice.getSelectionModel().getSelectedItem());
	}
	
	public void btnSubmitInvoiceClick(ActionEvent event) {
		InvoiceModel invoice = new InvoiceModel();
		String compName;
		Integer invNumber;
		LocalDate shipDate;
		
		compName = txtCompanyName.getText();
		invNumber = Integer.parseInt(txtInvoiceNumber.getText());
		shipDate = pickerShipDate.getValue();
		
		List <List<String>> arrList = new ArrayList<>();
	
		for (int i = 0; i <tblNewInvoice.getItems().size(); i++) {
			invoice = tblNewInvoice.getItems().get(i);
			arrList.add(new ArrayList<>());
			arrList.get(i).add(compName);
			arrList.get(i).add(invNumber.toString());
			arrList.get(i).add(shipDate.toString());
			arrList.get(i).add(invoice.getInvItemId());
			arrList.get(i).add(invoice.getInvQuantity().toString());
			arrList.get(i).add(invoice.getInvDesc());
			arrList.get(i).add(invoice.getInvWeight().toString());
			arrList.get(i).add(invoice.getInvPrice().toString());
			arrList.get(i).add(invoice.getInvCost().toString());
			arrList.get(i).add(invoice.getInvQuantity().toString());
		}
		
		try {
			Connection con = DBConnector.getConnection();

			for (int i = 0; i < arrList.size(); i++) {
				PreparedStatement stmt = con.prepareStatement( "insert into keyinvoices(COMPANY_RECIEVED, INVOICE_NUMBER, SHIP_DATE, "
						+ "ITEM_ID, QUANTITY, ITEM_DESCRIPTION, ITEM_WEIGHT, ITEM_PRICE, INVOICE_COST, inv_amount) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?)");
				for (int j = 0; j < arrList.get(i).size(); j++) {
					switch (j) {
						case 0: stmt.setString(j+1, arrList.get(i).get(j));
								break;
						case 1: stmt.setInt(j+1, Integer.parseInt(arrList.get(i).get(j)));
								break;
						case 2: stmt.setString(j+1, arrList.get(i).get(j));
								break;
						case 3: stmt.setString(j+1, arrList.get(i).get(j));
								break;
						case 4: stmt.setDouble(j+1, Double.parseDouble(arrList.get(i).get(j)));
								break;
						case 5: stmt.setString(j+1, arrList.get(i).get(j));
								break;
						case 6: stmt.setDouble(j+1, Double.parseDouble(arrList.get(i).get(j)));
								break;
						case 7: stmt.setDouble(j+1, Double.parseDouble(arrList.get(i).get(j)));
								break;
						case 8: stmt.setDouble(j+1, Double.parseDouble(arrList.get(i).get(j)));
								break;
						case 9: stmt.setDouble(j+1, Double.parseDouble(arrList.get(i).get(j)));
								break;
					}
				}
				stmt.execute();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) btnSubmit.getScene().getWindow();
		stage.close();
	}
}
