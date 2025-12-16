package com.example.serverjava;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Application {

    private final int PORT = 12345; // Puerto fijo para la conexión
    private TextArea logArea;
    private Button btnStart;
    private Button btnStop;
    private Label lblStatus;
    private Label lblClients;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private volatile boolean isRunning = false;
    private int clientCount = 0;
    private int connectedClients = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Servidor TCP - Autenticación Android");

        // --- Configuración de UI ---
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12;");

        lblStatus = new Label("Estado: DETENIDO");
        lblStatus.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: red;");
        lblClients = new Label("Clientes: 0");

        btnStart = new Button("INICIAR SERVIDOR");
        btnStart.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnStart.setOnAction(e -> startServerAction());

        btnStop = new Button("DETENER SERVIDOR");
        btnStop.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnStop.setDisable(true);
        btnStop.setOnAction(e -> stopServer());

        // --- Layout ---
        HBox topPanel = new HBox(10);
        topPanel.setPadding(new Insets(10));
        topPanel.getChildren().addAll(
                new Label("Puerto: " + PORT), btnStart, btnStop, lblStatus, lblClients, new Button("Limpiar Log")
        );

        BorderPane root = new BorderPane();
        root.setTop(topPanel);
        root.setCenter(logArea);

        primaryStage.setScene(new Scene(root, 750, 550));
        primaryStage.setOnCloseRequest(e -> stopServer());
        primaryStage.show();
        log("=== SERVIDOR INICIALIZADO ===");
        log("Configuración: Puerto " + PORT);
    }

    private void startServerAction() {
        if (isRunning) return;

        btnStart.setDisable(true);
        btnStop.setDisable(false);

        Platform.runLater(() -> {
            lblStatus.setText("Estado: INICIANDO...");
            lblStatus.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
        });

        new Thread(() -> startServer(PORT)).start();
    }

    private void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            threadPool = Executors.newCachedThreadPool();
            isRunning = true;

            Platform.runLater(() -> {
                lblStatus.setText("Estado: ACTIVO - Puerto " + port);
                lblStatus.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                log("✅ SERVIDOR INICIADO en el Puerto " + port);
                log("🟢 Esperando conexiones de Android...");
            });

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientCount++;
                    connectedClients++;
                    Platform.runLater(() -> lblClients.setText("Clientes: " + connectedClients));

                    String clientInfo = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
                    log("🔗 Cliente #" + clientCount + " conectado: " + clientInfo);
                    threadPool.execute(() -> handleClient(clientSocket, clientInfo));

                } catch (IOException e) {
                    if (isRunning) log("❌ Error aceptando conexión: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            Platform.runLater(() -> {
                lblStatus.setText("Estado: ERROR");
                lblStatus.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                log("❌ NO se pudo iniciar el servidor. Error: " + e.getMessage());
                resetUI();
            });
            isRunning = false;
        }
    }

    private void handleClient(Socket clientSocket, String clientInfo) {
        try (
                Socket socket = clientSocket;
                // IMPORTANTE: El orden de los streams DEBE coincidir con el cliente Kotlin
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream())
        ) {
            Object receivedObject = input.readObject();

            if (receivedObject instanceof User user) {
                log("📨 Datos recibidos de " + clientInfo);
                log("   Usuario: " + user.getUsername());

                String response = authenticate(user);
                output.writeObject(response);
                output.flush();

                log("📤 Respuesta enviada: " + (response.startsWith("SUCCESS") ? "✅" : "❌"));

            } else {
                output.writeObject("ERROR: Objeto de tipo incorrecto.");
                output.flush();
                log("⚠️ Objeto inválido recibido de " + clientInfo);
            }

        } catch (Exception e) {
            log("❌ Error en comunicación con " + clientInfo + ": " + e.getClass().getSimpleName());
        } finally {
            connectedClients--;
            Platform.runLater(() -> lblClients.setText("Clientes: " + connectedClients));
            log("← Cliente " + clientInfo + " desconectado");
        }
    }

    private String authenticate(User user) {
        String username = user.getUsername().trim();
        String password = user.getPassword().trim();

        // Lógica de Autenticación Requerida
        if ("admin".equals(username) && "admin".equals(password)) {
            return "SUCCESS: Bienvenido Administrador.";
        }
        return "ERROR: Credenciales incorrectas.";
    }

    private void stopServer() {
        if (!isRunning) return;
        isRunning = false;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            log("⚠️ Error cerrando socket: " + e.getMessage());
        }
        if (threadPool != null) threadPool.shutdownNow();
        Platform.runLater(() -> {
            lblStatus.setText("Estado: DETENIDO");
            lblStatus.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            resetUI();
            log("🛑 SERVIDOR DETENIDO");
        });
    }

    private void log(String message) {
        Platform.runLater(() -> {
            String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            logArea.appendText("[" + timestamp + "] " + message + "\n");
            logArea.setScrollTop(Double.MAX_VALUE);
        });
    }

    private void resetUI() {
        Platform.runLater(() -> {
            btnStart.setDisable(false);
            btnStop.setDisable(true);
        });
    }
}