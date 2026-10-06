# Instrucciones para el Reto Individual - Pipeline de Calidad

## Requisitos Previos

Asegúrate de tener instalado:
- **Java 17+** (ya tienes Java 26 instalado)
- **Maven 3.9+** (ya está instalado)
- **Jenkins** - Debe estar instalado y ejecutándose
- **SonarQube** - Debe estar instalado y ejecutándose
- **JMeter 5.6+** - Descargar de https://jmeter.apache.org/download_jmeter.cgi
- **Git** - Para clonar el repositorio
- **Slack** - Workspace con canal para notificaciones

## Paso 1: Preparar el Proyecto

### 1.1 Compilar el proyecto

Abre una terminal en el directorio del proyecto:

```bash
cd C:\Users\Admin\Desktop\00_PSW_ValeryChumpitaz\PSW_Pipeline_Base
mvn clean compile
```

Si tienes problemas de conexión, asegúrate de tener internet para descargar las dependencias.

### 1.2 Ejecutar el proyecto

```bash
mvn spring-boot:run
```

El proyecto se ejecutará en `http://localhost:8085`

### 1.3 Probar los endpoints

En otra terminal o navegador:

**GET /products:**
```bash
curl http://localhost:8085/products
```

**POST /login:**
```bash
curl -X POST http://localhost:8085/login -H "Content-Type: application/json" -d "{\"username\":\"admin\",\"password\":\"123456\"}"
```

### Evidencia 1
Toma una captura de pantalla del proyecto ejecutándose correctamente.

---

## Paso 2: Configurar Jenkins

### 2.1 Instalar plugins necesarios en Jenkins

1. Abre Jenkins: `http://localhost:8080`
2. Ve a "Manage Jenkins" → "Manage Plugins"
3. Instala los siguientes plugins:
   - **Pipeline** (generalmente viene instalado)
   - **SonarQube Scanner for Jenkins**
   - **Slack Notification Plugin**
   - **Jacoco Plugin** (para cobertura de código)

### 2.2 Configurar SonarQube en Jenkins

1. Ve a "Manage Jenkins" → "Configure System"
2. Busca la sección "SonarQube servers"
3. Haz clic en "Add SonarQube"
4. Configura:
   - **Name**: `SonarQube`
   - **Server URL**: `http://localhost:9000` (o tu URL de SonarQube)
   - **Server authentication token**: Crea una credencial con tu token de SonarQube
5. Haz clic en "Apply" y "Save"

### 2.3 Crear el Pipeline en Jenkins

1. En Jenkins, haz clic en "New Item"
2. Nombre: `psw-pipeline`
3. Selecciona "Pipeline"
4. En "Pipeline" → "Definition", selecciona "Pipeline script from SCM"
5. SCM: Git
6. Repository URL: La ruta local o remota del proyecto
7. Script Path: `Jenkinsfile`
8. Haz clic en "Apply" y "Save"

### 2.4 Ajustar el Jenkinsfile

Abre el archivo `Jenkinsfile` y ajusta:
- `JMETER_HOME`: Ruta donde instalaste JMeter
- `APP_URL`: URL de tu aplicación (generalmente `http://localhost:8085`)

### Evidencia 2
Toma una captura de pantalla del pipeline de Jenkins mostrando todas las etapas.

---

## Paso 3: Analizar con SonarQube

### 3.1 Crear proyecto en SonarQube

1. Abre SonarQube: `http://localhost:9000`
2. Haz clic en "Create new project"
3. Nombre: `psw-pipeline-base`
4. Key: `psw-pipeline-base`
5. Copia el token de autenticación

### 3.2 Ejecutar análisis manualmente (opcional)

```bash
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=TU_TOKEN
```

### 3.3 Configurar Quality Gate

1. En SonarQube, ve a "Quality Gates"
2. Puedes usar el Quality Gate por defecto o crear uno personalizado
3. Asegúrate de que el proyecto tenga asignado un Quality Gate

### 3.4 Interpretar resultados

Después de ejecutar el pipeline, revisa SonarQube:
- **Bugs**: Errores de código
- **Vulnerabilities**: Problemas de seguridad
- **Code Smells**: Deuda técnica
- **Coverage**: Porcentaje de código cubierto por tests
- **Duplications**: Código duplicado

### Evidencia 3
Toma una captura de pantalla del dashboard de SonarQube.

### 3.5 Análisis de 3 problemas

Selecciona 3 problemas encontrados y para cada uno responde:

1. **Problema detectado**: Describe qué detectó SonarQube
2. **Por qué afecta al proyecto**: Explica el impacto
3. **Mejora propuesta**: Sugiere cómo solucionarlo

Ejemplo:
- **Problema**: Uso de credenciales hardcoded en AuthController
- **Impacto**: Riesgo de seguridad, las credenciales están expuestas en el código
- **Mejora**: Mover credenciales a variables de entorno o archivo de configuración externo

---

## Paso 4: Ejecutar Pruebas de Carga con JMeter

### 4.1 Configuración del plan de pruebas

El archivo `jmeter-tests/pipeline-test.jmx` ya está configurado con:
- **50 usuarios concurrentes**
- **Ramp-up de 10 segundos** (5 usuarios por segundo)
- **5 iteraciones por usuario** (total: 250 peticiones)
- **2 endpoints**: GET /products y POST /login

