#!/bin/bash
# Script para executar OrçaFácil v2.0
# Requer: Java 21 LTS instalado

JAVA_HOME="/usr/lib/jvm/temurin-21-jdk-amd64"

if [ ! -d "$JAVA_HOME" ]; then
    # Tentar java no PATH
    JAVA_HOME=$(dirname $(dirname $(which java)))
fi

if [ ! -f "$JAVA_HOME/bin/java" ]; then
    echo "❌ ERRO: Java 21 não encontrado!"
    echo "Baixe em: https://adoptium.net/"
    exit 1
fi

cd "$(dirname "$0")"

# Compilar se necessário
if [ ! -d "out" ]; then
    echo "📦 Compilando projeto..."
    "$JAVA_HOME/bin/javac" -d out -sourcepath src src/com/orcafacil/Main.java
    if [ $? -ne 0 ]; then
        echo "❌ Erro na compilação!"
        exit 1
    fi
    echo "✅ Compilação concluída!"
fi

echo ""
echo "💰 Iniciando OrçaFácil v2.0 com Java 21..."
echo ""

# Executar
"$JAVA_HOME/bin/java" -cp out com.orcafacil.Main
