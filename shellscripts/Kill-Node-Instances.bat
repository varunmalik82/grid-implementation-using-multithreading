@ echo off
for /f "tokens=5 delims= " %%A in ('"netstat -ano | findstr 4723"') do taskkill /F /PID "%%A"