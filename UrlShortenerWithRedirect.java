import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.Random;

public class UrlShortenerWithRedirect extends Application {

    private static final String BASE_URL = "http://localhost:8000/";

    public static void main(String[] args) {
        launch(args);
    }

    
    @Override
    public void start(Stage primaryStage) {
    	// Create a StackPane to hold the VBox and apply padding to it
    	StackPane stackPane = new StackPane();
    	stackPane.setPadding(new Insets(20, 10, 10, 10)); // Adds padding to the top

    	// Create a VBox to stack UI elements vertically and center them
    	VBox root = new VBox(10); // You can adjust the spacing (10) as needed
    	root.setAlignment(Pos.CENTER);

    	// Set the background image of the form
    	Image backgroundImage = new Image("https://img.freepik.com/premium-photo/natural-marble-pattern-background_1258-22162.jpg");
    	BackgroundImage background = new BackgroundImage(
    	        backgroundImage,
    	        BackgroundRepeat.NO_REPEAT,
    	        BackgroundRepeat.NO_REPEAT,
    	        BackgroundPosition.CENTER,
    	        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
    	root.setBackground(new Background(background));

    	
    	

    	// Create a circular app icon with a border
        Image appIconImage = new Image("https://seo-hacker.com/wp-content/uploads/2018/04/Best-URL-Shortening-Tools-For-2018-.jpg");
        ImageView appIconImageView = new ImageView(appIconImage);
        Circle iconCircle = new Circle(120); // Set the circle radius to control the size
        iconCircle.setStroke(Color.YELLOW); // Set the border color
        iconCircle.setFill(new ImagePattern(appIconImage)); // Fill the circle with the image
        iconCircle.setStrokeWidth(8);
        VBox iconContainer = new VBox(iconCircle);
        iconContainer.setAlignment(Pos.CENTER);
        root.getChildren().add(iconContainer);

        
        // Add label for the title of the project
        Label titleLabel = new Label("Web URL Shortening Project");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
        root.getChildren().add(titleLabel);

        // Create a GridPane to arrange elements in a grid
        GridPane gridPane1 = new GridPane();
        gridPane1.setHgap(10);
        gridPane1.setVgap(10);
        gridPane1.setAlignment(Pos.CENTER);
  

  
        
        

        // Add label and text field for the original URL to the GridPane
        Label originalUrlLabel = new Label("Original URL :-");
        originalUrlLabel.setTextFill(Color.WHITE);
        originalUrlLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 25));
        TextField originalUrlTextField = new TextField();
        originalUrlTextField.setPrefWidth(300);

        // Add elements to the GridPane
        gridPane1.add(originalUrlLabel, 0, 0);
        gridPane1.add(originalUrlTextField, 1, 0);

