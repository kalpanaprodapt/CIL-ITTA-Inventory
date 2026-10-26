# Changelog - nso-cil-itta-inventory-ui

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
* [cabling] PatchPort: added schema, validation rules and search filters
* [cabling] Splice: added reconciliation support for discovery imports
* [cabling] FiberStrand: added CSV export with the same filters as search
* [vendor] Vendor: improved search performance with composite indexes
* [vendor] HardwareModel: added domain events for create, update, delete and status change
* [vendor] Sku: aligned enum values with the integration layer contract
* [vendor] SupportContract: added tenant scoped unique business code
* [vendor] Warranty: added bulk create and status change endpoints
* [software] SoftwareImage: documented lifecycle statuses and allowed transitions
* [software] SoftwareVersion: added optimistic locking via expected version
* [software] Patch: added field catalogue endpoint for the UI
* [software] LicenseKey: tightened field length limits after the data quality review
* [software] LicensePool: added schema, validation rules and search filters
* [ipam] IpSubnet: added reconciliation support for discovery imports
* [ipam] IpAddress: added CSV export with the same filters as search
* [ipam] IpPool: improved search performance with composite indexes
* [ipam] VrfInstance: added domain events for create, update, delete and status change
* [ipam] DnsRecord: aligned enum values with the integration layer contract
* [switching] Vlan: added tenant scoped unique business code
* [switching] VlanPool: added bulk create and status change endpoints
* [switching] RouteTarget: documented lifecycle statuses and allowed transitions
* [switching] BgpSession: added optimistic locking via expected version
* [switching] OspfArea: added field catalogue endpoint for the UI
* [circuit] Circuit: tightened field length limits after the data quality review
* [circuit] CircuitSegment: added schema, validation rules and search filters
* [circuit] CircuitProvider: added reconciliation support for discovery imports
* [circuit] CircuitTermination: added CSV export with the same filters as search
* [circuit] CircuitOrder: improved search performance with composite indexes
* [topology] Link: added domain events for create, update, delete and status change
* [topology] TopologyNode: aligned enum values with the integration layer contract
* [topology] TopologyEdge: added tenant scoped unique business code
* [topology] TopologyView: added bulk create and status change endpoints
* [topology] PathTrace: documented lifecycle statuses and allowed transitions
* [customer] Customer: added optimistic locking via expected version
* [customer] CustomerAccount: added field catalogue endpoint for the UI
* [customer] CustomerContact: tightened field length limits after the data quality review
* [customer] Contract: added schema, validation rules and search filters
* [customer] SlaProfile: added reconciliation support for discovery imports
* [service] ServiceCatalogItem: added CSV export with the same filters as search
* [service] ServiceInstance: improved search performance with composite indexes
* [service] ServiceOrder: added domain events for create, update, delete and status change
* [service] ServiceDependency: aligned enum values with the integration layer contract
* [service] ServiceEndpoint: added tenant scoped unique business code
* [mpls] MplsLsp: added bulk create and status change endpoints
* [mpls] VpnService: documented lifecycle statuses and allowed transitions
* [mpls] PseudoWire: added optimistic locking via expected version
* [mpls] TunnelEndpoint: added field catalogue endpoint for the UI
* [mpls] QosPolicy: tightened field length limits after the data quality review
* [optical] OpticalChannel: added schema, validation rules and search filters
* [optical] WdmSystem: added reconciliation support for discovery imports
* [optical] OtnLink: added CSV export with the same filters as search
* [optical] Amplifier: improved search performance with composite indexes
* [optical] Roadm: added domain events for create, update, delete and status change
* [radio] RadioUnit: aligned enum values with the integration layer contract
* [radio] BaseStation: added tenant scoped unique business code
* [radio] Antenna: added bulk create and status change endpoints
* [radio] Sector: documented lifecycle statuses and allowed transitions
* [radio] BackhaulLink: added optimistic locking via expected version
* [virtual] Vnf: added field catalogue endpoint for the UI
* [virtual] Hypervisor: tightened field length limits after the data quality review
* [virtual] VirtualMachine: added schema, validation rules and search filters
* [virtual] CloudAccount: added reconciliation support for discovery imports
* [virtual] K8sCluster: added CSV export with the same filters as search
* [discovery] DiscoveryJob: improved search performance with composite indexes
* [discovery] DiscoveryProfile: added domain events for create, update, delete and status change
* [discovery] DiscoveredAsset: aligned enum values with the integration layer contract
* [discovery] ReconciliationRule: added tenant scoped unique business code
* [discovery] ReconciliationResult: added bulk create and status change endpoints
* [change] ChangeRequest: documented lifecycle statuses and allowed transitions
* [change] MaintenanceWindow: added optimistic locking via expected version
* [change] WorkOrder: added field catalogue endpoint for the UI
* [change] FieldTask: tightened field length limits after the data quality review
* [change] Technician: added schema, validation rules and search filters
* [assetmgmt] Asset: added reconciliation support for discovery imports
* [assetmgmt] AssetTag: added CSV export with the same filters as search
* [assetmgmt] PurchaseOrder: improved search performance with composite indexes
* [assetmgmt] Invoice: added domain events for create, update, delete and status change
* [assetmgmt] DepreciationSchedule: aligned enum values with the integration layer contract
* [compliance] ComplianceRule: added tenant scoped unique business code
* [compliance] ComplianceCheck: added bulk create and status change endpoints
* [compliance] AuditRecord: documented lifecycle statuses and allowed transitions
* [compliance] ComplianceException: added optimistic locking via expected version
* [compliance] PolicyDocument: added field catalogue endpoint for the UI
* [alarm] AlarmDefinition: tightened field length limits after the data quality review
* [alarm] AlarmInstance: added schema, validation rules and search filters
* [alarm] EventSource: added reconciliation support for discovery imports
* [alarm] SuppressionRule: added CSV export with the same filters as search
* [alarm] TicketLink: improved search performance with composite indexes
* [capacity] CapacityPlan: added domain events for create, update, delete and status change
* [capacity] UtilizationSample: aligned enum values with the integration layer contract
* [capacity] ThresholdPolicy: added tenant scoped unique business code
* [capacity] ForecastModel: added bulk create and status change endpoints
* [capacity] Reservation: documented lifecycle statuses and allowed transitions
* [integration] IntegrationEndpoint: added optimistic locking via expected version
* [integration] SyncJob: added field catalogue endpoint for the UI
* [integration] SyncMapping: tightened field length limits after the data quality review
* [integration] ImportBatch: added schema, validation rules and search filters
* [integration] WebhookSubscription: added reconciliation support for discovery imports
* [geography] Country: added optimistic locking via expected version
* [geography] Region: added field catalogue endpoint for the UI
* [geography] City: tightened field length limits after the data quality review
* [geography] Site: added schema, validation rules and search filters
* [geography] Building: added reconciliation support for discovery imports
* [facility] Floor: added CSV export with the same filters as search
* [facility] Room: improved search performance with composite indexes
* [facility] Rack: added domain events for create, update, delete and status change
* [facility] RackUnit: aligned enum values with the integration layer contract
* [facility] PowerFeed: added tenant scoped unique business code
* [device] NetworkDevice: added bulk create and status change endpoints
* [device] Chassis: documented lifecycle statuses and allowed transitions
* [device] LineCard: added optimistic locking via expected version
* [device] PowerSupply: added field catalogue endpoint for the UI
* [device] FanTray: tightened field length limits after the data quality review
* [port] Port: added schema, validation rules and search filters
* [port] Interface: added reconciliation support for discovery imports
* [port] SubInterface: added CSV export with the same filters as search
* [port] LagGroup: improved search performance with composite indexes
* [port] Transceiver: added domain events for create, update, delete and status change
* [cabling] Cable: aligned enum values with the integration layer contract
* [cabling] PatchPanel: added tenant scoped unique business code
* [cabling] PatchPort: added bulk create and status change endpoints
* [cabling] Splice: documented lifecycle statuses and allowed transitions
* [cabling] FiberStrand: added optimistic locking via expected version
* [vendor] Vendor: added field catalogue endpoint for the UI
* [vendor] HardwareModel: tightened field length limits after the data quality review
* [vendor] Sku: added schema, validation rules and search filters
* [vendor] SupportContract: added reconciliation support for discovery imports
* [vendor] Warranty: added CSV export with the same filters as search
* [software] SoftwareImage: improved search performance with composite indexes
* [software] SoftwareVersion: added domain events for create, update, delete and status change
* [software] Patch: aligned enum values with the integration layer contract
* [software] LicenseKey: added tenant scoped unique business code
* [software] LicensePool: added bulk create and status change endpoints
* [ipam] IpSubnet: documented lifecycle statuses and allowed transitions
* [ipam] IpAddress: added optimistic locking via expected version
* [ipam] IpPool: added field catalogue endpoint for the UI
* [ipam] VrfInstance: tightened field length limits after the data quality review
* [ipam] DnsRecord: added schema, validation rules and search filters
* [switching] Vlan: added reconciliation support for discovery imports
* [switching] VlanPool: added CSV export with the same filters as search
* [switching] RouteTarget: improved search performance with composite indexes
* [switching] BgpSession: added domain events for create, update, delete and status change
* [switching] OspfArea: aligned enum values with the integration layer contract
* [circuit] Circuit: added tenant scoped unique business code
* [circuit] CircuitSegment: added bulk create and status change endpoints
* [circuit] CircuitProvider: documented lifecycle statuses and allowed transitions
* [circuit] CircuitTermination: added optimistic locking via expected version
* [circuit] CircuitOrder: added field catalogue endpoint for the UI
* [topology] Link: tightened field length limits after the data quality review
* [topology] TopologyNode: added schema, validation rules and search filters
* [topology] TopologyEdge: added reconciliation support for discovery imports
* [topology] TopologyView: added CSV export with the same filters as search
* [topology] PathTrace: improved search performance with composite indexes
* [customer] Customer: added domain events for create, update, delete and status change
* [customer] CustomerAccount: aligned enum values with the integration layer contract
* [customer] CustomerContact: added tenant scoped unique business code
* [customer] Contract: added bulk create and status change endpoints
* [customer] SlaProfile: documented lifecycle statuses and allowed transitions
* [service] ServiceCatalogItem: added optimistic locking via expected version
* [service] ServiceInstance: added field catalogue endpoint for the UI
* [service] ServiceOrder: tightened field length limits after the data quality review
* [service] ServiceDependency: added schema, validation rules and search filters
* [service] ServiceEndpoint: added reconciliation support for discovery imports
* [mpls] MplsLsp: added CSV export with the same filters as search
* [mpls] VpnService: improved search performance with composite indexes
* [mpls] PseudoWire: added domain events for create, update, delete and status change
* [mpls] TunnelEndpoint: aligned enum values with the integration layer contract
* [mpls] QosPolicy: added tenant scoped unique business code
* [optical] OpticalChannel: added bulk create and status change endpoints
* [optical] WdmSystem: documented lifecycle statuses and allowed transitions
* [optical] OtnLink: added optimistic locking via expected version
* [optical] Amplifier: added field catalogue endpoint for the UI
* [optical] Roadm: tightened field length limits after the data quality review
* [radio] RadioUnit: added schema, validation rules and search filters
* [radio] BaseStation: added reconciliation support for discovery imports
* [radio] Antenna: added CSV export with the same filters as search
* [radio] Sector: improved search performance with composite indexes
* [radio] BackhaulLink: added domain events for create, update, delete and status change
* [virtual] Vnf: aligned enum values with the integration layer contract
* [virtual] Hypervisor: added tenant scoped unique business code
* [virtual] VirtualMachine: added bulk create and status change endpoints
* [virtual] CloudAccount: documented lifecycle statuses and allowed transitions
* [virtual] K8sCluster: added optimistic locking via expected version
* [discovery] DiscoveryJob: added field catalogue endpoint for the UI
* [discovery] DiscoveryProfile: tightened field length limits after the data quality review
* [discovery] DiscoveredAsset: added schema, validation rules and search filters
* [discovery] ReconciliationRule: added reconciliation support for discovery imports
* [discovery] ReconciliationResult: added CSV export with the same filters as search
* [change] ChangeRequest: improved search performance with composite indexes
* [change] MaintenanceWindow: added domain events for create, update, delete and status change
* [change] WorkOrder: aligned enum values with the integration layer contract
* [change] FieldTask: added tenant scoped unique business code
* [change] Technician: added bulk create and status change endpoints
* [assetmgmt] Asset: documented lifecycle statuses and allowed transitions
* [assetmgmt] AssetTag: added optimistic locking via expected version
* [assetmgmt] PurchaseOrder: added field catalogue endpoint for the UI
* [assetmgmt] Invoice: tightened field length limits after the data quality review
* [assetmgmt] DepreciationSchedule: added schema, validation rules and search filters
* [compliance] ComplianceRule: added reconciliation support for discovery imports
* [compliance] ComplianceCheck: added CSV export with the same filters as search
* [compliance] AuditRecord: improved search performance with composite indexes
* [compliance] ComplianceException: added domain events for create, update, delete and status change
* [compliance] PolicyDocument: aligned enum values with the integration layer contract
* [alarm] AlarmDefinition: added tenant scoped unique business code
* [alarm] AlarmInstance: added bulk create and status change endpoints
* [alarm] EventSource: documented lifecycle statuses and allowed transitions
* [alarm] SuppressionRule: added optimistic locking via expected version
* [alarm] TicketLink: added field catalogue endpoint for the UI
* [capacity] CapacityPlan: tightened field length limits after the data quality review
* [capacity] UtilizationSample: added schema, validation rules and search filters
* [capacity] ThresholdPolicy: added reconciliation support for discovery imports
* [capacity] ForecastModel: added CSV export with the same filters as search
* [capacity] Reservation: improved search performance with composite indexes
* [integration] IntegrationEndpoint: added domain events for create, update, delete and status change
* [integration] SyncJob: aligned enum values with the integration layer contract
* [integration] SyncMapping: added tenant scoped unique business code
* [integration] ImportBatch: added bulk create and status change endpoints
* [integration] WebhookSubscription: documented lifecycle statuses and allowed transitions
* [geography] Country: added domain events for create, update, delete and status change
* [geography] Region: aligned enum values with the integration layer contract
* [geography] City: added tenant scoped unique business code
* [geography] Site: added bulk create and status change endpoints
* [geography] Building: documented lifecycle statuses and allowed transitions
* [facility] Floor: added optimistic locking via expected version
* [facility] Room: added field catalogue endpoint for the UI
* [facility] Rack: tightened field length limits after the data quality review
* [facility] RackUnit: added schema, validation rules and search filters
* [facility] PowerFeed: added reconciliation support for discovery imports
* [device] NetworkDevice: added CSV export with the same filters as search
* [device] Chassis: improved search performance with composite indexes
* [device] LineCard: added domain events for create, update, delete and status change
* [device] PowerSupply: aligned enum values with the integration layer contract
* [device] FanTray: added tenant scoped unique business code
* [port] Port: added bulk create and status change endpoints
* [port] Interface: documented lifecycle statuses and allowed transitions
* [port] SubInterface: added optimistic locking via expected version
* [port] LagGroup: added field catalogue endpoint for the UI
* [port] Transceiver: tightened field length limits after the data quality review
* [cabling] Cable: added schema, validation rules and search filters
* [cabling] PatchPanel: added reconciliation support for discovery imports
* [cabling] PatchPort: added CSV export with the same filters as search
* [cabling] Splice: improved search performance with composite indexes
* [cabling] FiberStrand: added domain events for create, update, delete and status change
* [vendor] Vendor: aligned enum values with the integration layer contract
* [vendor] HardwareModel: added tenant scoped unique business code
* [vendor] Sku: added bulk create and status change endpoints
* [vendor] SupportContract: documented lifecycle statuses and allowed transitions
* [vendor] Warranty: added optimistic locking via expected version
* [software] SoftwareImage: added field catalogue endpoint for the UI
* [software] SoftwareVersion: tightened field length limits after the data quality review
* [software] Patch: added schema, validation rules and search filters
* [software] LicenseKey: added reconciliation support for discovery imports
* [software] LicensePool: added CSV export with the same filters as search
* [ipam] IpSubnet: improved search performance with composite indexes
* [ipam] IpAddress: added domain events for create, update, delete and status change
* [ipam] IpPool: aligned enum values with the integration layer contract
* [ipam] VrfInstance: added tenant scoped unique business code
* [ipam] DnsRecord: added bulk create and status change endpoints
* [switching] Vlan: documented lifecycle statuses and allowed transitions
* [switching] VlanPool: added optimistic locking via expected version
* [switching] RouteTarget: added field catalogue endpoint for the UI
* [switching] BgpSession: tightened field length limits after the data quality review
* [switching] OspfArea: added schema, validation rules and search filters
* [circuit] Circuit: added reconciliation support for discovery imports
* [circuit] CircuitSegment: added CSV export with the same filters as search
* [circuit] CircuitProvider: improved search performance with composite indexes
* [circuit] CircuitTermination: added domain events for create, update, delete and status change
* [circuit] CircuitOrder: aligned enum values with the integration layer contract
* [topology] Link: added tenant scoped unique business code
* [topology] TopologyNode: added bulk create and status change endpoints
* [topology] TopologyEdge: documented lifecycle statuses and allowed transitions
* [topology] TopologyView: added optimistic locking via expected version
* [topology] PathTrace: added field catalogue endpoint for the UI
* [customer] Customer: tightened field length limits after the data quality review
* [customer] CustomerAccount: added schema, validation rules and search filters
* [customer] CustomerContact: added reconciliation support for discovery imports
* [customer] Contract: added CSV export with the same filters as search
* [customer] SlaProfile: improved search performance with composite indexes
* [service] ServiceCatalogItem: added domain events for create, update, delete and status change
* [service] ServiceInstance: aligned enum values with the integration layer contract
* [service] ServiceOrder: added tenant scoped unique business code
* [service] ServiceDependency: added bulk create and status change endpoints
* [service] ServiceEndpoint: documented lifecycle statuses and allowed transitions
* [mpls] MplsLsp: added optimistic locking via expected version
* [mpls] VpnService: added field catalogue endpoint for the UI
* [mpls] PseudoWire: tightened field length limits after the data quality review
* [mpls] TunnelEndpoint: added schema, validation rules and search filters
* [mpls] QosPolicy: added reconciliation support for discovery imports
* [optical] OpticalChannel: added CSV export with the same filters as search
* [optical] WdmSystem: improved search performance with composite indexes
* [optical] OtnLink: added domain events for create, update, delete and status change
* [optical] Amplifier: aligned enum values with the integration layer contract
* [optical] Roadm: added tenant scoped unique business code
* [radio] RadioUnit: added bulk create and status change endpoints
* [radio] BaseStation: documented lifecycle statuses and allowed transitions
* [radio] Antenna: added optimistic locking via expected version
* [radio] Sector: added field catalogue endpoint for the UI
* [radio] BackhaulLink: tightened field length limits after the data quality review
* [virtual] Vnf: added schema, validation rules and search filters
* [virtual] Hypervisor: added reconciliation support for discovery imports
* [virtual] VirtualMachine: added CSV export with the same filters as search
* [virtual] CloudAccount: improved search performance with composite indexes
* [virtual] K8sCluster: added domain events for create, update, delete and status change
* [discovery] DiscoveryJob: aligned enum values with the integration layer contract
* [discovery] DiscoveryProfile: added tenant scoped unique business code
* [discovery] DiscoveredAsset: added bulk create and status change endpoints
* [discovery] ReconciliationRule: documented lifecycle statuses and allowed transitions
* [discovery] ReconciliationResult: added optimistic locking via expected version
* [change] ChangeRequest: added field catalogue endpoint for the UI
* [change] MaintenanceWindow: tightened field length limits after the data quality review
* [change] WorkOrder: added schema, validation rules and search filters
* [change] FieldTask: added reconciliation support for discovery imports
* [change] Technician: added CSV export with the same filters as search
* [assetmgmt] Asset: improved search performance with composite indexes
* [assetmgmt] AssetTag: added domain events for create, update, delete and status change
* [assetmgmt] PurchaseOrder: aligned enum values with the integration layer contract
* [assetmgmt] Invoice: added tenant scoped unique business code
* [assetmgmt] DepreciationSchedule: added bulk create and status change endpoints
* [compliance] ComplianceRule: documented lifecycle statuses and allowed transitions
* [compliance] ComplianceCheck: added optimistic locking via expected version
* [compliance] AuditRecord: added field catalogue endpoint for the UI
* [compliance] ComplianceException: tightened field length limits after the data quality review
* [compliance] PolicyDocument: added schema, validation rules and search filters
* [alarm] AlarmDefinition: added reconciliation support for discovery imports
* [alarm] AlarmInstance: added CSV export with the same filters as search
* [alarm] EventSource: improved search performance with composite indexes
* [alarm] SuppressionRule: added domain events for create, update, delete and status change
* [alarm] TicketLink: aligned enum values with the integration layer contract
* [capacity] CapacityPlan: added tenant scoped unique business code
* [capacity] UtilizationSample: added bulk create and status change endpoints
* [capacity] ThresholdPolicy: documented lifecycle statuses and allowed transitions
* [capacity] ForecastModel: added optimistic locking via expected version
* [capacity] Reservation: added field catalogue endpoint for the UI
* [integration] IntegrationEndpoint: tightened field length limits after the data quality review
* [integration] SyncJob: added schema, validation rules and search filters
* [integration] SyncMapping: added reconciliation support for discovery imports
* [integration] ImportBatch: added CSV export with the same filters as search
* [integration] WebhookSubscription: improved search performance with composite indexes
* [geography] Country: tightened field length limits after the data quality review
* [geography] Region: added schema, validation rules and search filters
* [geography] City: added reconciliation support for discovery imports
* [geography] Site: added CSV export with the same filters as search
* [geography] Building: improved search performance with composite indexes
* [facility] Floor: added domain events for create, update, delete and status change
* [facility] Room: aligned enum values with the integration layer contract
* [facility] Rack: added tenant scoped unique business code
* [facility] RackUnit: added bulk create and status change endpoints
* [facility] PowerFeed: documented lifecycle statuses and allowed transitions
* [device] NetworkDevice: added optimistic locking via expected version
* [device] Chassis: added field catalogue endpoint for the UI
* [device] LineCard: tightened field length limits after the data quality review
* [device] PowerSupply: added schema, validation rules and search filters
* [device] FanTray: added reconciliation support for discovery imports
* [port] Port: added CSV export with the same filters as search
* [port] Interface: improved search performance with composite indexes
* [port] SubInterface: added domain events for create, update, delete and status change
* [port] LagGroup: aligned enum values with the integration layer contract
* [port] Transceiver: added tenant scoped unique business code
* [cabling] Cable: added bulk create and status change endpoints
* [cabling] PatchPanel: documented lifecycle statuses and allowed transitions
* [cabling] PatchPort: added optimistic locking via expected version
* [cabling] Splice: added field catalogue endpoint for the UI
* [cabling] FiberStrand: tightened field length limits after the data quality review
* [vendor] Vendor: added schema, validation rules and search filters
* [vendor] HardwareModel: added reconciliation support for discovery imports
* [vendor] Sku: added CSV export with the same filters as search
* [vendor] SupportContract: improved search performance with composite indexes
* [vendor] Warranty: added domain events for create, update, delete and status change
* [software] SoftwareImage: aligned enum values with the integration layer contract
* [software] SoftwareVersion: added tenant scoped unique business code
* [software] Patch: added bulk create and status change endpoints
* [software] LicenseKey: documented lifecycle statuses and allowed transitions
* [software] LicensePool: added optimistic locking via expected version
* [ipam] IpSubnet: added field catalogue endpoint for the UI
* [ipam] IpAddress: tightened field length limits after the data quality review
* [ipam] IpPool: added schema, validation rules and search filters
* [ipam] VrfInstance: added reconciliation support for discovery imports
* [ipam] DnsRecord: added CSV export with the same filters as search
* [switching] Vlan: improved search performance with composite indexes
* [switching] VlanPool: added domain events for create, update, delete and status change
* [switching] RouteTarget: aligned enum values with the integration layer contract
* [switching] BgpSession: added tenant scoped unique business code
* [switching] OspfArea: added bulk create and status change endpoints
* [circuit] Circuit: documented lifecycle statuses and allowed transitions
* [circuit] CircuitSegment: added optimistic locking via expected version
* [circuit] CircuitProvider: added field catalogue endpoint for the UI
* [circuit] CircuitTermination: tightened field length limits after the data quality review
* [circuit] CircuitOrder: added schema, validation rules and search filters
* [topology] Link: added reconciliation support for discovery imports
* [topology] TopologyNode: added CSV export with the same filters as search
* [topology] TopologyEdge: improved search performance with composite indexes
* [topology] TopologyView: added domain events for create, update, delete and status change
* [topology] PathTrace: aligned enum values with the integration layer contract
* [customer] Customer: added tenant scoped unique business code
* [customer] CustomerAccount: added bulk create and status change endpoints
* [customer] CustomerContact: documented lifecycle statuses and allowed transitions
* [customer] Contract: added optimistic locking via expected version
* [customer] SlaProfile: added field catalogue endpoint for the UI
* [service] ServiceCatalogItem: tightened field length limits after the data quality review
* [service] ServiceInstance: added schema, validation rules and search filters
* [service] ServiceOrder: added reconciliation support for discovery imports
* [service] ServiceDependency: added CSV export with the same filters as search
* [service] ServiceEndpoint: improved search performance with composite indexes
* [mpls] MplsLsp: added domain events for create, update, delete and status change
* [mpls] VpnService: aligned enum values with the integration layer contract
