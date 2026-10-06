# Configuración de Slack para Jenkins

## Paso 1: Crear una App en Slack

1. Ve a https://api.slack.com/apps
2. Haz clic en "Create New App"
3. Selecciona "From scratch"
4. Nombre de la app: `Jenkins Notifications`
5. Selecciona tu workspace de Slack
6. Haz clic en "Create App"

## Paso 2: Configurar Permissions (Permisos)

1. En el menú lateral, ve a "OAuth & Permissions"
2. En la sección "Scopes" → "Bot Token Scopes", agrega:
   - `chat:write` - Para enviar mensajes
   - `channels:join` - Para unirse a canales
   - `channels:read` - Para leer canales

3. Desplázate hacia abajo y haz clic en "Install to Workspace"
4. Copia el "Bot User OAuth Token" que empieza con `xoxb-`

## Paso 3: Crear un Canal en Slack

1. En tu workspace de Slack, crea un canal llamado `#build-notifications`
2. (Opcional) Si es privado, invita a la app usando `/invite @Jenkins Notifications`

## Paso 4: Instalar el Plugin de Slack en Jenkins

1. Abre Jenkins
2. Ve a "Manage Jenkins" → "Manage Plugins"
3. Busca "Slack Notification Plugin"
4. Instálalo y reinicia Jenkins

## Paso 5: Configurar Slack en Jenkins

1. Ve a "Manage Jenkins" → "Configure System"
2. Desplázate hasta la sección "Slack"
3. Configura:
   - **Workspace**: Tu nombre de workspace (ej: `miworkspace`)
   - **Credential**: Crea una nueva credencial tipo "Secret text"
     - Secret: El token `xoxb-` que copiaste
     - ID: `slack-bot-token`
   - **Default Channel**: `#build-notifications`
4. Haz clic en "Test Connection" para verificar
5. Haz clic en "Apply" y "Save"

## Paso 6: Usar en el Jenkinsfile

El Jenkinsfile ya incluye la configuración de Slack:

```groovy
post {
    success {
        slackSend(
            color: 'good',
            message: "✅ Pipeline SUCCESS: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
            channel: '#build-notifications'
        )
    }
    failure {
        slackSend(
            color: 'danger',
            message: "❌ Pipeline FAILED: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
            channel: '#build-notifications'
        )
    }
}
```

## Notas Importantes

- Asegúrate de que el token tenga los permisos correctos
- El canal debe existir en Slack antes de enviar notificaciones
- Si el token expira, necesitarás regenerarlo en la configuración de la app de Slack
