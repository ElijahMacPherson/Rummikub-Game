package com.rummikub;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TitleScreenController implements Initializable {
	@FXML
	private AnchorPane root;
	@FXML
	private ComboBox<String> cb_PlayerCount;
	@FXML
	private ComboBox<String> cb_Player0;
	@FXML
	private ComboBox<String> cb_Player1;
	@FXML
	private ComboBox<String> cb_Player2;
	@FXML
	private ComboBox<String> cb_Player3;
	@FXML
	private Button btn_Player0Tiles;
	@FXML
	private Button btn_Player1Tiles;
	@FXML
	private Button btn_Player2Tiles;
	@FXML
	private Button btn_Player3Tiles;
	@FXML
	private CheckBox ckBx_GameMode;
	@FXML
	private CheckBox ckBx_RigDraw;
	@FXML
	private VBox vb_PlayerStrategies;
	@FXML
	private VBox vb_PlayerTileSelection;
	@FXML
	private Button btn_Play;
	@FXML
	private Button btn_chooseFile;
	@FXML
	private Button btn_confirmTiles;
	@FXML
	private Button btn_cancelTiles;
	@FXML
	private Rectangle rect_background;
	@FXML
	private Rectangle rect_title;
	@FXML
	private Rectangle rect_tileSelection;
	@FXML
	private FlowPane flw_tileSelection;

	List<List<Tile>> playerTileList = new ArrayList<List<Tile>>();
	Stock stock = new Stock(true);
	int currentID = -1;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init_background_images();

		cb_PlayerCount.getItems().addAll("2", "3", "4");
		cb_Player0.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4");
		cb_Player1.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4");
		cb_Player2.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4");
		cb_Player3.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4");
		playerTileList.add(new ArrayList<Tile>());
		playerTileList.add(new ArrayList<Tile>());
		playerTileList.add(new ArrayList<Tile>());
		playerTileList.add(new ArrayList<Tile>());
		btn_Player0Tiles.setId("0");
		btn_Player1Tiles.setId("1");
		btn_Player2Tiles.setId("2");
		btn_Player3Tiles.setId("3");

		for (int i = 0; i < vb_PlayerStrategies.getChildren().size(); i++) {
			vb_PlayerStrategies.getChildren().get(i).setVisible(false);
			vb_PlayerTileSelection.getChildren().get(i).setVisible(false);
		}

	}

	@FXML
	public void handlePlayerCountCB(ActionEvent event) {
		int numPlayers = Integer.parseInt(cb_PlayerCount.getValue());

		for (int i = 0; i < vb_PlayerStrategies.getChildren().size(); i++) {
			@SuppressWarnings("unchecked")
			ComboBox<String> strategyNode = (ComboBox<String>) vb_PlayerStrategies.getChildren().get(i);
			Node tileSelectionNode = vb_PlayerTileSelection.getChildren().get(i);

			if (i < numPlayers) {
				strategyNode.setVisible(true);
				tileSelectionNode.setVisible(true);
			} else {
				strategyNode.setVisible(false);
				tileSelectionNode.setVisible(false);
			}
			strategyNode.setValue("Select");

		}

		for (List<Tile> list : playerTileList) {
			list.clear();
		}
		stock = new Stock(true);
		btn_Play.setDisable(true);
		btn_chooseFile.setDisable(true);
	}

	@FXML
	public void handleStrategyCB(ActionEvent event) {
		boolean check = true;
		int numPlayers = Integer.parseInt(cb_PlayerCount.getValue());
		for (int i = 0; i < numPlayers; i++) {
			@SuppressWarnings("unchecked")
			ComboBox<String> strategyNode = (ComboBox<String>) vb_PlayerStrategies.getChildren().get(i);
			if (strategyNode.getValue() == "Select")
				check = false;
		}

		if (check) {
			btn_Play.setDisable(false);
			btn_chooseFile.setDisable(false);
		} else {
			btn_Play.setDisable(true);
			btn_chooseFile.setDisable(true);
		}
	}

	@FXML
	public void handlePlayBtn(ActionEvent event) throws Exception {
		List<Player> players = getPlayers();

		if (players.size() >= 2) {

			Boolean waitAfterEachTurn = false;
			Boolean useGUI = true;
			Boolean rigDraw = ckBx_RigDraw.isSelected();
			Boolean testingMode = ckBx_GameMode.isSelected();
			Rummy.game = new Game(players, testingMode, rigDraw, waitAfterEachTurn, useGUI, createStock());

			// Get the event's source stage, and set the scene to be the game.
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(Rummy.loadScene("MainScreen.fxml"));
		} else {
			Print.print("Unknown strategy selected.");
		}

	}

	@FXML
	public void handleFileBtn(ActionEvent event) throws Exception {

		List<Player> players = getPlayers();
		if (players.size() >= 2) {

			// Get the event's source stage
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Rigged File");
			fileChooser.setInitialDirectory(new File(Constants.INPUT_FILE_DIRECTORY));
			File file = fileChooser.showOpenDialog(stage);

			if (file != null) {
				FileParser.reset();
				FileParser.parse(file);

				Boolean waitAfterEachTurn = false;
				Boolean useGUI = true;
				Boolean testingMode = ckBx_GameMode.isSelected();
				Boolean rigDraw = ckBx_RigDraw.isSelected();
				Rummy.game = new Game(players, testingMode, rigDraw, waitAfterEachTurn, useGUI, FileParser.stock);

				if (!FileParser.inputError) {
					// set the scene to be the main screen.
					stage.setScene(Rummy.loadScene("MainScreen.fxml"));
				} else {
					Print.print("Input error of some sort!");
				}
			}
		} else {
			Print.print("Unknown strategy selected.");
		}

	}

	@FXML
	public void handleTileSelectionButton(ActionEvent event) {
		rect_tileSelection.setVisible(true);
		flw_tileSelection.setVisible(true);
		btn_confirmTiles.setVisible(true);
		btn_cancelTiles.setVisible(true);
		btn_confirmTiles.setId(((Node) event.getSource()).getId());

		viewTiles(flw_tileSelection);
	}

	@FXML
	public void handleTileConfirmationButton(ActionEvent event) {
		int playerListIndex = Integer.parseInt(btn_confirmTiles.getId());
		playerTileList.get(playerListIndex).clear();
		int count = 0;
		for (Tile tile : stock.getStockArray()) {
			if (tile.selected) {
				count++;
				playerTileList.get(playerListIndex).add(tile);
			}
		}

		if (count == 14) {
			stock.getStockArray().removeAll(playerTileList.get(playerListIndex));

			rect_tileSelection.setVisible(false);
			flw_tileSelection.setVisible(false);
			btn_confirmTiles.setVisible(false);
			btn_cancelTiles.setVisible(false);
			btn_confirmTiles.setId("");
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning!");
			alert.setContentText("You must select 14 tiles to rid the player's rack!");

			alert.showAndWait();
		}
	}

	@FXML
	public void handleTileCancelButton(ActionEvent event) {
		int playerListIndex = Integer.parseInt(btn_confirmTiles.getId());
		playerTileList.get(playerListIndex).clear();

		for (Tile tile : stock.getStockArray()) {
			tile.selected = false;
		}

		rect_tileSelection.setVisible(false);
		flw_tileSelection.setVisible(false);
		btn_confirmTiles.setVisible(false);
		btn_cancelTiles.setVisible(false);
		btn_confirmTiles.setId("");
	}

	/*
	 * tile images of stock
	 */
	public void viewTiles(FlowPane pane) {
		pane.getChildren().clear();

		stock.getStockArray().addAll(playerTileList.get(Integer.parseInt(btn_confirmTiles.getId())));

		int i = 0;
		for (Tile tile : stock.getStockArray()) {
			Image img = tile.getTileImage();
			ImageView tileImg = new ImageView(img);

			tileImg.setPreserveRatio(true);
			tileImg.setFitWidth(35);
			tileImg.setId(Integer.toString(i));

			if (tile.selected) {
				tileImg.setFitWidth(tileImg.getFitWidth() - 10);
				tileImg.relocate(tileImg.getLayoutX() + 5, tileImg.getLayoutY());
			}

			tileImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					handleTileSelected(tileImg, tile);

				}
			});
			pane.getChildren().add(tileImg);
			i++;
		}
	}

	private void handleTileSelected(ImageView tileImg, Tile tile) {
		tile.selected = !tile.selected;

		if (tile.selected) {
			tileImg.setFitWidth(tileImg.getFitWidth() - 10);
			tileImg.relocate(tileImg.getLayoutX() + 5, tileImg.getLayoutY());

		} else {
			tileImg.setFitWidth(tileImg.getFitWidth() + 10);
			tileImg.relocate(tileImg.getLayoutX() - 5, tileImg.getLayoutY());
		}
	}

	private List<Player> getPlayers() {
		int numPlayers = Integer.parseInt(cb_PlayerCount.getValue());
		List<Player> players = new ArrayList<Player>();

		for (int i = 0; i < numPlayers; i++) {
			@SuppressWarnings("unchecked")
			ComboBox<String> currNode = (ComboBox<String>) vb_PlayerStrategies.getChildren().get(i);

			switch (currNode.getValue()) {
			case "Human":
				players.add(new Player("p" + i, new Strategy0()));
				break;
			case "Strategy 1":
				players.add(new Player("p" + i, new Strategy1()));
				break;
			case "Strategy 2":
				players.add(new Player("p" + i, new Strategy2()));
				break;
			case "Strategy 3":
				players.add(new Player("p" + i, new Strategy3()));
				break;
			case "Strategy 4":
				players.add(new Player("p" + i, new Strategy4()));
				break;
			default:
				return new ArrayList<Player>();
			}
		}
		return players;
	}

	private Stock createStock() {
		Stock riggedStock = new Stock(true);
		riggedStock.getStockArray().clear();

		int numPlayers = Integer.parseInt(cb_PlayerCount.getValue());
		for (int i = 0; i < numPlayers; i++) {

			Print.print(stock.getStockArray().size());
			if (playerTileList.get(i).size() == 14) {
				riggedStock.getStockArray().addAll(playerTileList.get(i));
			} else {
				for (int j = 0; j < 14; j++) {
					riggedStock.getStockArray().add(stock.getStockArray().remove(0));
				}
			}
		}
		riggedStock.getStockArray().addAll(stock.getStockArray());

		return riggedStock;
	}

	public void init_background_images() {
		rect_background.setFill(new ImagePattern(new Image(Constants.TITLE_BG_IMG)));
		rect_title.setFill(new ImagePattern(new Image(Constants.TITLE_IMG)));
	}

}
