#!/bin/sh
mp3agic=~/.m2/repository/com/mpatric/mp3agic/0.9.1/mp3agic-0.9.1.jar
jaudiotagger=~/.m2/repository/net/jthink/jaudiotagger/3.0.1/jaudiotagger-3.0.1.jar

jar_path=$mp3agic
module_name=mp3agic

mkdir tmp
cd tmp

jdeps --generate-module-info . $jar_path
javac --patch-module $module_name=$jar_path $module_name/module-info.java
jar uf $jar_path -C $module_name module-info.class
