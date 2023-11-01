#!/bin/sh
rm -rf target/ID3Manager
jpackage --type app-image -n ID3Manager -m jfx/de.b4.jfx.Main --runtime-image target/image/ --dest target
