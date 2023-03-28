package org.mailsender;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class PrimaryController {

    List<File> ATTACHMENTS = new ArrayList<>();
    @FXML
    Label senderMailText;
    @FXML
    Button sendMailButton;
    @FXML
    TextField recipientMail;
    @FXML
    TextField subject;
    @FXML
    TextArea textArea;
    @FXML
    ListView attachmentList;
    @FXML
    public void initialize() {
        senderMailText.setText(MailAuthenticator.username);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private void chooseFile(){
        FileChooser fc = new FileChooser();
        //fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files","*.pdf"));
        List<File> f = fc.showOpenMultipleDialog(null);
        ATTACHMENTS.addAll(f);
        for (File file: f){
            attachmentList.getItems().add(file.getAbsolutePath());
            attachmentList.setOpacity(1);
        }
    }
    @FXML
    private void deleteAttachmentFromList(){
        if(!ATTACHMENTS.isEmpty())
            ATTACHMENTS.remove(ATTACHMENTS.size()-1);
        attachmentList.getItems().clear();
        for (File file: ATTACHMENTS){
            attachmentList.getItems().add(file.getAbsolutePath());
            attachmentList.setOpacity(1);
        }
    }
    @FXML
    private void sendMail(){
        Message message;
        try{
            if(ATTACHMENTS == null){
                message = MailMessage.textMessage(recipientMail.getText(),subject.getText(),textArea.getText());
            }else{
                message = MailMessage.messageWithAttachments(recipientMail.getText(),subject.getText(),textArea.getText(), ATTACHMENTS);
            }

            Transport.send(message);
            recipientMail.clear();
            subject.clear();
            textArea.clear();
            attachmentList.getItems().clear();
            ATTACHMENTS = null;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Message sent!");

            alert.showAndWait();
        }catch (MessagingException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something went wrong!");
            alert.setHeaderText("We couldn't send your email");
            alert.setContentText("Please check if the recipient's address is correct");

            alert.showAndWait();
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
