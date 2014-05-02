:: You will need to change the following path to point to your local java installation
set path=%path%;"C:\Program Files\Java\jdk1.7.0_55\bin"

del src\com\onedrinkaway\app\*.class src\com\onedrinkaway\common\*.class src\com\onedrinkaway\db\*.class src\com\onedrinkaway\machinelearning\*.class src\com\onedrinkaway\test\*.class

javac src\com\onedrinkaway\app\*.java src\com\onedrinkaway\common\*.java src\com\onedrinkaway\db\*.java src\com\onedrinkaway\machinelearning\*.java src\com\onedrinkaway\test\*.java