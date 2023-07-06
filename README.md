# Game Store Testing

**Ramo:** INF331

**Profesor:** Oscar Reyes

**Estudiante:** Paulina Vega

Simulador de una tienda de videojuegos utilizando Java y realizando pruebas unitarias con JUnit.

## Requerimientos

```
Maven home: C:\Users\Name\apache-maven-3.8.1\bin\..
Java version: 20.0.1, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-20.0.1
Default locale: en_US, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```
Es decir, la versión de **Java que se usa es OpenJDK 20.0.1** y la de **Maven es 3.8.1**. Tanto la versión de Java que se utilizó como _source_ como la versión que se utilizó como _target_ son las mismas. El resto de versiones de los plugins se especifican en el archivo `pom.xml`.

## Supuestos 

Para el simulador se aplican los siguientes supuestos para facilitar su implementación:
- Se asume que ni el modo de Administrador ni el modo de Cliente requieren de credenciales.
- Se asume que no se necesita agregar una tarjeta o método de pago, lo único que se requiere es agregar la cantidad a comprar y verificarlo.
- Se asume que si un juego llega a la cantidad de 0, este no desaparecerá de la tienda, solo se actualizará su cantidad.
- Se asume que la tienda de videojuegos no requiere de un buscador (en este simulador, la tienda es una especie de lista, por lo que para encontrar el juego se debe hacer manualmente).
- Se asume que una vez se cierra la aplicación todos los juegos agregados para vender y las acciones realizadas en esa sesión desaparecerán.
  - Se agregaron unos juegos "base" que aparecen cada vez que se inicializa la aplicación, estos juegos se pueden comprar, pero una vez se cierra la aplicación vuelven a su estado inicial.
- Se asume que se ingresan los datos del juego correctamente, ya que una vez colocados estos no se pueden editar.
- Se asume que se utiliza dinero del tipo `int`, es decir, se ingresan `100` y no `100.0`.

## Instalación

La aplicación fue escrita en VSCode pero todo funciona correctamente en la terminal si se instalaron correctamente las versiones anteriormente mencionadas de Java y Mavel. Para llevar a cabo las pruebas, una vez en la dirección de `...\Game-Store-Testing`, es decir, donde se encuentra el archivo `pom.xml`, se puede escribir en la terminal:

```
nvm test
```
Que valida, compila y corre las pruebas de JUnit. Este comando es útil si se quiere solo las pruebas.

```
nvm install
```
Que valida, compila, corre las pruebas, empaqueta, verifica e instala el paquete en el repositorio local. Este comando es útil se si quiere hacer las pruebas y obtener el paquete `.jar`. Una vez se tenga este archivo, se ingresa a su respectiva carpeta `...\Game-Store-Testing\target` y se escribe lo siguiente en la terminal para ejecutar la aplicación:

```
java -jar game-store-1.0-SNAPSHOT.jar
```


