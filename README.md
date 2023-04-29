# TSDC_Vinilos_DesarrolloMobile_Uniandes

## Como configurar la aplicacion en ambiente local?
### Descarga el repositorio
```shell
Git clone https://github.com/CBarreiro22/TSDC_Vinilos_DesarrolloMobile_Uniandes
```
### Abrir el proyecto con Android Studio

![image](https://user-images.githubusercontent.com/111206402/235317579-b6b4a851-f33e-45a7-9bcc-fbdb5864ee7c.png)

### Construir el proyecto
Para construir el proyecto dar Ctrl+F9 De esta forma se comenzara a contruir el proyecto

### Estructura del proyecto
* Directorio ```Java/com.andes.vinilos```
  * Directorio Modelo : Encontraras todos los modelos implementados en el programa en archivos .java
  * Directorio network : Encontrars los que es el service adapter del proyecto
  * Directorio repositorie : En este paquete se encuentra los repositorios que se utilizaran, estos repositorios son la entrada al modelo
  * Directorio UI: Donde se encontrar los fragmentos y los adpatadores
  * Directorio View Model: En este paquete se encuentra el código de los mediadores, mas conocidos como ViewModesl de MVVM
* Directorio res
  * Directorio drawable : Aqui encontramos todas las imagenes utilizadas en el modelo
  * Direcorio layout: Se encontrara todos las interface .xml de la aplicacion
  * Directorio Menu: Encontraras el menu de la aplicacion
  * Directorio Navigation: Encontraras la navegacion entre interfaces
  * Directorio Values: Aqui se encontrara las variable que se definieron en el proyecto
  * Directorio themes: Encontrar los .xml para tema diurno y nocturno
  
  
  
## Procedimientos de Pruebas automatizadas
Para este proyecto se implementaron las pruebas automatizadas en espresso, para correr dichas pruebas es necesario realizarlo en dispositivo fisico ingresado en modo desarrollador

### Estructura del test
Para saber donde esta ubicado el archivo de test, ve ha android studio y ver de forma de carpeta del proyecto 

![image](https://user-images.githubusercontent.com/111206402/235320904-3fe4ed98-c392-4a07-a70d-465715669df9.png)

Luego dirigirse a la ruta ```app/src/androidTest/java/com.andes.vinilos/```

![image](https://user-images.githubusercontent.com/111206402/235320968-562baa92-90e6-4e2d-8fdb-c0b325b70c4f.png)



