#!/usr/bin/env bash
set -euo pipefail

cd -- "$(dirname -- "${BASH_SOURCE[0]}")"
temp_dir=$(mktemp -d)
java_pid=

aufraeumen() {
    if [[ -n "$java_pid" ]] && kill -0 "$java_pid" 2>/dev/null; then
        kill "$java_pid" 2>/dev/null || true
        wait "$java_pid" 2>/dev/null || true
    fi
    rm -rf -- "$temp_dir"
}
trap aufraeumen EXIT
trap 'exit 130' INT
trap 'exit 143' TERM

echo "Kompiliere Uebung 12 ..."
mvn -q compile dependency:build-classpath -Dmdep.outputFile="$temp_dir/classpath"

java -cp "target/classes:$(cat "$temp_dir/classpath")" \
    at.bbrz.oop.uebung12_observer.Uebung12 >"$temp_dir/ausgabe" 2>&1 &
java_pid=$!

echo "Java-Prozess: PID $java_pid"
echo "Threads teilen dieselbe PID; jeder Thread hat eine eigene TID."
echo "Anzeige: Hauptthread und Versandthreads (alle 100 ms)."
while kill -0 "$java_pid" 2>/dev/null; do
    printf '\n'
    # JVM-interne Threads ausblenden, damit die Versandthreads auffallen.
    ps -L -p "$java_pid" -o pid=PID,lwp=TID,stat=STATUS,comm=THREAD \
        | awk 'NR == 1 || $1 == $2 || $4 ~ /^webnachricht-/' || true
    sleep 0.1
done

status=0
wait "$java_pid" || status=$?
java_pid=
printf '\n--- Programmausgabe ---\n'
cat "$temp_dir/ausgabe"
exit "$status"
