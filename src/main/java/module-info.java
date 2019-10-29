module de.b4.jfx {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires mp3agic;
    requires org.apache.commons.lang3;
    requires org.apache.commons.text;
    requires org.kordamp.ikonli.javafx;
    opens de.b4.jfx.model to javafx.base;
    exports de.b4.jfx;
 }