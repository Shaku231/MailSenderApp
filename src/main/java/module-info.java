module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires mail;
    requires javafx.web;

    opens org.mailsender to javafx.fxml;
    exports org.mailsender;
}