module MasterMind {
    requires javafx.fxml;
    requires javafx.controls;

    opens MasterMind;
    opens MasterMind.ScenaGra;
    opens MasterMind.ScenaStart;
    opens MasterMind.ScenaConfig;
    opens MasterMind.ScenaWinLose;
}