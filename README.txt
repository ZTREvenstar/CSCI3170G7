#Group7
Zhang Tingrong 1155124498
Gao Jiacheng 1155124342
Yuan Chenjie 1155124316

#How to compile our project?
1st step: Please put all four .java files into a folder on your Linux machine.
2nd step: Please put all the database file into the a subfolder of the folder above. 
	For example, if .java files are inside "src", please build a new folder inside "src" called "data", and put all the database file inside "data".
3rd step: Run "javac -d . mainHandler.java system.java Bookstore.java Customer.java" to compile the project.

#How to run the project?
1st step: Run "java -cp .:mysql-connector-java-5.1.47.jar mainHandler" and you should see the system.
2nd step: Go to the system interface and use "Create Table" and "Insert Data" to build the database.
	When you using the "Insert data" function, please enter "data" as the path.
3rd step: Use "Set System Date" to set the system date
4nd step: Feel free to use other system interfaces and functions!