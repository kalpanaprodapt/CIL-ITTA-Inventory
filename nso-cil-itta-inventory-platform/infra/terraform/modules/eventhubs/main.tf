locals {
  topics = [
  "itta.inventory.geography.country",
  "itta.inventory.geography.region",
  "itta.inventory.geography.city",
  "itta.inventory.geography.site",
  "itta.inventory.geography.building",
  "itta.inventory.facility.floor",
  "itta.inventory.facility.room",
  "itta.inventory.facility.rack",
  "itta.inventory.facility.rackunit",
  "itta.inventory.facility.powerfeed",
  "itta.inventory.device.networkdevice",
  "itta.inventory.device.chassis",
  "itta.inventory.device.linecard",
  "itta.inventory.device.powersupply",
  "itta.inventory.device.fantray",
  "itta.inventory.port.port",
  "itta.inventory.port.interface",
  "itta.inventory.port.subinterface",
  "itta.inventory.port.laggroup",
  "itta.inventory.port.transceiver",
  "itta.inventory.cabling.cable",
  "itta.inventory.cabling.patchpanel",
  "itta.inventory.cabling.patchport",
  "itta.inventory.cabling.splice",
  "itta.inventory.cabling.fiberstrand",
  "itta.inventory.vendor.vendor",
  "itta.inventory.vendor.hardwaremodel",
  "itta.inventory.vendor.sku",
  "itta.inventory.vendor.supportcontract",
  "itta.inventory.vendor.warranty",
  "itta.inventory.software.softwareimage",
  "itta.inventory.software.softwareversion",
  "itta.inventory.software.patch",
  "itta.inventory.software.licensekey",
  "itta.inventory.software.licensepool",
  "itta.inventory.ipam.ipsubnet",
  "itta.inventory.ipam.ipaddress",
  "itta.inventory.ipam.ippool",
  "itta.inventory.ipam.vrfinstance",
  "itta.inventory.ipam.dnsrecord",
  "itta.inventory.switching.vlan",
  "itta.inventory.switching.vlanpool",
  "itta.inventory.switching.routetarget",
  "itta.inventory.switching.bgpsession",
  "itta.inventory.switching.ospfarea",
  "itta.inventory.circuit.circuit",
  "itta.inventory.circuit.circuitsegment",
  "itta.inventory.circuit.circuitprovider",
  "itta.inventory.circuit.circuittermination",
  "itta.inventory.circuit.circuitorder",
  "itta.inventory.topology.link",
  "itta.inventory.topology.topologynode",
  "itta.inventory.topology.topologyedge",
  "itta.inventory.topology.topologyview",
  "itta.inventory.topology.pathtrace",
  "itta.inventory.customer.customer",
  "itta.inventory.customer.customeraccount",
  "itta.inventory.customer.customercontact",
  "itta.inventory.customer.contract",
  "itta.inventory.customer.slaprofile",
  "itta.inventory.service.servicecatalogitem",
  "itta.inventory.service.serviceinstance",
  "itta.inventory.service.serviceorder",
  "itta.inventory.service.servicedependency",
  "itta.inventory.service.serviceendpoint",
  "itta.inventory.mpls.mplslsp",
  "itta.inventory.mpls.vpnservice",
  "itta.inventory.mpls.pseudowire",
  "itta.inventory.mpls.tunnelendpoint",
  "itta.inventory.mpls.qospolicy",
  "itta.inventory.optical.opticalchannel",
  "itta.inventory.optical.wdmsystem",
  "itta.inventory.optical.otnlink",
  "itta.inventory.optical.amplifier",
  "itta.inventory.optical.roadm",
  "itta.inventory.radio.radiounit",
  "itta.inventory.radio.basestation",
  "itta.inventory.radio.antenna",
  "itta.inventory.radio.sector",
  "itta.inventory.radio.backhaullink",
  "itta.inventory.virtual.vnf",
  "itta.inventory.virtual.hypervisor",
  "itta.inventory.virtual.virtualmachine",
  "itta.inventory.virtual.cloudaccount",
  "itta.inventory.virtual.k8scluster",
  "itta.inventory.discovery.discoveryjob",
  "itta.inventory.discovery.discoveryprofile",
  "itta.inventory.discovery.discoveredasset",
  "itta.inventory.discovery.reconciliationrule",
  "itta.inventory.discovery.reconciliationresult",
  "itta.inventory.change.changerequest",
  "itta.inventory.change.maintenancewindow",
  "itta.inventory.change.workorder",
  "itta.inventory.change.fieldtask",
  "itta.inventory.change.technician",
  "itta.inventory.assetmgmt.asset",
  "itta.inventory.assetmgmt.assettag",
  "itta.inventory.assetmgmt.purchaseorder",
  "itta.inventory.assetmgmt.invoice",
  "itta.inventory.assetmgmt.depreciationschedule",
  "itta.inventory.compliance.compliancerule",
  "itta.inventory.compliance.compliancecheck",
  "itta.inventory.compliance.auditrecord",
  "itta.inventory.compliance.complianceexception",
  "itta.inventory.compliance.policydocument",
  "itta.inventory.alarm.alarmdefinition",
  "itta.inventory.alarm.alarminstance",
  "itta.inventory.alarm.eventsource",
  "itta.inventory.alarm.suppressionrule",
  "itta.inventory.alarm.ticketlink",
  "itta.inventory.capacity.capacityplan",
  "itta.inventory.capacity.utilizationsample",
  "itta.inventory.capacity.thresholdpolicy",
  "itta.inventory.capacity.forecastmodel",
  "itta.inventory.capacity.reservation",
  "itta.inventory.integration.integrationendpoint",
  "itta.inventory.integration.syncjob",
  "itta.inventory.integration.syncmapping",
  "itta.inventory.integration.importbatch",
  "itta.inventory.integration.webhooksubscription",
  ]
}

resource "azurerm_eventhub_namespace" "this" {
  name                = var.name
  location            = var.location
  resource_group_name = var.resource_group_name
  sku                 = var.sku
  capacity            = var.capacity
  tags                = var.tags
}

resource "azurerm_eventhub" "topic" {
  for_each            = toset(local.topics)
  name                = each.value
  namespace_name      = azurerm_eventhub_namespace.this.name
  resource_group_name = var.resource_group_name
  partition_count     = var.partitions
  message_retention   = var.retention_days
}

resource "azurerm_eventhub_consumer_group" "integration" {
  for_each            = toset(local.topics)
  name                = "cil-integration"
  namespace_name      = azurerm_eventhub_namespace.this.name
  eventhub_name       = azurerm_eventhub.topic[each.value].name
  resource_group_name = var.resource_group_name
}
