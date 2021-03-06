package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {

    @FXML
    public Button btnClose;

    @FXML
    private Button btnConvert;

    @FXML
    private TextArea tb_Source;

    @FXML
    private TextArea tbDest;

    @FXML
    public void OnButtonClick(ActionEvent event){
        String txt=tb_Source.getText();

        if(txt.length()==0){
           new Alert(Alert.AlertType.ERROR,"请输入需要转换的内容！").showAndWait();
        }

        String lineSeparator = "\n";
        String[] lines=txt.split(lineSeparator);
        StringBuilder sb=new StringBuilder();

        boolean bhaveEnd=false;
        if(lines.length>0){
            for(String line:lines){
                if(line.length()==0){
                    continue;
                }
                StringBuilder sb_Spaces=new StringBuilder();
                int beginIndex=0;
                for(char c:line.toCharArray()){
                    if(c!=' ')
                        break;
                    beginIndex++;
                    sb_Spaces.append(" ");
                }
                bhaveEnd=false;
                if(line.endsWith(";")){
                    bhaveEnd=true;
                    line=line.substring(beginIndex,line.length()-1);
                }
                String[] dots=line.split("=");

                if(dots.length==2){
                    String s1=dots[1]+" = "+dots[0];

                    if(bhaveEnd)
                        sb.append((sb_Spaces.toString()+s1+";"+lineSeparator));
                    else
                        sb.append(sb_Spaces.toString()+s1+lineSeparator);
                }
            }
        }

        tbDest.setText(sb.toString());;
    }

    @FXML
    public void OnOpenFile(ActionEvent event) throws IOException {
        FileChooser fileChooser=new FileChooser();

        File file=fileChooser.showOpenDialog(tbDest.getScene().getWindow());

        StringBuilder sb=new StringBuilder();
        if(file!=null){
            for(String s :Files.readAllLines(Paths.get(file.toURI()))){
                sb.append(s+"\n");
            }
        }

        tb_Source.setText(sb.toString());
    }

    public void onExit(ActionEvent actionEvent) {
        System.exit(1);
    }
}
