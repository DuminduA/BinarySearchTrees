# I have not added the data file since it will increase the 
# size of the submission material. Moodle does not allow us to 
# upload more than 5mb.

# So you need to add the data folder inside the src folder.
# in the paths I require data file path to be 
# /home/akalanka/IdeaProjects/A1_140019E/Assignment_01_140019E/src/data/insert/set1/data_1.txt

# So if you are getting a file not found error please check the path is correct for 
# data files

# when runnning the BST you might get a stackoverflow error
# to avoid that use the terminal/command prompt to run the code.

# you have to increase the stack size.
# following commads should work

javac BST.java
java -Xss68m BST

# you can use -Xss35m, -Xss18m, -Xss9m as well. But i havent check for those commands'
# for -Xss68m program runs perfectly

javac SplayBST.java
java -Xss68m SplayBST

javac RedBlackBST.java
java -Xss68m RedBlackBST
