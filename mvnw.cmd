@ECHO OFF
SET MAVEN_CLI_OPTS=%MAVEN_CLI_OPTS% -Xms256m -Xmx512m
SET MAVEN_OPTS=%MAVEN_OPTS% -Xmx512m

SET CURRENT_DIR=%CD%
SET CURRENT_PARAMS=%*

SET "WRAPPER_HOME=%CURRENT_DIR%\.mvn"
SET "WRAPPER_JAR=%WRAPPER_HOME%\wrapper\maven-wrapper.jar"
SET "WRAPPER_CONF=%WRAPPER_HOME%\wrapper\maven-wrapper.properties"

IF NOT EXIST "%WRAPPER_JAR%" (CALL :ERR "ERROR: Could not find Maven Wrapper JAR.")
IF NOT EXIST "%WRAPPER_CONF%" (CALL :ERR "ERROR: Could not find Maven Wrapper properties file.")

SET "WRAPPER_CMD=%JAVA_HOME%\bin\java" -Dmaven.multiModuleProjectDirectory="%CURRENT_DIR%" %MAVEN_OPTS% %MAVEN_CLI_OPTS% -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %CURRENT_PARAMS%

CALL %WRAPPER_CMD%

:END
EXIT /B %ERRORLEVEL%

:ERR
ECHO %1
EXIT /B 1
