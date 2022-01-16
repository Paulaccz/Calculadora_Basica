package com.example.calculadora;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Hashtable;

public class Controller {

    @FXML
    private Label labelActual;

    @FXML
    private Label labelAnterior;

    private boolean hayComa = false;
    private boolean hayOperacion = false;
    private boolean igualPulsado = false;

    private String simbolo = "";
    private String simboloAnterior;

    private float operador1;
    private float operador2;
    private float resultadoAnterior = 0;

    private Evaluador evaluador;
    private Hashtable<String, Evaluador> calculador;

    public void init(Stage stage) {
        calculador = new Hashtable<String, Evaluador>();
        calculador.put("+", ((var a, var b) -> a + b));
        calculador.put("-", ((var a, var b) -> a - b));
        calculador.put("/", ((var a, var b) -> a / b));
        calculador.put("X", ((var a, var b) -> a * b));
        calculador.put("", ((var a, var b) -> b));
    }

    @FXML
    void answer(MouseEvent event) {
        labelActual.setText(String.valueOf(resultadoAnterior));
        hayComa = true;
        hayOperacion = false;
    }

    @FXML
    void borrar(MouseEvent event) {

        try {
            String ultimoCaracter = labelActual.getText().substring(labelActual.getText().length() - 1, labelActual.getText().length());
            if (ultimoCaracter.equals("."))
                hayComa = false;

            labelActual.setText(labelActual.getText().substring(0, labelActual.getText().length() - 1));
        } catch (StringIndexOutOfBoundsException e) {

        }
    }

    @FXML
    void limpiar(MouseEvent event) {
        labelActual.setText("");
        labelAnterior.setText("");
        operador1 = 0;
        operador2 = 0;
        simbolo = "";
        hayComa = false;
        igualPulsado = false;
    }

    public void limpiar_actual(MouseEvent mouseEvent) {
        labelActual.setText("");
        hayComa = false;
        igualPulsado = false;
    }

    @FXML
    void mostrarNumero(MouseEvent event) {

        if (!hayOperacion) {

            String num = ((Button) event.getSource()).getText();
            labelActual.setText(labelActual.getText() + num);

        } else if (hayOperacion) {

            hayOperacion = false;
            hayComa = false;

            labelActual.setText("");
            labelAnterior.setText("");

            String num = ((Button) event.getSource()).getText();
            labelActual.setText(labelActual.getText() + num);

        }
    }

    public void agregarComa(MouseEvent mouseEvent) {

        if (!hayComa) {
            if (labelActual.getText().length() == 0)
                labelActual.setText("0.");
            else
                labelActual.setText(labelActual.getText() + ".");

            hayComa = true;
        }
    }

    @FXML
    void mostrarOperador(ActionEvent event) {

        if (simbolo.isEmpty())
            simbolo = ((Button) event.getSource()).getText();
        else {
            simboloAnterior = simbolo;
            simbolo = ((Button) event.getSource()).getText();
            calcularOperacion(simboloAnterior);
        }

        try {
            operador1 = Float.parseFloat(labelAnterior.getText() + labelActual.getText());
        } catch (NumberFormatException e) {
            labelActual.setText("Err");
        }

        labelAnterior.setText(operador1 + simbolo);
        labelActual.setText("");

        hayComa = false;
        hayOperacion = false;
    }

    public void resolverOperacion(MouseEvent mouseEvent) {

        if (simbolo.equals("") /*&& !igualPulsado*/) {
            labelAnterior.setText("");
            calcularOperacion(simbolo);
            igualPulsado = true;
        } else if (!simbolo.equals("")) {
//            simbolo  = labelAnterior.getText().substring(labelAnterior.getText().length() - 1, labelAnterior.getText().length());
            calcularOperacion(simbolo);
            simbolo = "";
        }
    }

    private void calcularOperacion(String signo) {

        //La calculadora permite realizar operaciones con infinito

        hayComa = true;
        hayOperacion = true;

        try {
            operador2 = Float.parseFloat(labelActual.getText());

            evaluador = calculador.get(signo);

            float resultado = evaluador.resolver(operador1, operador2);

            labelAnterior.setText(labelAnterior.getText() + operador2);
            labelActual.setText(String.valueOf(resultado));

            operador1 = resultado;
            resultadoAnterior = operador1;

        } catch (NumberFormatException e) {
//            labelActual.setText("Err");
            labelActual.setText(String.valueOf(operador1));
            resultadoAnterior = operador1;
            hayOperacion = true;
        }/* catch (RuntimeException e) {
            labelActual.setText("Err");
            hayOperacion = true;
        }*/
    }
}