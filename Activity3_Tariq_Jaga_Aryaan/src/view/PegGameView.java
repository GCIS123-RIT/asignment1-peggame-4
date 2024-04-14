package view;

import controller.PegGameController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * The PegGameView class represents the graphical user interface for the Peg Game application.
 * It extends the JavaFX Application class and provides the visual components and layout of the game.
 * The class handles user interactions, displays the game board, and communicates with the PegGameController
 * to manage the game logic and state.
 */
public class PegGameView extends Application {
    private PegGameController controller;
    private GridPane boardPane;
    private Label statusLabel;
    private Label instructionLabel;
    private HBox guide;
    private Stage primaryStage;
    private Button loadButton;
    private Button saveButton;
    private Button exitButton;
    private Button resetButton;

    /**
     * The start method is called when the application is launched.
     * It sets up the primary stage, creates the main layout, and initializes the game components.
     *
     * @param primaryStage the primary stage of the application
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Peg Game");

        // Create the main layout
        VBox root = new VBox(16);
        root.setPadding(new Insets(16));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F5DEB3;");

        // Create the title text
        Text titleText = new Text("PEG GAME");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 120));
        titleText.setFill(Color.BLACK);

        // Create the board pane
        boardPane = new GridPane();
        boardPane.setHgap(8);
        boardPane.setVgap(8);
        boardPane.setAlignment(Pos.CENTER);

        // Create the status label
        statusLabel = new Label("Select a file to start the game.");
        statusLabel.setStyle("-fx-font-size: 16px;");

        // Create the instruction label
        instructionLabel = new Label("");
        instructionLabel.setStyle("-fx-font-size: 16px;");

        // Create the load and exit button layout
        VBox loadExitLayout = new VBox(8);
        loadExitLayout.setAlignment(Pos.CENTER);

        // Create the load button
        loadButton = new Button("Load Game");
        loadButton.setStyle("-fx-font-size: 16px;");

        // Create the exit button
        exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 16px;");

        // Add load and exit buttons to the layout
        loadExitLayout.getChildren().addAll(loadButton, exitButton);

        // Create the save and reset button layout
        VBox saveResetLayout = new VBox(8);
        saveResetLayout.setAlignment(Pos.CENTER);

        // Create the save button
        saveButton = new Button("Save Game");
        saveButton.setStyle("-fx-font-size: 16px;");
        saveButton.setVisible(false); // Initially hide the save button

        // Create the reset button
        resetButton = new Button("Main menu");
        resetButton.setStyle("-fx-font-size: 16px;");
        resetButton.setVisible(false); // Initially hide the reset button

        // Add save and reset buttons to the layout
        saveResetLayout.getChildren().addAll(saveButton, resetButton);

        // Add the rules
        VBox rulesBox = new VBox(8);
        rulesBox.setAlignment(Pos.CENTER);
        TextFlow rulesFlow = new TextFlow();
        Text rulesTitle = new Text("Rules:\n");
        rulesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text rule1 = new Text("1. Every jump must be a jump of a peg over a neighboring peg.\n");
        Text rule2 = new Text("2. There must be a space for the jumping peg to land in.\n");
        Text rule3 = new Text("3. Jumps can be made either on the diagonal or the horizontal lines.\n");
        Text rule4 = new Text("4. A peg that is jumped is removed just like in checkers.\n");
        rulesFlow.getChildren().addAll(rulesTitle, rule1, rule2, rule3, rule4);
        rulesBox.getChildren().add(rulesFlow);

        // Add components to the root layout
        root.getChildren().addAll(titleText, rulesBox, boardPane, statusLabel, instructionLabel, loadExitLayout, saveResetLayout);

        // Set the scene and show the stage
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();

        // Create the controller and pass necessary view components
        controller = new PegGameController(this, boardPane, statusLabel, instructionLabel, loadButton, saveButton, exitButton, resetButton);

        // Set event handlers
        loadButton.setOnAction(e -> controller.loadGame());
        exitButton.setOnAction(e -> controller.exitProgram());
        saveButton.setOnAction(e -> controller.saveGame());
        resetButton.setOnAction(e -> controller.resetGame());
    }

    /**
     * Displays an error dialog with the specified message.
     *
     * @param message the error message to display
     */
    public void showErrorDialog(String message) {
        Stage errorStage = new Stage();
        errorStage.initOwner(primaryStage);
        errorStage.setTitle("Error");

        Label errorLabel = new Label(message);
        errorLabel.setWrapText(true); // Allow text to wrap to multiple lines
        Button okButton = new Button("OK");

        okButton.setOnAction(e -> errorStage.close());

        VBox errorLayout = new VBox(16);
        errorLayout.setAlignment(Pos.CENTER);
        errorLayout.setPadding(new Insets(16));
        errorLayout.getChildren().addAll(errorLabel, okButton);

        Scene errorScene = new Scene(errorLayout);
        errorStage.setScene(errorScene);
        errorStage.sizeToScene(); // Adjust the window size based on the content
        errorStage.showAndWait();
    }

