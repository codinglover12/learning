import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class ShortenerUI {
	
	// main method along with some exceptions to handle the exceptions & errors in runtime
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/courier", "root", "Techmaster@123");

    // Database connection address with password,name,port number
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new RedirectHandler(connection));
        server.setExecutor(null);
        server.start();

        System.out.println("Server started on port 8000");
    }

    
    
    
    
    static class RedirectHandler implements HttpHandler {
        private final Connection connection;

        public RedirectHandler(Connection connection) {
            this.connection = connection;
        }

        
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestedPath = exchange.getRequestURI().getPath().substring(1); // Remove leading slash

            try {
                PreparedStatement statement = connection.prepareStatement("SELECT original_url FROM urls WHERE shortened_url = ?");
                statement.setString(1, requestedPath);
                ResultSet resultSet = statement.executeQuery();

                // conditional statement
                if (resultSet.next()) {
                    String originalURL = resultSet.getString("original_url");
                    exchange.getResponseHeaders().set("Location", originalURL);
                    exchange.sendResponseHeaders(301, -1); // Redirect with status code 301 (Moved Permanently)
                } else {
                    exchange.sendResponseHeaders(404, -1); // Not found
                }
                
                
                

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            exchange.close();
        }
    }
}