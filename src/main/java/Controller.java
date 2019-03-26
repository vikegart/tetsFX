import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private double eps = 0.001;
    //FXML
    //
    @FXML
    private LineChart<Double, Double> chart;
    @FXML
    private TableView<Graph> primaryTableView, secondaryTableView;
    @FXML
    private TableColumn<Graph, Double> xColumn, yColumn, xColumn1, yColumn1;
    @FXML
    private TableColumn<Graph, Integer> dotsAmountColumn, dotsAmountColumn1;
    @FXML
    private TextField xField, yField;

    @FXML
    private AnchorPane anchor;
    @FXML
    private List<Graph> graphs;
    //


    public Controller(){
        graphs = new ArrayList<>();
    }

    @FXML
    public void initialize(){
        initListeners();
        initTables();
        xField.setText("-2.5");
        yField.setText("2.5");
    }

    @FXML
    private void addDots(){
        List<Graph> graphList = secondaryTableView.getItems();
        for (int i = 0; i < graphList.size(); i++) {
            Graph graph =  graphList.get(i);
            if(graph != null){
                graph.addDots();
            }
        }
    }
    @FXML
    private void removeDots(){
        List<Graph> graphList = secondaryTableView.getItems();
        for (int i = 0; i < graphList.size(); i++) {
            Graph graph =  graphList.get(i);
            if(graph != null){
                graph.removeDots();
            }
        }
    }
    ///////
    @FXML
    private void createGraph(){
        double x0, y0;

        x0 = getX();
        y0 = getY();
        if(isFineValue(x0) && isFineValue(y0)){
            Graph newGraph = new Graph(x0, y0, eps);
            graphs.add(newGraph);
            primaryTableView.getItems().add(newGraph);
            chart.getData().addAll(newGraph.series);
        }
    }
    @FXML
    private void deleteGraph(){
        Graph graph = primaryTableView.getSelectionModel().getSelectedItem();
        if(graph != null){
            graphs.remove(graph);
            primaryTableView.getItems().remove(graph);
            secondaryTableView.getItems().remove(graph);
            chart.getData().removeAll(graph.series);
        }
    }
    //////
    @FXML
    private void removeGraph(){
        Graph graph = secondaryTableView.getSelectionModel().getSelectedItem();
        if(graph != null){
            secondaryTableView.getItems().remove(graph);
        }
    }
    @FXML
    private void addGraph(){
        Graph graph = primaryTableView.getSelectionModel().getSelectedItem();
        if(graph != null){
            secondaryTableView.getItems().add(graph);
        }
    }

    //WORK METHODS
    //
    private boolean isFineValue(double valueFromField){
        if(valueFromField == Double.MAX_VALUE){
            return false;
        }else{
            return true;
        }
    }
    private double getX(){
        try{
            String xStr = xField.getText();
            double xValue = Double.valueOf(xStr);
            return xValue;
        }catch (Exception e){
            System.err.println("Error: getX() + " + e);
            showMessageError("X field", "Bad X field value!");
            return Double.MAX_VALUE;
        }
    }
    private double getY(){
        try{
            String yStr = yField.getText();
            double yValue = Double.valueOf(yStr);
            return yValue;
        }catch (Exception e){
            System.err.println("Error: getY() + " + e);
            showMessageError("Y field", "Bad Y field value!");
            return Double.MAX_VALUE;
        }
    }

    //AUXILIARY METHODS
    //
    public static void showMessageError(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    //INIT METHODS
    //
    private void initListeners(){
        anchor.setOnKeyReleased(event -> {
            switch(event.getCode()){
                case ADD:
                    addDots();
                    break;
                case SUBTRACT:
                    removeDots();
                    break;
                case ESCAPE:
                    Stage stage = ((Stage)xField.getScene().getWindow());
                    stage.close();
                    break;
            }
        });
        primaryTableView.setOnKeyReleased(event -> {
            switch(event.getCode()){
                case DELETE:
                    deleteGraph();
                    break;
                case ENTER:
                    addGraph();
                    break;
            }
        });
        secondaryTableView.setOnKeyReleased(event -> {
            switch(event.getCode()){
                case DELETE:
                    removeGraph();
                    break;
            }
        });

        xField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try{
                        String regex = "-?\\d*.?\\d*";
                        int limit = 7;
                        if (!newValue.matches(regex) || newValue.length() > limit) {
                            xField.setText(oldValue);
                        }
                    }catch (Exception e){
                        System.err.println("Error: Joiner.setLimiter() + " + e);
                    }
                });
        yField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try{
                        String regex = "-?\\d*.?\\d*";
                        int limit = 7;
                        if (!newValue.matches(regex) || newValue.length() > limit) {
                            xField.setText(oldValue);
                        }
                    }catch (Exception e){
                        System.err.println("Error: Joiner.setLimiter() + " + e);
                    }
                });
    }
    private void initTables(){
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x0"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y0"));
        dotsAmountColumn.setCellValueFactory(new PropertyValueFactory<>("dotsAmount"));

        xColumn1.setCellValueFactory(new PropertyValueFactory<>("x0"));
        yColumn1.setCellValueFactory(new PropertyValueFactory<>("y0"));
        dotsAmountColumn1.setCellValueFactory(new PropertyValueFactory<>("dotsAmount"));
    }
}
