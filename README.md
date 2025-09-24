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

**CYRProject** is a customized fan community platform designed for fans of the artist **Choi Yoori**.  
It goes beyond the limitations of official fan cafes by providing a space where fans can **participate, create, and communicate freely**.

### Key Goals:
- Provide an integrated platform for both official and unofficial artist-related information.
- Enable fans to share and showcase their own creative works.
- Offer interactive social features (XP system, polls/surveys, event calendar) to foster an engaging fan culture.
- Maintain a safe and sustainable community with moderation tools.

---

## Target Audience & Market

- **Target Users:** Fans of Choi Yoori currently using fan cafes or online communities, and those interested in creative fan activities.
- **Market Focus:** Online fan community platforms and user-generated content ecosystems, tailored for specific artist fandoms.

---

## Features
| **Category**                | **Details**                                                                 |
|-----------------------------|-----------------------------------------------------------------------------|
| **Core Platform Features**  |                                                                             |
| 🔐 Authentication & Social Login | Easy registration and login, supporting OAuth 2.0                         |
| 🗂️ Segmented Community Boards  | Multiple boards for different purposes (concert reviews, fan letters, fan art, etc.) |
| 🖼️ Personalized Artist Gallery | Users can curate and share their own galleries of artist-related content   |
| 🏆 Experience & Achievement System | Earn XP and badges through posting, commenting, and attendance          |
| 📊 Polls & Surveys             | Fans can create polls on events, concerts, merchandise, with real-time results |
| 📅 Integrated Calendar         | Combines official schedules with fan-submitted events                      |
| 🛡️ Safety & Moderation         | User reporting and admin tools to ensure a healthy community               |
| **Architecture & Tech Stack** |                                                                            |
| ⚙️ Backend                     | Spring Boot, Java 21, Spring Security, OAuth 2.0, JPA, MySQL               |
| 🎨 Frontend                    | React, Vite, Styled Components, Recoil                                     |
| ☁️ Infrastructure              | AWS EC2, S3, RDS, Docker, Vercel                                           |
| ➕ Additional                  | Jsoup (HTML parsing), Lombok, JUnit for testing                            |

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
## Expected Impact

- Fan Community Activation: Encourage active participation through interactive boards, real-time chat, and XP rewards.

- Differentiated Fan Experience: Offers galleries, goods exchange, and polls not available in official fan cafes.

- User-Generated Content Growth: Supports fan-made artwork, covers, videos, with systems to highlight popular content.

- Potential Collaboration with Agencies: Community analytics provide quantitative insights into fan preferences.

- Safe & Sustainable Environment: User reporting and moderation ensure healthy community culture.

---

<a href="#top">⬆ Return</a>

---
</div>