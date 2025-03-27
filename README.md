
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
        A1[AccountController] --> S1[AccountService]
        A1 --> S1
        T1[TransactionController] --> S2[TransactionService]
    end

    subgraph Service Layer
        S1 --> R1[AccountRepository]
        S2 --> R2[TransactionRepository]
        S2 --> K1[KafkaPublisherService]
        S2 --> N1[NotificationService]
        S2 --> ST1[TransactionStorageService]
    end

    subgraph Repository Layer
        R1
        R2
    end

    subgraph Events
        K1
        N1
    end

    subgraph Utils
        ST1
    end

    subgraph Configuration
        Main[MyMiniBankApplication]
    end

    subgraph Exception Handling
        EH[GlobalExceptionHandler]
    end
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
