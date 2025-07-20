# 👨‍🏫 SkillTech – Plataforma web educativa

![Banner SkillTech](https://github.com/mariarosete/skillTech/blob/main/front/banner.png?raw=true)

SkillTech es una plataforma web desarrollada para la gestión de cursos y recursos educativos, dirigida a estudiantes y profesionales de informática. 
Brinda una experiencia de aprendizaje personalizada y acceso centralizado a materiales educativos.

---
## 📑 Tabla de contenidos

- [👨‍🏫 SkillTech – Plataforma web educativa](#-skilltech--plataforma-web-educativa)
- [🛠 Tecnologías utilizadas](#-tecnologías-utilizadas)
- [🚀 Funcionalidades destacadas](#-funcionalidades-destacadas)
- [✨ Aspectos destacados](#-aspectos-destacados)
- [💻 Cómo ejecutar el frontend](#-cómo-ejecutar-el-frontend)
- [💻 Cómo ejecutar el backend](#-cómo-ejecutar-el-backend)
- [🔐 Acceso al panel de administración](#-acceso-al-panel-de-administración)
- [📸 Capturas de pantalla](#-capturas-de-pantalla)
- [🔮 Próximas mejoras](#-próximas-mejoras)
- [📩 Contacto](#-contacto)
---

## 🛠 Tecnologías utilizadas

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)


---

## 🚀 Funcionalidades destacadas

### 👩‍💻 Para administradores:

- Crear, editar y eliminar cursos con título, descripción, categoría y materiales asociados.
- Gestionar usuarios (registro, edición y eliminación).
- Asignar materiales educativos a los cursos (PDF, enlaces, vídeos).
- Gestionar las inscripciones de los alumnos a los cursos.
- Visualizar y responder solicitudes de soporte enviadas por los usuarios.
  
### 🎓 Para alumnos:

- Registro e inicio de sesión con rol (alumno o administrador). Autenticación mediante JWT.
- Acceso a los cursos en los que está inscrito.
- Consulta y edición parcial de su perfil personal.
- Envío de solicitudes de soporte a través de un formulario de contacto.

---
## ✨ Aspectos destacados

- Separación clara entre backend (API REST con Spring Boot) y frontend (HTML + JS).
- Autenticación segura mediante **JWT** y control de acceso por roles.
- Panel de administración completo y funcional, con diseño responsive.
- Simulación realista de un entorno profesional de formación online.
---

## 💻 Cómo ejecutar el frontend


1. Clona este repositorio o descárgalo como ZIP.

   ```bash
   git clone https://github.com/mariarosete/skillTech.git
   ```

2. Abre la carpeta `front` en tu editor de código (por ejemplo, VSCode).

3. Navega hasta el archivo:

   ```
   front/usuarios/html/anonimo/indexAnonimo.html
   ```

4. Haz clic derecho sobre el archivo y selecciona **"Open with Live Server"**.

🔗 [Repositorio SkillTech (Frontend)](https://github.com/mariarosete/skillTech/tree/main/front)

---

## 💻 Cómo ejecutar el backend

1. Asegúrate de tener Java 17+ y Eclipse (o cualquier IDE compatible con Maven y Spring Boot).

2. Si aún no lo has hecho, clona este repositorio:

   ```bash
   git clone https://github.com/mariarosete/skillTech.git
   ```

3. En Eclipse, ve a:

   ```
   File > Import > Maven > Existing Maven Projects
   ```

4. Selecciona como **Root Directory** la carpeta:

   ```
   skillTech/back
   ```

5. Eclipse detectará automáticamente el archivo `pom.xml`. Márcalo y haz clic en **Finish**.

6. Una vez importado, ejecuta la clase principal:

   ```
   PdawMariaRoseteTokensApplication.java
   ```

   con clic derecho → **Run As > Spring Boot App**.

🔗 [Repositorio SkillTech (Backend)](https://github.com/mariarosete/skillTech/tree/main/back)

---

## 🔐 Acceso al panel de administración

Puedes acceder al panel de administración con las siguientes credenciales:

- **Usuario:** `maria`  
- **Contraseña:** `123`

---

## 📸 Capturas de pantalla

| 🏠 Inicio | 📋 Catálogo |
|----------|-------------|
| ![1](https://github.com/mariarosete/skillTech/blob/main/screenshots/1.png?raw=true) | ![4](https://github.com/mariarosete/skillTech/blob/main/screenshots/4.png?raw=true) |
| ![2](https://github.com/mariarosete/skillTech/blob/main/screenshots/2.png?raw=true) | ![5](https://github.com/mariarosete/skillTech/blob/main/screenshots/5.png?raw=true) |
| ![3](https://github.com/mariarosete/skillTech/blob/main/screenshots/3.png?raw=true) | |

| 📨 Contacto | 🔑 Inicio de sesión |
|-------------|----------------------|
| ![12](https://github.com/mariarosete/skillTech/blob/main/screenshots/12.png?raw=true) | ![6](https://github.com/mariarosete/skillTech/blob/main/screenshots/6.png?raw=true) |

| 👤 Panel de usuario (alumno) | |
|------------------------------|--|
| ![7](https://github.com/mariarosete/skillTech/blob/main/screenshots/7.png?raw=true) | ![8](https://github.com/mariarosete/skillTech/blob/main/screenshots/8.png?raw=true) |
| ![9](https://github.com/mariarosete/skillTech/blob/main/screenshots/9.png?raw=true) | ![10](https://github.com/mariarosete/skillTech/blob/main/screenshots/10.png?raw=true) |
| ![11](https://github.com/mariarosete/skillTech/blob/main/screenshots/11.png?raw=true) | |

| 📚 Panel de administración | 🔄 Gestión de usuarios |
|----------------------------|------------------------|
| ![13](https://github.com/mariarosete/skillTech/blob/main/screenshots/13.png?raw=true) | ![14](https://github.com/mariarosete/skillTech/blob/main/screenshots/14.png?raw=true) |
| ![15](https://github.com/mariarosete/skillTech/blob/main/screenshots/15.png?raw=true) |

| 📨 Solicitudes de soporte | 📂 Gestión de contenido |
|---------------------------|--------------------------|
| ![16](https://github.com/mariarosete/skillTech/blob/main/screenshots/16.png?raw=true) | ![17](https://github.com/mariarosete/skillTech/blob/main/screenshots/17.png?raw=true) |


---
## 🔮 Próximas mejoras

- Incorporación de estadísticas visuales sobre cursos y actividad del usuario.
- Soporte multilenguaje para mejorar la accesibilidad.
---
## 📩 Contacto

<p align="center">
  <a href="mailto:marlarosete89@gmail.com">
    <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" />
  </a>
  <a href="https://linkedin.com/in/mariarosetesuarez">
    <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" />
  </a>
  <a href="https://github.com/mariarosete">
    <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" />
  </a>
</p>


---

