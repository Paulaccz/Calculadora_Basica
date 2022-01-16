package com.example.calculadora;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Vista.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        stage.setTitle("Calculadora Paula");
//        stage.getIcons().add(new Image("file:.\\src\\main\\resources\\icono_calculadora.png"));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icono_calculadora.png")));
        stage.setScene(scene);
        ((Controller) fxmlLoader.getController()).init(stage);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}