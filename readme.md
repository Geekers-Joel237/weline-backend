# Weline — Backend service

[🇬🇧 English](#english) · [🇫🇷 Français](#francais)

<a id="english"></a>
## 🇬🇧 English

Backend service for Weline, a virtual queue management platform — and a future assistant for administrative procedures — designed for the Cameroonian context. The project was built with robustness, scalability and sound engineering practices (DDD, TDD) in mind from day one.

### Tech stack

- **Language:** Java 21 (LTS)
- **Framework:** Spring Boot 3.x
- **Database:** PostgreSQL 16
- **Cache:** Redis 7
- **Build:** Gradle
- **Security:** Spring Security
- **Observability:** Spring Boot Actuator & Logback
- **API documentation:** springdoc-openapi (Swagger UI)
- **Containers:** Docker & Docker Compose

### Prerequisites

- [Git](https://git-scm.com/)
- [JDK 21](https://adoptium.net/) (Eclipse Temurin recommended)
- [Docker](https://www.docker.com/products/docker-desktop/) and Docker Compose
- An IDE of your choice (e.g. [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [VS Code](https://code.visualstudio.com/))

### Run locally

The whole development environment runs on Docker Compose — no manual PostgreSQL or Redis install needed.

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/Geekers-Joel237/weline-backend.git
    cd weline-backend
    ```

2.  **Start the full environment** — builds the Java image, pulls PostgreSQL and Redis, and starts the three services wired together:

    ```bash
    cp .env.example .env
    docker-compose up --build
    ```

    The first run can take a few minutes while base images download and the application builds.

3.  **Check that everything is up:**

    * **Health check:** open [http://localhost:8088/actuator/health](http://localhost:8088/actuator/health) — you should see `{"status":"UP"}`.
    * **Explore the API:** the interactive documentation (below) is the quickest way to try every endpoint.

4.  **Stop the environment:** press `Ctrl + C`, then:

    ```bash
    docker-compose down
    ```

### API documentation (Swagger UI)

**springdoc-openapi** generates interactive, always-current API documentation. Once the application is running, Swagger UI lets you browse every endpoint grouped by tag, inspect request and response models, and **send requests straight from the browser**.

**[http://localhost:8088/swagger-ui.html](http://localhost:8088/swagger-ui.html)** — the raw OpenAPI 3 spec is at `http://localhost:8088/v3/api-docs`.

### Architecture

The code follows **Domain-Driven Design** as a **modular monolith**: each business capability is isolated in its own package (bounded context).

- `com.geekersjoel237.weline.iam` — identity and access (users, agents, authentication).
- `com.geekersjoel237.weline.partners` — partner companies and their service points.
- `com.geekersjoel237.weline.queues` — the core domain: queues, tickets and turns.
- `com.geekersjoel237.weline.notifications` — outbound notifications (WhatsApp, push).
- `com.geekersjoel237.weline.shared` — shared configuration, utilities and infrastructure (e.g. `SecurityConfig`).

### CI/CD

**GitHub Actions** runs `.github/workflows/build.yml` on every push to `main` and `develop`, checking that the project compiles and that all unit and integration tests (`./gradlew test`) pass.

### MVP roadmap

- [x] **Epic 1: Project foundations & infrastructure**
- [x] **Epic 2: User onboarding & authentication**
- [x] **Epic 3: Queue core (user experience)**
- [ ] **Epic 4: Agent dashboard**

---

<a id="francais"></a>
## 🇫🇷 Français

Le service backend pour Weline, une plateforme de gestion de files d'attente virtuelles et un futur assistant de démarches, conçu pour le contexte camerounais. Ce projet est construit avec une philosophie privilégiant la robustesse, la scalabilité et les meilleures pratiques de développement (DDD, TDD) dès le départ.

### Stack Technique

Ce projet utilise une sélection de technologies modernes et éprouvées pour garantir une fondation solide :

- **Langage :** Java 21 (LTS)
- **Framework :** Spring Boot 3.x
- **Base de Données :** PostgreSQL 16
- **Cache :** Redis 7
- **Gestion de Build :** Gradle
- **Sécurité :** Spring Security
- **Observabilité :** Spring Boot Actuator & Logback
- **Documentation API :** springdoc-openapi (Swagger UI)
- **Conteneurisation :** Docker & Docker Compose

### Prérequis

Avant de commencer, assurez-vous d'avoir les outils suivants installés sur votre machine :

- [Git](https://git-scm.com/)
- [JDK 21](https://adoptium.net/) (Eclipse Temurin est recommandé)
- [Docker](https://www.docker.com/products/docker-desktop/) et Docker Compose
- Un IDE de votre choix (ex: [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [VS Code](https://code.visualstudio.com/))

### Installation & Lancement (Environnement Local)

L'intégralité de l'environnement de développement est gérée par Docker Compose. Aucune installation manuelle de PostgreSQL ou Redis n'est nécessaire.

1.  **Clonez le dépôt :**

    ```bash
    git clone https://github.com/Geekers-Joel237/weline-backend.git
    cd weline-backend
    ```

2.  **Lancez l'environnement complet :**
    Cette commande unique va construire l'image Docker de l'application Java, télécharger les images de PostgreSQL et Redis, et démarrer les trois services en les reliant entre eux.

    ```bash
    cp .env.example .env
    docker-compose up --build
    ```

    La première exécution peut prendre plusieurs minutes, le temps de télécharger les images de base et de construire l'application.

3.  **Vérifiez que tout fonctionne :**
    Une fois le démarrage terminé, vous devriez voir les logs des trois services dans votre terminal.

    * **Test de santé rapide :** Ouvrez votre navigateur et allez sur [http://localhost:8088/actuator/health](http://localhost:8088/actuator/health). Vous devriez voir `{"status":"UP"}`.
    * **Explorez l'API :** La meilleure façon de vérifier que tout est fonctionnel est d'utiliser la documentation interactive. Voir la section ci-dessous.

4.  **Pour arrêter l'environnement :**
    Appuyez sur `Ctrl + C` dans le terminal, puis exécutez la commande suivante pour arrêter et supprimer les conteneurs.

    ```bash
    docker-compose down
    ```

### Documentation de l'API (Swagger UI)

Ce projet intègre **springdoc-openapi** pour générer automatiquement une documentation d'API interactive et à jour.

Une fois l'application démarrée, vous pouvez accéder à l'interface Swagger UI pour :

- Voir tous les endpoints disponibles, groupés par tags.
- Consulter les détails de chaque endpoint (méthode HTTP, description, paramètres).
- Visualiser les modèles de données (DTOs) attendus en entrée et en sortie.
- **Exécuter des requêtes directement depuis votre navigateur** pour tester l'API.

**URL d'accès à la documentation :**

### [http://localhost:8088/swagger-ui.html](http://localhost:8088/swagger-ui.html)

Le fichier de spécification OpenAPI 3 brut est également disponible à l'adresse `http://localhost:8088/v3/api-docs`.

### Architecture du Projet

Le code est structuré en suivant les principes du **Domain-Driven Design (DDD)** sous forme d'un **Monolithe Modulaire**. Chaque fonctionnalité métier est isolée dans son propre package (Bounded Context) pour une meilleure organisation et une maintenance facilitée.

- `com.geekersjoel237.weline.iam` : Gestion des identités et des accès (utilisateurs, agents, authentification).
- `com.geekersjoel237.weline.partners` : Gestion des entreprises partenaires et de leurs points de service.
- `com.geekersjoel237.weline.queues` : Le cœur du métier. Gère toute la logique des files d'attente, des tickets et des tours.
- `com.geekersjoel237.weline.notifications` : Module technique responsable de l'envoi des notifications externes (WhatsApp, Push).
- `com.geekersjoel237.weline.shared` : Configurations communes, classes utilitaires et infrastructure partagée (ex: `SecurityConfig`).

### Stratégie de CI/CD

Ce projet utilise **GitHub Actions** pour l'intégration continue. Le workflow défini dans `.github/workflows/build.yml` se déclenche automatiquement à chaque `push` sur les branches `main` et `develop`.

Ce pipeline garantit que :

1.  Le projet compile correctement.
2.  Tous les tests unitaires et d'intégration (`./gradlew test`) passent avec succès.

### Roadmap du MVP

Statut du développement des fonctionnalités clés pour la version minimum viable.

- [x] **Epic 1 : Fondations du Projet & Infrastructure**
- [x] **Epic 2 : Onboarding & Authentification des Utilisateurs**
- [x] **Epic 3 : Le Cœur de la File d'Attente (Expérience Utilisateur)**
- [ ] **Epic 4 : Le Dashboard de l'Agent**

