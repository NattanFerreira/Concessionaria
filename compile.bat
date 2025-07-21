@echo off
echo Compilando...
javac -cp "lib\sqlite-jdbc.jar" src\models\*.java src\data\*.java src\controllers\*.java src\ExemploNovoEstilo.java
if %errorlevel% equ 0 echo ✅ Compilado com sucesso!
