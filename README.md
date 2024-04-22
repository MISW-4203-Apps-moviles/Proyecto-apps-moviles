# Vinilos App

Vinilos es una aplicación móvil Android que permite visualizar y gestionar contenido asociado a
coleccionistas de música.

## Prerequisitos

- [Android Studio](https://developer.android.com/studio) (la última versión estable)
- Java Development Kit (JDK) 8 o superior

## Configuración del Proyecto

1. Clona el repositorio en tu máquina local:

```bash
git clone https://github.com/MISW-4203-Apps-moviles/Proyecto-apps-moviles.git
```

2. Abre Android Studio y selecciona `Open an existing Android Studio project` (Abrir un proyecto de
   Android Studio existente).
3. Navega hasta la carpeta del repositorio clonado y selecciónala.
4. Espera a que Android Studio sincronice el proyecto y descargue las dependencias requeridas.

## Ejecutando la Aplicación

Tienes dos opciones para ejecutar la aplicación: usando un emulador o un dispositivo físico.

### Ejecutando en un Emulador

1. En Android Studio, haz clic en el icono `AVD Manager` en la barra de herramientas o ve
   a `Tools` > `AVD Manager`.
2. Crea un nuevo Dispositivo Virtual o selecciona uno existente.
3. Haz clic en `Run` en la barra de herramientas o ve a `Run` > `Run 'app'`.
4. En la ventana `Select Deployment Target`, elige el emulador en el que quieres ejecutar tu
   aplicación y haz clic en `OK`.

Android Studio se encarga de instalar y compilar la aplicación en el emulador seleccionado.

### Ejecutando en un Dispositivo Físico

1. Habilita la depuración USB en tu dispositivo Android. Los pasos varían según tu dispositivo y
   versión de Android.
2. Conecta tu dispositivo a tu computadora a través de USB.
3. En Android Studio, haz clic en `Run` en la barra de herramientas o ve a `Run` > `Run 'app'`.
4. En la ventana `Select Deployment Target`, elige tu dispositivo conectado y haz clic en `OK`.

Android Studio se encarga de instalar y compilar la aplicación en el dispositivo.

## Compilando desde la Línea de Comandos

Si prefieres compilar la aplicación desde la línea de comandos, puedes usar el Gradle
wrapper (`gradlew` o `gradlew.bat` en Windows) incluido en el proyecto.

Para compilar una versión de depuración de la aplicación, ejecuta:

```bash
./gradlew assembleDebug
```

Para compilar una versión de lanzamiento de la aplicación, ejecuta:

```bash
./gradlew assembleRelease
```

Para instalar una versión de depuración de la aplicación en un dispositivo conectado, ejecuta:

```bash
./gradlew installDebug
```

Para más información [Build your app from the command line](https://developer.android.com/build/building-cmdline).
