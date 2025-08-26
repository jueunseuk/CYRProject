<div id="top">

<!-- HEADER STYLE: CLASSIC -->
<div align="center">

# CYR PROJECT

<em>Empowering Innovation, Accelerating Impact Every Step</em>

<!-- BADGES -->
<img src="https://img.shields.io/github/last-commit/jueunseuk/CYRProject?style=flat&logo=git&logoColor=white&color=0080ff" alt="last-commit">
<img src="https://img.shields.io/github/languages/top/jueunseuk/CYRProject?style=flat&color=0080ff" alt="repo-top-language">
<img src="https://img.shields.io/github/languages/count/jueunseuk/CYRProject?style=flat&color=0080ff" alt="repo-language-count">

<em>Built with the tools and technologies:</em>

<img src="https://img.shields.io/badge/Spring Boot-6DB33F.svg?style=flat&logo=SpringBoot&logoColor=white" alt="Amazon">
<img src="https://img.shields.io/badge/Spring Security-6DB33F.svg?style=flat&logo=SpringSecurity&logoColor=white" alt="Amazon">
<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=flat&logo=Gradle&logoColor=white" alt="Gradle">
<br/>
<img src="https://img.shields.io/badge/MySQL-4479A1.svg?style=flat&logo=MySQL&logoColor=white" alt="MySQL">
<img src="https://img.shields.io/badge/AWS-FF9900.svg?style=flat&logo=Amazon&logoColor=white" alt="Amazon">
<img src="https://img.shields.io/badge/render-000000.svg?style=flat&logo=render&logoColor=white" alt="Docker">
<img src="https://img.shields.io/badge/Docker-2496ED.svg?style=flat&logo=Docker&logoColor=white" alt="Docker">
<br/>
<img src="https://img.shields.io/badge/intellijidea-000000.svg?style=flat&logo=intellijidea&logoColor=white" alt="Docker">

</div>
<br>

---

## Table of Contents

- [Overview](#overview)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Usage](#usage)
    - [Testing](#testing)
- [Features](#features)
- [Project Structure](#project-structure)

---

## Overview

CYRProject is a comprehensive backend framework tailored for building scalable, secure, and maintainable web applications. It integrates modern security practices, cloud storage solutions, and a rich domain model to streamline development workflows.

**Why CYRProject?**

This project aims to simplify backend development with a robust architecture that supports secure user management, media handling, and flexible data modeling. The core features include:

- 🛡️ **Security & Authentication:** Implements JWT, OAuth, and role-based access control for secure user interactions.
- ☁️ **Cloud Storage Integration:** Seamlessly manages media uploads and retrievals via Amazon S3.
- 📊 **Standardized API Responses:** Ensures consistent success and error messaging across all endpoints.
- 🖼️ **Media & Gallery Management:** Supports image uploads, galleries, and media organization.
- 🔧 **Modular Domain Models:** Rich entities for users, posts, comments, polls, and more, enabling flexible application design.
- 🚀 **Deployment & Build Automation:** Uses Gradle and Docker for streamlined build, testing, and deployment processes.

---

## Features

|      | Component            | Details                                                                                     |
| :--- | :------------------- | :------------------------------------------------------------------------------------------ |
| ⚙️  | **Architecture**     | <ul><li>Spring Boot MVC architecture</li><li>Layered structure: Controller, Service, Repository</li><li>Uses MySQL as primary database</li></ul> |
| 🔩 | **Code Quality**     | <ul><li>Uses Lombok for reducing boilerplate</li><li>Adheres to Java best practices</li><li>Gradle build with clear task separation</li></ul> |
| 📄 | **Documentation**    | <ul><li>Dockerfile for containerization</li><li>README includes setup and usage instructions</li></ul> |
| 🔌 | **Integrations**      | <ul><li>Spring Boot for web framework</li><li>JWT for authentication</li><li>Jsoup for HTML parsing</li><li>AWS SDK for cloud interactions</li></ul> |
| 🧩 | **Modularity**        | <ul><li>Component-based with clear separation of concerns</li><li>Uses Spring's dependency injection</li></ul> |
| 🧪 | **Testing**           | <ul><li>JUnit 5 (Jupiter) for unit tests</li><li>Tests cover controllers, services, repositories</li></ul> |
| ⚡️  | **Performance**       | <ul><li>Uses Spring Boot's asynchronous processing where applicable</li><li>Dockerized environment for consistent deployment</li></ul> |
| 🛡️ | **Security**          | <ul><li>JWT tokens for stateless authentication</li><li>Spring Security integration</li></ul> |
| 📦 | **Dependencies**      | <ul><li>Java 21 JDK</li><li>Spring Boot, Lombok, MySQL connector, AWS SDK, Jsoup, JUnit</li></ul> |

---

## Project Structure

```sh
└── CYRProject/
    ├── Dockerfile
    ├── README.md
    ├── build.gradle
    ├── gradlew
    ├── gradlew.bat
    ├── settings.gradle
    └── src
        └── main
```

---

## Getting Started

### Prerequisites

This project requires the following dependencies:

- **Programming Language:** Java
- **Package Manager:** Gradle
- **Container Runtime:** Docker

### Installation

Build CYRProject from the source and install dependencies:

1. **Clone the repository:**

    ```sh
    ❯ git clone https://github.com/jueunseuk/CYRProject
    ```

2. **Navigate to the project directory:**

    ```sh
    ❯ cd CYRProject
    ```

3. **Install the dependencies:**

**Using [docker](https://www.docker.com/):**

```sh
❯ docker build -t jueunseuk/CYRProject .
```
**Using [gradle](https://gradle.org/):**

```sh
❯ gradle build
```

### Usage

Run the project with:

**Using [docker](https://www.docker.com/):**

```sh
docker run -it {image_name}
```
**Using [gradle](https://gradle.org/):**

```sh
gradle run
```

### Testing

Cyrproject uses the {__test_framework__} test framework. Run the test suite with:

**Using [docker](https://www.docker.com/):**

```sh
echo 'INSERT-TEST-COMMAND-HERE'
```
**Using [gradle](https://gradle.org/):**

```sh
gradle test
```

---

<a href="#top">⬆ Return</a>

---
</div>