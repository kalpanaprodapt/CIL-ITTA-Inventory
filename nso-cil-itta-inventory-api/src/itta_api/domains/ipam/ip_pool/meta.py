"""Field catalogue for Ip Pool; drives the UI forms, tables and filters."""
from itta_core.models import EntityMeta, FieldMeta

ENTITY = "IpPool"
PATH = "ip-pools"

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
        name="subnetId",
        label="Subnet Id",
        type="reference",
        required=True,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="rangeStart",
        label="Range Start",
        type="string",
        required=True,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="rangeEnd",
        label="Range End",
        type="string",
        required=True,
        max_length=255,
        enum_values=(),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="poolType",
        label="Pool Type",
        type="enum",
        required=False,
        max_length=None,
        enum_values=("DYNAMIC", "STATIC", "NAT",),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="leaseSeconds",
        label="Lease Seconds",
        type="integer",
        required=False,
        max_length=None,
        enum_values=(),
        filterable=True,
        sortable=True,
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
    FieldMeta(
        name="lifecycleStage",
        label="Lifecycle Stage",
        type="enum",
        required=False,
        max_length=None,
        enum_values=("PLAN", "BUILD", "OPERATE", "RETIRE",),
        filterable=True,
        sortable=True,
    ),
    FieldMeta(
        name="sourceSystem",
        label="Source System",
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
    title="Ip Pool",
    domain="ipam",
    path=PATH,
    fields=FIELDS,
)


def field_names() -> list[str]:
    return [f.name for f in FIELDS]


def required_fields() -> list[str]:
    return [f.name for f in FIELDS if f.required]