        // Add button to shorten the URL with a gradient background
        Button shortenButton = new Button("Shorten");
        shortenButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #4CAF50, #45A049);" +
                        "-fx-text-fill: white;"
        );
        shortenButton.setFont(Font.font("Roboto", FontWeight.BOLD, 15));

        // Set the preferred width and height of the button (smaller size)
        shortenButton.setPrefWidth(100);
        shortenButton.setPrefHeight(30);

        // Add label for the shortened URL
        Label shortenedUrlLabel = new Label("Shortened URL :-");
        shortenedUrlLabel.setTextFill(Color.WHITE);
        shortenedUrlLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 25));

        // Add text field for the shortened URL
        TextField shortenedUrlTextField = new TextField();
        shortenedUrlTextField.setPrefWidth(300);
        shortenedUrlTextField.setEditable(false);

        // Create an HBox to arrange buttons side by side
        HBox buttonBox = new HBox(10); // You can adjust the spacing (10) as needed
        buttonBox.setAlignment(Pos.CENTER);

        // Add button to copy the shortened URL to the clipboard
        Button copyButton = new Button("Copy");
        copyButton.setStyle("-fx-background-color: red;");
        copyButton.setDisable(true);
        copyButton.setTextFill(Color.WHITE);
        copyButton.setFont(Font.font("Roboto", FontWeight.BOLD, 15));

        // Set the preferred width and height of the copy button (smaller size)
        copyButton.setPrefWidth(100);
        copyButton.setPrefHeight(30);

        // Label to show "Copied" message
        Label copiedLabel = new Label("");
        copiedLabel.setTextFill(Color.GREEN); // Set color to green
        copiedLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 15));

        // Apply CSS to remove underline (set as a non-link)
        copiedLabel.setStyle("-fx-underline: false;");

        // Add elements to the buttonBox
        buttonBox.getChildren().addAll(shortenButton, copyButton, copiedLabel);

        // Add elements to the GridPane
        gridPane1.add(shortenedUrlLabel, 0, 1);
        gridPane1.add(shortenedUrlTextField, 1, 1);

        // Add elements to the VBox
     // Add elements to the VBox
        root.getChildren().addAll(gridPane1, buttonBox); // Use 'root' instead of 'root1'


        // Handle button clicks
        shortenButton.setOnAction(e -> {
            String originalUrl = originalUrlTextField.getText();
            String shortenedUrl = shortenUrl(originalUrl);

            if (shortenedUrl != null) {
                shortenedUrlTextField.setText(shortenedUrl);
                copyButton.setDisable(false);
                copiedLabel.setText(""); // Reset "Copied" message
            } else {
                shortenedUrlTextField.setText("Failed to shorten URL.");
                copyButton.setDisable(true);
            }
        });

        copyButton.setOnAction(e -> {
            String shortenedUrl = shortenedUrlTextField.getText();

            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(shortenedUrl);
            clipboard.setContent(content);

            // Set "Copied" message
            copiedLabel.setText("Text-Copied Successfully");
        });

     // ... (the rest of your code)

    	// Add the VBox to the StackPane
    	stackPane.getChildren().add(root);

    	// Create a scene
    	Scene scene = new Scene(stackPane, 800, 600);

    	// Set the scene and show the stage
    	primaryStage.setScene(scene);
    	primaryStage.show();
        // Start the HTTP server for redirection
        startHttpServer();
    }

    // Implement your URL shortening logic here
    private String shortenUrl(String originalUrl) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/courier", "root", "Techmaster@123");

            // Check if the URL already exists in the database
            String selectQuery = "SELECT shortened_url FROM urls WHERE original_url = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, originalUrl);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                return BASE_URL + resultSet.getString("shortened_url");
            }

            // Generate a unique shortened URL
            String shortenedUrl = generateShortenedUrl();

            // Insert the original URL and shortened URL into the database
            String insertQuery = "INSERT INTO urls (original_url, shortened_url) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, originalUrl);
            insertStatement.setString(2, shortenedUrl);

            insertStatement.executeUpdate();

            connection.close();

            return BASE_URL + shortenedUrl;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Generate a unique shortened URL (e.g., using a random string)
    private String generateShortenedUrl() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortenedUrl = new StringBuilder();
        Random random = new Random();
        int length = 6; // Length of the shortened URL

        for (int i = 0; i < length; i++) {
            shortenedUrl.append(characters.charAt(random.nextInt(characters.length())));
        }

        return shortenedUrl.toString();
    }

    // Start a simple HTTP server for redirection
    private void startHttpServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/", new RedirectHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle HTTP redirection
    class RedirectHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestedPath = exchange.getRequestURI().getPath().substring(1); // Remove leading slash

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/courier", "root", "Techmaster@123");

                PreparedStatement statement = connection.prepareStatement("SELECT original_url FROM urls WHERE shortened_url = ?");
                statement.setString(1, requestedPath);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String originalURL = resultSet.getString("original_url");
                    exchange.getResponseHeaders().set("Location", originalURL);
                    exchange.sendResponseHeaders(301, -1); // Redirect with status code 301 (Moved Permanently)
                } else {
                    exchange.sendResponseHeaders(404, -1); // Not found
                }

                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            exchange.close();
        }
    }
}
