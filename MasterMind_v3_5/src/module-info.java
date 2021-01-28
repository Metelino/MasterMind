module MasterMind {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;

    opens MasterMind;
    opens MasterMind.ScenaGra;
    opens MasterMind.ScenaStart;
    opens MasterMind.ScenaConfig;
    opens MasterMind.ScenaWinLose;
}