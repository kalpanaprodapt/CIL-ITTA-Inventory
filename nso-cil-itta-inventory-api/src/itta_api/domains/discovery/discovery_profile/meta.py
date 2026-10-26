"""Field catalogue for Discovery Profile; drives the UI forms, tables and filters."""
from itta_core.models import EntityMeta, FieldMeta

ENTITY = "DiscoveryProfile"
PATH = "discovery-profiles"

FIELDS: tuple[FieldMeta, ...] = (
    FieldMeta(
        name="code",
        label="Code",
        type="string",
        required=True,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="name",
        label="Name",
        type="string",
        required=True,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="description",
        label="Description",
        type="text",
        required=False,
        max_length=4000,
        enum_values=(),
        filterable=False,
        sortable=False,
    ),
    FieldMeta(
        name="status",
        label="Status",
        type="enum",
        required=True,
        max_length=None,
        enum_values=("PLANNED", "ACTIVE", "MAINTENANCE", "DECOMMISSIONED",),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="externalRef",
        label="External Ref",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="profileName",
        label="Profile Name",
        type="string",
        required=True,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="protocol",
        label="Protocol",
        type="enum",
        required=False,
        max_length=None,
        enum_values=("SNMP", "SSH", "NETCONF", "GNMI", "REST",),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="credentialRef",
        label="Credential Ref",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="scopeCidr",
        label="Scope Cidr",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="scheduleCron",
        label="Schedule Cron",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="costCenter",
        label="Cost Center",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="notes",
        label="Notes",
        type="text",
        required=False,
        max_length=4000,
        enum_values=(),
        filterable=False,
        sortable=False,
    ),
    FieldMeta(
        name="effectiveFrom",
        label="Effective From",
        type="date",
        required=False,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="effectiveTo",
        label="Effective To",
        type="date",
        required=False,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="labels",
        label="Labels",
        type="string",
        required=False,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
)

META = EntityMeta(
    entity=ENTITY,
    title="Discovery Profile",
    domain="discovery",
    path=PATH,
    fields=FIELDS,
)


def field_names() -> list[str]:
    return [f.name for f in FIELDS]


def required_fields() -> list[str]:
    return [f.name for f in FIELDS if f.required]
