module jfx {
    exports de.b4.jfx;
    requires javafx.base;
    requires javafx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires javafx.media;
    requires org.apache.commons.text;
    requires mp3agic;
    opens de.b4.jfx.model;
}