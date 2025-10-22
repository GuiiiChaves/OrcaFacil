@echo off
title Testes OrçaFácil
echo ===============================
echo   🚀 Rodando testes OrçaFácil
echo ===============================
echo.

REM Caminho para o JUnit 5 (versão standalone)
set JUNIT_JAR=junit-platform-console-standalone-1.10.2.jar

REM Verifica se o JAR existe, senão baixa automaticamente
if not exist "%JUNIT_JAR%" (
    echo 📦 Baixando JUnit 5...
    powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar','%JUNIT_JAR%')"
)

echo 🔧 Compilando classes...
javac -d out -cp "%JUNIT_JAR%" src\main\java\com\orcafacil\**\*.java src\test\java\com\orcafacil\**\*.java

echo.
echo 🧪 Executando testes...
java -jar "%JUNIT_JAR%" -cp out --scan-class-path

echo.
echo ✅ Testes finalizados!
pause
