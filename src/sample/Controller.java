package sample;

import database_elements.DatabaseHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Film;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private DatabaseHandler databaseHandler;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfGenre;
    @FXML
    private TextField tfYear;
    @FXML
    private TextField tfRating;
    @FXML
    private Button insertBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableColumn<Film, Integer> colID;
    @FXML
    private TableColumn<Film, String> colTitle;
    @FXML
    private TableColumn<Film, String> colGenre;
    @FXML
    private TableColumn<Film, Integer> colYear;
    @FXML
    private TableColumn<Film, Integer> colRating;
    @FXML
    private TableView<Film> tvFilms;

    private ArrayList<TextField> textFields;

    private void setButtonListeners() {
        insertBtn.setOnMouseClicked(mouseEvent -> {
            if(textFieldsNotEmpty()){
                databaseHandler.insertRecord(
                        Integer.parseInt(tfID.getText()),
                        tfTitle.getText(), tfGenre.getText(),
                        Integer.parseInt(tfYear.getText()),
                        Integer.parseInt(tfRating.getText()));
                resetTextFields();
                showFilms();
            }else{
                warnUser();
            }
        });

        updateBtn.setOnMouseClicked(mouseEvent -> {
            if(textFieldsNotEmpty()){
                databaseHandler.updateRecord(Integer.parseInt(tfID.getText()),
                        tfTitle.getText(), tfGenre.getText(),
                        Integer.parseInt(tfYear.getText()),
                        Integer.parseInt(tfRating.getText()));
                resetTextFields();
                showFilms();
            }else{
                warnUser();
            }
        });


        deleteBtn.setOnMouseClicked(mouseEvent -> {
            if(!tfID.getText().isEmpty()){
                databaseHandler.deleteRecord(Integer.parseInt(tfID.getText()));
                showFilms();
            }else{
                tfID.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                tfID.setText("ID Required!");
            }

        });
    }

    public boolean textFieldsNotEmpty(){
        return !tfID.getText().isEmpty() && !tfTitle.getText().isEmpty() && !tfGenre.getText().isEmpty()
                && !tfYear.getText().isEmpty() && !tfRating.getText().isEmpty();
    }

    public void resetTextFields(){
        for(TextField textField : textFields){
            if(textField.getStyle() != null){
                textField.setStyle(null);
            }
            if(textField.getText() != null){
                textField.setText(null);
            }
        }
    }

    private void warnUser() {
        for(TextField textField : textFields){
            if(textField.getText().isEmpty()){
                textField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                textField.setText("required!");
            }
        }
    }

    private void setTableListener() {
        tvFilms.setOnMouseClicked(mouseEvent -> {
            Film film = tvFilms.getSelectionModel().getSelectedItem();
            tfID.setText(String.valueOf(film.getFilmID()));
            tfTitle.setText(film.getFilmTitle());
            tfGenre.setText(film.getFilmGenre());
            tfYear.setText(String.valueOf(film.getFilmYear()));
            tfRating.setText(String.valueOf(film.getFilmRating()));
        });
    }

    public void showFilms(){
        ObservableList<Film> filmList = databaseHandler.getFilms();
        colID.setCellValueFactory(new PropertyValueFactory<>("filmID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("filmTitle"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("filmGenre"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("filmYear"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("filmRating"));
        tvFilms.setItems(filmList);
    }

    private void createTextFieldList() {
        textFields = new ArrayList<>();
        textFields.add(tfID);
        textFields.add(tfTitle);
        textFields.add(tfGenre);
        textFields.add(tfYear);
        textFields.add(tfRating);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTextFieldList();
        databaseHandler = new DatabaseHandler();
        setButtonListeners();
        setTableListener();
        showFilms();
    }


}