# CIL / ITTA Network Inventory - fabricated code base

Synthetic but internally consistent code base for the Uniti **Common Integration Layer (CIL)** ITTA
network inventory. Repository names follow the `nso-cil-*` convention from
`CIL_NETWORK_Consolidated_Report_2026-10-22`.

Four repositories of 250,000 lines each (1,000,000 in total), all driven by one catalog of
24 domains and 120 inventory record types (geography, facility, devices, ports, cabling, vendors,
software, IPAM, routing, circuits, topology, customers, services, MPLS, optical, radio, virtual,
discovery, change, assets, compliance, alarms, capacity, integration).

| Repository | Stack | Content |
|------------|-------|---------|
| `nso-cil-itta-inventory-service` | Java 17, Spring Boot 3.3, JPA, Flyway, Kafka | 24 deployable services + shared module, system of record |
| `nso-cil-itta-inventory-api` | Python 3.12, FastAPI, Pydantic v2, httpx | 24 domain API facades: filters, normalisation, reconciliation, export, field catalogue |
| `nso-cil-itta-inventory-ui` | React 18, TypeScript, Vite, TanStack Query, zod | List / detail / create / edit for every record type |
| `nso-cil-itta-inventory-platform` | Docker, Helm, Kubernetes (rendered GitOps), Argo CD, Azure DevOps, Terraform | 49 workloads x 6 environments |

Request path: `React UI -> FastAPI facade -> Spring Boot service -> PostgreSQL / Kafka`.

The repositories were produced by the scripts in `../_generator`; they are not production code.
