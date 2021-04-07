#Group7
Zhang Tingrong 1155124498
Gao Jiacheng 1155124342
Yuan Chenjie 1155124316


#How to compile our project?
1st step: Please put all four .java files into a folder on your Linux machine.

2nd step: Please put all the database file into the a subfolder of the folder above. 
	For example, if .java files are inside "src", please build a new folder inside "src" called "txtdata", and put all the database file inside "data". The database txt files can only be put into the subfolder.

3rd step: Run "javac -d . mainHandler.java" to compile the project.


#How to run the project?
1st step: Run "java -cp .:mysql-connector-java-5.1.47.jar mainHandler" and you should see the system.

2nd step: Go to the system interface and use "Create Table" and "Insert Data" to build the database.
	When you are using the "Insert data" function, please type "txtdata" as the path, that is, the name of the subfolder without any slash like \ or /. You can put the database txt file in deeper subfolder, then you should type like "txtdata/database". There is no slash at the beginning and the end of the path.

3rd step: Go to "System interface" Use "Set System Date" to set the system date.

4nd step: Feel free to use other system interfaces and functions!