### 4.2 Abrir el plan en JMeter GUI

1. Abre JMeter: `jmeter.bat` (en Windows)
2. File → Open → selecciona `jmeter-tests/pipeline-test.jmx`
3. Verifica la configuración:
   - Thread Group: 50 threads, ramp-up 10s, 5 loops
   - HTTP Requests: GET /products y POST /login
   - Listeners: View Results Tree, Summary Report

### 4.3 Ejecutar prueba manualmente (opcional)

1. Asegúrate de que la aplicación esté ejecutándose en `http://localhost:8085`
2. En JMeter, haz clic en el botón verde "Start"
3. Espera a que termine (aprox. 10-15 segundos)
4. Revisa los resultados en "View Results Tree" y "Summary Report"

### Evidencia 4
Toma capturas de:
- Configuración de JMeter (Thread Group y HTTP Requests)
- Resultados de la ejecución (Summary Report)

---

## Paso 5: Analizar los Resultados de JMeter

Basándote en los resultados obtenidos, responde:

1. **¿Qué endpoint presentó mejor comportamiento?**
   - Compara tiempos de respuesta entre GET /products y POST /login

2. **¿Cuál presentó mayor tiempo de respuesta?**
   - Identifica el endpoint más lento

3. **¿Se produjeron errores?**
   - Revisa el porcentaje de errores en el reporte

4. **¿El sistema soportó la carga utilizada?**
   - Evalúa si el throughput y tiempos de respuesta son aceptables

5. **¿Qué mejora recomendarías?**
   - Sugerencias para optimizar el rendimiento

---

## Paso 6: Integrar Slack

Sigue las instrucciones en el archivo `SLACK_SETUP.md` para:
1. Crear una app en Slack
2. Configurar permisos
3. Crear el canal `#build-notifications`
4. Instalar el plugin de Slack en Jenkins
5. Configurar el token en Jenkins

### Evidencia 5
Toma una captura de pantalla de la notificación recibida en Slack después de ejecutar el pipeline.

---

## Paso 7: Ejecutar el Pipeline Completo

### 7.1 Asegúrate de que todo esté listo

- Jenkins está ejecutándose
- SonarQube está ejecutándose
- El proyecto está compilado
- La aplicación no está ejecutándose (el Jenkinsfile la iniciará)

### 7.2 Ejecutar el pipeline

1. En Jenkins, abre el job `psw-pipeline`
2. Haz clic en "Build Now"
3. Observa la ejecución en "Console Output"

### 7.3 Flujo del pipeline

1. **Checkout**: Descarga el código
2. **Build**: Compila el proyecto con Maven
3. **Unit Tests**: Ejecuta tests unitarios
4. **SonarQube Analysis**: Analiza calidad de código
5. **Quality Gate**: Verifica si cumple criterios de calidad
6. **Start Application**: Inicia la aplicación Spring Boot
7. **JMeter Tests**: Ejecuta pruebas de carga
8. **Stop Application**: Detiene la aplicación
9. **Slack Notification**: Envía notificación del resultado

---

## Documento de Entrega

Crea un documento con:

1. **Datos del estudiante**
   - Nombre
   - Carrera
   - Semestre

2. **Captura del proyecto funcionando**
   - Screenshot de la aplicación ejecutándose

3. **Captura del pipeline de Jenkins**
   - Screenshot mostrando todas las etapas

4. **Evidencia de SonarQube**
   - Screenshot del dashboard
   - Análisis de 3 problemas con:
     - Qué detectó
     - Por qué afecta
     - Mejora propuesta

5. **Configuración y resultados de JMeter**
   - Configuración (Thread Group, HTTP Requests)
   - Resultados (Summary Report)
   - Análisis de resultados:
     - Mejor endpoint
     - Peor endpoint
     - Errores producidos
     - Soporte de carga
     - Mejoras recomendadas

6. **Evidencia de Slack**
   - Screenshot de la notificación recibida

7. **Conclusiones**
   - Reflexión sobre la importancia del pipeline automatizado
   - Dificultades encontradas
   - Aprendizajes obtenidos

---

## Troubleshooting

### El proyecto no compila
- Verifica que tengas conexión a internet
- Ejecuta `mvn clean install -U` para forzar actualización de dependencias

### SonarQube no conecta
- Verifica que SonarQube esté ejecutándose en el puerto 9000
- Revisa el token de autenticación
- Verifica la URL del servidor en Jenkins

### JMeter no encuentra el archivo
- Ajusta la ruta `JMETER_HOME` en el Jenkinsfile
- Verifica que el archivo `.jmx` exista en la ruta correcta

### La aplicación no inicia en el pipeline
- Verifica que el puerto 8085 esté disponible
- Aumenta el tiempo de espera en el stage "Start Application"

### Slack no envía notificaciones
- Verifica el token de Slack
- Asegúrate de que el canal exista
- Revisa los permisos de la app de Slack

---

## Notas Importantes

- Las evidencias deben corresponder a tu propia ejecución
- No se evalúa obtener resultados "perfectos"
- Se evalúa que puedas configurar, ejecutar, interpretar y explicar el funcionamiento de cada etapa
- Documenta cualquier problema que encuentres y cómo lo solucionaste
