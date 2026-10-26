export interface NavEntity {
  name: string;
  title: string;
  path: string;
}

export interface NavDomain {
  domain: string;
  title: string;
  entities: NavEntity[];
}

export const NAVIGATION: NavDomain[] = [
  {
    "domain": "geography",
    "title": "Geography",
    "entities": [
      {
        "name": "Country",
        "title": "Country",
        "path": "countries"
      },
      {
        "name": "Region",
        "title": "Region",
        "path": "regions"
      },
      {
        "name": "City",
        "title": "City",
        "path": "cities"
      },
      {
        "name": "Site",
        "title": "Site",
        "path": "sites"
      },
      {
        "name": "Building",
        "title": "Building",
        "path": "buildings"
      }
    ]
  },
  {
    "domain": "facility",
    "title": "Facility",
    "entities": [
      {
        "name": "Floor",
        "title": "Floor",
        "path": "floors"
      },
      {
        "name": "Room",
        "title": "Room",
        "path": "rooms"
      },
      {
        "name": "Rack",
        "title": "Rack",
        "path": "racks"
      },
      {
        "name": "RackUnit",
        "title": "Rack Unit",
        "path": "rack-units"
      },
      {
        "name": "PowerFeed",
        "title": "Power Feed",
        "path": "power-feeds"
      }
    ]
  },
  {
    "domain": "device",
    "title": "Network Device",
    "entities": [
      {
        "name": "NetworkDevice",
        "title": "Network Device",
        "path": "network-devices"
      },
      {
        "name": "Chassis",
        "title": "Chassis",
        "path": "chassises"
      },
      {
        "name": "LineCard",
        "title": "Line Card",
        "path": "line-cards"
      },
      {
        "name": "PowerSupply",
        "title": "Power Supply",
        "path": "power-supplies"
      },
      {
        "name": "FanTray",
        "title": "Fan Tray",
        "path": "fan-traies"
      }
    ]
  },
  {
    "domain": "port",
    "title": "Port and Interface",
    "entities": [
      {
        "name": "Port",
        "title": "Port",
        "path": "ports"
      },
      {
        "name": "Interface",
        "title": "Interface",
        "path": "interfaces"
      },
      {
        "name": "SubInterface",
        "title": "Sub Interface",
        "path": "sub-interfaces"
      },
      {
        "name": "LagGroup",
        "title": "Lag Group",
        "path": "lag-groups"
      },
      {
        "name": "Transceiver",
        "title": "Transceiver",
        "path": "transceivers"
      }
    ]
  },
  {
    "domain": "cabling",
    "title": "Cabling",
    "entities": [
      {
        "name": "Cable",
        "title": "Cable",
        "path": "cables"
      },
      {
        "name": "PatchPanel",
        "title": "Patch Panel",
        "path": "patch-panels"
      },
      {
        "name": "PatchPort",
        "title": "Patch Port",
        "path": "patch-ports"
      },
      {
        "name": "Splice",
        "title": "Splice",
        "path": "splices"
      },
      {
        "name": "FiberStrand",
        "title": "Fiber Strand",
        "path": "fiber-strands"
      }
    ]
  },
  {
    "domain": "vendor",
    "title": "Vendor and Model",
    "entities": [
      {
        "name": "Vendor",
        "title": "Vendor",
        "path": "vendors"
      },
      {
        "name": "HardwareModel",
        "title": "Hardware Model",
        "path": "hardware-models"
      },
      {
        "name": "Sku",
        "title": "Sku",
        "path": "skus"
      },
      {
        "name": "SupportContract",
        "title": "Support Contract",
        "path": "support-contracts"
      },
      {
        "name": "Warranty",
        "title": "Warranty",
        "path": "warranties"
      }
    ]
  },
  {
    "domain": "software",
    "title": "Software and Licensing",
    "entities": [
      {
        "name": "SoftwareImage",
        "title": "Software Image",
        "path": "software-images"
      },
      {
        "name": "SoftwareVersion",
        "title": "Software Version",
        "path": "software-versions"
      },
      {
        "name": "Patch",
        "title": "Patch",
        "path": "patchs"
      },
      {
        "name": "LicenseKey",
        "title": "License Key",
        "path": "license-keies"
      },
      {
        "name": "LicensePool",
        "title": "License Pool",
        "path": "license-pools"
      }
    ]
  },
  {
    "domain": "ipam",
    "title": "IP Management",
    "entities": [
      {
        "name": "IpSubnet",
        "title": "Ip Subnet",
        "path": "ip-subnets"
      },
      {
        "name": "IpAddress",
        "title": "Ip Address",
        "path": "ip-addresses"
      },
      {
        "name": "IpPool",
        "title": "Ip Pool",
        "path": "ip-pools"
      },
      {
        "name": "VrfInstance",
        "title": "Vrf Instance",
        "path": "vrf-instances"
      },
      {
        "name": "DnsRecord",
        "title": "Dns Record",
        "path": "dns-records"
      }
    ]
  },
  {
    "domain": "switching",
    "title": "L2 and L3 Routing",
    "entities": [
      {
        "name": "Vlan",
        "title": "Vlan",
        "path": "vlans"
      },
      {
        "name": "VlanPool",
        "title": "Vlan Pool",
        "path": "vlan-pools"
      },
      {
        "name": "RouteTarget",
        "title": "Route Target",
        "path": "route-targets"
      },
      {
        "name": "BgpSession",
        "title": "Bgp Session",
        "path": "bgp-sessions"
      },
      {
        "name": "OspfArea",
        "title": "Ospf Area",
        "path": "ospf-areas"
      }
    ]
  },
  {
    "domain": "circuit",
    "title": "Circuits",
    "entities": [
      {
        "name": "Circuit",
        "title": "Circuit",
        "path": "circuits"
      },
      {
        "name": "CircuitSegment",
        "title": "Circuit Segment",
        "path": "circuit-segments"
      },
      {
        "name": "CircuitProvider",
        "title": "Circuit Provider",
        "path": "circuit-providers"
      },
      {
        "name": "CircuitTermination",
        "title": "Circuit Termination",
        "path": "circuit-terminations"
      },
      {
        "name": "CircuitOrder",
        "title": "Circuit Order",
        "path": "circuit-orders"
      }
    ]
  },
  {
    "domain": "topology",
    "title": "Topology and Links",
    "entities": [
      {
        "name": "Link",
        "title": "Link",
        "path": "links"
      },
      {
        "name": "TopologyNode",
        "title": "Topology Node",
        "path": "topology-nodes"
      },
      {
        "name": "TopologyEdge",
        "title": "Topology Edge",
        "path": "topology-edges"
      },
      {
        "name": "TopologyView",
        "title": "Topology View",
        "path": "topology-views"
      },
      {
        "name": "PathTrace",
        "title": "Path Trace",
        "path": "path-traces"
      }
    ]
  },
  {
    "domain": "customer",
    "title": "Customers and SLA",
    "entities": [
      {
        "name": "Customer",
        "title": "Customer",
        "path": "customers"
      },
      {
        "name": "CustomerAccount",
        "title": "Customer Account",
        "path": "customer-accounts"
      },
      {
        "name": "CustomerContact",
        "title": "Customer Contact",
        "path": "customer-contacts"
      },
      {
        "name": "Contract",
        "title": "Contract",
        "path": "contracts"
      },
      {
        "name": "SlaProfile",
        "title": "Sla Profile",
        "path": "sla-profiles"
      }
    ]
  },
  {
    "domain": "service",
    "title": "Services",
    "entities": [
      {
        "name": "ServiceCatalogItem",
        "title": "Service Catalog Item",
        "path": "service-catalog-items"
      },
      {
        "name": "ServiceInstance",
        "title": "Service Instance",
        "path": "service-instances"
      },
      {
        "name": "ServiceOrder",
        "title": "Service Order",
        "path": "service-orders"
      },
      {
        "name": "ServiceDependency",
        "title": "Service Dependency",
        "path": "service-dependencies"
      },
      {
        "name": "ServiceEndpoint",
        "title": "Service Endpoint",
        "path": "service-endpoints"
      }
    ]
  },
  {
    "domain": "mpls",
    "title": "MPLS and VPN",
    "entities": [
      {
        "name": "MplsLsp",
        "title": "Mpls Lsp",
        "path": "mpls-lsps"
      },
      {
        "name": "VpnService",
        "title": "Vpn Service",
        "path": "vpn-services"
      },
      {
        "name": "PseudoWire",
        "title": "Pseudo Wire",
        "path": "pseudo-wires"
      },
      {
        "name": "TunnelEndpoint",
        "title": "Tunnel Endpoint",
        "path": "tunnel-endpoints"
      },
      {
        "name": "QosPolicy",
        "title": "Qos Policy",
        "path": "qos-policies"
      }
    ]
  },
  {
    "domain": "optical",
    "title": "Optical",
    "entities": [
      {
        "name": "OpticalChannel",
        "title": "Optical Channel",
        "path": "optical-channels"
      },
      {
        "name": "WdmSystem",
        "title": "Wdm System",
        "path": "wdm-systems"
      },
      {
        "name": "OtnLink",
        "title": "Otn Link",
        "path": "otn-links"
      },
      {
        "name": "Amplifier",
        "title": "Amplifier",
        "path": "amplifiers"
      },
      {
        "name": "Roadm",
        "title": "Roadm",
        "path": "roadms"
      }
    ]
  },
  {
    "domain": "radio",
    "title": "Radio and Mobile",
    "entities": [
      {
        "name": "RadioUnit",
        "title": "Radio Unit",
        "path": "radio-units"
      },
      {
        "name": "BaseStation",
        "title": "Base Station",
        "path": "base-stations"
      },
      {
        "name": "Antenna",
        "title": "Antenna",
        "path": "antennas"
      },
      {
        "name": "Sector",
        "title": "Sector",
        "path": "sectors"
      },
      {
        "name": "BackhaulLink",
        "title": "Backhaul Link",
        "path": "backhaul-links"
      }
    ]
  },
  {
    "domain": "virtual",
    "title": "Virtual and Cloud",
    "entities": [
      {
        "name": "Vnf",
        "title": "Vnf",
        "path": "vnfs"
      },
      {
        "name": "Hypervisor",
        "title": "Hypervisor",
        "path": "hypervisors"
      },
      {
        "name": "VirtualMachine",
        "title": "Virtual Machine",
        "path": "virtual-machines"
      },
      {
        "name": "CloudAccount",
        "title": "Cloud Account",
        "path": "cloud-accounts"
      },
      {
        "name": "K8sCluster",
        "title": "K8s Cluster",
        "path": "k8s-clusters"
      }
    ]
  },
  {
    "domain": "discovery",
    "title": "Discovery and Reconciliation",
    "entities": [
      {
        "name": "DiscoveryJob",
        "title": "Discovery Job",
        "path": "discovery-jobs"
      },
      {
        "name": "DiscoveryProfile",
        "title": "Discovery Profile",
        "path": "discovery-profiles"
      },
      {
        "name": "DiscoveredAsset",
        "title": "Discovered Asset",
        "path": "discovered-assets"
      },
      {
        "name": "ReconciliationRule",
        "title": "Reconciliation Rule",
        "path": "reconciliation-rules"
      },
      {
        "name": "ReconciliationResult",
        "title": "Reconciliation Result",
        "path": "reconciliation-results"
      }
    ]
  },
  {
    "domain": "change",
    "title": "Change and Work Management",
    "entities": [
      {
        "name": "ChangeRequest",
        "title": "Change Request",
        "path": "change-requests"
      },
      {
        "name": "MaintenanceWindow",
        "title": "Maintenance Window",
        "path": "maintenance-windows"
      },
      {
        "name": "WorkOrder",
        "title": "Work Order",
        "path": "work-orders"
      },
      {
        "name": "FieldTask",
        "title": "Field Task",
        "path": "field-tasks"
      },
      {
        "name": "Technician",
        "title": "Technician",
        "path": "technicians"
      }
    ]
  },
  {
    "domain": "assetmgmt",
    "title": "Asset and Finance",
    "entities": [
      {
        "name": "Asset",
        "title": "Asset",
        "path": "assets"
      },
      {
        "name": "AssetTag",
        "title": "Asset Tag",
        "path": "asset-tags"
      },
      {
        "name": "PurchaseOrder",
        "title": "Purchase Order",
        "path": "purchase-orders"
      },
      {
        "name": "Invoice",
        "title": "Invoice",
        "path": "invoices"
      },
      {
        "name": "DepreciationSchedule",
        "title": "Depreciation Schedule",
        "path": "depreciation-schedules"
      }
    ]
  },
  {
    "domain": "compliance",
    "title": "Compliance and Audit",
    "entities": [
      {
        "name": "ComplianceRule",
        "title": "Compliance Rule",
        "path": "compliance-rules"
      },
      {
        "name": "ComplianceCheck",
        "title": "Compliance Check",
        "path": "compliance-checks"
      },
      {
        "name": "AuditRecord",
        "title": "Audit Record",
        "path": "audit-records"
      },
      {
        "name": "ComplianceException",
        "title": "Compliance Exception",
        "path": "compliance-exceptions"
      },
      {
        "name": "PolicyDocument",
        "title": "Policy Document",
        "path": "policy-documents"
      }
    ]
  },
  {
    "domain": "alarm",
    "title": "Alarms and Events",
    "entities": [
      {
        "name": "AlarmDefinition",
        "title": "Alarm Definition",
        "path": "alarm-definitions"
      },
      {
        "name": "AlarmInstance",
        "title": "Alarm Instance",
        "path": "alarm-instances"
      },
      {
        "name": "EventSource",
        "title": "Event Source",
        "path": "event-sources"
      },
      {
        "name": "SuppressionRule",
        "title": "Suppression Rule",
        "path": "suppression-rules"
      },
      {
        "name": "TicketLink",
        "title": "Ticket Link",
        "path": "ticket-links"
      }
    ]
  },
  {
    "domain": "capacity",
    "title": "Capacity Planning",
    "entities": [
      {
        "name": "CapacityPlan",
        "title": "Capacity Plan",
        "path": "capacity-plans"
      },
      {
        "name": "UtilizationSample",
        "title": "Utilization Sample",
        "path": "utilization-samples"
      },
      {
        "name": "ThresholdPolicy",
        "title": "Threshold Policy",
        "path": "threshold-policies"
      },
      {
        "name": "ForecastModel",
        "title": "Forecast Model",
        "path": "forecast-models"
      },
      {
        "name": "Reservation",
        "title": "Reservation",
        "path": "reservations"
      }
    ]
  },
  {
    "domain": "integration",
    "title": "Integration Layer",
    "entities": [
      {
        "name": "IntegrationEndpoint",
        "title": "Integration Endpoint",
        "path": "integration-endpoints"
      },
      {
        "name": "SyncJob",
        "title": "Sync Job",
        "path": "sync-jobs"
      },
      {
        "name": "SyncMapping",
        "title": "Sync Mapping",
        "path": "sync-mappings"
      },
      {
        "name": "ImportBatch",
        "title": "Import Batch",
        "path": "import-batchs"
      },
      {
        "name": "WebhookSubscription",
        "title": "Webhook Subscription",
        "path": "webhook-subscriptions"
      }
    ]
  }
];

export function findDomain(domain: string): NavDomain | undefined {
  return NAVIGATION.find((d) => d.domain === domain);
}
