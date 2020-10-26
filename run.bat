call mvn clean install

call java.exe -Dfile.encoding=windows-1252 -jar target\jcstress.jar -v
pause