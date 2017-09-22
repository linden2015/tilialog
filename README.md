# TiliaLog

A Java program that helps you log time.

![image](https://s27.postimg.org/5qz87modf/tilialog_0_10_reduced.png)

## Features

* Stamp current time (configurable)
* Generate report (configurable)
* Time calculations
* Close and open application without losing data

## Instructions

Run compiled code:

    bash run.bash

Run tests:

    bash test.bash

Package to jar:

    bash package.bash

Build from source:

    mvn test exec:java

Adding a desktop entry for Ubuntu Gnome:

* copy `tilialog.desktop.dist` to `tilialog.desktop`
* edit path of `Exec` to the correct directory
* copy the `.desktop` file to `~/.local/share/applications/`

## Dependencies

* Java 1.8

## Backlog

* Notify user of new version
* Dependency injection. Entire app is built in main.
* Automated UI testing
* Improved text readability
* Application can be used without Java installed

## License

Copyright 2017 NIELS VAN DER LINDEN

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

https://opensource.org/licenses/mit-license.php
