/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editordecampos;

import HashMap.CHashMap;
import Util.Utilidades;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.SwingUtilities;
import jfxtras.labs.util.event.MouseControlUtil;

/**
 * FXML Controller class
 *
 * @author Cristian
 */
public class EditorDeCamposFXMLController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField dsDescricao;
    @FXML
    private TextField dsLabel;
    @FXML
    private ComboBox<String> dsTipo;
    @FXML
    private ComboBox<String> dsTipoValor;
    @FXML
    private TextField posX;
    @FXML
    private TextField posY;
    @FXML
    private TextField qtdAltura;
    @FXML
    private TextField qtdComprimento;
    @FXML
    private TextField dsAtributo;
    @FXML
    private TextArea dsSQl;
    @FXML
    private CheckBox isEditavel;

    @FXML
    private AnchorPane anchorPane;

    private static ArrayList<CampoDinamico> camposImport = new ArrayList<CampoDinamico>();
    private static ArrayList<CampoDinamico> campos = new ArrayList<CampoDinamico>();
    private static HashMap<CampoDinamico, Object> map = new HashMap<>();

    @FXML
    private TableView<CampoDinamico> tableOS;

    @FXML
    private TableColumn<CampoDinamico, Integer> columnIDOS;
    @FXML
    private TableColumn<CampoDinamico, String> columnDsDescricaoOS;
    @FXML
    private TableColumn<CampoDinamico, String> columnDsTipoOS;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregaComboBox();
        createImportedComponents();
        tableOS.setOnKeyReleased(getKeyEvent());

    }

    public void carregaComboBox() {
        SwingUtilities.invokeLater(() -> {
            dsTipo.getItems().addAll("TextField", "Label", "ComboBox", "TextArea");
            dsTipoValor.getItems().addAll("Letras", "Numérico", "Letras e numéricos");
        });
    }

    public void setCamposDinamicos(ArrayList<CampoDinamico> camposImport) {
        this.campos = camposImport;
        this.camposImport = new ArrayList<>();
        this.camposImport.addAll(camposImport);

    }

    public void createImportedComponents() {
        ArrayList<CampoDinamico> camposTemp = new ArrayList<>();
        camposTemp.addAll(campos);
        campos = new ArrayList<>();
        for (CampoDinamico campo : camposTemp) {
            try {
                switch (campo.getTipo().toUpperCase()) {
                    case "TEXTFIELD": {
                        montaTextField(campo);
                        break;
                    }
                    case "LABEL": {
                        montaLabel(campo);
                        break;
                    }
                    case "TEXTAREA": {
                        montaTextArea(campo);
                        break;
                    }
                    case "CHECKBOX": {
                        montaCheckBox(campo);
                        break;
                    }
                    case "COMBOBOX": {
                        montaComboBox(campo);
                        break;
                    }
                    case "DATEPICKER": {
                        montaDatePicker(campo);
                        break;
                    }
                    case "SEPARADOR": {
                        montaSeparador(campo);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createComponente(Object component, CampoDinamico campo) {
        CampoDinamico campoDinamico = new CampoDinamico();
        if (campo != null) {
            campoDinamico.setId(campo.getId());
            campoDinamico.setIsEditavel(true);
            campoDinamico.setDsSQL(campo.getDsSQL());
            campoDinamico.setDsDescricao(campo.getDsDescricao());
            campoDinamico.setLabel(campo.getLabel());
            campoDinamico.setTipoValor(campo.getTipoValor());
            campoDinamico.setDsAtributo(campo.getDsAtributo());
        } else {
            campoDinamico.setId(0);
            campoDinamico.setIsEditavel(true);
            campoDinamico.setDsSQL("");
            campoDinamico.setDsDescricao("");
            campoDinamico.setLabel("");
            campoDinamico.setTipoValor("");
            campoDinamico.setDsAtributo("");
        }

        if (component instanceof Button) {
            Button button = (Button) component;
            campoDinamico.setPosX((int) button.getLayoutX());
            campoDinamico.setPosY((int) button.getLayoutY());
            campoDinamico.setAltura((int) button.getPrefHeight());
            campoDinamico.setComprimento((int) button.getPrefWidth());
            campoDinamico.setLabel(button.getText());
            campoDinamico.setTipo("Label");
        } else if (component instanceof TextField) {
            TextField textField = (TextField) component;
            campoDinamico.setPosX((int) textField.getLayoutX());
            campoDinamico.setPosY((int) textField.getLayoutY());
            campoDinamico.setAltura((int) textField.getPrefHeight());
            campoDinamico.setComprimento((int) textField.getPrefWidth());
            campoDinamico.setTipo("TextField");
        } else if (component instanceof TextArea) {
            TextArea textArea = (TextArea) component;
            campoDinamico.setPosX((int) textArea.getLayoutX());
            campoDinamico.setPosY((int) textArea.getLayoutY());
            campoDinamico.setAltura((int) textArea.getPrefHeight());
            campoDinamico.setComprimento((int) textArea.getPrefWidth());
            campoDinamico.setTipo("TextArea");
        } else if (component instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) component;
            campoDinamico.setPosX((int) checkBox.getLayoutX());
            campoDinamico.setPosY((int) checkBox.getLayoutY());
            campoDinamico.setAltura((int) checkBox.getPrefHeight());
            campoDinamico.setComprimento((int) checkBox.getPrefWidth());
            campoDinamico.setLabel(checkBox.getText());
            campoDinamico.setTipo("CheckBox");
        } else if (component instanceof ComboBox) {
            ComboBox comboBox = (ComboBox) component;
            campoDinamico.setPosX((int) comboBox.getLayoutX());
            campoDinamico.setPosY((int) comboBox.getLayoutY());
            campoDinamico.setAltura((int) comboBox.getPrefHeight());
            campoDinamico.setComprimento((int) comboBox.getPrefWidth());
            campoDinamico.setTipo("ComboBox");
        } else if (component instanceof DatePicker) {
            DatePicker datePicker = (DatePicker) component;
            campoDinamico.setPosX((int) datePicker.getLayoutX());
            campoDinamico.setPosY((int) datePicker.getLayoutY());
            campoDinamico.setAltura((int) datePicker.getPrefHeight());
            campoDinamico.setComprimento((int) datePicker.getPrefWidth());
            campoDinamico.setTipo("DatePicker");
        } else if (component instanceof ToggleButton) {
            ToggleButton toggleButton = (ToggleButton) component;
            campoDinamico.setPosX((int) toggleButton.getLayoutX());
            campoDinamico.setPosY((int) toggleButton.getLayoutY());
            campoDinamico.setAltura((int) toggleButton.getPrefHeight());
            campoDinamico.setComprimento((int) toggleButton.getPrefWidth());
            campoDinamico.setLabel(toggleButton.getText());
            campoDinamico.setTipo("ToggleButton");
        } else if (component instanceof Separator) {
            Separator separador = (Separator) component;
            campoDinamico.setPosX((int) separador.getLayoutX());
            campoDinamico.setPosY((int) separador.getLayoutY());
            campoDinamico.setAltura((int) separador.getPrefHeight());
            campoDinamico.setComprimento((int) separador.getPrefWidth());
            campoDinamico.setTipo("Separador");
        }
        map.put(campoDinamico, component);
        campos.add(campoDinamico);
        carregaTable(false);
        tableOS.getSelectionModel().select(campoDinamico);
        carregaInfoPanel();
    }

    public void createLabelN() {
        Button label = new Button("Label");
        label.setPrefSize(100, 25);
        label.setLayoutX(200);
        label.setLayoutY(200);
        MouseControlUtil.makeDraggable(label, getEvent(), null);
        label.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(label));
        });
        label.setOnKeyReleased(getKeyEvent());
        setOnClick(label);
        anchorPane.getChildren().add(label);
        createComponente(label, null);
    }

    private void montaLabel(CampoDinamico campo) {
        Button label = new Button(campo.getLabel());
        label.setMinWidth(campo.getComprimento());
        label.setMinHeight(campo.getAltura());
        label.setPrefSize(campo.getComprimento(), campo.getAltura());
        label.setLayoutX(campo.getPosX());
        label.setLayoutY(campo.getPosY());
        MouseControlUtil.makeDraggable(label, getEvent(), null);
        label.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(label));
        });
        label.setOnKeyReleased(getKeyEvent());
        setOnClick(label);
        anchorPane.getChildren().add(label);
        createComponente(label, campo);

    }

    public void createTextField() {
        TextField textField = new TextField();
        textField.setPrefSize(100, 25);
        textField.setLayoutX(200);
        textField.setLayoutY(200);
        MouseControlUtil.makeDraggable(textField, getEvent(), null);
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(textField));
        });
        textField.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(textField);
        createComponente(textField, null);
    }

    private void montaTextField(CampoDinamico campo) {
        TextField textField = new TextField();
        textField.setMinWidth(campo.getComprimento());
        textField.setMinHeight(campo.getAltura());
        textField.setPrefSize(campo.getComprimento(), campo.getAltura());
        textField.setLayoutX(campo.getPosX());
        textField.setLayoutY(campo.getPosY());
        MouseControlUtil.makeDraggable(textField, getEvent(), null);
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(textField));
        });
        textField.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(textField);
        createComponente(textField, campo);
    }

    public void createTextArea() {
        TextArea textArea = new TextArea();
        textArea.setPrefSize(100, 25);
        textArea.setLayoutX(200);
        textArea.setLayoutY(200);
        MouseControlUtil.makeDraggable(textArea, getEvent(), null);
        textArea.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(textArea));
        });
        textArea.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(textArea);
        createComponente(textArea, null);
    }

    private void montaTextArea(CampoDinamico campo) {
        TextArea textArea = new TextArea();
        textArea.setMinWidth(campo.getComprimento());
        textArea.setMinHeight(campo.getAltura());
        textArea.setPrefSize(campo.getComprimento(), campo.getAltura());
        textArea.setLayoutX(campo.getPosX());
        textArea.setLayoutY(campo.getPosY());
        textArea.setEditable(campo.isIsEditavel());
        MouseControlUtil.makeDraggable(textArea, getEvent(), null);
        textArea.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(textArea));
        });
        textArea.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(textArea);
        createComponente(textArea, campo);
    }

    public void createDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefSize(100, 25);
        datePicker.setLayoutX(200);
        datePicker.setLayoutY(200);
        MouseControlUtil.makeDraggable(datePicker, getEvent(), null);
        datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(datePicker));
        });
        datePicker.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(datePicker);
        createComponente(datePicker, null);
    }

    private void montaDatePicker(CampoDinamico campo) {
        DatePicker datePicker = new DatePicker();
        datePicker.setMinWidth(campo.getComprimento());
        datePicker.setMinHeight(campo.getAltura());
        datePicker.setPrefSize(campo.getComprimento(), campo.getAltura());
        datePicker.setLayoutX(campo.getPosX());
        datePicker.setLayoutY(campo.getPosY());
        MouseControlUtil.makeDraggable(datePicker, getEvent(), null);
        datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(datePicker));
        });
        datePicker.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(datePicker);
        createComponente(datePicker, campo);
    }

    public void createComboBox() {
        ComboBox<Object> comboBox = new ComboBox<>();
        comboBox.setPrefSize(100, 25);
        comboBox.setLayoutX(200);
        comboBox.setLayoutY(200);
        MouseControlUtil.makeDraggable(comboBox, getEvent(), null);
        comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(comboBox));
        });
        comboBox.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(comboBox);
        createComponente(comboBox, null);
    }

    private void montaComboBox(CampoDinamico campo) {
        ComboBox comboBox = new ComboBox();
        comboBox.setMinWidth(campo.getComprimento());
        comboBox.setMinHeight(campo.getAltura());
        comboBox.setPrefSize(campo.getComprimento(), campo.getAltura());
        comboBox.setLayoutX(campo.getPosX());
        comboBox.setLayoutY(campo.getPosY());
        MouseControlUtil.makeDraggable(comboBox, getEvent(), null);
        comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(comboBox));
        });
        comboBox.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(comboBox);
        createComponente(comboBox, campo);
    }

    public void createToggleButton() {
        ToggleButton toggleButton = new ToggleButton("Toggle Button");
        toggleButton.setPrefSize(100, 25);
        toggleButton.setLayoutX(200);
        toggleButton.setLayoutY(200);
        MouseControlUtil.makeDraggable(toggleButton, getEvent(), null);
        toggleButton.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(toggleButton));
        });
        toggleButton.setOnKeyReleased(getKeyEvent());
        setOnClick(toggleButton);
        anchorPane.getChildren().add(toggleButton);
        createComponente(toggleButton, null);
    }

    private void montaToggleButton(CampoDinamico campo) {
        ToggleButton toggleButton = new ToggleButton(campo.getLabel());
        toggleButton.setMinWidth(campo.getComprimento());
        toggleButton.setMinHeight(campo.getAltura());
        toggleButton.setPrefSize(campo.getComprimento(), campo.getAltura());
        toggleButton.setLayoutX(campo.getPosX());
        toggleButton.setLayoutY(campo.getPosY());
        MouseControlUtil.makeDraggable(toggleButton, getEvent(), null);
        toggleButton.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(toggleButton));
        });
        toggleButton.setOnKeyReleased(getKeyEvent());
        setOnClick(toggleButton);
        anchorPane.getChildren().add(toggleButton);
        createComponente(toggleButton, campo);
    }

    public void createSeparador() {
        Separator separador = new Separator();
        separador.setPrefSize(100, 25);
        separador.setLayoutX(200);
        separador.setLayoutY(200);
        MouseControlUtil.makeDraggable(separador, getEvent(), null);
        separador.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(separador));
        });
        separador.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(separador);
        createComponente(separador, null);
    }

    private void montaSeparador(CampoDinamico campo) {
        Separator separador = new Separator();
        separador.setMinWidth(campo.getComprimento());
        separador.setMinHeight(campo.getAltura());
        separador.setPrefSize(campo.getComprimento(), campo.getAltura());
        separador.setLayoutX(campo.getPosX());
        separador.setLayoutY(campo.getPosY());
        MouseControlUtil.makeDraggable(separador, getEvent(), null);
        separador.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(separador));
        });
        separador.setOnKeyReleased(getKeyEvent());
        anchorPane.getChildren().add(separador);
        createComponente(separador, campo);
    }

    public void createCheckBox() {
        CheckBox checkBox = new CheckBox("CheckBox");
        checkBox.setPrefSize(100, 25);
        checkBox.setLayoutX(200);
        checkBox.setLayoutY(200);
        MouseControlUtil.makeDraggable(checkBox, getEvent(), null);
        checkBox.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(checkBox));
        });
        checkBox.setOnKeyReleased(getKeyEvent());
        setOnClick(checkBox);
        anchorPane.getChildren().add(checkBox);
        createComponente(checkBox, null);
    }

    private void montaCheckBox(CampoDinamico campo) {
        CheckBox checkBox = new CheckBox(campo.getLabel());
        checkBox.setMinWidth(campo.getComprimento());
        checkBox.setMinHeight(campo.getAltura());
        checkBox.setPrefSize(campo.getComprimento(), campo.getAltura());
        checkBox.setLayoutX(campo.getPosX());
        checkBox.setLayoutY(campo.getPosY());
        MouseControlUtil.makeDraggable(checkBox, getEvent(), null);
        checkBox.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            tableOS.getSelectionModel().select(getCampo(checkBox));
        });
        checkBox.setOnKeyReleased(getKeyEvent());
        setOnClick(checkBox);
        anchorPane.getChildren().add(checkBox);
        createComponente(checkBox, campo);
    }

    public void carregaTable(boolean later) {
        if (later) {
            SwingUtilities.invokeLater(() -> {
                columnIDOS.setCellValueFactory(new PropertyValueFactory<>("id"));
                columnDsDescricaoOS.setCellValueFactory(new PropertyValueFactory<>("dsDescricao"));
                columnDsTipoOS.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                tableOS.setItems(getCamposOS());
            });
        } else {
            columnIDOS.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnDsDescricaoOS.setCellValueFactory(new PropertyValueFactory<>("dsDescricao"));
            columnDsTipoOS.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            tableOS.setItems(getCamposOS());
        }
    }

    @SuppressWarnings("empty-statement")
    public ObservableList<CampoDinamico> getCamposOS() {
        ObservableList<CampoDinamico> camposObservable = FXCollections.observableArrayList();
        campos.forEach((campo) -> {
            camposObservable.add(campo);
        });

        return camposObservable;
    }

    public void carregaInfoPanel() {
        CampoDinamico campoDinamico = tableOS.getSelectionModel().getSelectedItem();
        if (campoDinamico != null) {
            id.setText(Utilidades.converteInt(campoDinamico.getId()));
            dsDescricao.setText(campoDinamico.getDsDescricao());
            dsLabel.setText(campoDinamico.getLabel());
            dsTipo.getSelectionModel().select(campoDinamico.getTipo());
            dsAtributo.setText(campoDinamico.getDsAtributo());
            //dsTipoValor;
            posX.setText(Utilidades.converteInt(campoDinamico.getPosX()));
            posY.setText(Utilidades.converteInt(campoDinamico.getPosY()));
            qtdAltura.setText(Utilidades.converteInt(campoDinamico.getAltura()));
            qtdComprimento.setText(Utilidades.converteInt(campoDinamico.getComprimento()));
            dsSQl.setText(campoDinamico.getDsSQL());
            isEditavel.setSelected(campoDinamico.isIsEditavel());
        } else {
            limpaCampos();
        }
    }

    private void limpaCampos() {
        id.setText("");
        dsDescricao.setText("");
        dsLabel.setText("");
        dsTipo.getSelectionModel().select(null);
        dsAtributo.setText("");
        //dsTipoValor;
        posX.setText("");
        posY.setText("");
        qtdAltura.setText("");
        qtdComprimento.setText("");
        dsSQl.setText("");
        isEditavel.setSelected(false);
    }

    public void salvarEAtualizar() {
        CampoDinamico campoDinamico = tableOS.getSelectionModel().getSelectedItem();
        Object component = map.get(campoDinamico);
        map.remove(campoDinamico);
        campos.remove(campoDinamico);

        if (campoDinamico != null) {
            campoDinamico.setId(Utilidades.validaInt(id.getText()));
            campoDinamico.setDsDescricao(dsDescricao.getText());
            campoDinamico.setLabel(dsLabel.getText());
            campoDinamico.setTipo(dsTipo.getSelectionModel().getSelectedItem());
            campoDinamico.setPosX(Utilidades.validaInt(posX.getText()));
            campoDinamico.setPosY(Utilidades.validaInt(posY.getText()));
            campoDinamico.setAltura(Utilidades.validaInt(qtdAltura.getText()));
            campoDinamico.setComprimento(Utilidades.validaInt(qtdComprimento.getText()));
            campoDinamico.setDsSQL(dsSQl.getText());
            campoDinamico.setIsEditavel(isEditavel.isSelected());
            campoDinamico.setDsAtributo(dsAtributo.getText());
            //dsTipoValor;

            if (component instanceof Button) {
                Button button = (Button) component;
                button.setLayoutX(campoDinamico.getPosX());
                button.setLayoutY(campoDinamico.getPosY());
                button.setText(campoDinamico.getLabel());
                button.setPrefSize(campoDinamico.getComprimento(), campoDinamico.getAltura());
            } else if (component instanceof TextField) {
                TextField textField = (TextField) component;
                textField.setLayoutX(campoDinamico.getPosX());
                textField.setLayoutY(campoDinamico.getPosY());
                textField.setPrefSize(campoDinamico.getComprimento(), campoDinamico.getAltura());
            } else if (component instanceof TextArea) {
                TextArea textArea = (TextArea) component;
                textArea.setLayoutX(campoDinamico.getPosX());
                textArea.setLayoutY(campoDinamico.getPosY());
                textArea.setPrefSize(campoDinamico.getComprimento(), campoDinamico.getAltura());
            } else if (component instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) component;
                checkBox.setLayoutX(campoDinamico.getPosX());
                checkBox.setLayoutY(campoDinamico.getPosY());
                checkBox.setPrefSize(campoDinamico.getComprimento(), campoDinamico.getAltura());
            } else if (component instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) component;
                comboBox.setLayoutX(campoDinamico.getPosX());
                comboBox.setLayoutY(campoDinamico.getPosY());
                comboBox.setPrefSize(campoDinamico.getComprimento(), campoDinamico.getAltura());
            } else if (component instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) component;
                datePicker.setLayoutX(campoDinamico.getPosX());
                datePicker.setLayoutY(campoDinamico.getPosY());
                datePicker.setPrefSize(campoDinamico.getComprimento(), campoDinamico.getAltura());
            } else if (component instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) component;
                toggleButton.setLayoutX(campoDinamico.getPosX());
                toggleButton.setLayoutY(campoDinamico.getPosY());
                toggleButton.setText(campoDinamico.getLabel());
                toggleButton.setPrefSize(campoDinamico.getComprimento(), campoDinamico.getAltura());
            } else if (component instanceof Separator) {
                Separator sepatador = (Separator) component;
                sepatador.setLayoutX(campoDinamico.getPosX());
                sepatador.setLayoutY(campoDinamico.getPosY());
                sepatador.setPrefSize(campoDinamico.getComprimento(), campoDinamico.getAltura());
            }
        }
        map.put(campoDinamico, component);
        campos.add(campoDinamico);
        carregaTable(true);
    }

    public EventHandler<MouseEvent> getEvent() {

        EventHandler<MouseEvent> eventHandler = (MouseEvent event) -> {
            tableOS.getSelectionModel().select(getCampo(event.getSource()));
            int layoutX = 0;
            int layoutY = 0;
            for (Node ol : anchorPane.getChildren()) {
                if (ol.equals(event.getSource())) {
                    layoutX = (int) ol.getLayoutX();
                    layoutY = (int) ol.getLayoutY();
                }
            }
            CampoDinamico campo = getCampo(event.getSource());
            campo.setPosX(layoutX);
            campo.setPosY(layoutY);
            carregaInfoPanel();
        };
        return eventHandler;
    }

    public void setOnClick(Object object) {
        if (object instanceof Button) {
            Button button = (Button) object;
            button.setOnMouseClicked((MouseEvent mouseEvent) -> {
                CampoDinamico campoDinamico = getCampo(object);
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    tableOS.getSelectionModel().select(campoDinamico);
                    carregaInfoPanel();
                    if (mouseEvent.getClickCount() == 2) {
                        button.setVisible(false);
                        TextArea textarea = new TextArea(button.getText());
                        textarea.setLayoutX(button.getLayoutX());
                        textarea.setLayoutY(button.getLayoutY());
                        textarea.setPrefWidth(button.getPrefWidth() + 50);
                        textarea.setPrefHeight(button.getHeight() + 10);
                        anchorPane.getChildren().add(textarea);

                        textarea.setOnKeyPressed(event -> {
                            System.out.println(event.getCode());
                            if (event.getCode().equals(KeyCode.ENTER)) {
                                button.setText(textarea.getText());
                                anchorPane.getChildren().remove(textarea);
                                atualizarCampo(campoDinamico, button);
                                button.setVisible(true);
                            }
                        });
                    }
                }
            });

        } else if (object instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) object;
            checkBox.setOnMouseClicked((MouseEvent mouseEvent) -> {
                CampoDinamico campoDinamico = getCampo(object);
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    tableOS.getSelectionModel().select(campoDinamico);
                    carregaInfoPanel();
                    if (mouseEvent.getClickCount() == 2) {
                        checkBox.setVisible(false);
                        TextArea textarea = new TextArea(checkBox.getText());
                        textarea.setLayoutX(checkBox.getLayoutX());
                        textarea.setLayoutY(checkBox.getLayoutY());
                        textarea.setPrefWidth(checkBox.getPrefWidth() + 50);
                        textarea.setPrefHeight(checkBox.getHeight() + 10);
                        anchorPane.getChildren().add(textarea);

                        textarea.setOnKeyPressed(event -> {
                            System.out.println(event.getCode());
                            if (event.getCode().equals(KeyCode.ENTER)) {
                                checkBox.setText(textarea.getText());
                                anchorPane.getChildren().remove(textarea);
                                atualizarCampo(campoDinamico, checkBox);
                                checkBox.setVisible(true);
                            }
                        });
                    }
                }
            });
        } else if (object instanceof ToggleButton) {
            ToggleButton toggleButton = (ToggleButton) object;
            toggleButton.setOnMouseClicked((MouseEvent mouseEvent) -> {
                CampoDinamico campoDinamico = getCampo(object);
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    tableOS.getSelectionModel().select(campoDinamico);
                    carregaInfoPanel();
                    if (mouseEvent.getClickCount() == 2) {
                        toggleButton.setVisible(false);
                        TextArea textarea = new TextArea(toggleButton.getText());
                        textarea.setLayoutX(toggleButton.getLayoutX());
                        textarea.setLayoutY(toggleButton.getLayoutY());
                        textarea.setPrefWidth(toggleButton.getPrefWidth() + 50);
                        textarea.setPrefHeight(toggleButton.getHeight() + 10);
                        anchorPane.getChildren().add(textarea);

                        textarea.setOnKeyPressed(event -> {
                            System.out.println(event.getCode());
                            if (event.getCode().equals(KeyCode.ENTER)) {
                                toggleButton.setText(textarea.getText());
                                anchorPane.getChildren().remove(textarea);
                                atualizarCampo(campoDinamico, toggleButton);
                                toggleButton.setVisible(true);
                            }
                        });
                    }
                }
            });
        } else if (object instanceof Node) {
            Node node = (Node) object;
            node.setOnMouseClicked((MouseEvent mouseEvent) -> {
                CampoDinamico campoDinamico = getCampo(object);
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    tableOS.getSelectionModel().select(campoDinamico);
                    carregaInfoPanel();
                }
            });
        }
    }

    public CampoDinamico getCampo(Object object) {
        for (CampoDinamico campo : map.keySet()) {
            if (map.get(campo).equals(object)) {
                return campo;
            }
        }
        return null;
    }

    private EventHandler<KeyEvent> getKeyEvent() {
        return (KeyEvent event) -> {
            if (tableOS.equals(event.getSource())) {
                if (tableOS.getSelectionModel().getSelectedItem() != null) {
                    CampoDinamico campoDinamico = tableOS.getSelectionModel().getSelectedItem();
                    anchorPane.getChildren().remove(map.get(campoDinamico));
                    campos.remove(campoDinamico);
                    map.remove(campoDinamico);
                    carregaTable(true);
                    limpaCampos();
                }
            } else if (event.getCode().equals(KeyCode.DELETE)) {
                if (campos.contains(getCampo(event.getSource()))) {
                    campos.remove(getCampo(event.getSource()));
                }
                if (map.containsKey(getCampo(event.getSource()))) {
                    map.remove(getCampo(event.getSource()));
                }

                anchorPane.getChildren().remove(event.getSource());
                carregaTable(true);
                limpaCampos();
            }
        };
    }

    private void atualizarCampo(CampoDinamico campo, Object object) {

        map.remove(campo);
        campos.remove(campo);
        if (object instanceof Button) {
            Button button = (Button) object;
            campo.setLabel(button.getText());
        } else if (object instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) object;
            campo.setLabel(checkBox.getText());
        } else if (object instanceof ToggleButton) {
            ToggleButton toggleButton = (ToggleButton) object;
            campo.setLabel(toggleButton.getText());
        }
        map.put(campo, object);
        campos.add(campo);
        carregaTable(true);
        carregaInfoPanel();
    }

    public static ArrayList<CampoDinamico> getCamposAlteradosDB() {
        ArrayList<CampoDinamico> camposAlterados = new ArrayList<CampoDinamico>();
        for (CampoDinamico c1 : campos) {
            if (c1.getId() == 0) {
                camposAlterados.add(c1);
            } else {
                for (CampoDinamico c2 : camposImport) {
                    if (c1.getId() == c2.getId()) {
                        if (!compareFields(c1, c2)) {
                            camposAlterados.add(c1);
                        }
                    }
                }
            }
        }
        return camposAlterados;
    }

    public static ArrayList<CampoDinamico> getCamposRemovidosDB() {
        ArrayList<CampoDinamico> camposRemovidos = new ArrayList<CampoDinamico>();
        for (CampoDinamico campoDinamico : camposImport) {
            boolean controle = false;
            for (CampoDinamico campoAtual : campos) {
                if (campoAtual.getId() == campoDinamico.getId()) {
                    controle = true;
                    break;
                }
            }
            if (!controle) {
                camposRemovidos.add(campoDinamico);
            }
        }
        return camposRemovidos;
    }

    public static boolean compareFields(CampoDinamico c1, CampoDinamico c2) {
        boolean iguais = true;
        if (!c1.getLabel().equals(c2.getLabel())) {
            iguais = false;
        }
        if (!c1.getDsDescricao().equals(c2.getDsDescricao())) {
            iguais = false;
        }
        if (!c1.getDsAtributo().equals(c2.getDsAtributo())) {
            iguais = false;
        }
        if (!c1.getDsSQL().equals(c2.getDsSQL())) {
            iguais = false;
        }
        if (!c1.getTipo().equals(c2.getTipo())) {
            iguais = false;
        }
        if (!c1.getTipoValor().equals(c2.getTipoValor())) {
            iguais = false;
        }
        if (!(c1.getAltura() == c2.getAltura())) {
            iguais = false;
        }
        if (!(c1.getComprimento() == c2.getComprimento())) {
            iguais = false;
        }
        if (!(c1.getPosX() == c2.getPosX())) {
            iguais = false;
        }
        if (!(c1.getPosY() == c2.getPosY())) {
            iguais = false;
        }

        return iguais;
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        System.out.println("exitApplication");
        Platform.exit();
    }

    @FXML
    public void stop(ActionEvent event) {
        System.out.println("Stage is closing");
    }
}
