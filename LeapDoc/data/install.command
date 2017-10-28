#!/bin/bash

here="`dirname \"$0\"`"
cd "$here" || exit 1

echo "Installing MapleSaga. This may take a while."

mkdir -p ~/lib
cp -r MapleSaga.app/Contents/Frameworks/* ~/lib

mv MapleSaga.app/Contents/Resources/drive_c/Nexon/MapleStory/MapleSaga.game MapleSaga.app/Contents/Resources/drive_c/Nexon/MapleStory/MapleSaga.exe

osascript -e 'tell app "System Events" to display dialog "MapleSaga is installed! Double-click MapleSaga.app to start playing."'
