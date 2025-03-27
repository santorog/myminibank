
# 💼 MyMiniBank

Un projet Spring Boot minimaliste de gestion de comptes et transactions bancaires.

---

## 🔧 Fonctionnalités principales

- Création et consultation de comptes
- Récupération de transactions par compte
- Événements de transaction (Kafka)
- Service de notification
- Gestion des erreurs centralisée
- Validation personnalisée

---

## 🧱 Architecture technique

```mermaid
graph TD
    subgraph Web Layer
        A1[AccountController]
        T1[TransactionController]
    end

    subgraph Service Layer
        S1[AccountService]
        S2[TransactionService]
        K1[KafkaPublisherService]
        N1[NotificationService]
        ST1[TransactionStorageService]
    end

    subgraph Repository Layer
        R1[AccountRepository]
        R2[TransactionRepository]
    end

    subgraph Configuration
        Main[MyMiniBankApplication]
    end

    subgraph Exception Handling
        EH[GlobalExceptionHandler]
    end

    %% Relations
    A1 --> S1
    A1 --> S1
    T1 --> S2
    S1 --> R1
    S2 --> R2
    S1 --> K1
    K1 --> N1
    K1 --> ST1
```

### Endpoints principaux

- `GET /account/{id}` → AccountController
- `POST /account` → AccountController
- `GET /transaction/{id}` → TransactionController

---

## 📁 Structure du projet

| Dossier / fichier | Rôle |
|------------------|------|
| `controller/`     | API REST |
| `service/`        | Logique métier |
| `repository/`     | Accès base de données |
| `model/`          | Entités métier |
| `dto/`            | Objets de transfert API |
| `events/`         | Gestion des événements |
| `validator/`      | Validation custom |
| `exception/`      | Gestion des erreurs globales |

---

## ✅ Stack technique

- Java 21 (Amazon Corretto)
- Spring Boot
- Spring Data JPA
- H2 Database (test)
- Kafka (via abstraction événementielle)
