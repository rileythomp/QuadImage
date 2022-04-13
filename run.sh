javac src/*java
cp src/*class .
java Main ${1} ${2}
rm *class src/*class
