# Codestrukturanalyse mit jQAssistant

Dieses Repository zeigt verschiedene Möglichkeiten zur Codeanalyse mit jQAssistant. 


Zusätzliche Informationen findet ihr in der zugehörigen Blogreihe:
* Teil 1: [jQAssistant in die eigene Codebasis integrieren](https://uxitra.de/2024/04/04/codestrukturanalyse-mit-jqassistant-teil-1/)
* Teil 2: [Analyse und Überwachung der Codequalität mit jQAssistant](https://uxitra.de/2024/06/25/codeanalyse-mit-jqassistant-teil-2/)
* Teil 3: [Softwarestrukturanalyse mit jQAssistant](https://uxitra.de/2024/08/02/codeanalyse-mit-jqassistant-teil-3/)

## Aufbau
Das Projekt im Ordner `example-springboot-project` zeigt die integration von jQAssistant in ein Spring-Boot Maven Projekt. \
Die Struktur des Projekts besteht aus den drei Säulen `movie`, `scheudle` und `theater`. Hierbei hat `schedule` jeweils eine Abhängigkeit zu `movie` und `theater`. Die Struktur einer einzelnen Säule folgt intern dem Schichtenmodell.