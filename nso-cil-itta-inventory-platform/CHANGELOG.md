# Changelog - nso-cil-itta-inventory-platform

All notable changes to this repository are listed here.

## 1.0.0 - 2026-10-22

* [geography] Country: added schema, validation rules and search filters
* [geography] Region: added reconciliation support for discovery imports
* [geography] City: added CSV export with the same filters as search
* [geography] Site: improved search performance with composite indexes
* [geography] Building: added domain events for create, update, delete and status change
* [facility] Floor: aligned enum values with the integration layer contract
* [facility] Room: added tenant scoped unique business code
* [facility] Rack: added bulk create and status change endpoints
* [facility] RackUnit: documented lifecycle statuses and allowed transitions
* [facility] PowerFeed: added optimistic locking via expected version
* [device] NetworkDevice: added field catalogue endpoint for the UI
* [device] Chassis: tightened field length limits after the data quality review
* [device] LineCard: added schema, validation rules and search filters
* [device] PowerSupply: added reconciliation support for discovery imports
* [device] FanTray: added CSV export with the same filters as search
* [port] Port: improved search performance with composite indexes
* [port] Interface: added domain events for create, update, delete and status change
* [port] SubInterface: aligned enum values with the integration layer contract
* [port] LagGroup: added tenant scoped unique business code
* [port] Transceiver: added bulk create and status change endpoints
* [cabling] Cable: documented lifecycle statuses and allowed transitions
* [cabling] PatchPanel: added optimistic locking via expected version
* [cabling] PatchPort: added field catalogue endpoint for the UI
* [cabling] Splice: tightened field length limits after the data quality review
* [cabling] FiberStrand: added schema, validation rules and search filters
* [vendor] Vendor: added reconciliation support for discovery imports
* [vendor] HardwareModel: added CSV export with the same filters as search
* [vendor] Sku: improved search performance with composite indexes
* [vendor] SupportContract: added domain events for create, update, delete and status change
* [vendor] Warranty: aligned enum values with the integration layer contract
* [software] SoftwareImage: added tenant scoped unique business code
* [software] SoftwareVersion: added bulk create and status change endpoints
* [software] Patch: documented lifecycle statuses and allowed transitions
* [software] LicenseKey: added optimistic locking via expected version
* [software] LicensePool: added field catalogue endpoint for the UI
* [ipam] IpSubnet: tightened field length limits after the data quality review
* [ipam] IpAddress: added schema, validation rules and search filters
* [ipam] IpPool: added reconciliation support for discovery imports
* [ipam] VrfInstance: added CSV export with the same filters as search
* [ipam] DnsRecord: improved search performance with composite indexes
* [switching] Vlan: added domain events for create, update, delete and status change
* [switching] VlanPool: aligned enum values with the integration layer contract
* [switching] RouteTarget: added tenant scoped unique business code
* [switching] BgpSession: added bulk create and status change endpoints
* [switching] OspfArea: documented lifecycle statuses and allowed transitions
* [circuit] Circuit: added optimistic locking via expected version
* [circuit] CircuitSegment: added field catalogue endpoint for the UI
* [circuit] CircuitProvider: tightened field length limits after the data quality review
* [circuit] CircuitTermination: added schema, validation rules and search filters
* [circuit] CircuitOrder: added reconciliation support for discovery imports
* [topology] Link: added CSV export with the same filters as search
* [topology] TopologyNode: improved search performance with composite indexes
* [topology] TopologyEdge: added domain events for create, update, delete and status change
* [topology] TopologyView: aligned enum values with the integration layer contract
* [topology] PathTrace: added tenant scoped unique business code
* [customer] Customer: added bulk create and status change endpoints
* [customer] CustomerAccount: documented lifecycle statuses and allowed transitions
* [customer] CustomerContact: added optimistic locking via expected version
* [customer] Contract: added field catalogue endpoint for the UI
* [customer] SlaProfile: tightened field length limits after the data quality review
* [service] ServiceCatalogItem: added schema, validation rules and search filters
* [service] ServiceInstance: added reconciliation support for discovery imports
* [service] ServiceOrder: added CSV export with the same filters as search
* [service] ServiceDependency: improved search performance with composite indexes
* [service] ServiceEndpoint: added domain events for create, update, delete and status change
* [mpls] MplsLsp: aligned enum values with the integration layer contract
* [mpls] VpnService: added tenant scoped unique business code
* [mpls] PseudoWire: added bulk create and status change endpoints
* [mpls] TunnelEndpoint: documented lifecycle statuses and allowed transitions
* [mpls] QosPolicy: added optimistic locking via expected version
* [optical] OpticalChannel: added field catalogue endpoint for the UI
* [optical] WdmSystem: tightened field length limits after the data quality review
* [optical] OtnLink: added schema, validation rules and search filters
* [optical] Amplifier: added reconciliation support for discovery imports
* [optical] Roadm: added CSV export with the same filters as search
* [radio] RadioUnit: improved search performance with composite indexes
* [radio] BaseStation: added domain events for create, update, delete and status change
* [radio] Antenna: aligned enum values with the integration layer contract
* [radio] Sector: added tenant scoped unique business code
* [radio] BackhaulLink: added bulk create and status change endpoints
* [virtual] Vnf: documented lifecycle statuses and allowed transitions
* [virtual] Hypervisor: added optimistic locking via expected version
* [virtual] VirtualMachine: added field catalogue endpoint for the UI
* [virtual] CloudAccount: tightened field length limits after the data quality review
* [virtual] K8sCluster: added schema, validation rules and search filters
* [discovery] DiscoveryJob: added reconciliation support for discovery imports
* [discovery] DiscoveryProfile: added CSV export with the same filters as search
* [discovery] DiscoveredAsset: improved search performance with composite indexes
* [discovery] ReconciliationRule: added domain events for create, update, delete and status change
* [discovery] ReconciliationResult: aligned enum values with the integration layer contract
* [change] ChangeRequest: added tenant scoped unique business code
* [change] MaintenanceWindow: added bulk create and status change endpoints
* [change] WorkOrder: documented lifecycle statuses and allowed transitions
* [change] FieldTask: added optimistic locking via expected version
* [change] Technician: added field catalogue endpoint for the UI
* [assetmgmt] Asset: tightened field length limits after the data quality review
* [assetmgmt] AssetTag: added schema, validation rules and search filters
* [assetmgmt] PurchaseOrder: added reconciliation support for discovery imports
* [assetmgmt] Invoice: added CSV export with the same filters as search
* [assetmgmt] DepreciationSchedule: improved search performance with composite indexes
* [compliance] ComplianceRule: added domain events for create, update, delete and status change
* [compliance] ComplianceCheck: aligned enum values with the integration layer contract
* [compliance] AuditRecord: added tenant scoped unique business code
* [compliance] ComplianceException: added bulk create and status change endpoints
* [compliance] PolicyDocument: documented lifecycle statuses and allowed transitions
* [alarm] AlarmDefinition: added optimistic locking via expected version
* [alarm] AlarmInstance: added field catalogue endpoint for the UI
* [alarm] EventSource: tightened field length limits after the data quality review
* [alarm] SuppressionRule: added schema, validation rules and search filters
* [alarm] TicketLink: added reconciliation support for discovery imports
* [capacity] CapacityPlan: added CSV export with the same filters as search
* [capacity] UtilizationSample: improved search performance with composite indexes
* [capacity] ThresholdPolicy: added domain events for create, update, delete and status change
* [capacity] ForecastModel: aligned enum values with the integration layer contract
* [capacity] Reservation: added tenant scoped unique business code
* [integration] IntegrationEndpoint: added bulk create and status change endpoints
* [integration] SyncJob: documented lifecycle statuses and allowed transitions
* [integration] SyncMapping: added optimistic locking via expected version
* [integration] ImportBatch: added field catalogue endpoint for the UI
* [integration] WebhookSubscription: tightened field length limits after the data quality review
* [geography] Country: added bulk create and status change endpoints
* [geography] Region: documented lifecycle statuses and allowed transitions
* [geography] City: added optimistic locking via expected version
* [geography] Site: added field catalogue endpoint for the UI
* [geography] Building: tightened field length limits after the data quality review
* [facility] Floor: added schema, validation rules and search filters
* [facility] Room: added reconciliation support for discovery imports
* [facility] Rack: added CSV export with the same filters as search
* [facility] RackUnit: improved search performance with composite indexes
* [facility] PowerFeed: added domain events for create, update, delete and status change
* [device] NetworkDevice: aligned enum values with the integration layer contract
* [device] Chassis: added tenant scoped unique business code
* [device] LineCard: added bulk create and status change endpoints
* [device] PowerSupply: documented lifecycle statuses and allowed transitions
* [device] FanTray: added optimistic locking via expected version
* [port] Port: added field catalogue endpoint for the UI
* [port] Interface: tightened field length limits after the data quality review
* [port] SubInterface: added schema, validation rules and search filters
* [port] LagGroup: added reconciliation support for discovery imports
* [port] Transceiver: added CSV export with the same filters as search
* [cabling] Cable: improved search performance with composite indexes
* [cabling] PatchPanel: added domain events for create, update, delete and status change
* [cabling] PatchPort: aligned enum values with the integration layer contract
* [cabling] Splice: added tenant scoped unique business code
* [cabling] FiberStrand: added bulk create and status change endpoints
* [vendor] Vendor: documented lifecycle statuses and allowed transitions
* [vendor] HardwareModel: added optimistic locking via expected version
* [vendor] Sku: added field catalogue endpoint for the UI
* [vendor] SupportContract: tightened field length limits after the data quality review
* [vendor] Warranty: added schema, validation rules and search filters
* [software] SoftwareImage: added reconciliation support for discovery imports
* [software] SoftwareVersion: added CSV export with the same filters as search
* [software] Patch: improved search performance with composite indexes
* [software] LicenseKey: added domain events for create, update, delete and status change
* [software] LicensePool: aligned enum values with the integration layer contract
* [ipam] IpSubnet: added tenant scoped unique business code
* [ipam] IpAddress: added bulk create and status change endpoints
* [ipam] IpPool: documented lifecycle statuses and allowed transitions
* [ipam] VrfInstance: added optimistic locking via expected version
* [ipam] DnsRecord: added field catalogue endpoint for the UI
* [switching] Vlan: tightened field length limits after the data quality review
* [switching] VlanPool: added schema, validation rules and search filters
* [switching] RouteTarget: added reconciliation support for discovery imports
* [switching] BgpSession: added CSV export with the same filters as search
* [switching] OspfArea: improved search performance with composite indexes
* [circuit] Circuit: added domain events for create, update, delete and status change
* [circuit] CircuitSegment: aligned enum values with the integration layer contract
* [circuit] CircuitProvider: added tenant scoped unique business code
* [circuit] CircuitTermination: added bulk create and status change endpoints
* [circuit] CircuitOrder: documented lifecycle statuses and allowed transitions
* [topology] Link: added optimistic locking via expected version
* [topology] TopologyNode: added field catalogue endpoint for the UI
* [topology] TopologyEdge: tightened field length limits after the data quality review
* [topology] TopologyView: added schema, validation rules and search filters
* [topology] PathTrace: added reconciliation support for discovery imports
* [customer] Customer: added CSV export with the same filters as search
* [customer] CustomerAccount: improved search performance with composite indexes
* [customer] CustomerContact: added domain events for create, update, delete and status change
* [customer] Contract: aligned enum values with the integration layer contract
* [customer] SlaProfile: added tenant scoped unique business code
* [service] ServiceCatalogItem: added bulk create and status change endpoints
* [service] ServiceInstance: documented lifecycle statuses and allowed transitions
* [service] ServiceOrder: added optimistic locking via expected version
* [service] ServiceDependency: added field catalogue endpoint for the UI
* [service] ServiceEndpoint: tightened field length limits after the data quality review
* [mpls] MplsLsp: added schema, validation rules and search filters
* [mpls] VpnService: added reconciliation support for discovery imports
* [mpls] PseudoWire: added CSV export with the same filters as search
* [mpls] TunnelEndpoint: improved search performance with composite indexes
* [mpls] QosPolicy: added domain events for create, update, delete and status change
* [optical] OpticalChannel: aligned enum values with the integration layer contract
* [optical] WdmSystem: added tenant scoped unique business code
* [optical] OtnLink: added bulk create and status change endpoints
* [optical] Amplifier: documented lifecycle statuses and allowed transitions
* [optical] Roadm: added optimistic locking via expected version
* [radio] RadioUnit: added field catalogue endpoint for the UI
* [radio] BaseStation: tightened field length limits after the data quality review
* [radio] Antenna: added schema, validation rules and search filters
* [radio] Sector: added reconciliation support for discovery imports
* [radio] BackhaulLink: added CSV export with the same filters as search
* [virtual] Vnf: improved search performance with composite indexes
* [virtual] Hypervisor: added domain events for create, update, delete and status change
* [virtual] VirtualMachine: aligned enum values with the integration layer contract
* [virtual] CloudAccount: added tenant scoped unique business code
* [virtual] K8sCluster: added bulk create and status change endpoints
* [discovery] DiscoveryJob: documented lifecycle statuses and allowed transitions
* [discovery] DiscoveryProfile: added optimistic locking via expected version
* [discovery] DiscoveredAsset: added field catalogue endpoint for the UI
* [discovery] ReconciliationRule: tightened field length limits after the data quality review
* [discovery] ReconciliationResult: added schema, validation rules and search filters
* [change] ChangeRequest: added reconciliation support for discovery imports
* [change] MaintenanceWindow: added CSV export with the same filters as search
* [change] WorkOrder: improved search performance with composite indexes
* [change] FieldTask: added domain events for create, update, delete and status change
* [change] Technician: aligned enum values with the integration layer contract
* [assetmgmt] Asset: added tenant scoped unique business code
* [assetmgmt] AssetTag: added bulk create and status change endpoints
* [assetmgmt] PurchaseOrder: documented lifecycle statuses and allowed transitions
* [assetmgmt] Invoice: added optimistic locking via expected version
* [assetmgmt] DepreciationSchedule: added field catalogue endpoint for the UI
* [compliance] ComplianceRule: tightened field length limits after the data quality review
* [compliance] ComplianceCheck: added schema, validation rules and search filters
* [compliance] AuditRecord: added reconciliation support for discovery imports
* [compliance] ComplianceException: added CSV export with the same filters as search
* [compliance] PolicyDocument: improved search performance with composite indexes
* [alarm] AlarmDefinition: added domain events for create, update, delete and status change
* [alarm] AlarmInstance: aligned enum values with the integration layer contract
* [alarm] EventSource: added tenant scoped unique business code
* [alarm] SuppressionRule: added bulk create and status change endpoints
* [alarm] TicketLink: documented lifecycle statuses and allowed transitions
* [capacity] CapacityPlan: added optimistic locking via expected version
* [capacity] UtilizationSample: added field catalogue endpoint for the UI
* [capacity] ThresholdPolicy: tightened field length limits after the data quality review
* [capacity] ForecastModel: added schema, validation rules and search filters
* [capacity] Reservation: added reconciliation support for discovery imports
* [integration] IntegrationEndpoint: added CSV export with the same filters as search
* [integration] SyncJob: improved search performance with composite indexes
* [integration] SyncMapping: added domain events for create, update, delete and status change
* [integration] ImportBatch: aligned enum values with the integration layer contract
* [integration] WebhookSubscription: added tenant scoped unique business code
* [geography] Country: added CSV export with the same filters as search
* [geography] Region: improved search performance with composite indexes
* [geography] City: added domain events for create, update, delete and status change
* [geography] Site: aligned enum values with the integration layer contract
* [geography] Building: added tenant scoped unique business code
* [facility] Floor: added bulk create and status change endpoints
* [facility] Room: documented lifecycle statuses and allowed transitions
* [facility] Rack: added optimistic locking via expected version
* [facility] RackUnit: added field catalogue endpoint for the UI
* [facility] PowerFeed: tightened field length limits after the data quality review
* [device] NetworkDevice: added schema, validation rules and search filters
* [device] Chassis: added reconciliation support for discovery imports
* [device] LineCard: added CSV export with the same filters as search
* [device] PowerSupply: improved search performance with composite indexes
* [device] FanTray: added domain events for create, update, delete and status change
* [port] Port: aligned enum values with the integration layer contract
* [port] Interface: added tenant scoped unique business code
* [port] SubInterface: added bulk create and status change endpoints
* [port] LagGroup: documented lifecycle statuses and allowed transitions
* [port] Transceiver: added optimistic locking via expected version
* [cabling] Cable: added field catalogue endpoint for the UI
* [cabling] PatchPanel: tightened field length limits after the data quality review
