
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
