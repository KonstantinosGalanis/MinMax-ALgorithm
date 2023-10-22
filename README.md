# MinMax-Algorithm

This is a programm written in java implementing the MinMax algorithm and the alpha-beta pruning variant. 
Before you run it make sure to download and import the [org.json](https://repo1.maven.org/maven2/org/json/json/20230227/json-20230227.jar) library.

# Functionality

The program repeatedly prints the following options menu: 

```
-i <filename> : insert tree from file
-j [<filename>] : print tree in the specified filename using JSON format
-d [<filename>] : print tree in the specified filename using DOT format
-c : calculate tree using min-max algorithm
-p : calculate tree using min-max and alpha-beta pruning optimization
-q : quit this program

$>
```
● -i <filepath>: The program reads the contents of the file <filepath>. If
the file is read successfully, the message “OK” is printed.

● -c: The MinMax algorithm for the tree is calculated.

● -p: MinMax algorithm with alpha-beta pruning optimization is calculated for the tree.

● -j <filepath>: Prints to file <filepath> the contents of the tree in format
JSON. Finally, if the file is written successfully, the message “OK” is printed.

● -d <filepath>: Prints to file <filepath> contents of tree in format
suitable for the graphviz suite.Finally, if the file is written successfully, the message “OK” is printed.

● -q: Iiteration stops and the program terminates.

# Compilation and Execution

First download the the [org.json](https://repo1.maven.org/maven2/org/json/json/20230227/json-20230227.jar) library. Then compile the file using the command:
```
javac -cp .:filepath/to/json/file/json-20230227.jar MinMax/MinMax.java
```
Finaly xecute the program using the command:
```
java -cp .:filepath/to/json/file/json-20230227.jar MinMax/MinMax
