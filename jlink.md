mkdir ./tmp
cp ~/.m2/repository/com/mpatric/mp3agic/0.9.1/mp3agic-0.9.1.jar ./tmp

jdeps --generate-module-info . <jar_path> 
javac --patch-module <module_name>=<jar_path> <module_name>/module-info.java 
jar uf <jar_path> -C <module_name> module-info.class
