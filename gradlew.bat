@rem
@rem Gradle startup script for Windows
@rem

@echo off
setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.

set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

if exist "%JAVA_HOME%\bin\java.exe" (
    set JAVACMD=%JAVA_HOME%\bin\java.exe
) else (
    where java >nul 2>nul
    if %ERRORLEVEL% EQU 0 (
        set JAVACMD=java
    ) else (
        echo.
        echo ERROR: JAVA_HOME is not set and no Java executable was found.
        echo Please install Java and configure JAVA_HOME.
        echo.
        exit /b 1
    )
)

"%JAVACMD%" ^
    "-Dorg.gradle.appname=%APP_BASE_NAME%" ^
    -classpath "%CLASSPATH%" ^
    org.gradle.wrapper.GradleWrapperMain %*

if %ERRORLEVEL% NEQ 0 (
    exit /b %ERRORLEVEL%
)

endlocal