    /**
     * Displays a guide showing the visual representation of pegs and holes.
     */
    public void showGuide() {
        // Create the guide
        guide = new HBox(8);
        Circle pegCircle = new Circle(8, Color.WHITE);
        Label pegLabel = new Label("Peg");
        Circle holeCircle = new Circle(8, Color.BLACK);
        Label holeLabel = new Label("Hole");
        guide.getChildren().addAll(pegCircle, pegLabel, holeCircle, holeLabel);
        guide.setAlignment(Pos.CENTER_LEFT);

        // Add the guide to the root layout
        ((VBox) boardPane.getParent()).getChildren().add(0, guide);
    }

    /**
     * Displays a confirmation dialog when the user attempts to exit the game without saving.
     */
    public void showExitConfirmation() {
        Stage confirmationStage = new Stage();
        confirmationStage.initOwner(primaryStage);
        confirmationStage.setTitle("Exit Confirmation");

        Label confirmationLabel = new Label("Are you sure you want to exit without saving?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            confirmationStage.close();
            controller.resetGameState();
        });

        noButton.setOnAction(e -> confirmationStage.close());

        VBox confirmationLayout = new VBox(16);
        confirmationLayout.setAlignment(Pos.CENTER);
        confirmationLayout.setPadding(new Insets(16));
        confirmationLayout.getChildren().addAll(confirmationLabel, yesButton, noButton);

        Scene confirmationScene = new Scene(confirmationLayout, 300, 150);
        confirmationStage.setScene(confirmationScene);
        confirmationStage.showAndWait();
    }

    /**
     * Updates the game board display based on the current game state and selected location.
     *
     * @param game             the current PegGame instance
     * @param selectedLocation the currently selected location on the board
     */
    public void updateBoard(model.PegGame game, model.Location selectedLocation) {
        boardPane.getChildren().clear();
        int boardSize = game.getBoardSize();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                model.Location location = new model.Location(row, col);
                StackPane cellPane = new StackPane();
                Circle invisibleCircle = new Circle(24); // Invisible circle with the same size as white circles
                invisibleCircle.setFill(Color.TRANSPARENT);
                if (game.hasPegAt(location)) {
                    Circle pegCircle = new Circle(24);
                    pegCircle.setFill(Color.WHITE);
                    if (selectedLocation != null && selectedLocation.equals(location)) {
                        pegCircle.setStyle("-fx-stroke: yellow; -fx-stroke-width: 4;");
                    }
                    int finalRow = row;
                    int finalCol = col;
                    pegCircle.setOnMouseClicked(e -> controller.handleMove(finalRow, finalCol));
                    cellPane.getChildren().addAll(invisibleCircle, pegCircle);
                } else {
                    Circle holeCircle = new Circle(8);
                    holeCircle.setFill(Color.BLACK);
                    int finalRow = row;
                    int finalCol = col;
                    holeCircle.setOnMouseClicked(e -> controller.handleMove(finalRow, finalCol));
                    cellPane.getChildren().addAll(invisibleCircle, holeCircle);
                }
                cellPane.setAlignment(Pos.CENTER);
                boardPane.add(cellPane, col, row);
            }
        }
    }

    /**
     * Returns the primary stage of the application.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}