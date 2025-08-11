module de.b4.jfx {
    exports de.b4.jfx;
    exports de.b4.jfx.model;
    requires javafx.base;
    requires javafx.controls;
    requires atlantafx.base;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires java.desktop;
    requires javafx.media;
    requires mp3agic;
    requires jaudiotagger;
    //requires de.b4.jfx;
    //requires javafx.graphics;
    opens de.b4.jfx.model;
}