# MicroservicioModeloReglas
Microservicio web utilizando JAVA para soluciones basadas en reglas de negocio


+---------------------------+      +--------------------------+      +----------------------------+
|      Servicio de Datos    |----->|   Servicio de Reglas     |<-----|    Interfaz de Gestión     |
|  (Clientes y Comerciales) |      | (Motor de Asignación)    |      |        de Reglas           |
|    (Spring Boot + JPA)    |<-----|                          |      | (App Web para no técnicos) |
+---------------------------+      +--------------------------+      +----------------------------+
          ^     |                            |
          |     | (Datos de Cliente)         | (Comercial Asignado)
          |     |                            |
          |     +----------------------------+
          |
+---------------------------+
|   AWS RDS para MySQL      |
|  (Base de Datos Central)  |
+---------------------------+