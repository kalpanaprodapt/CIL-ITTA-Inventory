# nso-cil-itta-inventory-api

Python / FastAPI **integration and query API** for the Uniti Common Integration Layer (CIL) ITTA network
inventory. It fronts the Spring Boot system-of-record services in `nso-cil-itta-inventory-service`.

Responsibilities per entity:

* typed request / response schemas (camelCase on the wire)
* search filter translation and CSV export
* normalisation of inbound records from external systems
* reconciliation (dry-run / apply) of external records against the system of record
* a field catalogue endpoint (`/meta`) consumed by the React UI

| Domain | Title | Entities | ASGI app |
|--------|-------|----------|----------|
| geography | Geography | Country, Region, City, Site, Building | `itta_api.domains.geography.app:app` |
| facility | Facility | Floor, Room, Rack, RackUnit, PowerFeed | `itta_api.domains.facility.app:app` |
| device | Network Device | NetworkDevice, Chassis, LineCard, PowerSupply, FanTray | `itta_api.domains.device.app:app` |
| port | Port and Interface | Port, Interface, SubInterface, LagGroup, Transceiver | `itta_api.domains.port.app:app` |
| cabling | Cabling | Cable, PatchPanel, PatchPort, Splice, FiberStrand | `itta_api.domains.cabling.app:app` |
| vendor | Vendor and Model | Vendor, HardwareModel, Sku, SupportContract, Warranty | `itta_api.domains.vendor.app:app` |
| software | Software and Licensing | SoftwareImage, SoftwareVersion, Patch, LicenseKey, LicensePool | `itta_api.domains.software.app:app` |
| ipam | IP Management | IpSubnet, IpAddress, IpPool, VrfInstance, DnsRecord | `itta_api.domains.ipam.app:app` |
| switching | L2 and L3 Routing | Vlan, VlanPool, RouteTarget, BgpSession, OspfArea | `itta_api.domains.switching.app:app` |
| circuit | Circuits | Circuit, CircuitSegment, CircuitProvider, CircuitTermination, CircuitOrder | `itta_api.domains.circuit.app:app` |
| topology | Topology and Links | Link, TopologyNode, TopologyEdge, TopologyView, PathTrace | `itta_api.domains.topology.app:app` |
| customer | Customers and SLA | Customer, CustomerAccount, CustomerContact, Contract, SlaProfile | `itta_api.domains.customer.app:app` |
| service | Services | ServiceCatalogItem, ServiceInstance, ServiceOrder, ServiceDependency, ServiceEndpoint | `itta_api.domains.service.app:app` |
| mpls | MPLS and VPN | MplsLsp, VpnService, PseudoWire, TunnelEndpoint, QosPolicy | `itta_api.domains.mpls.app:app` |
| optical | Optical | OpticalChannel, WdmSystem, OtnLink, Amplifier, Roadm | `itta_api.domains.optical.app:app` |
| radio | Radio and Mobile | RadioUnit, BaseStation, Antenna, Sector, BackhaulLink | `itta_api.domains.radio.app:app` |
| virtual | Virtual and Cloud | Vnf, Hypervisor, VirtualMachine, CloudAccount, K8sCluster | `itta_api.domains.virtual.app:app` |
| discovery | Discovery and Reconciliation | DiscoveryJob, DiscoveryProfile, DiscoveredAsset, ReconciliationRule, ReconciliationResult | `itta_api.domains.discovery.app:app` |
| change | Change and Work Management | ChangeRequest, MaintenanceWindow, WorkOrder, FieldTask, Technician | `itta_api.domains.change.app:app` |
| assetmgmt | Asset and Finance | Asset, AssetTag, PurchaseOrder, Invoice, DepreciationSchedule | `itta_api.domains.assetmgmt.app:app` |
| compliance | Compliance and Audit | ComplianceRule, ComplianceCheck, AuditRecord, ComplianceException, PolicyDocument | `itta_api.domains.compliance.app:app` |
| alarm | Alarms and Events | AlarmDefinition, AlarmInstance, EventSource, SuppressionRule, TicketLink | `itta_api.domains.alarm.app:app` |
| capacity | Capacity Planning | CapacityPlan, UtilizationSample, ThresholdPolicy, ForecastModel, Reservation | `itta_api.domains.capacity.app:app` |
| integration | Integration Layer | IntegrationEndpoint, SyncJob, SyncMapping, ImportBatch, WebhookSubscription | `itta_api.domains.integration.app:app` |

## Run

```
pip install -e .[dev]
INVENTORY_DEVICE_URL=http://localhost:8102 uvicorn itta_api.domains.device.app:app --reload
pytest
```
