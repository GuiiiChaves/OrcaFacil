@REM Script para executar OrçaFácil v2.0
@REM Requer: Java 21 LTS instalado

@echo off
setlocal enabledelayedexpansion

REM Detectar Java 21
if exist "C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot\bin\java.exe" (
    set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot"
) else (
    REM Tentar Java no PATH
    where java >nul 2>nul
    if errorlevel 1 (
        echo ❌ ERRO: Java 21 nao encontrado!
        echo Baixe em: https://adoptium.net/
        exit /b 1
    )
    set "JAVA_HOME="
)

REM Definir caminho
cd /d "%~dp0"

REM Compilar se necessário
if not exist "out" (
    echo 📦 Compilando projeto...
    if defined JAVA_HOME (
        "!JAVA_HOME!\bin\javac.exe" -d out -sourcepath src src/com/orcafacil/Main.java
    ) else (
        javac -d out -sourcepath src src/com/orcafacil/Main.java
    )
    if errorlevel 1 (
        echo ❌ Erro na compilacao!
        exit /b 1
    )
    echo ✅ Compilacao concluida!
)

echo.
echo 💰 Iniciando OrçaFácil v2.0 com Java 21...
echo.

REM Executar
if defined JAVA_HOME (
    "!JAVA_HOME!\bin\java.exe" -cp out com.orcafacil.Main
) else (
    java -cp out com.orcafacil.Main
)

endlocal
