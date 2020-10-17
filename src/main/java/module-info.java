module jfx {
    exports de.b4.jfx;
    requires javafx.base;
    requires javafx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires java.desktop;
    requires javafx.media;
    requires mp3agic;
    opens de.b4.jfx.model;
}