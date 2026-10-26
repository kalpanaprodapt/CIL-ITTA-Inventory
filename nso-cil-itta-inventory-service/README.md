# nso-cil-itta-inventory-service

System-of-record services for the Uniti **Common Integration Layer (CIL)** ITTA network inventory.
Java 17, Spring Boot 3.3.4, PostgreSQL 16, Flyway, Kafka.

Each bounded context is an independently deployable Spring Boot service built from the same layered
structure (`model`, `dto`, `mapper`, `repository`, `spec`, `validation`, `service`, `event`, `web`).

| Module | Domain | Entities | Port |
|--------|--------|----------|------|
| inventory-geography-service | Geography | Country, Region, City, Site, Building | 8100 |
| inventory-facility-service | Facility | Floor, Room, Rack, RackUnit, PowerFeed | 8101 |
| inventory-device-service | Network Device | NetworkDevice, Chassis, LineCard, PowerSupply, FanTray | 8102 |
| inventory-port-service | Port and Interface | Port, Interface, SubInterface, LagGroup, Transceiver | 8103 |
| inventory-cabling-service | Cabling | Cable, PatchPanel, PatchPort, Splice, FiberStrand | 8104 |
| inventory-vendor-service | Vendor and Model | Vendor, HardwareModel, Sku, SupportContract, Warranty | 8105 |
| inventory-software-service | Software and Licensing | SoftwareImage, SoftwareVersion, Patch, LicenseKey, LicensePool | 8106 |
| inventory-ipam-service | IP Management | IpSubnet, IpAddress, IpPool, VrfInstance, DnsRecord | 8107 |
| inventory-switching-service | L2 and L3 Routing | Vlan, VlanPool, RouteTarget, BgpSession, OspfArea | 8108 |
| inventory-circuit-service | Circuits | Circuit, CircuitSegment, CircuitProvider, CircuitTermination, CircuitOrder | 8109 |
| inventory-topology-service | Topology and Links | Link, TopologyNode, TopologyEdge, TopologyView, PathTrace | 8110 |
| inventory-customer-service | Customers and SLA | Customer, CustomerAccount, CustomerContact, Contract, SlaProfile | 8111 |
| inventory-service-service | Services | ServiceCatalogItem, ServiceInstance, ServiceOrder, ServiceDependency, ServiceEndpoint | 8112 |
| inventory-mpls-service | MPLS and VPN | MplsLsp, VpnService, PseudoWire, TunnelEndpoint, QosPolicy | 8113 |
| inventory-optical-service | Optical | OpticalChannel, WdmSystem, OtnLink, Amplifier, Roadm | 8114 |
| inventory-radio-service | Radio and Mobile | RadioUnit, BaseStation, Antenna, Sector, BackhaulLink | 8115 |
| inventory-virtual-service | Virtual and Cloud | Vnf, Hypervisor, VirtualMachine, CloudAccount, K8sCluster | 8116 |
| inventory-discovery-service | Discovery and Reconciliation | DiscoveryJob, DiscoveryProfile, DiscoveredAsset, ReconciliationRule, ReconciliationResult | 8117 |
| inventory-change-service | Change and Work Management | ChangeRequest, MaintenanceWindow, WorkOrder, FieldTask, Technician | 8118 |
| inventory-assetmgmt-service | Asset and Finance | Asset, AssetTag, PurchaseOrder, Invoice, DepreciationSchedule | 8119 |
| inventory-compliance-service | Compliance and Audit | ComplianceRule, ComplianceCheck, AuditRecord, ComplianceException, PolicyDocument | 8120 |
| inventory-alarm-service | Alarms and Events | AlarmDefinition, AlarmInstance, EventSource, SuppressionRule, TicketLink | 8121 |
| inventory-capacity-service | Capacity Planning | CapacityPlan, UtilizationSample, ThresholdPolicy, ForecastModel, Reservation | 8122 |
| inventory-integration-service | Integration Layer | IntegrationEndpoint, SyncJob, SyncMapping, ImportBatch, WebhookSubscription | 8123 |

## Build

```
mvn -B verify
mvn -B -pl inventory-device-service -am spring-boot:run
```

## Conventions

* Every table is tenant scoped (`tenant_id`) and carries `version`, `created_*`, `updated_*` audit columns.
* `code` is the business key, unique per tenant.
* Domain events are published to `itta.inventory.<domain>.<entity>` after commit.
* Deployment manifests and pipelines live in `nso-cil-itta-inventory-platform`.
