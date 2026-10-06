# PSW Pipeline Base - Configuración de CI/CD

Este proyecto ha sido configurado para implementar un pipeline automatizado de calidad continua con Jenkins, SonarQube, JMeter y Slack.

## 📁 Archivos de Configuración Creados

### 1. `Jenkinsfile`
Pipeline de Jenkins con las siguientes etapas:
- **Checkout**: Obtención del código
- **Build**: Compilación con Maven
- **Unit Tests**: Ejecución de tests unitarios con JaCoCo
- **SonarQube Analysis**: Análisis estático de código
- **Quality Gate**: Verificación de criterios de calidad
- **Start Application**: Inicio de la aplicación Spring Boot
- **JMeter Tests**: Pruebas de carga (50 usuarios, 5 iteraciones)
- **Stop Application**: Detención de la aplicación
- **Slack Notification**: Notificaciones automáticas

### 2. `sonar-project.properties`
Configuración de SonarQube:
- Project Key: `psw-pipeline-base`
- Análisis de código fuente en `src/main/java`
- Configuración de JaCoCo para cobertura
- Java 17

### 3. `jmeter-tests/pipeline-test.jmx`
Plan de pruebas de JMeter configurado con:
- **50 usuarios concurrentes**
- **Ramp-up de 10 segundos**
- **5 iteraciones por usuario** (250 peticiones totales)
- **2 endpoints testeados**:
  - GET /products
  - POST /login (con credenciales admin/123456)

### 4. `SLACK_SETUP.md`
Guía paso a paso para configurar Slack en Jenkins.

### 5. `INSTRUCCIONES.md`
Instrucciones completas para completar el reto individual.

## 🚀 Endpoints del Proyecto

### GET /products
```
http://localhost:8085/products
```
Retorna una lista de productos.

### POST /login
```
http://localhost:8085/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```
Autenticación con credenciales hardcoded.

## 📋 Próximos Pasos

1. **Instalar herramientas**:
   - Jenkins: http://localhost:8080
   - SonarQube: http://localhost:9000
   - JMeter: https://jmeter.apache.org/download_jmeter.cgi

2. **Configurar Jenkins**:
   - Instalar plugins (SonarQube Scanner, Slack Notification, Jacoco)
   - Configurar servidor SonarQube
   - Crear pipeline usando el Jenkinsfile

3. **Configurar Slack**:
   - Seguir instrucciones en `SLACK_SETUP.md`

4. **Ejecutar pipeline**:
   - Compilar proyecto: `mvn clean package`
   - Ejecutar en Jenkins
   - Recopilar evidencias

## ⚠️ Notas Importantes

- El proyecto usa Spring Boot 3.5.6 con Java 17
- El puerto de la aplicación es 8085
- Las credenciales de login están hardcoded (esto será detectado por SonarQube como vulnerabilidad)
- JaCoCo está configurado para generar reportes de cobertura

## 📚 Referencias

- [Documentación de Jenkins Pipelines](https://www.jenkins.io/doc/book/pipeline/)
- [Documentación de SonarQube](https://docs.sonarqube.org/)
- [Documentación de JMeter](https://jmeter.apache.org/usermanual/index.html)
