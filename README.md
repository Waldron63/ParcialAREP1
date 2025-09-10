## Parcial Primer corte AREP 2025-2
# Santiago Gualdrón Rincón
para arrancar el servidor:
primero colocar 

mvn clean install

luego iniciar los servidores con 

java -cp target/classes edu.eci.arep.parcialarep1.FacadeServer.java

java -cp target/classes edu.eci.arep.parcialarep1.HttpServer.java

con los 2 servidores corriendo, la pagina inicial HTML (esta quemada dentro del codigo)

utilizando "http://localhost:45000/cliente"

para poder entrar al index de la página web.

<img width="819" height="599" alt="image" src="https://github.com/user-attachments/assets/246b0bc1-b755-4de3-b46a-2361bea21b63" />

cada boton hace la funcionalidad requerida, mostrada de la siquiente forma:
(NOTA: por temas de tiempo y de algun error en el JS, no se muestra el resultado en pantalla pero si imprime en FacadeServer la respuesta con el formato JSON)

- ADD: (NOTA: dar en el boton click, NO dar enter porque no ingresa los numeros a la lista)
  
  <img width="377" height="211" alt="image" src="https://github.com/user-attachments/assets/e5cb5cc9-f310-4e57-acef-036fcd5d26e8" />
  <img width="941" height="318" alt="image" src="https://github.com/user-attachments/assets/74b6bd76-caba-492e-a783-bf12d077a540" />


- LIST

  <img width="222" height="105" alt="image" src="https://github.com/user-attachments/assets/bf7754d4-cd09-4a08-abc5-30cb7d98623d" />

  añadiendo tambien los numeros 1, 3, 65, -5, 0.456
  <img width="1008" height="610" alt="image" src="https://github.com/user-attachments/assets/70fae5fe-4916-45ad-b1dd-1b3030011243" />
  
- CLEAR

  <img width="958" height="308" alt="image" src="https://github.com/user-attachments/assets/b181ffcc-7743-4065-b272-e2779f4f24e9" />
  <img width="1011" height="301" alt="image" src="https://github.com/user-attachments/assets/7e3cf556-1138-4c23-aa57-1e830f5f6978" />


-STATS

  <img width="1131" height="337" alt="image" src="https://github.com/user-attachments/assets/2dbabaa3-d63c-4cc8-97ce-56ab8420595f" />

video:
https://drive.google.com/file/d/1ZBQ0eZDn7ESCBYSJWp5YQpjb1M5QUH84/view?usp=drivesdk

