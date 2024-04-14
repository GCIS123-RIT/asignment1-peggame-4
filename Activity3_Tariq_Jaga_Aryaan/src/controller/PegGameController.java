package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import model.GameState;
import model.Location;
import model.Move;
import model.PegGame;
import model.PegGameException;
import model.PegGameFactory;
import view.PegGameView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The PegGameController class handles the game logic and user interactions for the Peg Game.
 * It communicates with the PegGameView to update the user interface and with the model classes
 * to manage the game state and perform game actions.
 */
public class PegGameController {
    private PegGameView view;
    private PegGame game;
    private GridPane boardPane;
    private Label statusLabel;
    private Label instructionLabel;
    private Location selectedLocation;
    private Button loadButton;
    private Button saveButton;
    private Button exitButton;
    private Button resetButton;

    /**
     * Constructs a PegGameController with the specified view and UI components.
     *
     * @param view              the PegGameView instance
     * @param boardPane         the GridPane representing the game board
     * @param statusLabel       the Label for displaying game status
     * @param instructionLabel  the Label for displaying game instructions
     * @param loadButton        the Button for loading a game
     * @param saveButton        the Button for saving the game
     * @param exitButton        the Button for exiting the game
     * @param resetButton       the Button for resetting the game
     */
    public PegGameController(PegGameView view, GridPane boardPane, Label statusLabel, Label instructionLabel,
                              Button loadButton, Button saveButton, Button exitButton, Button resetButton) {
        this.view = view;
        this.boardPane = boardPane;
        this.statusLabel = statusLabel;
        this.instructionLabel = instructionLabel;
        this.loadButton = loadButton;
        this.saveButton = saveButton;
        this.exitButton = exitButton;
        this.resetButton = resetButton;
    }

    /**
     * Loads a game from a file chosen by the user.
     * If a valid file is selected, the game is initialized, and the board and UI are updated accordingly.
     * If an error occurs during loading, an error dialog is displayed.
     */
    public void loadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Game File");
        fileChooser.setInitialDirectory(new File("C:/Users/tariq/OneDrive/Desktop/Activity3_Tariq_Jaga_Aryaan/src/resources"));
        File file = fileChooser.showOpenDialog(view.getPrimaryStage());
        if (file != null) {
            try {
                game = PegGameFactory.createPegGame(file.getAbsolutePath());
                updateBoard();
                instructionLabel.setText("Click a peg to move.");
                view.showGuide();
                loadButton.setVisible(false);
                saveButton.setVisible(true);
                exitButton.setVisible(false);
                resetButton.setVisible(true);
                boardPane.setVisible(true); // Make the boardPane visible
            } catch (Exception e) {
                view.showErrorDialog("Error loading game file. Please make sure the file is a valid .txt file and not empty or formatted wrong");
            }
        }
    }

    /**
     * Saves the current game state to a file chosen by the user.
     * If no game is loaded, an appropriate status message is displayed.
     * If an error occurs during saving, an error status message is displayed.
     */
    public void saveGame() {
        if (game == null) {
            statusLabel.setText("No game loaded to save.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game File");
        fileChooser.setInitialDirectory(new File("C:/Users/tariq/OneDrive/Desktop/Activity3_Tariq_Jaga_Aryaan/src/resources"));
        File file = fileChooser.showSaveDialog(view.getPrimaryStage());
        if (file != null) {
            try {
                saveGameToFile(file);
                statusLabel.setText("Game saved successfully.");
            } catch (IOException e) {
                statusLabel.setText("Error saving game file.");
            }
        }
    }

    /**
     * Saves the current game state to the specified file.
     *
     * @param file the file to save the game state to
     * @throws IOException if an I/O error occurs while writing to the file
     */
    private void saveGameToFile(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            int boardSize = game.getBoardSize();
            writer.write(String.valueOf(boardSize) + "\n");

            for (int row = 0; row < boardSize; row++) {
                StringBuilder line = new StringBuilder();
                for (int col = 0; col < boardSize; col++) {
                    Location location = new Location(row, col);
                    if (game.hasPegAt(location)) {
                        line.append("o");
                    } else {
                        line.append(".");
                    }
                }
                writer.write(line.toString() + "\n");
            }
        }
    }

    /**
     * Resets the game state and prompts the user for confirmation if the game is in progress.
     * If no game is loaded or the user confirms the reset, the game state is reset to the initial state.
     */
    public void resetGame() {
        if (game != null && saveButton.isVisible()) {
            view.showExitConfirmation();
        } else {
            resetGameState();
        }
    }

    /**
     * Resets the game state to the initial state.
     * Clears the game board, resets the UI labels and buttons, and hides the game board.
     */
    public void resetGameState() {
        game = null;
        selectedLocation = null;
        boardPane.getChildren().clear();
        statusLabel.setText("Game not started. Select a file to start the game.");
        instructionLabel.setText("");
        loadButton.setVisible(true);
        saveButton.setVisible(false);
        boardPane.setVisible(false);
        exitButton.setVisible(true);
        resetButton.setVisible(false);
    }

    /**
     * Exits the program by closing the primary stage.
     */
    public void exitProgram() {
        view.getPrimaryStage().close();
    }

    /**
     * Handles a move made by the user on the game board.
     * If a peg is selected, it attempts to make a move to the clicked location.
     * If the move is valid, the game state is updated, and the board is refreshed.
     * If the move is invalid or an exception occurs, an appropriate message is displayed.
     *
     * @param row the row index of the clicked location
     * @param col the column index of the clicked location
     */
    public void handleMove(int row, int col) {
        Location clickedLocation = new Location(row, col);
        if (selectedLocation == null) {
            if (game.hasPegAt(clickedLocation)) {
                selectedLocation = clickedLocation;
                updateBoard();
                instructionLabel.setText("Choose the location to move to.");
            }
        } else {
            try {
                Move move = new Move(selectedLocation, clickedLocation);
                if (game.isValidMove(move.getFrom(), move.getTo())) {
                    game.makeMove(move);
                    selectedLocation = null;
                    updateBoard();
                    updateStatus();
                    instructionLabel.setText("Click a peg to move.");
                } else {
                    selectedLocation = null;
                    updateBoard();
                    instructionLabel.setText("Click a peg to move.");
                }
            } catch (PegGameException e) {
                statusLabel.setText("Invalid move. " + e.getMessage());
                selectedLocation = null;
                updateBoard();
                instructionLabel.setText("Click a peg to move.");
            }
        }
    }

    /**
     * Updates the game board display based on the current game state and selected location.
     */
    private void updateBoard() {
        view.updateBoard(game, selectedLocation);
    }

    /**
     * Updates the game status label based on the current game state.
     * Displays the appropriate message for each game state (in progress, stalemate, won).
     */
    private void updateStatus() {
        if (game == null) {
            statusLabel.setText("Game not started. Select a file to start the game.");
        } else {
            GameState state = game.getGameState();
            switch (state) {
                case IN_PROGRESS:
                    statusLabel.setText("Game in progress");
                    break;
                case STALEMATE:
                    statusLabel.setText("Game over. Stalemate!");
                    break;
                case WON:
                    statusLabel.setText("Congratulations! You won!");
                    break;
            }
        }
    }
}