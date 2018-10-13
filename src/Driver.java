import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Driver extends Application{

	
	private TableView table = new TableView();
	private static ObservableList<Book> library;
	
	public static void main(String[] args) {
		
		Application.launch();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		File libraryFile = new File("library.bin");

			try {
			library = getDataFromFile(libraryFile);
			}
			catch (Exception e)
			{
				library = FXCollections.observableArrayList();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("No Media Library");
				alert.setHeaderText(null);
				alert.setContentText("Could not read in existing library, starting a new one");
				alert.showAndWait();
			}
		
		//library.add(new Core("Test","test"));
		//TableView Setup
		TableColumn nameCol = new TableColumn("Name");
		nameCol.setCellValueFactory( new PropertyValueFactory<Book, String>("name"));
		nameCol.setMinWidth(200);
		TableColumn publisherCol = new TableColumn("Publisher");
		publisherCol.setMinWidth(75);
		publisherCol.setCellValueFactory( new PropertyValueFactory<Book, String>("publisher"));
		TableColumn categoryCol = new TableColumn("Category");
		categoryCol.setMinWidth(75);
		categoryCol.setCellValueFactory( new PropertyValueFactory<Book,  String>("category"));
		TableColumn typeCol = new TableColumn("Type");
		typeCol.setMinWidth(75);
		typeCol.setCellValueFactory( new PropertyValueFactory<Book, String>("type"));
		TableColumn campaignSettingCol = new TableColumn("Setting");
		campaignSettingCol.setCellValueFactory( new PropertyValueFactory<Book, String>("setting"));
		campaignSettingCol.setMinWidth(75);
		TableColumn lvlCol = new TableColumn("Levels");
		TableColumn startLvlCol = new TableColumn("Start");
		startLvlCol.setCellValueFactory( new PropertyValueFactory<Book, Integer>("startLvl"));
		TableColumn endLvlCol = new TableColumn("End");
		endLvlCol.setCellValueFactory( new PropertyValueFactory<Book, Integer>("endLvl"));
		lvlCol.getColumns().addAll(startLvlCol, endLvlCol);
		
		table.setRowFactory( e -> {
			TableRow row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Book rowData = (Book)row.getItem();
		            openPDF(rowData.getPath());
		        }
		});
		    return row;
		});

		table.setItems(library);
		table.setPrefSize(800, 590);
		table.getColumns().addAll(nameCol, publisherCol, categoryCol, typeCol, campaignSettingCol, lvlCol);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		
		//Controls Setup
		
		//Add New Item
		GridPane addItem = new GridPane();
		addItem.setPrefHeight(100);
		addItem.setPrefSize(260, 100);
		addItem.setHgap(10);
		addItem.setVgap(5);
		addItem.setPadding(new Insets(10,10,10,10));
		addItem.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
		
		ObservableList<String> categories = FXCollections.observableArrayList("Core","Adventure","Rules","Setting");
		
		Label itemName = new Label("Name: ");
		TextField itemNameTF = new TextField();
		Label itemPublisher = new Label("Publisher: ");
		TextField itemPublisherTF = new TextField();
		Label itemType = new Label("Type: ");
		Label itemCategory = new Label("Category: ");
		ComboBox itemCategoryTF = new ComboBox(categories);
		TextField itemTypeTF = new TextField();
		Label itemSetting = new Label("Setting: ");
		TextField itemSettingTF = new TextField();
		Label itemStartLvl = new Label("Start LVL:");
		TextField itemStartLvlTF = new TextField();
		Label itemEndLvl = new Label("End LVL: ");
		TextField itemEndLvlTF = new TextField();
		Label itemPath = new Label("Filepath: ");
		TextField itemPathTF = new TextField();
		Button addItemB = new Button("Add");
		Label info = new Label("");
		
		
		itemCategoryTF.setOnAction(e -> {
			try {
				String category = itemCategoryTF.getValue().toString();
				switch(category) 
				{
					case "Core": 
					{
						itemName.setText("* Name: ");
						itemPublisher.setText("* Publisher: ");
						itemType.setText("Type: ");
						itemSetting.setText("Setting: ");
						itemStartLvl.setText("Start LVL");
						itemEndLvl.setText("End LVL");
						info.setText("* - required field");
						
						itemStartLvlTF.setDisable(true);
						itemEndLvlTF.setDisable(true);
						itemSettingTF.setDisable(true);
						itemTypeTF.setDisable(true);
						
						break;
	
					}
					case "Adventure": 
					{
						itemName.setText("* Name: ");
						itemPublisher.setText("* Publisher: ");
						itemType.setText("* Type: ");
						itemSetting.setText("* Setting: ");
						itemStartLvl.setText("* Start LVL");
						itemEndLvl.setText("* End LVL");
						info.setText("* - required field");
						
						itemStartLvlTF.setDisable(false);
						itemEndLvlTF.setDisable(false);
						itemSettingTF.setDisable(false);
						itemTypeTF.setDisable(false);
						
						break;
					}
					case "Rules": 
					{
						itemName.setText("* Name: ");
						itemPublisher.setText("* Publisher: ");
						itemType.setText("* Type: ");
						itemSetting.setText("* Setting: ");
						itemStartLvl.setText("Start LVL");
						itemEndLvl.setText("End LVL");
						info.setText("* - required field");	
						
						itemStartLvlTF.setDisable(true);
						itemEndLvlTF.setDisable(true);
						itemSettingTF.setDisable(false);
						itemTypeTF.setDisable(false);
						
						break;
					}
					case "Setting":
					{
						itemName.setText("* Name: ");
						itemPublisher.setText("* Publisher: ");
						itemType.setText("* Type: ");
						itemSetting.setText("* Setting: ");
						itemStartLvl.setText("Start LVL");
						itemEndLvl.setText("End LVL");
						info.setText("* - required field");	
						
						itemStartLvlTF.setDisable(true);
						itemEndLvlTF.setDisable(true);
						itemSettingTF.setDisable(false);
						itemTypeTF.setDisable(false);

						
						break;
					}
				}
			}
			catch (NullPointerException m)
			{
				System.out.println(m.getMessage());
			}
			
		});
		
		
		
		addItemB.setOnAction(e -> {
			String name;
			String publisher;
			String type;
			String setting;
			String path;
			int startLvl;
			int endLvl;
			//check category to determine fields that are needed
			try {
				String category = itemCategoryTF.getValue().toString();
				switch(category) 
				{
					case "Core": 
					{	try {
						if(!itemNameTF.getText().equals("") && !itemPublisherTF.getText().equals(""))
						{
							name = itemNameTF.getText();
							publisher = itemPublisherTF.getText();
							path = itemPathTF.getText();
							 
							for(Book book : library)
							{
								if(book.getName().equalsIgnoreCase(name))
								{
									throw new DuplicateMediaItemException("An item with this name already exists");
								}
							}
							
							createNew(category, name, publisher, path, library);
						}
						else
						{
							System.out.println("please fill in all of the required fields");
						}
						break;
						}
						catch(DuplicateMediaItemException m)
						{
							System.out.println(m.getMessage());
						}
						catch(Exception m)
						{
							System.out.println(m.getMessage());
						}
					
					}
					case "Adventure": 
					{
						try {
						if(!itemNameTF.getText().equals("") && !itemPublisherTF.getText().equals("")&& !itemTypeTF.getText().equals("") && !itemSettingTF.getText().equals("") 
								&& !itemStartLvlTF.getText().equals("") && !itemEndLvlTF.getText().equals(""))
						{
							name = itemNameTF.getText();
							publisher = itemPublisherTF.getText();
							path = itemPathTF.getText();
							type = itemTypeTF.getText();
							setting = itemSettingTF.getText();
							startLvl = Integer.parseInt(itemStartLvlTF.getText());
							endLvl = Integer.parseInt(itemEndLvlTF.getText());
							
							for(Book book : library)
							{
								if(book.getName().equalsIgnoreCase(name))
								{
									throw new DuplicateMediaItemException("An item with this name already exists");
								}

							}
								createNew(category, name, publisher, path, type, setting, startLvl, endLvl, library);
							}
												
						else
						{
							System.out.println("please fill in all of the required fields");
						}
						break;
						
						}
						catch(DuplicateMediaItemException m)
						{
							System.out.println(m.getMessage());
						}
						catch(Exception m)
						{
							System.out.println(m.getMessage());
						}
						
					}
					case "Rules": 
					{
						try {
							if(!itemNameTF.getText().equals("") && !itemPublisherTF.getText().equals("")&& !itemTypeTF.getText().equals("") && !itemSettingTF.getText().equals("") )
							{
								name = itemNameTF.getText();
								publisher = itemPublisherTF.getText();
								path = itemPathTF.getText();
								type = itemTypeTF.getText();
								setting = itemSettingTF.getText();
								
								for(Book book : library)
								{
									if(book.getName().equalsIgnoreCase(name))
									{
										throw new DuplicateMediaItemException("An item with this name already exists");
									}

								}
									createNew(category, name, publisher, path, type, setting, library);
								}

							else
							{
								System.out.println("please fill in all of the required fields");
							}
							break;
							
							}
							catch(DuplicateMediaItemException m)
							{
								System.out.println(m.getMessage());
							}
							catch(Exception m)
							{
								System.out.println(m.getMessage());
							}
					}
					case "Setting":
					{
						try {
							if(!itemNameTF.getText().equals("") && !itemPublisherTF.getText().equals("")&& !itemTypeTF.getText().equals("") && !itemSettingTF.getText().equals("") )
							{
								
								name = itemNameTF.getText();
								publisher = itemPublisherTF.getText();
								path = itemPathTF.getText();
								type = itemTypeTF.getText();
								setting = itemSettingTF.getText();
								for(Book book : library)
								{
									if(book.getName().equalsIgnoreCase(name))
									{
										throw new DuplicateMediaItemException("An item with this name already exists");
									}

								}
									createNew(category, name, publisher, path, type, setting, library);
							}

							else
							{
								System.out.println("please fill in all of the required fields");
							}
							break;
							
							}
							catch(DuplicateMediaItemException m)
							{
								System.out.println(m.getMessage());
							}
							catch(Exception m)
							{
								System.out.println(m.getMessage());
							}
						
						
					}
				}
			}
			catch (NullPointerException m)
			{
				System.out.println(m.getMessage());
			}
			
			
			//refresh TableView
			
			
			
			
		});
		
		addItem.add(itemName, 0, 0);
		addItem.add(itemNameTF, 1, 0);
		addItem.add(itemPublisher, 0, 1);
		addItem.add(itemPublisherTF, 1, 1);
		addItem.add(itemCategory, 0, 2);
		addItem.add(itemCategoryTF, 1, 2);
		addItem.add(itemPath, 0, 3);
		addItem.add(itemPathTF, 1, 3);
		addItem.add(itemType, 0, 4);
		addItem.add(itemTypeTF, 1, 4);
		addItem.add(itemSetting, 0, 5);
		addItem.add(itemSettingTF, 1, 5);
		addItem.add(itemStartLvl, 0, 6);
		addItem.add(itemStartLvlTF, 1, 6);
		addItem.add(itemEndLvl, 0, 7);
		addItem.add(itemEndLvlTF, 1, 7);
		addItem.add(addItemB, 0, 8);
		addItem.add(info, 1, 8);
		
		//remove Item
		Button removeB = new Button("Remove");
		removeB.setOnAction(e -> {
			try {
			Book selectedBook = (Book) table.getSelectionModel().getSelectedItem();
			removeItem(selectedBook.getName(), library);
			
			}
			catch(NullPointerException m) {
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Error: "+m.getClass());
				error.setHeaderText(null);
				error.setContentText("Please Select an Item from the list on the left and try again.");
				error.showAndWait();
			}
		});
		
		Button saveB = new Button("Save");
		saveB.setOnAction(e ->{
			writeDataFile(libraryFile, library);
		});
		
		
		//update controls
		
		ObservableList<String> updateable = FXCollections.observableArrayList("Name","Publisher","Type","Setting", "Start LVL", "End LVL", "Filepath");
		
		GridPane updateItem = new GridPane();
		//updateItem.setPrefHeight(590);
		updateItem.setPrefSize(275, 100);
		updateItem.setPadding(new Insets(10,10,10,10));
		updateItem.setHgap(10);
		updateItem.setVgap(5);
		updateItem.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
		
		Label updateField = new Label("Update: ");
		ComboBox updateFieldCB = new ComboBox(updateable);
		TextField updateFieldTF = new TextField();
		Button updateFieldB = new Button("Update");
		
		updateFieldB.setOnAction(e -> {
			
			try {	
				
					Book tempBook;
					
					if(table.getSelectionModel().getSelectedItem() instanceof Adventure)
					{
						tempBook = (Adventure) table.getSelectionModel().getSelectedItem();
					}
					else if(table.getSelectionModel().getSelectedItem() instanceof Core)
					{
						tempBook = (Core) table.getSelectionModel().getSelectedItem();
					}
					else if(table.getSelectionModel().getSelectedItem() instanceof Rules)
					{
						tempBook = (Rules) table.getSelectionModel().getSelectedItem();
					}
					else if(table.getSelectionModel().getSelectedItem() instanceof Setting)
					{
						tempBook = (Setting) table.getSelectionModel().getSelectedItem();
					}
					else
					{
						tempBook = (Book) table.getSelectionModel().getSelectedItem();
					}
				
					if(!updateFieldTF.getText().equals(""))
					{
						String category = updateFieldCB.getValue().toString();
						
						switch(category)
						{
						case "Name":
						{
							tempBook.setName(updateFieldTF.getText());
							break;
						}
						case "Publisher":
						{
							tempBook.setPublisher(updateFieldTF.getText());
							break;
						}
						case "Type":
						{
							tempBook.setType(updateFieldTF.getText());
							break;
						}
						case "Setting":
						{
							tempBook.setSetting(updateFieldTF.getText());
							break;
						}
						case "Start LVL":
						{
							tempBook.setStartLvl(Integer.parseInt(updateFieldTF.getText()));
							break;
						}
						case "End LVL":
						{
							tempBook.setEndLvl(Integer.parseInt(updateFieldTF.getText()));
							break;
						}
						case "Filepath":
						{
							
							String newPath = copyFile(tempBook.getCategory(), updateFieldTF.getText(), tempBook.getName(), tempBook.getPublisher());
							tempBook.setPath(newPath);
							break;
						}
						}
						table.refresh();
					}
					else
					{
						Alert infoAlert = new Alert(AlertType.INFORMATION);
						infoAlert.setTitle("INFO: Missing Data");
						infoAlert.setHeaderText(null);
						infoAlert.setContentText("Please enter the new value.");
						infoAlert.showAndWait();
					}
				}
				catch(NullPointerException m)
				{
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("Error: "+m.getClass());
					error.setHeaderText(null);
					error.setContentText("Please Select an Item from the list on the left and try again.");
					error.showAndWait();
				}
				catch(Exception m)
				{
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("Error: "+m.getClass());
					error.setHeaderText(null);
					error.setContentText("Error. see heading for type and report to Developer");
					error.showAndWait();	
				}
			
		});
		
		updateItem.add(updateField, 0, 0);
		updateItem.add(updateFieldCB, 0, 1);
		updateItem.add(updateFieldTF, 1, 1);
		updateItem.add(updateFieldB, 0, 2);
		
		//Scene Setup
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		menuBar.getMenus().addAll(menuFile);
		
		MenuItem save = new MenuItem("Save");
		save.setOnAction(e -> {
			writeDataFile(libraryFile, library);
		});
		
		MenuItem exit = new MenuItem("Save and Exit");
		exit.setOnAction(e-> {
			writeDataFile(libraryFile, library);
			System.exit(0);
		});
		
		menuFile.getItems().addAll(save, exit);
		
		VBox controls = new VBox(10);
		controls.minWidth(300);
		controls.getChildren().addAll(addItem, updateItem, removeB);
		
		HBox main = new HBox(10);
		
		main.getChildren().addAll(table,controls);
		
		VBox app = new VBox();
		app.setPrefHeight(600);
		
		app.getChildren().addAll(menuBar, main);
		
		Scene root = new Scene(app, 1100, 600);
		
		primaryStage.setScene(root);
		primaryStage.setTitle("Pathfinder Library v.05");
		primaryStage.getIcons().add(new Image("file:icon.png"));
		primaryStage.setOnCloseRequest(event -> {
			writeDataFile(libraryFile, library);
		});
		primaryStage.show();
		
	}

	public static ObservableList<Book> getDataFromFile(File libraryFile) throws ClassNotFoundException, IOException
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(libraryFile));
		
		ArrayList mediaLibrary = (ArrayList<Book>) ois.readObject();
		ois.close();
		ObservableList<Book> library = FXCollections.observableArrayList(mediaLibrary);
		return library;
	}

	public static void writeDataFile(File libraryFile, ObservableList<Book> library) 
	{
		try
		{
			ArrayList<Book> tempLibrary = new ArrayList<>(library);
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(libraryFile));
			oos.writeObject(tempLibrary);
			oos.close();
		}
		catch (Exception e)
		{
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error");
			error.setHeaderText(null);
			error.setContentText("Error: could not write the account information to disk.");
			error.showAndWait();

			e.printStackTrace();
		}
	}

	public static boolean removeItem(String strName, ObservableList<Book> library)
	{
		boolean isPresent = false;
		try {
				//using iterator to prevent ConcurrentModificationException
				Iterator<Book> iter = library.iterator();
				while ( iter.hasNext())
				{
					Book item = iter.next();
					
				if (item.getName().equalsIgnoreCase(strName))
				{
					iter.remove();
					isPresent = true;
				}
			}

			if (isPresent == false)
			{
				throw new MediaItemDoesntExistException("Error: No Item of With That Name Exists.");
			}
			return true;
		}
		catch(MediaItemDoesntExistException e)
		{
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error: " + e.getClass());
			error.setHeaderText(null);
			error.setContentText(e.getMessage());
			error.showAndWait();
			
			return false;
		}
	}

	public static boolean createNew(String category, String name, String publisher, String path, ObservableList<Book> library)
	{
		String newPath;
		
		try {
			if (category.equals("Core"))
			{
				if(!path.equals(""))
				{
					newPath = copyFile(category, path, name, publisher);
				}
				else
				{
					newPath = "";
				}
				
				library.add(new Core(name, publisher, newPath));
				

				return true;
			}
			return false;
		}
		catch (Exception m)
		{
			return false;
		}
		
	}
	
	public static boolean createNew(String category, String name, String publisher, String path, String type, String setting, ObservableList<Book> library)
	{
		try {
			
			String newPath;
			if(!path.equals(""))
			{
				newPath = copyFile(category, path, name, publisher);
			}
			else
			{
				newPath = "";
			}
			
			if (category.equals("Rules"))
			{
				
				library.add(new Rules(name, publisher, newPath, type, setting));
				
				return true;
			}
			
			else if (category.equals("Setting"))
			{
				library.add(new Setting(name, publisher, newPath, type, setting));
				
				return true;
			}
			return false;
		}
		catch (Exception m)
		{
			return false;
		}
	}

	public static boolean createNew(String category, String name, String publisher, String path, String type, String setting, int startlvl, int endlvl, ObservableList<Book> library)
	{
		try {
			String newPath;
			if (category.equals("Adventure"))
			{
				
				if(!path.equals(""))
				{
					newPath = copyFile(category, path, name, publisher);
				}
				else
				{
					newPath = "";
				}
				
				library.add(new Adventure(name, publisher, newPath, type, setting, startlvl, endlvl));

				return true;
			}
			return false;
		}
		catch (Exception m)
		{
			return false;
		}
	}
	
	public boolean openPDF(String path)
	{
		try
		{
			File file = new File(path);
			HostServices hostServices = getHostServices();
			hostServices.showDocument(file.getAbsolutePath());
			return true;
		}
		catch(Exception m)
		{
			return false;
		}
	}
	
	public static String copyFile(String category, String path, String name, String publisher)
	{
		try {
		
		String newPath = new File(".").getCanonicalPath()+"\\files\\"+category+"\\"+publisher+"\\";
		
		new File(newPath).mkdirs();
			
		Path sourceDirectory = Paths.get(path);
		Path destinationDirectory = Paths.get(new File(".").getCanonicalPath()+"\\files\\"+category+"\\"+publisher+"\\"+name+".pdf");
		

		
		if(sourceDirectory != destinationDirectory)
		{
		Files.copy(sourceDirectory, destinationDirectory);
		}
		
		return destinationDirectory.toString();
		}
		catch(Exception m)
		{
			System.out.println(m.getMessage());
			return("error");
		}
	
	}
	
}
