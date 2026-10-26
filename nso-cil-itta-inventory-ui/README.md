# nso-cil-itta-inventory-ui

React 18 + TypeScript + Vite single page application for the Uniti **CIL ITTA network inventory**.
It talks to `nso-cil-itta-inventory-api` (FastAPI), which fronts the Spring Boot services.

Every record type has the same feature folder: `types`, `schema` (zod), `api`, `hooks` (TanStack Query),
`columns`, `filterState`, `FilterBar`, `Form`, list / detail / create / edit pages and unit tests.

| Domain | Record types | Route |
|--------|--------------|-------|
| Geography | Country, Region, City, Site, Building | `/geography/…` |
| Facility | Floor, Room, Rack, RackUnit, PowerFeed | `/facility/…` |
| Network Device | NetworkDevice, Chassis, LineCard, PowerSupply, FanTray | `/device/…` |
| Port and Interface | Port, Interface, SubInterface, LagGroup, Transceiver | `/port/…` |
| Cabling | Cable, PatchPanel, PatchPort, Splice, FiberStrand | `/cabling/…` |
| Vendor and Model | Vendor, HardwareModel, Sku, SupportContract, Warranty | `/vendor/…` |
| Software and Licensing | SoftwareImage, SoftwareVersion, Patch, LicenseKey, LicensePool | `/software/…` |
| IP Management | IpSubnet, IpAddress, IpPool, VrfInstance, DnsRecord | `/ipam/…` |
| L2 and L3 Routing | Vlan, VlanPool, RouteTarget, BgpSession, OspfArea | `/switching/…` |
| Circuits | Circuit, CircuitSegment, CircuitProvider, CircuitTermination, CircuitOrder | `/circuit/…` |
| Topology and Links | Link, TopologyNode, TopologyEdge, TopologyView, PathTrace | `/topology/…` |
| Customers and SLA | Customer, CustomerAccount, CustomerContact, Contract, SlaProfile | `/customer/…` |
| Services | ServiceCatalogItem, ServiceInstance, ServiceOrder, ServiceDependency, ServiceEndpoint | `/service/…` |
| MPLS and VPN | MplsLsp, VpnService, PseudoWire, TunnelEndpoint, QosPolicy | `/mpls/…` |
| Optical | OpticalChannel, WdmSystem, OtnLink, Amplifier, Roadm | `/optical/…` |
| Radio and Mobile | RadioUnit, BaseStation, Antenna, Sector, BackhaulLink | `/radio/…` |
| Virtual and Cloud | Vnf, Hypervisor, VirtualMachine, CloudAccount, K8sCluster | `/virtual/…` |
| Discovery and Reconciliation | DiscoveryJob, DiscoveryProfile, DiscoveredAsset, ReconciliationRule, ReconciliationResult | `/discovery/…` |
| Change and Work Management | ChangeRequest, MaintenanceWindow, WorkOrder, FieldTask, Technician | `/change/…` |
| Asset and Finance | Asset, AssetTag, PurchaseOrder, Invoice, DepreciationSchedule | `/assetmgmt/…` |
| Compliance and Audit | ComplianceRule, ComplianceCheck, AuditRecord, ComplianceException, PolicyDocument | `/compliance/…` |
| Alarms and Events | AlarmDefinition, AlarmInstance, EventSource, SuppressionRule, TicketLink | `/alarm/…` |
| Capacity Planning | CapacityPlan, UtilizationSample, ThresholdPolicy, ForecastModel, Reservation | `/capacity/…` |
| Integration Layer | IntegrationEndpoint, SyncJob, SyncMapping, ImportBatch, WebhookSubscription | `/integration/…` |

```
npm ci
npm run dev        # http://localhost:5173, proxies /api to VITE_PROXY
npm test
npm run build
```
